package de.ipk_gatersleben.bit.bi.isa.components.tdf;

public class Variable {

	private String id;
	private String name;
	private String accessionNumber;

	public Variable(String id, String name, String accessionNumber) {
		this.setId(id);
		this.setName(name);
		this.setAccessionNumber(accessionNumber);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
}