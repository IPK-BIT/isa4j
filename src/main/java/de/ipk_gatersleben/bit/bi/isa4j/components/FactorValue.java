package de.ipk_gatersleben.bit.bi.isa4j.components;

public class FactorValue extends Value<Factor> {

	public FactorValue(Factor category, double value, OntologyAnnotation unit) {
		super(category, value, unit);
	}

	public FactorValue(Factor category, double value) {
		super(category, value);
	}

	public FactorValue(Factor category, OntologyAnnotation value, OntologyAnnotation unit) {
		super(category, value, unit);
	}

	public FactorValue(Factor category, OntologyAnnotation value) {
		super(category, value);
	}

	public FactorValue(Factor category, String value) {
		super(category, value);
	}
}
