package de.ipk_gatersleben.bit.bi.isa4j.components;

public class ProcessParameterValue extends Value<Protocol> {

	public ProcessParameterValue(Protocol category, double value, OntologyAnnotation unit) {
		super(category, value, unit);
	}

	public ProcessParameterValue(Protocol category, double value) {
		super(category, value);
	}

	public ProcessParameterValue(Protocol category, OntologyAnnotation value, OntologyAnnotation unit) {
		super(category, value, unit);
	}

	public ProcessParameterValue(Protocol category, OntologyAnnotation value) {
		super(category, value);
	}

	public ProcessParameterValue(Protocol category, String value) {
		super(category, value);
	}

}
