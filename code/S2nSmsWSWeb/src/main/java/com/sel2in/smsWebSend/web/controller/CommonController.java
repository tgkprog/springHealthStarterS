package com.sel2in.smsWebSend.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.User;
import com.sel2in.smsWebSend.web.utils.ControllerUtils;

@Controller
public class CommonController {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(CommonController.class.getName());

	@RequestMapping(value = "/")
	public ModelAndView welcome(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("redirect:/login.jsp");
		User loggedInUser = (User) request.getSession().getAttribute("CURRENT_USER");
		if(loggedInUser != null) {
			logger.log(Sel2inLogger.INFO, "********************User FOUND in Session");
			logger.log(Sel2inLogger.INFO, "loggedInUser: " + loggedInUser.toString());
			mav = new ModelAndView("/resources/angular/index.jsp");
		} else {
			logger.log(Sel2inLogger.INFO, "********************User NOT found in Session");
			if(request.getSession() != null) {
				request.getSession().invalidate();
				logger.log(Sel2inLogger.INFO, "********************Session invalidated**");
			}
			logger.log(Sel2inLogger.INFO, "loggedInUser is NULL");
			ControllerUtils.deleteAllCookies(request, response);
		}
		logger.log(Sel2inLogger.INFO, "\n\n"+mav.getViewName()+"\n\n");
		return mav;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("redirect:/login.jsp");
		logger.log(Sel2inLogger.INFO, "********************Logout called");
		request.getSession().removeAttribute("CURRENT_USER");
		logger.log(Sel2inLogger.INFO, "********************User removed from session");
		request.getSession().invalidate();
		logger.log(Sel2inLogger.INFO, "********************Session invalidated");
		ControllerUtils.deleteAllCookies(request, response);
		return mav;
	}

	@RequestMapping(value = "/loginerr", method = RequestMethod.GET)
	public ModelAndView loginerr(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/loginerr.jsp");
		mav.addObject("loginerr", "Invalid username or password!");
		return mav;
	}

}
