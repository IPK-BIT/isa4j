# This script takes a class name, an investigation, study, and assay xml configuration file as its arguments and generates
# a java class representing and validating that configuration in isa4j

require "nokogiri"

class_name = ARGV[0]
investigation_xml = Nokogiri::XML(File.open(ARGV[1]))
study_xml = Nokogiri::XML(File.open(ARGV[2]))
assay_xml = Nokogiri::XML(File.open(ARGV[3]))

investigation_enum = investigation_xml.css("field").map do |field|
  if field["header"].start_with?("Comment[")
    header = field["header"][8..-2]
    name = header.upcase.gsub(" ", "_")
    section = field["section"].gsub(" ", "_")
    description = field.content.gsub("\n", " ").lstrip
    required = field["is-required"]
    description = "[required]" + description if(required == "true")

    "
    /**
     * #{description}
     */
    #{name}(\"#{header}\", InvestigationAttribute.#{section}, #{required})"
  else
    nil
  end
end.reject(&:nil?).join(",") + ";"

study_enum = study_xml.css("field").map do |field|
  if field["header"].start_with?("Characteristics[")
    header = field["header"][16..-2]
    name = header.upcase.gsub(" ", "_")
    description = field.content.gsub("\n", " ").lstrip
    required = field["is-required"]
    description = "[required]" + description if(required == "true")

    "
    /**
     * #{description}
     */
    #{name}(\"#{header}\", #{required}, 0)" #@TODO block index currently hard-coded
  else
    nil
  end
end.reject(&:nil?).join(",") + ";"

assay_enum = assay_xml.css("field").map do |field|
  if field["header"].start_with?("Characteristics[")
    header = field["header"][16..-2]
    name = header.upcase.gsub(" ", "_")
    description = field.content.gsub("\n", " ").lstrip
    required = field["is-required"]
    description = "[required]" + description if(required == "true")

    "
    /**
     * #{description}
     */
    #{name}(\"#{header}\", #{required}, 0)" #@TODO block index currently hard-coded
  else
    nil
  end
end.reject(&:nil?).join(",") + ";"

java_code = <<-JAVA_CODE
package de.ipk_gatersleben.bit.bi.isa4j.configurations;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Stream;

import de.ipk_gatersleben.bit.bi.isa4j.components.Assay;
import de.ipk_gatersleben.bit.bi.isa4j.components.CommentCollection;
import de.ipk_gatersleben.bit.bi.isa4j.components.Commentable;
import de.ipk_gatersleben.bit.bi.isa4j.components.Investigation;
import de.ipk_gatersleben.bit.bi.isa4j.components.Study;
import de.ipk_gatersleben.bit.bi.isa4j.constants.InvestigationAttribute;
import de.ipk_gatersleben.bit.bi.isa4j.exceptions.MissingFieldException;

/**
 * @author psaroudakis, arendd
 *
 */
public class #{class_name} {
	public enum InvestigationFile implements InvestigationConfigEnum {
    #{investigation_enum}

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
		
		public static boolean validate(Investigation investigation) {
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
	
	public enum StudyFile implements WideTableConfigEnum {
    #{study_enum}
		
		private String fieldName;
		private boolean required;
		private int groupIndex; // the how many n-th object does this characteristic belong to? (i.e the first group is usually the source, second the process, third the sample)
		
		private StudyFile(String fieldName, boolean required, int groupIndex) {
			this.fieldName = fieldName;
			this.required = required;
			this.groupIndex = groupIndex;
		}
		
		public String getFieldName() {
			return this.fieldName;
		}
		
		public boolean isRequired() {
			return this.required;
		}
		
		public int getGroupIndex() {
			return this.groupIndex;
		}
		
		private static void validateCustomProperties(Study study) {
		}
		
		public static boolean validate(Study study) {
			if(!study.hasWrittenHeaders()) {
				throw new IllegalStateException("Study file for " + study.toString() + "can only be validated after headers are written." +
						"Please write headers with .writeHeadersFromExample or call validate after at least one line has been written.");
			}
			ArrayList<LinkedHashMap<String, String[]>> headers = study.getHeaders();
			Stream.of(StudyFile.values())
				.filter(c -> c.isRequired())
				.forEach(c -> {
					if(!headers.get(c.getGroupIndex()).containsKey("Characteristics[" + c.getFieldName() + "]"))
						throw new MissingFieldException("Missing Characteristic header in Study file: " + c.getFieldName());
				});
			validateCustomProperties(study);
			return true;
		}
	}

	public enum AssayFile implements WideTableConfigEnum {
    #{assay_enum}

		private String fieldName;
		private boolean required;
		private int groupIndex; // the how many n-th object does this characteristic belong to? (i.e the first group is usually the source, second the process, third the sample)
		
		private AssayFile(String fieldName, boolean required, int groupIndex) {
			this.fieldName = fieldName;
			this.required = required;
			this.groupIndex = groupIndex;
		}
		
		public String getFieldName() {
			return this.fieldName;
		}
		
		public boolean isRequired() {
			return this.required;
		}
		
		public int getGroupIndex() {
			return this.groupIndex;
		}
		
		private static void validateCustomProperties(Assay assay) {
			//@TODO 
		}
		
		public static boolean validate(Assay assay) {
			if(!assay.hasWrittenHeaders()) {
				throw new IllegalStateException("Assay file for " + assay.toString() + "can only be validated after headers are written." +
						"Please write headers with .writeHeadersFromExample or call validate after at least one line has been written.");
			}
			ArrayList<LinkedHashMap<String, String[]>> headers = assay.getHeaders();
			Stream.of(AssayFile.values())
				.filter(c -> c.isRequired())
				.forEach(c -> {
					if(!headers.get(c.getGroupIndex()).containsKey("Characteristics[" + c.getFieldName() + "]"))
						throw new MissingFieldException("Missing Characteristic header in Assay file: " + c.getFieldName());
				});
			validateCustomProperties(assay);
			return true;
		}
		
	}
}
JAVA_CODE

IO.write(class_name+".java", java_code)
