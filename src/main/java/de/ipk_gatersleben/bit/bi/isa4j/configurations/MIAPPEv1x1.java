package de.ipk_gatersleben.bit.bi.isa4j.configurations;

import java.util.List;
import java.util.stream.Stream;

import de.ipk_gatersleben.bit.bi.isa4j.components.CommentCollection;
import de.ipk_gatersleben.bit.bi.isa4j.components.Commentable;
import de.ipk_gatersleben.bit.bi.isa4j.components.Investigation;
import de.ipk_gatersleben.bit.bi.isa4j.components.Study;
import de.ipk_gatersleben.bit.bi.isa4j.constants.InvestigationAttribute;
import de.ipk_gatersleben.bit.bi.isa4j.exceptions.MissingFieldException;

public class MIAPPEv1x1 {
	public enum InvestigationFile implements ConfigEnum {
		/**
		 * License for the reuse of the data associated with this investigation. The Creative Commons licenses cover most use cases and are recommended.
		 */
		INVESTIGATION_LICENSE("Investigation License", InvestigationAttribute.INVESTIGATION, false),
		/**
		 * [required] The version of MIAPPE used.
		 */
		MIAPPE_VERSION("MIAPPE Version",  InvestigationAttribute.INVESTIGATION, true),
		PERSON_ID("Investigation Person ID", InvestigationAttribute.INVESTIGATION_CONTACTS, false);

		private String fieldName;
		private InvestigationAttribute section;
		private boolean required;

		private InvestigationFile(String fieldName, InvestigationAttribute section, boolean required) {
			this.fieldName = fieldName;
			this.section = section;
			this.required = required;
		}
		
		public String getFieldName() {
			return this.fieldName;
		}
		
		public boolean isRequired() {
			return this.required;
		}
		
		public InvestigationAttribute getSection() {
			return this.section;
		}
	}
	
	private static void validateInvestigationBlockComments(List<? extends Commentable> commentable, InvestigationAttribute block) {
		commentable.stream().forEach( unit -> {
			CommentCollection comments = unit.comments();
			Stream.of(InvestigationFile.values())
			.filter(c -> c.isRequired() && c.getSection() == block)
			.forEach(c -> {
				if(comments.getByName(c.getFieldName()).isEmpty())
					throw new MissingFieldException("Missing comment in block " + block.toString() + " for " + unit.toString() + ": " + c.getFieldName());
			});
		});
	}
	
	public static boolean validateInvestigation(Investigation investigation) {
		// Check if all required investigation comments are present
		CommentCollection comments = investigation.comments();
		Stream.of(InvestigationFile.values())
			.filter(c -> c.isRequired() && c.getSection() == InvestigationAttribute.INVESTIGATION)
			.forEach(c -> {
				if(comments.getByName(c.getFieldName()).isEmpty())
					throw new MissingFieldException("Missing comment in block " + InvestigationAttribute.INVESTIGATION.toString() + ": " + c.getFieldName());
			});
		
		validateInvestigationBlockComments(investigation.getPublications(), InvestigationAttribute.INVESTIGATION_PUBLICATIONS);
		validateInvestigationBlockComments(investigation.getContacts(), InvestigationAttribute.INVESTIGATION_CONTACTS);
		
		validateInvestigationBlockComments(investigation.getStudies(), InvestigationAttribute.STUDY);
		for(Study s : investigation.getStudies()) {
			validateInvestigationBlockComments(s.getPublications(), InvestigationAttribute.STUDY_PUBLICATIONS);
			validateInvestigationBlockComments(s.getContacts(), InvestigationAttribute.STUDY_CONTACTS);
			validateInvestigationBlockComments(s.getDesignDescriptors(), InvestigationAttribute.STUDY_DESIGN_DESCRIPTORS);
			validateInvestigationBlockComments(s.getFactors(), InvestigationAttribute.STUDY_FACTORS);
			validateInvestigationBlockComments(s.getAssays(), InvestigationAttribute.STUDY_ASSAYS);
			validateInvestigationBlockComments(s.getProtocols(), InvestigationAttribute.STUDY_PROTOCOLS);
		}
			
		return true;
	}
}
