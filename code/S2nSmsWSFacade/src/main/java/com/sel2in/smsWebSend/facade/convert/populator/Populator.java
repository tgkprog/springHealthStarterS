package com.sel2in.smsWebSend.facade.convert.populator;

public interface Populator<SOURCE, TARGET> {
	void populate(SOURCE source, TARGET target) throws ConversionException;
	
}
