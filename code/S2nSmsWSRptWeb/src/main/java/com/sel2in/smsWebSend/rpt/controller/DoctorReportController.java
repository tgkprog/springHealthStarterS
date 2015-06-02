package com.sel2in.smsWebSend.rpt.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.rpt.facade.DoctorReportFacade;
import com.sel2in.smsWebSend.rpt.facade.dto.response.UserCountRes;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "/doctorReport")
public class DoctorReportController {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(DoctorReportController.class.getName());

	@Autowired(required = true)
	private DoctorReportFacade reportFacade;

	@RequestMapping(value = "getNumberAptsPerDoctor", method = RequestMethod.POST)
	public @ResponseBody UserCountRes getNumberAptsPerDoctor(final HttpServletRequest req) {
		UserCountRes userCountRes = null;
		// logger.log(Sel2inLogger.INFO, "Search text :: " + searchText);
		userCountRes = reportFacade.getNumberAptsPerDoctor();
		return userCountRes;
	}

}
