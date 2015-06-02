package com.sel2in.smsWebSend.web.utils;

import java.util.Set;

import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.Privilege;
import com.sel2in.smsWebSend.model.Role;

public class StringUtils {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(StringUtils.class.getName());

	private StringUtils() {
	}

	public static boolean isNullOrEmpty(String data) {
		logger.log(Sel2inLogger.DEBUG, "data: " + data);
		return data == null || data.isEmpty();
	}

	public static Integer getInt(String data) {
		logger.log(Sel2inLogger.DEBUG, "data: " + data);
		Integer dataInt = null;
		try {
			dataInt = Integer.parseInt(data);
		} catch (NumberFormatException e) {
		}
		logger.log(Sel2inLogger.DEBUG, "dataInt: " + dataInt);
		return dataInt;
	}
	
	public static String getRoleString(Set<Role> roles) {
		String roleString = "";
		if (roles != null && !roles.isEmpty()) {
			for (Role role : roles) {
				roleString = "," + role.getName();
			}
		}
		if (roleString.startsWith(",")) {
			roleString = roleString.substring(1);
		}
		logger.log(Sel2inLogger.DEBUG, "roleString: " + roleString);
		return roleString;
	}

	public static String getPrivilegeString(Set<Privilege> privileges) {
		String privilegeString = "";
		if (privileges != null && !privileges.isEmpty()) {
			for (Privilege privilege : privileges) {
				privilegeString = "," + privilege.getName();
			}
		}
		if (privilegeString.startsWith(",")) {
			privilegeString = privilegeString.substring(1);
		}
		logger.log(Sel2inLogger.DEBUG, "roleString: " + privilegeString);
		return privilegeString;
	}

}
