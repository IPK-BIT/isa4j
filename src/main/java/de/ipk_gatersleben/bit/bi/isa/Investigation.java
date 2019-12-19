/**
 * Copyright (c) 2019 Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU GPLv3 license (https://www.gnu.org/licenses/gpl-3.0.en.html)
 *
 * Contributors:
 *      Leibniz Institute of Plant Genetics and Crop Plant Research (IPK), Gatersleben, Germany
 */
package de.ipk_gatersleben.bit.bi.isa;

import de.ipk_gatersleben.bit.bi.isa.components.*;
import de.ipk_gatersleben.bit.bi.isa.constants.InvestigationAttribute;
import de.ipk_gatersleben.bit.bi.isa.constants.PartOfEntity;
import de.ipk_gatersleben.bit.bi.isa.constants.Props;
import de.ipk_gatersleben.bit.bi.isa.constants.Symbol;
import de.ipk_gatersleben.bit.bi.isa.io.ThreadPool;
import de.ipk_gatersleben.bit.bi.isa.io.Writer;
import de.ipk_gatersleben.bit.bi.isa.util.LoggerUtil;
import de.ipk_gatersleben.bit.bi.isa4j.Assay;
import de.ipk_gatersleben.bit.bi.isa4j.Study;
import de.ipk_gatersleben.bit.bi.isa4j.components.Comment;
import de.ipk_gatersleben.bit.bi.isa4j.components.Person;
import de.ipk_gatersleben.bit.bi.isa4j.components.DesignDescriptor;
import de.ipk_gatersleben.bit.bi.isa4j.components.Factor;
import de.ipk_gatersleben.bit.bi.isa4j.components.Ontology;
import de.ipk_gatersleben.bit.bi.isa4j.components.OntologyAnnotation;
import de.ipk_gatersleben.bit.bi.isa4j.components.Protocol;
import de.ipk_gatersleben.bit.bi.isa4j.components.Publication;
import de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil;

import static de.ipk_gatersleben.bit.bi.isa4j.util.StringUtil.mergeAttributes;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class to represent an Investigation in the ISA Context, which contains the
 * overall information about the goals and means of the experiment.
 *
 * @author liufe, arendd
 */
public class Investigation {

	/**
	 * The defined identifier for the {@link Investigation}.
	 */
	private String identifier;

	/**
	 * The list of the used ontologies for this investigation {@link Ontology}
	 */
	private ArrayList<Ontology> ontologies = new ArrayList<>();

	/**
	 * The title of the {@link Investigation}.
	 */
	private String title;

	/**
	 * A brief description of the aims of the {@link Investigation}
	 */
	private String description;

	/**
	 * The date the {@link Investigation} was submitted
	 */
	private Date submissionDate;

	/**
	 * The date the {@link Investigation} was released.
	 */
	private Date publicReleaseDate;

	/**
	 * A list of {@link Comment}s used to further describe the {@link Investigation}
	 */
	private ArrayList<Comment> comments = new ArrayList<>();

	/**
	 * Connected {@link Publication}s of this {@link Investigation}
	 */
	private ArrayList<Publication> publications = new ArrayList<>();

	/**
	 * People, who are associated with the {@link Investigation}
	 */
	private ArrayList<Person> contacts = new ArrayList<>();

	/**
	 * Studies of investigations {@link Study}
	 */
	private List<Study> studies = new ArrayList<>();

	/**
	 * Flag of function write, if a thread can't write file, this value will be
	 * false.
	 */
	public AtomicBoolean flagOfWriteFunction = new AtomicBoolean(true);

