package de.ipk_gatersleben.bit.bi.isa4j.components;

public class ParameterValue extends Value<ProtocolParameter> {

	public ParameterValue(ProtocolParameter category, double value, OntologyAnnotation unit) {
		super(category, value, unit);
	}

	public ParameterValue(ProtocolParameter category, double value) {
		super(category, value);
	}

	public ParameterValue(ProtocolParameter category, OntologyAnnotation value, OntologyAnnotation unit) {
		super(category, value, unit);
	}

	public ParameterValue(ProtocolParameter category, OntologyAnnotation value) {
		super(category, value);
	}

	public ParameterValue(ProtocolParameter category, String value) {
		super(category, value);
	}

}
