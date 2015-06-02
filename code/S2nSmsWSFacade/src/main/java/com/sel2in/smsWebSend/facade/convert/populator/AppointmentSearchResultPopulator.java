package com.sel2in.smsWebSend.facade.convert.populator;

import com.sel2in.smsWebSend.facade.dto.AppointmentResultDto;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.Appointment;

public class AppointmentSearchResultPopulator implements Populator<Appointment, AppointmentResultDto> {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(AppointmentSearchResultPopulator.class.getName());

	@Override
	public void populate(Appointment source, AppointmentResultDto target) throws ConversionException {
		logger.log(Sel2inLogger.DEBUG, "populating...");
		target.setUserName(source.getUser().getFullName());
		target.setAppointmentDate(source.getAppointmentDate().toString());
		target.setAppointmentId(source.getId());
	}

}
