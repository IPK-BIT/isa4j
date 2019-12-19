package de.ipk_gatersleben.bit.bi.isa.components.tdf;

public class Scale {

	private String name;
	private String accesssionNumber;
	private String timeScale;

	public Scale(String name, String accesssionNumber, String timeScale) {
		this.name = name;
		this.accesssionNumber = accesssionNumber;
		this.timeScale = timeScale;
	}

	public String getAccesssionNumber() {
		return accesssionNumber;
	}

	public String getName() {
		return name;
	}

	public String getTimeScale() {
		return timeScale;
	}

	public void setAccesssionNumber(String accesssionNumber) {
		this.accesssionNumber = accesssionNumber;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTimeScale(String timeScale) {
		this.timeScale = timeScale;
	}

}
