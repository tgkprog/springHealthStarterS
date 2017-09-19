package com.sel2in.smsWebSend.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sel2in.smsWebSend.facade.AppointmentFacade;
import com.sel2in.smsWebSend.facade.AptFacade;
import com.sel2in.smsWebSend.facade.UserFacade;
import com.sel2in.smsWebSend.facade.dto.AppointmentRecordDto;
import com.sel2in.smsWebSend.facade.dto.appointment.AptDayDto;
import com.sel2in.smsWebSend.facade.dto.response.AppSettingVOResponseDto;
import com.sel2in.smsWebSend.facade.dto.response.AppointmentSeachResultResDto;
import com.sel2in.smsWebSend.facade.dto.response.ResponseDto;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.AppSettingVO;
import com.sel2in.smsWebSend.services.AppSettingService;

@Controller
@RequestMapping(value = "/appointment")
public class DailyDevSummaryController {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(DailyDevSummaryController.class.getName());

	@Autowired(required = true)
	private AppointmentFacade appointmentFacade;

	@Autowired(required = true)
	private AppSettingService appSettingsService;

	@Autowired(required = true)
	private UserFacade userFacade;

	@Autowired(required = true)
	private AptFacade aptFacade;

	@PreAuthorize("hasRole('appointment_day_apts')")
	@RequestMapping(value = "dayApts", method = RequestMethod.POST)
	public @ResponseBody AptDayDto getApts(@RequestBody final AppointmentRecordDto appointmentDto) {
		AptDayDto res = aptFacade.getApts(appointmentDto.getDoctorId() + "", appointmentDto.getAppointmentDay());
		return res;
	}

	@PreAuthorize("hasRole('appointment_book_appointment')")
	@RequestMapping(value = "bookAppointment", method = RequestMethod.POST)
	public @ResponseBody ResponseDto bookAppointment(@RequestBody final AppointmentRecordDto appointmentDto) {
		ResponseDto responseDto = appointmentFacade.bookAppointment(appointmentDto);
		return responseDto;
	}

	@PreAuthorize("hasRole('appointment_search')")
	@RequestMapping(value = "appointmentSearch", method = RequestMethod.GET)
	public @ResponseBody AppointmentSeachResultResDto getPatientAppointmentHistory(final HttpServletRequest req) {
		String userId = req.getParameter("userId");
		AppointmentSeachResultResDto responseDto = null;
		if (userId != null) {
			responseDto = appointmentFacade.searchAppointmentByUserEmailOrMobile(userId);
		} else {
			responseDto = new AppointmentSeachResultResDto();
			responseDto.setErrorNo(1);
			responseDto.setErrorMsg("User Email/Mobile is required for getting patient appointment");
			logger.log(Sel2inLogger.WARN, "User Email/Mobile is required for getting patient appointment");
		}
		return responseDto;
	}

	@PreAuthorize("hasRole('appointment_notification_to_patients')")
	@RequestMapping(value = "notificationToPatients", method = RequestMethod.GET)
	public @ResponseBody ResponseDto appointmentNotificationToPatient() {
		ResponseDto responseDto = appointmentFacade.appointmentNotificationToPatient();
		return responseDto;
	}

	/*@RequestMapping(value="appSettingdById", method=RequestMethod.GET)
	public @ResponseBody AppSettingVO getAppSettingsById(){
		return appSettingsService.getServiceById("1");
	}*/

	@PreAuthorize("hasRole('appointment_params')")
	@RequestMapping(value = "/params", method = RequestMethod.GET)
	public @ResponseBody AppSettingVOResponseDto  appSettingsList() {
		AppSettingVOResponseDto response = new AppSettingVOResponseDto();
		AppSettingVO appSettingVO = null ;
		try {
			appSettingVO = appSettingsService.getAppSetting("1");
			
			response.setIndexNo(appSettingVO.getIndexNo());
			response.setLanguage(appSettingVO.getLanguage());
			response.setMainId(appSettingVO.getMainId());
			response.setParamName(appSettingVO.getParamName());
			response.setSubId(appSettingVO.getSubId());
			response.setValue(appSettingVO.getValue());
			
			response.setErrorNo(0);
			response.setSuccessMsg("fetched appSetting successfully");
		}catch(Exception e){
			response.setErrorNo(1);
			response.setErrorMsg("Unknown error");
		}
		return response;
	}
}
