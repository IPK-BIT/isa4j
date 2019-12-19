/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa.io;

import de.ipk_gatersleben.bit.bi.isa.Investigation;
import de.ipk_gatersleben.bit.bi.isa4j.Assay;
import de.ipk_gatersleben.bit.bi.isa4j.Study;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class to handle the overall {@link ExecutorService} of the ISA4j API
 *
 * @author liufe, arendd
 */
public class ThreadPool {

    /**
     * The {@link ExecutorService} for the whole API
     */
    private static ExecutorService overallExecutorService = Executors.newCachedThreadPool();

    /**
     * {@link CountDownLatch} for all documents to write ({@link Investigation},
     * {@link Study} and {@link Assay})
     */
    private static CountDownLatch overallCountDownLatch;

    /**
     * Calculate the initial size of the overall {@link CountDownLatch} before
     * starting to write ISATab files
     *
     * @param investigation the {@link Investigation} to write
     */
    public static void initial(Investigation investigation) {
        int n = 1;

        for (int i = 0; i < investigation.getStudies().size(); i++) {
            n++;
            n = n + investigation.getStudies().get(i).getAssays().size();

        }
        overallCountDownLatch = new CountDownLatch(n);

        start();
    }

    /**
     * Close the overall {@link ExecutorService}
     */
    public static void shutdown() {
        overallExecutorService.shutdown();
    }

    /**
     * Initiate the overall {@link ExecutorService};
     */
    public static void start() {
        if (overallExecutorService.isShutdown()) {
            overallExecutorService = Executors.newCachedThreadPool();
        }
    }

    /**
     * Run a thread in the {@link ExecutorService}
     *
     * @param thread to run
     */
    public static void execute(Runnable thread) {
        overallExecutorService.execute(thread);
    }

    /**
     * Count down the overall {@link CountDownLatch}
     */
    public static void overallCountDownLatchDown() {
        if (overallCountDownLatch != null) {
            overallCountDownLatch.countDown();
        }
    }

    /**
     * Call await() function for overall {@link CountDownLatch}
     *
     * @throws InterruptedException
     */
    public static void overallCountDownLatchAwait() throws InterruptedException {
        if (overallCountDownLatch != null) {
            overallCountDownLatch.await();
        }
    }
}