	/**
	 * Constructor, every Investigation should have a identifier.
	 *
	 * @param identifier identifier of investigation
	 */
	public Investigation(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * Write the data of investigation to file. The name of investigation doesn't
	 * need "txt" in end. It will add automatic. When a investigation has too many
	 * data, then can use the position of study and assay. When the attribute
	 * position of study and assay is "mid", the file will write the data continue.
	 * But the name of the entities should be same and the flag should be false. And
	 * the class study and assay must be same with the first one. So in this
	 * situation the user should use function "writeToFile(Path path, String name)".
	 *
	 * @param path            the address of the file
	 * @param name            the name of document
	 * @param flagOfOverwrite the flag of overwrite, true will overwrite, false will
	 *                        not overwrite
	 * @return if the operation is successful
	 */
	public boolean writeToFile(Path path, String name, boolean flagOfOverwrite) {
		ThreadPool.initial(this);
		File path_file = path.toFile();
		if (!path_file.exists() && !path_file.mkdirs()) {
			LoggerUtil.logger.error("The folder can be created! Path: " + path.toString());
			return false;

		}
		String i_Name = name + "_investigation.txt";
		Path i_Path = Paths.get(path.toString(), i_Name);
		File file = i_Path.toFile();

		if (this.studies.size() > 0) {
			if (file.exists() && (this.studies.get(0).getLocation() == PartOfEntity.HEAD) && (!flagOfOverwrite)) {
				LoggerUtil.logger
						.error("The investigation file already exists! Path: " + path.toString() + " name: " + i_Name);
				ThreadPool.overallCountDownLatchDown();
				return false;
			}
		} else {
			if (file.exists() && (!flagOfOverwrite)) {
				LoggerUtil.logger
						.error("The investigation file aready exists! Path: " + path.toString() + " name: " + i_Name);
				ThreadPool.overallCountDownLatchDown();
				return false;
			}
		}

		if (!flagOfOverwrite) {
			for (Study study : this.studies) {
				if (study.getLocation() != PartOfEntity.HEAD) {
					break;
				}
				File studyFile = Paths.get(path.toString(), study.getFileName()).toFile();
				if (studyFile.exists()) {
					LoggerUtil.logger.error(
							"The study file '" + study.getFileName() + "' already exists! Path: " + path.toString());
					return false;
				}
				for (Assay assay : study.getAssays()) {
					if (assay.getLocation() != PartOfEntity.HEAD)
						break;
					File assayFile = Paths.get(path.toString(), assay.getFileName()).toFile();
					if (assayFile.exists()) {
						LoggerUtil.logger.error("The assay file '" + assay.getFileName() + "' already exists! Path: "
								+ path.toString());
						return false;
					}
				}
			}
		}

		if (!file.exists()) {
			try {
				if (file.createNewFile())
					LoggerUtil.logger.trace("The file does not exist. New file is created. Path: " + file.getPath());
				else
					LoggerUtil.logger.fatal("Can not create new file! Path: " + file.getPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!file.renameTo(file)) {
			LoggerUtil.logger.fatal("Please close the existing investigation file first! Path: " + file.getPath());
			return false;
		}

		for (Study study : this.studies) {
			Thread writeStudy = new Thread(() -> study.writeToFile(path, flagOfOverwrite));
			ThreadPool.execute(writeStudy);
			LoggerUtil.logger.trace("Study: " + study.getFileName() + " is start to write.");
		}

		CountDownLatch stop = new CountDownLatch(2);
		if (file.exists()) {
			if (this.studies.size() > 0) {
				if (this.studies.get(0).getLocation() == PartOfEntity.HEAD) {
					WriteToFileThread writeThread_OverwriteWithStudy = new WriteToFileThread(file, false, stop);
					ThreadPool.execute(writeThread_OverwriteWithStudy);
				} else {
					ThreadPool.overallCountDownLatchDown();
				}
			} else {
				WriteToFileThread writeThread_OverwriteWithoutStudy = new WriteToFileThread(file, false, stop);
				ThreadPool.execute(writeThread_OverwriteWithoutStudy);
			}
		} else {
			WriteToFileThread writeInvestigation = new WriteToFileThread(file, true, stop);
			ThreadPool.execute(writeInvestigation);
		}

		try {
			ThreadPool.overallCountDownLatchAwait();
			ThreadPool.shutdown();
		} catch (Exception e) {
			LoggerUtil.logger.error(e.getMessage());
		}
		if (!flagOfWriteFunction.get()) {
			flagOfWriteFunction.set(true);
			return false;
		}
		return true;
	}

	/**
	 * Write only the content of investigation file to stream. His study and assay
	 * don't be written to stream.
	 *
	 * @param outputStream the outputStream
	 * @return if the write is success
	 */
	public boolean writeToStream(OutputStream outputStream, boolean closeStreamAfterWriting) {
		if (protocolTemplateOfInvestigation.size() <= 0 || factorTemplateOfInvestigation.size() <= 0) {
			LoggerUtil.logger.error("Before write Investigation, it must write study and assay.");
			return false;
		}
		try {
			CountDownLatch stop = new CountDownLatch(2);
			if (outputStream == null) {
				LoggerUtil.logger.error("The Stream can't be null.");
				return false;
			}

			ThreadPool.start();
			PipedInputStream in = new PipedInputStream();
			PipedOutputStream out = new PipedOutputStream(in);
			if (!writeToPipedStream(out, stop))
				return false;
			Writer writer = new Writer(outputStream, in, stop);
			return writer.writeToStream();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * The thread for write data. By this thread start to the operation of write to
	 * file.
	 */
	private class WriteToFileThread extends Thread {
		File file;
		boolean append;
		CountDownLatch stop;

		/**
		 * Constructor of thread. It includes all parameters, those it needs.
		 *
		 * @param file   the file, that data should be write in
		 * @param append the file will be overwrited or not overwrited
		 * @param stop   the CountDownLatch, record the operation for pipedStream
		 */
		private WriteToFileThread(File file, boolean append, CountDownLatch stop) {
			this.file = file;
			this.append = append;
			this.stop = stop;
		}

		@Override
		public void run() {
			try {
				PipedInputStream in = new PipedInputStream();
				PipedOutputStream out = new PipedOutputStream(in);
				if (!writeToPipedStream(out, stop))
					LoggerUtil.logger.error("The write of data fails.");

				LoggerUtil.logger.trace("Start writing investigation '" + file.getName() + "'.");
				if (!new Writer(file, in, stop).writeToFile(append))
					LoggerUtil.logger.error("Can not write investigation '" + file.getName() + "'.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Write all content of investigation, include study and study's assay
	 * <p>
	 * This function is slow and because of one stream, can't use multi thread. If
	 * you want the process quickly run, please use the function
	 * "writeContentToStream" of every file and use multi thread
	 *
	 * @param outputStream the stream, the date will be wried in to
	 * @return if the write successful
	 */
	public boolean writeRecursivelyToStream(OutputStream outputStream, boolean closeStreamAfterWriting) {
		for (Study study : this.studies) {
			if (!study.writeRecursivelyToStream(outputStream, closeStreamAfterWriting))
				return false;
		}
		return writeToStream(outputStream,closeStreamAfterWriting);
	}

	/**
	 * write data to pipedStream PipedStream is used to connect with file. By other
	 * thread will read the data from pipedStream and write to file.
	 *
	 * @param out  PipedOutputStream
	 * @param stop CountDownLatch, ensure the end of the threads for pipedStream
	 * @return true
	 */
	private boolean writeToPipedStream(PipedOutputStream out, CountDownLatch stop) {
		WriteToPipedStreamThread writeToPipe = new WriteToPipedStreamThread(out, stop);
		ThreadPool.execute(writeToPipe);
		return true;
	}

	/**
	 * Thread, that can write the information of investigation to PipedStream
	 */
	private class WriteToPipedStreamThread extends Thread {

		/**
		 * the pipedOutputStream
		 */
		PipedOutputStream pos;

		/**
		 * CountDownLatch, ensure the end of the threads for pipedStream
		 */
		CountDownLatch stop;

		/**
		 * Constructor for thread, the information of investigation to pipedStream to
		 * write. But this thread don't write his study and assay
		 *
		 * @param pos  the pipedOutputStream
		 * @param stop CountDownLatch, ensure the end of the threads for pipedStream
		 */
		private WriteToPipedStreamThread(PipedOutputStream pos, CountDownLatch stop) {
			this.pos = pos;
			this.stop = stop;
		}

		@Override
		public void run() {
			try {

				LoggerUtil.logger.debug("write data to investigation");
				// ONTOLOGY SOURCE REFERENCE
				pos.write(InvestigationAttribute.ONTOLOGY_SOURCE_REFERENCE.toString().getBytes(Props.DEFAULT_CHARSET));
				pos.write(InvestigationAttribute.TERM_SOURCE_NAME.toString().getBytes(Props.DEFAULT_CHARSET));
				if (ontologies != null) {
					for (Ontology o : ontologies)
						pos.write((o.getName() + Symbol.TAB).getBytes(Props.DEFAULT_CHARSET));
					pos.write(Symbol.ENTER.toString().getBytes(Props.DEFAULT_CHARSET));

					pos.write(InvestigationAttribute.TERM_SOURCE_FILE.toString().getBytes(Props.DEFAULT_CHARSET));
					for (Ontology o : ontologies)
						pos.write(((o.getURL() == null ? Symbol.EMPTY.toString() : o.getURL().toString()) + Symbol.TAB)
								.getBytes(Props.DEFAULT_CHARSET));
					pos.write(Symbol.ENTER.toString().getBytes(Props.DEFAULT_CHARSET));

					pos.write(InvestigationAttribute.TERM_SOURCE_VERSION.toString().getBytes(Props.DEFAULT_CHARSET));
					for (Ontology o : ontologies)
						pos.write(((o.getVersion() == null ? Symbol.EMPTY.toString() : o.getVersion()) + Symbol.TAB)
								.getBytes(Props.DEFAULT_CHARSET));
					pos.write(Symbol.ENTER.toString().getBytes(Props.DEFAULT_CHARSET));

					pos.write(InvestigationAttribute.TERM_SOURCE_DESCRIPTION.toString().getBytes(Props.DEFAULT_CHARSET));
					for (Ontology o : ontologies)
						pos.write(((o.getDescription() == null ? Symbol.EMPTY.toString() : o.getDescription())
								+ Symbol.TAB).getBytes(Props.DEFAULT_CHARSET));
					pos.write(Symbol.ENTER.toString().getBytes(Props.DEFAULT_CHARSET));
				} else {
					pos.write(Symbol.ENTER.toString().getBytes(Props.DEFAULT_CHARSET));
					pos.write(InvestigationAttribute.TERM_SOURCE_FILE.toString().getBytes(Props.DEFAULT_CHARSET));
					pos.write(Symbol.ENTER.toString().getBytes(Props.DEFAULT_CHARSET));
					pos.write(InvestigationAttribute.TERM_SOURCE_VERSION.toString().getBytes(Props.DEFAULT_CHARSET));
					pos.write(Symbol.ENTER.toString().getBytes(Props.DEFAULT_CHARSET));
					pos.write(InvestigationAttribute.TERM_SOURCE_DESCRIPTION.toString().getBytes(Props.DEFAULT_CHARSET));
					pos.write(Symbol.ENTER.toString().getBytes(Props.DEFAULT_CHARSET));
				}

				// INVESTIGATION
				pos.write((InvestigationAttribute.INVESTIGATION.toString()
						+ InvestigationAttribute.INVESTIGATION_IDENTIFIER
						+ (identifier == null ? Symbol.EMPTY : identifier) + Symbol.ENTER
						+ InvestigationAttribute.INVESTIGATION_TITLE + (title == null ? Symbol.EMPTY : title)
						+ Symbol.ENTER + InvestigationAttribute.INVESTIGATION_DESCRIPTION
						+ (description == null ? Symbol.EMPTY : description) + Symbol.ENTER).getBytes(Props.DEFAULT_CHARSET));

				if (submissionDate == null) {
					pos.write(InvestigationAttribute.INVESTIGATION_SUBMISSION_DATE.toString().getBytes(Props.DEFAULT_CHARSET));
				} else {
					pos.write((InvestigationAttribute.INVESTIGATION_SUBMISSION_DATE.toString()
							+ submissionDate.toString()).getBytes(Props.DEFAULT_CHARSET));
				}
				pos.write(Symbol.ENTER.toString().getBytes(Props.DEFAULT_CHARSET));
				if (publicReleaseDate == null) {
					pos.write(InvestigationAttribute.INVESTIGATION_PUBLIC_RELEASE_DATE.toString()
							.getBytes(Props.DEFAULT_CHARSET));
				} else {
					pos.write((InvestigationAttribute.INVESTIGATION_PUBLIC_RELEASE_DATE.toString()
							+ publicReleaseDate.toString()).getBytes(Props.DEFAULT_CHARSET));
				}
				pos.write(Symbol.ENTER.toString().getBytes(Props.DEFAULT_CHARSET));

				pos.write(writeCommentList(comments).toString().getBytes(Props.DEFAULT_CHARSET));

				// INVESTIGATION PUBLICATIONS
				pos.write(InvestigationAttribute.INVESTIGATION_PUBLICATIONS.toString().getBytes(Props.DEFAULT_CHARSET));

				pos.write(getAllValueOfClassAttribute(InvestigationAttribute.INVESTIGATION_PUBMED_ID,
						Publication.Attributes.PUBMED_ID, publications).getBytes(Props.DEFAULT_CHARSET));

				pos.write(getAllValueOfClassAttribute(InvestigationAttribute.INVESTIGATION_PUBLICATION_DOI,
						Publication.Attributes.DOI, publications).getBytes(Props.DEFAULT_CHARSET));

				pos.write(getAllValueOfClassAttribute(InvestigationAttribute.INVESTIGATION_PUBLICATION_AUTHOR_LIST,
						Publication.Attributes.AUTHOR_LIST, publications).getBytes(Props.DEFAULT_CHARSET));

				pos.write(getAllValueOfClassAttribute(InvestigationAttribute.INVESTIGATION_PUBLICATION_TITLE,
						Publication.Attributes.TITLE, publications).getBytes(Props.DEFAULT_CHARSET));

				pos.write(getAllValueOfClassAttribute(InvestigationAttribute.INVESTIGATION_PUBLICATION_STATUS,
						Publication.Attributes.STATUS, publications).getBytes(Props.DEFAULT_CHARSET));

				pos.write(getAllValueOfClassAttribute(
						StringUtil.mergeAttributes(InvestigationAttribute.INVESTIGATION_PUBLICATION_STATUS.toString(),
								InvestigationAttribute.TERM_ACCESSION_NUMBER.toString()),
						Publication.Attributes.STATUS_TERM_ACCESSION_NUMBER, publications).getBytes(Props.DEFAULT_CHARSET));

				pos.write(getAllValueOfClassAttribute(
						StringUtil.mergeAttributes(InvestigationAttribute.INVESTIGATION_PUBLICATION_STATUS.toString(),
								InvestigationAttribute.TERM_SOURCE_REF.toString()),
						Publication.Attributes.STATUS_TERM_SOURCE_REF, publications).getBytes(Props.DEFAULT_CHARSET));

				// INVESTIGATION CONTACTS
				pos.write(InvestigationAttribute.INVESTIGATION_CONTACTS.toString().getBytes(Props.DEFAULT_CHARSET));

				pos.write(getAllValueOfClassAttribute(InvestigationAttribute.INVESTIGATION_PERSON_LAST_NAME,
						Person.Attributes.LAST_NAME, contacts).getBytes(Props.DEFAULT_CHARSET));

				pos.write(getAllValueOfClassAttribute(InvestigationAttribute.INVESTIGATION_PERSON_FIRST_NAME,
						Person.Attributes.FIRST_NAME, contacts).getBytes(Props.DEFAULT_CHARSET));

				pos.write(getAllValueOfClassAttribute(InvestigationAttribute.INVESTIGATION_PERSON_MID_INITIALS,
						Person.Attributes.MID_INITIALS, contacts).getBytes(Props.DEFAULT_CHARSET));

				pos.write(getAllValueOfClassAttribute(InvestigationAttribute.INVESTIGATION_PERSON_EMAIL,
						Person.Attributes.EMAIL, contacts).getBytes(Props.DEFAULT_CHARSET));

				pos.write(getAllValueOfClassAttribute(InvestigationAttribute.INVESTIGATION_PERSON_PHONE,
						Person.Attributes.PHONE, contacts).getBytes(Props.DEFAULT_CHARSET));

				pos.write(getAllValueOfClassAttribute(InvestigationAttribute.INVESTIGATION_PERSON_FAX,
						Person.Attributes.FAX, contacts).getBytes(Props.DEFAULT_CHARSET));

				pos.write(getAllValueOfClassAttribute(InvestigationAttribute.INVESTIGATION_PERSON_ADDRESS,
						Person.Attributes.ADDRESS, contacts).getBytes(Props.DEFAULT_CHARSET));

				pos.write(getAllValueOfClassAttribute(InvestigationAttribute.INVESTIGATION_PERSON_AFFILIATION,
						Person.Attributes.AFFILIATION, contacts).getBytes(Props.DEFAULT_CHARSET));

				pos.write(getAllValueOfClassAttribute(InvestigationAttribute.INVESTIGATION_PERSON_ROLES,
						Person.Attributes.ROLES, contacts).getBytes(Props.DEFAULT_CHARSET));

				pos.write(getAllValueOfClassAttribute(
						mergeAttributes(InvestigationAttribute.INVESTIGATION_PERSON_ROLES.toString(),
								InvestigationAttribute.TERM_ACCESSION_NUMBER.toString()),
						Person.Attributes.ROLES_TERM_ACCESSION_NUMBER, contacts).getBytes(Props.DEFAULT_CHARSET));

				pos.write(getAllValueOfClassAttribute(
						mergeAttributes(InvestigationAttribute.INVESTIGATION_PERSON_ROLES.toString(),
								InvestigationAttribute.TERM_SOURCE_REF.toString()),
						Person.Attributes.ROLES_TERM_SOURCE_REF, contacts).getBytes(Props.DEFAULT_CHARSET));

//                Comment of contacts
				writeCommentOfComponentsToPipedStream(contacts, pos);

//                 STUDY
				for (Study study : studies) {
					pos.write((InvestigationAttribute.STUDY.toString() + InvestigationAttribute.STUDY_IDENTIFIER
							+ (study.getIdentifier() == null ? Symbol.EMPTY.toString() : study.getIdentifier())
							+ Symbol.ENTER + InvestigationAttribute.STUDY_FILE_NAME
							+ (study.getFileName() == null ? Symbol.EMPTY.toString() : study.getFileName())
							+ Symbol.ENTER + InvestigationAttribute.STUDY_TITLE
							+ (study.getTitle() == null ? Symbol.EMPTY.toString() : study.getTitle()) + Symbol.ENTER
							+ InvestigationAttribute.STUDY_DESCRIPTION
							+ (study.getDescription() == null ? Symbol.EMPTY.toString() : study.getDescription())
							+ Symbol.ENTER).getBytes(Props.DEFAULT_CHARSET));
					if (study.getSubmissionDate() == null) {
						pos.write(InvestigationAttribute.STUDY_SUBMISSION_DATE.toString().getBytes(Props.DEFAULT_CHARSET));
					} else {
						pos.write((InvestigationAttribute.STUDY_SUBMISSION_DATE.toString()
								+ study.getSubmissionDate().toString()).getBytes(Props.DEFAULT_CHARSET));
					}
					pos.write(Symbol.ENTER.toString().getBytes(Props.DEFAULT_CHARSET));
					if (study.getPublicReleaseDate() == null) {
						pos.write(InvestigationAttribute.STUDY_PUBLIC_RELEASE_DATE.toString().getBytes(Props.DEFAULT_CHARSET));
					} else {
						pos.write((InvestigationAttribute.STUDY_PUBLIC_RELEASE_DATE.toString()
								+ study.getPublicReleaseDate().toString()).getBytes(Props.DEFAULT_CHARSET));
					}
					pos.write(Symbol.ENTER.toString().getBytes(Props.DEFAULT_CHARSET));

					pos.write(writeCommentList(study.getComments()).toString().getBytes(Props.DEFAULT_CHARSET));

					// STUDY DESIGN DESCRIPTORS
					pos.write(InvestigationAttribute.STUDY_DESIGN_DESCRIPTORS.toString().getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_DESIGN_TYPE,
							DesignDescriptor.DesignDescriptorAttributes.TYPE, study.getDesignDescriptors())
									.getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(
							mergeAttributes(InvestigationAttribute.STUDY_DESIGN_TYPE.toString(),
									InvestigationAttribute.TERM_ACCESSION_NUMBER.toString()),
							DesignDescriptor.DesignDescriptorAttributes.TERM_ACCESSION_NUMBER,
							study.getDesignDescriptors()).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(
							mergeAttributes(InvestigationAttribute.STUDY_DESIGN_TYPE.toString(),
									InvestigationAttribute.TERM_SOURCE_REF.toString()),
							DesignDescriptor.DesignDescriptorAttributes.SOURCE_REF, study.getDesignDescriptors())
									.getBytes(Props.DEFAULT_CHARSET));

					writeCommentOfComponentsToPipedStream(study.getDesignDescriptors(), pos);

					// STUDY PUBLICATIONS
					pos.write(InvestigationAttribute.STUDY_PUBLICATIONS.toString().getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_PUBMED_ID,
							Publication.Attributes.PUBMED_ID, study.getPublications()).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_PUBLICATION_DOI,
							Publication.Attributes.DOI, study.getPublications()).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_PUBLICATION_AUTHOR_LIST,
							Publication.Attributes.AUTHOR_LIST, study.getPublications()).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_PUBLICATION_TITLE,
							Publication.Attributes.TITLE, study.getPublications()).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_PUBLICATION_STATUS,
							Publication.Attributes.STATUS, study.getPublications()).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(
							mergeAttributes(InvestigationAttribute.STUDY_PUBLICATION_STATUS.toString(),
									InvestigationAttribute.TERM_ACCESSION_NUMBER.toString()),
							Publication.Attributes.STATUS_TERM_ACCESSION_NUMBER, study.getPublications())
									.getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(
							mergeAttributes(InvestigationAttribute.STUDY_PUBLICATION_STATUS.toString(),
									InvestigationAttribute.TERM_SOURCE_REF.toString()),
							Publication.Attributes.STATUS_TERM_SOURCE_REF, study.getPublications())
									.getBytes(Props.DEFAULT_CHARSET));

					// STUDY FACTORS
					List<Factor> factors = new ArrayList<>();
					if (study.getRowsOfStudy().size() > 0) {
						while (factorTemplateOfInvestigation.get(study.getFileName()) == null) {
							LoggerUtil.logger.debug("wait for the factor template of study " + study.getFileName());
						}
						factors.addAll(factorTemplateOfInvestigation.get(study.getFileName()));
						for (Assay a : study.getAssays())
							if (a.getRowsOfAssay().size() > 0) {
								while (factorTemplateOfInvestigation.get(a.getFileName()) == null) {
									LoggerUtil.logger
											.debug("wait for the protocol template of assay " + a.getFileName());
								}
								List<Factor> factorsInAssay = new ArrayList<>(
										factorTemplateOfInvestigation.get(a.getFileName()));
								for (Factor factorInAssay : factorsInAssay) {
									if (!factors.contains(factorInAssay)) {
										factors.add(factorInAssay);
									}
								}
							}
					}

					pos.write(InvestigationAttribute.STUDY_FACTORS.toString().getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_FACTOR_NAME,
							Factor.Attributes.NAME, factors).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_FACTOR_TYPE,
							Factor.Attributes.TYPE, factors).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(
							mergeAttributes(InvestigationAttribute.STUDY_FACTOR_TYPE.toString(),
									InvestigationAttribute.TERM_ACCESSION_NUMBER.toString()),
							Factor.Attributes.TYPE_TERM_ACCESSION_NUMBER, factors).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(
							mergeAttributes(InvestigationAttribute.STUDY_FACTOR_TYPE.toString(),
									InvestigationAttribute.TERM_SOURCE_REF.toString()),
							Factor.Attributes.TYPE_TERM_SOURCE_REF, factors).getBytes(Props.DEFAULT_CHARSET));

					writeCommentOfComponentsToPipedStream(factors, pos);

					// STUDY ASSAYS
					pos.write(InvestigationAttribute.STUDY_ASSAYS.toString().getBytes(Props.DEFAULT_CHARSET));
					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_ASSAY_FILE_NAME,
							Assay.Attribute.FILE_NAME, study.getAssays()).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_ASSAY_MEASUREMENT_TYPE,
							Assay.Attribute.MEASUREMENT_TYPE, study.getAssays()).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(
							mergeAttributes(InvestigationAttribute.STUDY_ASSAY_MEASUREMENT_TYPE.toString(),
									InvestigationAttribute.TERM_ACCESSION_NUMBER.toString()),
							Assay.Attribute.MEASUREMENT_TYPE_TERM_ACCESSION_NUMBER, study.getAssays())
									.getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(
							mergeAttributes(InvestigationAttribute.STUDY_ASSAY_MEASUREMENT_TYPE.toString(),
									InvestigationAttribute.TERM_SOURCE_REF.toString()),
							Assay.Attribute.MEASUREMENT_TYPE_TERM_SOURCE_REF, study.getAssays())
									.getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_ASSAY_TECHNOLOGY_TYPE,
							Assay.Attribute.TECHNOLOGY_TYPEM, study.getAssays()).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(
							mergeAttributes(InvestigationAttribute.STUDY_ASSAY_TECHNOLOGY_TYPE.toString(),
									InvestigationAttribute.TERM_ACCESSION_NUMBER.toString()),
							Assay.Attribute.TECHNOLOGY_TYPE_TERM_ACCESSION_NUMBER, study.getAssays())
									.getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(
							mergeAttributes(InvestigationAttribute.STUDY_ASSAY_TECHNOLOGY_TYPE.toString(),
									InvestigationAttribute.TERM_SOURCE_REF.toString()),
							Assay.Attribute.TECHNOLOGY_TYPE_TERM_SOURCE_REF, study.getAssays())
									.getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_ASSAY_TECHNOLOGY_PLATFORM,
							Assay.Attribute.TECHNOLOGY_PLATFORM, study.getAssays()).getBytes(Props.DEFAULT_CHARSET));

