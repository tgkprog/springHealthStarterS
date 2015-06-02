package com.sel2in.smsWebSend.facade;

import java.util.Date;

import com.sel2in.smsWebSend.facade.dto.DoctorInfoDto;
import com.sel2in.smsWebSend.facade.dto.appointment.AptDayDto;
import com.sel2in.smsWebSend.facade.dto.response.AppointmentLanes;

public interface AptFacade {
	AppointmentLanes getSchedule(Date date, DoctorInfoDto doctor) throws Exception ;

	AppointmentLanes getScduleFromService(Date date, DoctorInfoDto doctor);

	AptDayDto getApts(String doctorId, Date appointmentDay);
}
