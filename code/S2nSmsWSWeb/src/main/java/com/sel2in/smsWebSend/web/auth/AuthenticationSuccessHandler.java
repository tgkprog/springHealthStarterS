package com.sel2in.smsWebSend.web.auth;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;

import com.sel2in.smsWebSend.facade.dto.response.LoginRespDto;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.User;

public class AuthenticationSuccessHandler extends
		AbstractAuthenticationTargetUrlRequestHandler
		implements
		org.springframework.security.web.authentication.AuthenticationSuccessHandler {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(AuthenticationSuccessHandler.class.getName());

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		logger.log(Sel2inLogger.DEBUG, "commence()");
		logger.log(Sel2inLogger.DEBUG, "User attempting to login:" + authentication.getName());
		LoginRespDto loginResponse = new LoginRespDto();
		loginResponse.setLoginId(authentication.getName());
		User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
		request.getSession().setAttribute("loggedInUser", user);
		logger.log(Sel2inLogger.DEBUG, "User email: " + user.getEmail());
		loginResponse.setErrorNo(0);
		loginResponse.setSuccessMsg("You have successfully loggedin.");
		loginResponse.setLoginSuccess(Boolean.TRUE);
		loginResponse.setUserRole(user.getRoleType());
		loginResponse.setUserId(user.getId());
		logger.log(Sel2inLogger.DEBUG, "Roles: " + user.getRoles());
		loginResponse.setUserFirstName(user.getFirstName());
		loginResponse.setUserLastName(user.getLastName());
		logger.log(Sel2inLogger.DEBUG, "User Successfully Logged in");
		logger.log(Sel2inLogger.DEBUG, "Exit: login()");
		String loginResponseJson = "{\"errorNo\":0,\"errorMsg\":null,\"successMsg\":\"You have successfully loggedin.\",\"loginId\":\""
				+ authentication.getName()
				+ "\",\"userRole\":\""
				+ user.getRoleType()
				+ "\",\"userFirstName\":\""
				+ user.getFirstName()
				+ "\",\"userLastName\":\""
				+ user.getLastName()
				+ "\",\"userId\":" + user.getId() + ",\"loginSuccess\":true,\"validationFailed\":false}";
		Cookie userCookie = new Cookie("user", URLEncoder.encode(loginResponseJson, "UTF-8"));
		userCookie.setMaxAge(60 * 60 * 24);
		response.addCookie(userCookie);
		logger.log(Sel2inLogger.DEBUG, "loginResponseJson = " + loginResponseJson);
		handle(request, response, authentication);
		postProcessRequest(request, user);
	}

	/**
	 * Removes temporary authentication-related data which may have been stored
	 * in the session during the authentication process.
	 * 
	 * @param request
	 * @param user
	 */
	protected final void postProcessRequest(HttpServletRequest request, User user) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			logger.log(Sel2inLogger.WARN,
					"Current user is NOT attached to the session.");
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		session.setAttribute("CURRENT_USER", user);
		logger.log(Sel2inLogger.DEBUG, "Current user is attached to the session.");
		logger.log(Sel2inLogger.DEBUG, "Roles: " + user.getRoles());
	}
}
