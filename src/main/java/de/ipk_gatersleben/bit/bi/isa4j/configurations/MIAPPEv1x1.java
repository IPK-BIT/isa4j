package de.ipk_gatersleben.bit.bi.isa4j.configurations;

import de.ipk_gatersleben.bit.bi.isa4j.components.CommentCollection;
import de.ipk_gatersleben.bit.bi.isa4j.components.Investigation;

import de.ipk_gatersleben.bit.bi.isa4j.constants.InvestigationAttribute;

public class MIAPPEv1x1 {
	public enum InvestigationFile implements ConfigEnum {
		/**
		 * License for the reuse of the data associated with this investigation. The Creative Commons licenses cover most use cases and are recommended.
		 */
		INVESTIGATION_LICENSE("Investigation License", InvestigationAttribute.INVESTIGATION, false),
		/**
		 * [required] The version of MIAPPE used.
		 */
		MIAPPE_VERSION("MIAPPE Version",  InvestigationAttribute.INVESTIGATION, true);

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
	
	public static boolean validateInvestigation(Investigation investigation) {
		CommentCollection comments = investigation.comments();

		for(InvestigationFile c : InvestigationFile.values()) { 
		    if(c.isRequired() &&  comments.getByName(c.getFieldName()).isEmpty())
		    	return false; //@TODO throw error with explanation what's wrong
		}
		
		return true;
	}
}
