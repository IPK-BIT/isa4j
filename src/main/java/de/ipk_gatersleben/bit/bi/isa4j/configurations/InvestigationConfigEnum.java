package de.ipk_gatersleben.bit.bi.isa4j.configurations;

import de.ipk_gatersleben.bit.bi.isa4j.components.Investigation;
import de.ipk_gatersleben.bit.bi.isa4j.constants.InvestigationAttribute;

/**
 * 
 * Interface for providing functions for the specific investigation fields of an
 * ISA configuration
 * 
 * @author psaroudakis, arendd
 *
 */
public interface InvestigationConfigEnum {
	/**
	 * Provide the name of the field.
	 * 
	 * @return name
	 */
	public String getFieldName();

	/**
	 * Check if the specific field is required in the corresponding configuration
	 * 
	 * @return <true> if the field is required
	 */
	public boolean isRequired();

	/**
	 * Provide the corresponding block of the field within the {@link Investigation}
	 * File
	 * 
	 * @return section
	 */
	public InvestigationAttribute getSection();
}
