package com.sel2in.smsWebSend.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sel2in.smsWebSend.facade.AptFacade;
import com.sel2in.smsWebSend.facade.dto.AppointmentRecordDto;
import com.sel2in.smsWebSend.facade.dto.DoctorInfoDto;
import com.sel2in.smsWebSend.facade.dto.response.AppointmentLanes;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;

@Controller
@RequestMapping(value = "/doctor")
public class DoctorController {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(DoctorController.class.getName());

	@Autowired(required = true)
	private AptFacade aptFacade;

	@PreAuthorize("hasRole('doctor_schedule')")
	@RequestMapping(value = "schedule", method = RequestMethod.POST)
	public void visit() {
		// TODO
	}

	@PreAuthorize("hasRole('doctor_appointment_details')")
	@RequestMapping(value = "appointmentDetails", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody AppointmentLanes appointmentDetails(@RequestBody AppointmentRecordDto appointmentDto) {
		DoctorInfoDto doctorInfoDto = new DoctorInfoDto(appointmentDto.getDoctorId());
		AppointmentLanes response = aptFacade.getScduleFromService(appointmentDto.getAppointmentDay(), doctorInfoDto);
		response.setErrorNo(0);
		response.setSuccessMsg("fetched schedule successfully");
		logger.log(Sel2inLogger.INFO, "Fetched schedule successfully");
		return response;
	}

}