					// STUDY PROTOCOLS
					pos.write(InvestigationAttribute.STUDY_PROTOCOLS.toString().getBytes(Props.DEFAULT_CHARSET));

					List<Protocol> protocols = new ArrayList<>();
					if (study.getRowsOfStudy().size() > 0) {
						while (protocolTemplateOfInvestigation.get(study.getFileName()) == null) {
							LoggerUtil.logger.error("wait for the protocol template of study " + study.getFileName());

						}
						protocols.addAll(protocolTemplateOfInvestigation.get(study.getFileName()));
						for (Assay a : study.getAssays())
							if (a.getRowsOfAssay().size() > 0) {
								while (protocolTemplateOfInvestigation.get(a.getFileName()) == null) {
									LoggerUtil.logger
											.error("wait for the protocol template of assay " + a.getFileName());
								}
								ArrayList<Protocol> protocolsInAssay = new ArrayList<>(
										protocolTemplateOfInvestigation.get(a.getFileName()));
								for (Protocol p : protocolsInAssay) {
									if (!protocols.contains(p)) {
										protocols.add(p);
									}
								}
							}
					}

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_PROTOCOL_NAME,
							Protocol.Attributes.NAME, protocols).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_PROTOCOL_TYPE,
							Protocol.Attributes.TYPE, protocols).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(
							mergeAttributes(InvestigationAttribute.STUDY_PROTOCOL_TYPE.toString(),
									InvestigationAttribute.TERM_ACCESSION_NUMBER.toString()),
							Protocol.Attributes.TYPE_TERM_ACCESSION_NUMBER, protocols).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(
							mergeAttributes(InvestigationAttribute.STUDY_PROTOCOL_TYPE.toString(),
									InvestigationAttribute.TERM_SOURCE_REF.toString()),
							Protocol.Attributes.TYPE_TERM_SOURCE_REF, protocols).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_PROTOCOL_DESCRIPTION,
							Protocol.Attributes.DESCRIPTION, protocols).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_PROTOCOL_URI,
							Protocol.Attributes.URI, protocols).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_PROTOCOL_VERSION,
							Protocol.Attributes.VERSION, protocols).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_PROTOCOL_PARAMETERS_NAME,
							Protocol.Attributes.PARAMETERS_NAME, protocols).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(
							mergeAttributes(InvestigationAttribute.STUDY_PROTOCOL_PARAMETERS_NAME.toString(),
									InvestigationAttribute.TERM_ACCESSION_NUMBER.toString()),
							Protocol.Attributes.PARAMETERS_NAME_TERM_ACCESSION_NUMBER, protocols)
									.getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(
							mergeAttributes(InvestigationAttribute.STUDY_PROTOCOL_PARAMETERS_NAME.toString(),
									InvestigationAttribute.TERM_SOURCE_REF.toString()),
							Protocol.Attributes.PARAMETERS_NAME_TERM_SOURCE_REF, protocols).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_PROTOCOL_COMPONENTS_NAME,
							Protocol.Attributes.COMPONENTS_NAME, protocols).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_PROTOCOL_COMPONENTS_TYPE,
							Protocol.Attributes.COMPONENTS_TYPE, protocols).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(
							mergeAttributes(InvestigationAttribute.STUDY_PROTOCOL_COMPONENTS_TYPE.toString(),
									InvestigationAttribute.TERM_ACCESSION_NUMBER.toString()),
							Protocol.Attributes.TYPE_TERM_ACCESSION_NUMBER, protocols).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(
							mergeAttributes(InvestigationAttribute.STUDY_PROTOCOL_COMPONENTS_TYPE.toString(),
									InvestigationAttribute.TERM_SOURCE_REF.toString()),
							Protocol.Attributes.TYPE_TERM_SOURCE_REF, protocols).getBytes(Props.DEFAULT_CHARSET));

					// STUDY CONTACTS
					pos.write(InvestigationAttribute.STUDY_CONTACTS.toString().getBytes(Props.DEFAULT_CHARSET));
					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_PERSON_LAST_NAME,
							Person.Attributes.LAST_NAME, study.getContacts()).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_PERSON_FIRST_NAME,
							Person.Attributes.FIRST_NAME, study.getContacts()).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_PERSON_MID_INITIALS,
							Person.Attributes.MID_INITIALS, study.getContacts()).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_PERSON_EMAIL,
							Person.Attributes.EMAIL, study.getContacts()).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_PERSON_PHONE,
							Person.Attributes.PHONE, study.getContacts()).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_PERSON_FAX,
							Person.Attributes.FAX, study.getContacts()).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_PERSON_ADDRESS,
							Person.Attributes.ADDRESS, study.getContacts()).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_PERSON_AFFILIATION,
							Person.Attributes.AFFILIATION, study.getContacts()).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(InvestigationAttribute.STUDY_PERSON_ROLES,
							Person.Attributes.ROLES, study.getContacts()).getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(
							mergeAttributes(InvestigationAttribute.STUDY_PERSON_ROLES.toString(),
									InvestigationAttribute.TERM_ACCESSION_NUMBER.toString()),
							Person.Attributes.ROLES_TERM_ACCESSION_NUMBER, study.getContacts())
									.getBytes(Props.DEFAULT_CHARSET));

					pos.write(getAllValueOfClassAttribute(
							mergeAttributes(InvestigationAttribute.STUDY_PERSON_ROLES.toString(),
									InvestigationAttribute.TERM_SOURCE_REF.toString()),
							Person.Attributes.ROLES_TERM_SOURCE_REF, study.getContacts()).getBytes(Props.DEFAULT_CHARSET));

					writeCommentOfComponentsToPipedStream(study.getContacts(), pos);
				}
				pos.close();
				stop.countDown();
			} catch (IOException e) {
				e.printStackTrace();
				stop.countDown();
			}
		}

	}

	/**
	 * Get all same attribute of a list (For example:all Publications'name in
	 * publication's list)
	 *
	 * @param a             the attribute, the title of parameter
	 * @param attributeEnum the {@link Enum} for the requested attribute
	 * @param list          the list of Term
	 * @return String type informations of the member of data
	 */
	private <T> String getAllValueOfClassAttribute(InvestigationAttribute a, Enum<?> attributeEnum, List<T> list) {
		String s = a.toString();
		s += getAllValuesOfClassAttribute(attributeEnum, list);
		return s;
	}

	/**
	 * Get all same attribute of a list (For example:all Publications'name)
	 *
	 * @param a             the attribute, the title of parameter
	 * @param attributeEnum the {@link Enum} for the requested attribute
	 * @param list          the list of Term
	 * @return String type informations of the member of data
	 */
	private <T> String getAllValueOfClassAttribute(String a, Enum<?> attributeEnum, List<T> list) {
		String s = a;
		s += getAllValuesOfClassAttribute(attributeEnum, list);
		return s;
	}

	/**
	 * Make a stringBuilder, that has all information of a list of comment
	 *
	 * @param list list of comment
	 * @return all information of the list
	 */
	private StringBuilder writeCommentList(ArrayList<Comment> list) {
		StringBuilder sb = new StringBuilder();
		if (list != null)
			for (Comment c : list) {
				sb.append(InvestigationAttribute.COMMENT);
				sb = StringUtil.putParameterInStringBuilder(sb, c.getName());
				sb.append(c.getValue()).append(Symbol.ENTER);
			}
		return sb;

	}

	/**
	 * Add an {@link Ontology} to the {@link Investigation}
	 *
	 * @param ontology the {@link Ontology} source reference, which you want to add
	 */
	public void addOntology(Ontology ontology) {
		if (ontologies == null)
			ontologies = new ArrayList<>();
		this.ontologies.add(ontology);
	}

	/**
	 * Add a contact to the {@link Investigation}
	 *
	 * @param contact the contact of a people,which will be add
	 */
	public void addContact(Person contact) {
		contacts.add(contact);

	}

	/**
	 * Add a comment to list
	 *
	 * @param comment comment
	 */
	public void addComment(Comment comment) {
		this.comments.add(comment);
	}

	/**
	 * Add a publication to list
	 *
	 * @param publication publication
	 */
	public void addPublication(Publication publication) {
		publications.add(publication);
	}

	/**
	 * Add a study to study list The identifier and filename must be alone, 2
	 * studies can't have same identifier or filename.
	 *
	 * @param study the study of investigation, which will be add
	 */
	public boolean addStudy(Study study) {
		for (Study studyInInvestigation : studies) {
			if (study.getIdentifier().equals(studyInInvestigation.getIdentifier())) {
				LoggerUtil.logger.error("The investigation " + identifier + " can't add the study. "
						+ "There is a study in the investigation, that its identifier is " + study.getIdentifier()
						+ ", please change that identifier!");
				return false;
			}
			if (study.getFileName().equals(studyInInvestigation.getFileName())) {
				LoggerUtil.logger.error("The investigation " + identifier + " can't add the study. "
						+ "There is a study in the investigation, that its fileName is " + study.getFileName()
						+ ", please change that identifier!");
				return false;
			}
		}
		study.setInvestigation(this);
		this.studies.add(study);
		return true;
	}

	/**
	 * Aet id of invesigation
	 *
	 * @return id of invesigation
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Set id of invesigation
	 *
	 * @param iD id of invesigation
	 */
	public void setIdentifier(String iD) {
		identifier = iD;
	}

	/**
	 * Get all linked {@link Ontology}
	 *
	 * @return ontologies
	 */
	public ArrayList<Ontology> getOntologies() {
		return ontologies;
	}

	/**
	 * Set linked {@link Ontology}
	 *
	 * @param ontologies the library of {@link OntologyAnnotation}
	 */
	public void setOntologies(ArrayList<Ontology> ontologies) {
		this.ontologies = ontologies;
	}

	/**
	 * Get title
	 *
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set title
	 *
	 * @param title title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get Description
	 *
	 * @return Description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set Description
	 *
	 * @param description Description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get SubmissionDate
	 *
	 * @return SubmissionDate
	 */
	public Date getSubmissionDate() {
		return submissionDate;
	}

	/**
	 * Set SubmissionDate
	 *
	 * @param submissionDate SubmissionDate
	 */
	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	/**
	 * Get PublicReleaseDate
	 *
	 * @return PublicReleaseDate
	 */
	public Date getPublicReleaseDate() {
		return publicReleaseDate;
	}

	/**
	 * Set PublicReleaseDate
	 *
	 * @param publicReleaseDate PublicReleaseDate
	 */
	public void setPublicReleaseDate(Date publicReleaseDate) {
		this.publicReleaseDate = publicReleaseDate;
	}

	/**
	 * Get comments of investigation
	 *
	 * @return comments of investigation
	 */
	public ArrayList<Comment> getComments() {
		return comments;
	}

	/**
	 * Set comments of investigation
	 *
	 * @param comments comment of investigation
	 */
	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

	/**
	 * Get Publication of investigation
	 *
	 * @return Publication of investigation
	 */
	public ArrayList<Publication> getPublications() {
		return publications;
	}

	/**
	 * Set Publication of investigation
	 *
	 * @param publications Publication of investigation
	 */
	public void setPublications(ArrayList<Publication> publications) {
		this.publications = publications;
	}

	/**
	 * Get contact of investigation
	 *
	 * @return contact of investigation
	 */
	public ArrayList<Person> getContacts() {
		return contacts;
	}

	/**
	 * Set contact of investigation
	 *
	 * @param contacts contact of investigation
	 */
	public void setContacts(ArrayList<Person> contacts) {

		this.contacts = contacts;
	}

	/**
	 * Get studies of investigation
	 *
	 * @return studies of investigation
	 */
	public List<Study> getStudies() {
		return studies;
	}

	/**
	 * Set studies of investigation
	 *
	 * @param studies studies of investigation
	 */
	public void setStudies(ArrayList<Study> studies) {
		this.studies = studies;
		for (Study study : studies) {
			study.setInvestigation(this);
		}
	}

	/**
	 * Get template of comment in contact. The comments of contact can be not same,
	 * and the information of 2 people can have different comments. So the template
	 * of comment should be first confirmed. Then write the all content of comments
	 * to pipedStream.
	 *
	 * @param list list of component
	 * @param pos  the pipedStream
	 */
	private <T> void writeCommentOfComponentsToPipedStream(List<T> list, PipedOutputStream pos) {

		if (list != null && list.size() > 0) {
			Set<String> template = new HashSet<>();
			if (list.get(0) instanceof Person) {
				List<Person> contacts = new ArrayList<>();
				Collections.addAll(contacts, list.toArray(new Person[list.size()]));

				for (Person contact : contacts) {
					if (contact.getComments() != null) {
						for (Comment comment : contact.getComments()) {
							template.add(comment.getName());
						}
					}
				}

				for (String type : template) {
					StringBuilder content = new StringBuilder(InvestigationAttribute.COMMENT.toString());
					content = StringUtil.putParameterInStringBuilder(content, type);
					for (Person contact : contacts) {
						if (contact.getComments() != null) {
							for (Comment comment : contact.getComments()) {
								if (comment.getName().equals(type)) {
									content.append(comment.getValue());
									break;
								}
							}
						}
						content.append(Symbol.TAB);
					}
					try {
						pos.write(content.toString().getBytes(Props.DEFAULT_CHARSET));
						pos.write(Symbol.ENTER.toString().getBytes(Props.DEFAULT_CHARSET));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return;
			}

			if (list.get(0) instanceof DesignDescriptor) {
				List<DesignDescriptor> designDescriptors = new ArrayList<>();
				Collections.addAll(designDescriptors, list.toArray(new DesignDescriptor[list.size()]));

				for (DesignDescriptor designDescriptor : designDescriptors) {
					if (designDescriptor.getComments() != null) {
						for (Comment comment : designDescriptor.getComments()) {
							template.add(comment.getName());
						}
					}
				}

				for (String type : template) {
					StringBuilder content = new StringBuilder(InvestigationAttribute.COMMENT.toString());
					content = StringUtil.putParameterInStringBuilder(content, type);
					for (DesignDescriptor designDescriptor : designDescriptors) {
						if (designDescriptor.getComments() != null) {
							for (Comment comment : designDescriptor.getComments()) {
								if (comment.getName().equals(type)) {
									content.append(comment.getValue());
									break;
								}
							}
						}
						content.append(Symbol.TAB);
					}
					try {
						pos.write(content.toString().getBytes());
						pos.write(Symbol.ENTER.toString().getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return;
			}

			if (list.get(0) instanceof Factor) {
				List<Factor> factors = new ArrayList<>();
				Collections.addAll(factors, list.toArray(new Factor[list.size()]));

				for (Factor factor : factors) {
					if (factor.getComments() != null) {
						for (Comment comment : factor.getComments()) {
							template.add(comment.getName());
						}
					}
				}

				for (String type : template) {
					StringBuilder content = new StringBuilder(InvestigationAttribute.COMMENT.toString());
					content = StringUtil.putParameterInStringBuilder(content, type);
					for (Factor factor : factors) {
						if (factor.getComments() != null) {
							for (Comment comment : factor.getComments()) {
								if (comment.getName().equals(type)) {
									content.append(comment.getValue());
									break;
								}
							}
						}
						content.append(Symbol.TAB);
					}
					try {
						pos.write(content.toString().getBytes());
						pos.write(Symbol.ENTER.toString().getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return;
			}
		}
	}

	/**
	 * The template of protocol for all {@link Study} and {@link Assay} in the
	 * {@link Investigation} Key is the filename of study or assay and value is the
	 * template of protocol
	 */
	ConcurrentMap<String, List<Protocol>> protocolTemplateOfInvestigation = new ConcurrentHashMap<>();

	/**
	 * The template of factor for all {@link Study} and {@link Assay} in the
	 * {@link Investigation} Key is the filename of study or assay and value is the
	 * template of protocol
	 */
	ConcurrentMap<String, List<Factor>> factorTemplateOfInvestigation = new ConcurrentHashMap<>();

	/**
	 * get all same attribute of a list (For example:all Publications'name)
	 *
	 * @param attributeEnum the {@link Enum} for the requested attribute
	 * @param list          the list of Term
	 * @return String type, the value of the requested attribute
	 */
	private static <T> String getAllValuesOfClassAttribute(Enum<?> attributeEnum, List<T> list) {
		StringBuilder sb = new StringBuilder();
		if (list != null)
			for (T l : list) {
				if (attributeEnum instanceof OntologyAnnotation.OntologyTermAttributes) {
					OntologyAnnotation.OntologyTermAttributes m = (OntologyAnnotation.OntologyTermAttributes) attributeEnum;
					OntologyAnnotation ontologyAnnotation = (OntologyAnnotation) l;
					sb.append(ontologyAnnotation.getValueOfOntologyTermAttribute(m)).append(Symbol.TAB);
					continue;
				}
				if (attributeEnum instanceof Publication.Attributes) {
					Publication.Attributes m = (Publication.Attributes) attributeEnum;
					Publication publication = (Publication) l;
					sb.append(publication.getValueOfPublicationAttribute(m)).append(Symbol.TAB);
					continue;
				}
				if (attributeEnum instanceof Person.Attributes) {
					Person.Attributes m = (Person.Attributes) attributeEnum;
					Person c = (Person) l;
					sb.append(c.getValueOfAttribute(m)).append(Symbol.TAB);
					continue;
				}
				if (attributeEnum instanceof Factor.Attributes) {
					Factor.Attributes m = (Factor.Attributes) attributeEnum;
					Factor s = (Factor) l;
					sb.append(s.getValueOfFactorAttribute(m)).append(Symbol.TAB);
					continue;
				}
				if (attributeEnum instanceof Assay.Attribute) {
					Assay.Attribute m = (Assay.Attribute) attributeEnum;
					Assay a = (Assay) l;
					sb.append(a.getValueOfAssayAttribute(m)).append(Symbol.TAB);
					continue;
				}
				if (attributeEnum instanceof Protocol.Attributes) {
					Protocol.Attributes m = (Protocol.Attributes) attributeEnum;
					Protocol a = (Protocol) l;
					sb.append(a.getValueOfProtocolAttribute(m)).append(Symbol.TAB);
					continue;
				}
				if (attributeEnum instanceof DesignDescriptor.DesignDescriptorAttributes) {
					DesignDescriptor.DesignDescriptorAttributes m = (DesignDescriptor.DesignDescriptorAttributes) attributeEnum;
					DesignDescriptor a = (DesignDescriptor) l;
					sb.append(a.getValueOfDesignDescriptorAttribute(m)).append(Symbol.TAB);
				}
			}

		// if the content is empty, validator for ISA-Tab maybe thinks, the content is
		// null (sometimes), then the content is empty, give it a space.
		if (sb.length() == 0)
			sb.append(Symbol.SPACE);
		sb.append(Symbol.ENTER);
		return sb.toString();
	}

	/**
	 * Collection of units, those by this investigation are used
	 */
	public static final Map<String, OntologyAnnotation> unitMap = new ConcurrentHashMap<>();

}
