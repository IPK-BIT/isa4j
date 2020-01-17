package de.ipk_gatersleben.bit.bi.isa4j.components;

public class ParameterValue extends Value<Protocol> {

	public ParameterValue(Protocol category, double value, OntologyAnnotation unit) {
		super(category, value, unit);
	}

	public ParameterValue(Protocol category, double value) {
		super(category, value);
	}

	public ParameterValue(Protocol category, OntologyAnnotation value, OntologyAnnotation unit) {
		super(category, value, unit);
	}

	public ParameterValue(Protocol category, OntologyAnnotation value) {
		super(category, value);
	}

	public ParameterValue(Protocol category, String value) {
		super(category, value);
	}

}
