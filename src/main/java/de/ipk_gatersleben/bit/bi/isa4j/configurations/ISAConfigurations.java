package de.ipk_gatersleben.bit.bi.isa4j.configurations;

public enum ISAConfigurations {
	MIAPPEv1x1(MIAPPEv1x1.class);
	
	private Class clazz;
	
	private ISAConfigurations(Class clazz) {
		this.clazz = clazz;
	}
	
	public Class getClazz() {
		return(this.clazz);
	}

}
