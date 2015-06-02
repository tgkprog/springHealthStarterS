package com.sel2in.smsWebSend.web.auth;

import java.io.BufferedReader;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.google.gson.Gson;

public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(CustomUsernamePasswordAuthenticationFilter.class.getName());
	
    private String jsonUsername;
    private String jsonPassword;

    @Override
    protected String obtainPassword(HttpServletRequest request) {
    	logger.log(Sel2inLogger.DEBUG, "obtainPassword()");
        String password = null;
        BufferedReader reader = null;
        try {
        	if (request.getHeader("Content-Type").contains("application/json")) {
            	reader = request.getReader();
                Gson gson = new Gson();
                LoginRequest loginRequest = gson.fromJson(reader, LoginRequest.class);
                password = loginRequest.getPassword();
            }else{
                password = super.obtainPassword(request);
            }
		} catch (Exception e) {
			logger.log(Sel2inLogger.ERROR, e.getMessage());
			logger.log(Sel2inLogger.ERROR, "" + e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e2) {
					logger.log(Sel2inLogger.ERROR, e2.getMessage());
				}
			}
		}
        return password;
    }

    @Override
    protected String obtainUsername(HttpServletRequest request){
    	logger.log(Sel2inLogger.DEBUG, "obtainUsername()");
        String username = null;
        BufferedReader reader = null;
        try {
	        if (request.getHeader("Content-Type").contains("application/json")) {
	        	reader = request.getReader();
                Gson gson = new Gson();
                LoginRequest loginRequest = gson.fromJson(reader, LoginRequest.class);
	            username = loginRequest.getLoginId();
	        }else{
	            username = super.obtainUsername(request);
	        }
        } catch (Exception e) {
        	e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e2) {
					logger.log(Sel2inLogger.ERROR, e2.getMessage());
				}
			}
		}
        return username;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
    	logger.log(Sel2inLogger.DEBUG, "attemptAuthentication()");
        if (request.getHeader("Content-Type").contains("application/json")) {
        	BufferedReader reader = null;
            try {
            	reader = request.getReader();
                Gson gson = new Gson();
                LoginRequest loginRequest = gson.fromJson(reader, LoginRequest.class);
                this.jsonUsername = loginRequest.getLoginId();
                this.jsonPassword = loginRequest.getPassword();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
    			if (reader != null) {
    				try {
    					reader.close();
					} catch (Exception e2) {
						logger.log(Sel2inLogger.ERROR, e2.getMessage());
					}
    			}
    		}
        }
        return super.attemptAuthentication(request, response);
    }
}
