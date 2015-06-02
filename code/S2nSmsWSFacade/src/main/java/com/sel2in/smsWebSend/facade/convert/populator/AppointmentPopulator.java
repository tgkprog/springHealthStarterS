package com.sel2in.smsWebSend.facade.convert.populator;

import com.sel2in.smsWebSend.facade.dto.AppointmentInfoDto;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.Appointment;

public class AppointmentPopulator implements Populator<Appointment, AppointmentInfoDto> {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(AppointmentPopulator.class.getName());

	@Override
	public void populate(Appointment source, AppointmentInfoDto target) throws ConversionException {
		logger.log(Sel2inLogger.DEBUG, "populating...");
		target.setAppointmentId(source.getId());
		target.setVisitedDate(source.getAppointmentDate().toString());
		target.setDoctorName(source.getDoctor().getUser().getFullName());
		target.setPatientId(source.getUser().getId());
	}

}
