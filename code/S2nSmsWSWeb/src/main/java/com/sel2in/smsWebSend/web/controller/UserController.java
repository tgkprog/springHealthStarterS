package com.sel2in.smsWebSend.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sel2in.smsWebSend.facade.AppointmentFacade;
import com.sel2in.smsWebSend.facade.UserFacade;
import com.sel2in.smsWebSend.facade.dto.UserRegDto;
import com.sel2in.smsWebSend.facade.dto.response.AppointmentHistoryResDto;
import com.sel2in.smsWebSend.facade.dto.response.SearchResultResDto;
import com.sel2in.smsWebSend.facade.dto.response.UserRegRespDto;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.web.utils.ControllerUtils;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(UserController.class.getName());

	@Autowired(required = true)
	private UserFacade userFacade;

	@Autowired(required = true)
	private AppointmentFacade appointmentFacade;

	@PreAuthorize("hasRole('user_registration')")
	@RequestMapping(value = "registration", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody UserRegRespDto registration(@RequestBody final UserRegDto userRegDto) {
		ControllerUtils.addAddressToUserRegDtoForAngularApp(userRegDto);
		UserRegRespDto responseDto = userFacade.createUser(userRegDto);
		return responseDto;
	}

    @PreAuthorize("hasRole('user_search')")
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public @ResponseBody SearchResultResDto search(final HttpServletRequest req) {
		String searchText = req.getParameter("searchText");
		logger.log(Sel2inLogger.INFO, "searchText: " + searchText);
		SearchResultResDto responseDto = userFacade.searchUserByEmailOrMobile(searchText);
		logger.log(Sel2inLogger.INFO, "responseDto.getResult(): " + responseDto.getResult());
		return responseDto;
	}

    @PreAuthorize("hasRole('user_profile')")
	@RequestMapping(value = "profile", method = RequestMethod.POST)
	public void profile() {
		// TODO
	}

    @PreAuthorize("hasRole('user_visits')")
	@RequestMapping(value = "visits", method = RequestMethod.GET)
	public @ResponseBody AppointmentHistoryResDto visit(final HttpServletRequest req) {
		String patientId = req.getParameter("patientId");
		logger.log(Sel2inLogger.INFO, "patientId: " + patientId);
		AppointmentHistoryResDto response = appointmentFacade.loadPatientAppointmentHistory(patientId);
		return response;
	}

}
