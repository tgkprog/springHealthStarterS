package com.sel2in.smsWebSend.web.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;

import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;

public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {
	
	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(AuthenticationEntryPoint.class.getName());

    @Override
    public void commence(HttpServletRequest arg0, HttpServletResponse arg1, AuthenticationException arg2) throws IOException, ServletException {
    	logger.log(Sel2inLogger.DEBUG, "commence()");
        arg1.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
