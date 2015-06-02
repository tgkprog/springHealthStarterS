package com.sel2in.smsWebSend.facade.convert.populator;

import com.sel2in.smsWebSend.facade.dto.PatientRecordDto;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.PatientRecord;
import com.sel2in.smsWebSend.facade.impl.ConfigurationHelper;

public class PatientRecordPopulator implements Populator<PatientRecord, PatientRecordDto> {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(PatientRecordPopulator.class.getName());

	@Override
	public void populate(PatientRecord source, PatientRecordDto target) throws ConversionException {
		logger.log(Sel2inLogger.DEBUG, "populating...");
		target.setPatientRecordId(source.getId());
		target.setAppointmentId(source.getAppopintmentId());
		target.setSummary(source.getSummary());
		target.setPrescription(source.getPrescription());

	}

}
