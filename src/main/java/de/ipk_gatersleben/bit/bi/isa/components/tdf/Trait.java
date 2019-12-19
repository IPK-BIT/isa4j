package de.ipk_gatersleben.bit.bi.isa.components.tdf;

public class Trait {

	private String name;
	private String accessionNumber;
	private Variable variable;
	private Method method;
	private Scale scale;

	public Trait(String name, String accessionNumber) {
		this.setName(name);
		this.setAccessionNumber(accessionNumber);
	}

	public Trait(String name, String accessionNumber, Variable variable, Method method) {
		this.setName(name);
		this.setAccessionNumber(accessionNumber);
		this.setVariable(variable);
		this.setMethod(method);
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public Variable getVariable() {
		return variable;
	}

	public void setVariable(Variable variable) {
		this.variable = variable;
	}

	public String getAccessionNumber() {
		return accessionNumber;
	}

	public void setAccessionNumber(String accessionNumber) {
		this.accessionNumber = accessionNumber;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Scale getScale() {
		return scale;
	}

	public void setScale(Scale scale) {
		this.scale = scale;
	}

}
