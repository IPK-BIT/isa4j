package de.ipk_gatersleben.bit.bi.isa.components.tdf;

import de.ipk_gatersleben.bit.bi.isa4j.components.Publication;

public class Method {

	private String name;
	private String accessionNumber;
	private String description;
	private Publication associatedReference;

	public Method(String name, String accessionNumber, String description, Publication associatedReference) {
		this.name = name;
		this.accessionNumber = accessionNumber;
		this.description = description;
		this.associatedReference = associatedReference;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccessionNumber() {
		return accessionNumber;
	}

	public void setAccessionNumber(String accessionNumber) {
		this.accessionNumber = accessionNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Publication getAssociatedReference() {
		return associatedReference;
	}

	public void setAssociatedReference(Publication associatedReference) {
		this.associatedReference = associatedReference;
	}
}