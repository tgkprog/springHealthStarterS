package com.sel2in.smsWebSend.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sel2in.smsWebSend.facade.UserFacade;
import com.sel2in.smsWebSend.facade.dto.LoginDto;
import com.sel2in.smsWebSend.facade.dto.response.LoginRespDto;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.User;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(LoginController.class.getName());

	@Autowired(required = true)
	private UserFacade userFacade;

	@RequestMapping(value = "login", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody LoginRespDto login(@RequestBody final LoginDto loginDto) {
		// Print test logs
		Sel2inLogFactory.printLogs();
		logger.log(Sel2inLogger.INFO, "java.util.logging.manager=" + System.getProperty("java.util.logging.manager"));
		logger.log(Sel2inLogger.INFO, "User attempting to login: " + loginDto.getLoginId());
		LoginRespDto loginResponse = new LoginRespDto();
		loginResponse.setLoginId(loginDto.getLoginId());
		User user = userFacade.getUserByEmail(loginDto.getLoginId());
		if (user != null && user.getPassword().equals(loginDto.getPassword())) {
			logger.log(Sel2inLogger.INFO, "User email: " + user.getEmail());
			loginResponse.setErrorNo(0);
			loginResponse.setSuccessMsg("You have successfully loggedin.");
			loginResponse.setLoginSuccess(Boolean.TRUE);
			loginResponse.setUserRole(user.getRoleType());
			loginResponse.setUserId(user.getId());
			loginResponse.setUserFirstName(user.getFirstName());
			loginResponse.setUserLastName(user.getLastName());
			logger.log(Sel2inLogger.INFO, "User Successfully Logged in");
		} else {
			loginResponse.setErrorNo(1);
			loginResponse.setErrorMsg("Invalid Credentials");
			logger.log(Sel2inLogger.WARN, "Invalid Credentials");
		}
		return loginResponse;
	}
	
	@RequestMapping(value = "/clearCache", method = RequestMethod.POST)
	public ModelAndView clearCache(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/success.jsp");
		String clearAllCache = request.getParameter("clearAllCache");
		String clearCache = request.getParameter("clearCache");
		String cacheName = request.getParameter("cacheName");
		logger.log(Sel2inLogger.INFO, "clearAllCache: " + clearAllCache);
		logger.log(Sel2inLogger.INFO, "clearCache: " + clearCache);
		logger.log(Sel2inLogger.INFO, "cacheName: " + cacheName);
		String status = userFacade.clearCache(clearAllCache, clearCache, cacheName);
		mav.addObject("status", status);
		logger.log(Sel2inLogger.INFO, "status: " + status);
		return mav;
	}

}
