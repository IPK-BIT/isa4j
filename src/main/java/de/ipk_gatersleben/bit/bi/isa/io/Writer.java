/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa.io;

import de.ipk_gatersleben.bit.bi.isa.constants.Props;
import de.ipk_gatersleben.bit.bi.isa.util.LoggerUtil;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.util.concurrent.CountDownLatch;

/**
 * This is a helper class for writing files and streams. The constructor need an
 * input and an output. Input is a pipedStream and the output can be a file, a
 * path or a outputStream.
 *
 * @author liufe, arendd
 */
public class Writer {

	/**
	 * The size of the buffer, how many byte can it read at one time
	 */
	private final int BUFFER_SIZE = 1024;

	/**
	 * OutputStream to write a file
	 */
	private OutputStream outputStream;

	/**
	 * InputStream to write ISA data
	 */
	private PipedInputStream inputStream;

	/**
	 * CountDownLatch to control the read/write threads
	 */
	private CountDownLatch latch;

	/**
	 * Define if the data will be written at the end of the file
	 */
	private boolean append = true;

	/**
	 * The file to write the ISA data.
	 */
	private File outputFile;

	/**
	 * Function to write the ISA content into a file
	 *
	 * @param append define if the content should be append at the end of the file
	 *               or overwrite
	 * @return true if write operation was successful, else if failed
	 */
	public boolean writeToFile(boolean append) {
		this.append = append;
		try {
			LoggerUtil.logger.info("write start: " + outputFile.getName());
			if (!outputFile.exists() && !outputFile.createNewFile()) {
				return false;
			}
			WritePipedInputToFileThread readFromPipe = new WritePipedInputToFileThread(inputStream);
			ThreadPool.execute(readFromPipe);

			latch.await();

			ThreadPool.overallCountDownLatchDown();
			LoggerUtil.logger.info("write finish: " + outputFile.getName());
			return true;
		} catch (IOException | InterruptedException e) {
			LoggerUtil.logger.error(e.getMessage());
			return false;
		}
	}

	/**
	 * Function to write the ISA content into a stream
	 *
	 * @return true if write operation was successful, else if failed
	 */
	public boolean writeToStream() {
		try {
			WritePipedInputToStreamThread readFromPipe = new WritePipedInputToStreamThread(inputStream);
			ThreadPool.execute(readFromPipe);

			latch.await();

			ThreadPool.overallCountDownLatchDown();
			LoggerUtil.logger.info("write finish: Stream");
			ThreadPool.shutdown();
			return true;
		} catch (InterruptedException e) {
			LoggerUtil.logger.error(e.getMessage());
			return false;
		}
	}

	/**
	 * Constructor with a defined output path
	 *
	 * @param outputPath  path of the output file
	 * @param inputStream the input stream
	 * @param latch       the {@link CountDownLatch} to finish the write process
	 */
	public Writer(Path outputPath, PipedInputStream inputStream, CountDownLatch latch) {

		this.inputStream = inputStream;
		this.outputFile = outputPath.toFile();
		this.latch = latch;
	}

	/**
	 * Constructor with a defined output file
	 *
	 * @param outputstream the output file
	 * @param inputStream  the PipedInputStream
	 * @param latch        the {@link CountDownLatch} to finish the write process
	 */
	public Writer(File outputstream, PipedInputStream inputStream, CountDownLatch latch) {
		this.outputFile = outputstream;
		this.inputStream = inputStream;
		this.latch = latch;
	}

	/**
	 * Constructor with a defined output stream
	 *
	 * @param outputStream the outputStream
	 * @param inputStream  the PipedInputStream
	 * @param latch        the {@link CountDownLatch} to finish the write process
	 */
	public Writer(OutputStream outputStream, PipedInputStream inputStream, CountDownLatch latch) {
		this.outputStream = outputStream;
		this.inputStream = inputStream;
		this.latch = latch;
	}

	/**
	 * The thread to read data from the pipedInputStream and write to file
	 */
	private class WritePipedInputToFileThread extends Thread {

		PipedInputStream pipedInputStream;

		private WritePipedInputToFileThread(PipedInputStream pipedInputStream) {
			this.pipedInputStream = pipedInputStream;
		}

		@Override
		public void run() {
			try {
				FileOutputStream osFile = new FileOutputStream(outputFile, append);

				FileChannel channel = osFile.getChannel();
				OutputStreamWriter os = new OutputStreamWriter(osFile, Props.DEFAULT_CHARSET);
				FileLock lock;
				while (true) {
					try {
						lock = channel.tryLock();
						break;
					} catch (Exception e) {
						LoggerUtil.logger.info("Other thread use this file!");
						try {
							sleep(1000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
				}
				byte[] buffer = new byte[BUFFER_SIZE];
				int len;
				while ((len = pipedInputStream.read(buffer)) != -1) {
					LoggerUtil.logger.debug("read from pipedstream:" + outputFile.getName());
					LoggerUtil.logger
							.trace("Read the content from pipe: " + new String(buffer, Props.DEFAULT_CHARSET));
					os.write(new String(buffer, 0, len, Props.DEFAULT_CHARSET));
					os.flush();
				}
				if (lock != null) {
					lock.release();
				}
				channel.close();
				os.close();
				latch.countDown();
				pipedInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * The thread to read data from the pipedInputStream and write to the output
	 * stream
	 */
	private class WritePipedInputToStreamThread extends Thread {

		PipedInputStream pis;

		private WritePipedInputToStreamThread(PipedInputStream pipedInputStream) {
			this.pis = pipedInputStream;
		}

		@Override
		public void run() {
			try {
				byte[] buffer = new byte[BUFFER_SIZE];
				int len;
				while ((len = pis.read(buffer)) != -1) {
					LoggerUtil.logger
							.trace("Read the content from pipe: " + new String(buffer, Props.DEFAULT_CHARSET));
					outputStream.write(buffer, 0, len);
					outputStream.flush();
				}

			} catch (IOException e) {
				LoggerUtil.logger.error("outputStream can not write!");
				e.printStackTrace();
			} finally {
				latch.countDown();
				try {
					pis.close();
//					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
