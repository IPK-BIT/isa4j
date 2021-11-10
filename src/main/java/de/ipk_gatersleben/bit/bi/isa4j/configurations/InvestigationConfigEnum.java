package de.ipk_gatersleben.bit.bi.isa4j.configurations;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

import de.ipk_gatersleben.bit.bi.isa4j.components.CommentCollection;
import de.ipk_gatersleben.bit.bi.isa4j.components.Investigation;
import de.ipk_gatersleben.bit.bi.isa4j.components.Study;
import de.ipk_gatersleben.bit.bi.isa4j.configurations.MIAPPEv1x1.InvestigationFile;
import de.ipk_gatersleben.bit.bi.isa4j.constants.InvestigationAttribute;
import de.ipk_gatersleben.bit.bi.isa4j.exceptions.MissingFieldException;

public interface InvestigationConfigEnum {
	public String getFieldName();
	public boolean isRequired();
	public InvestigationAttribute getSection();
}
