package de.ipk_gatersleben.bit.bi.isa4j.configurations;

import de.ipk_gatersleben.bit.bi.isa4j.constants.InvestigationAttribute;

public interface InvestigationConfigEnum {
	public String getFieldName();
	public boolean isRequired();
	public InvestigationAttribute getSection();
}
