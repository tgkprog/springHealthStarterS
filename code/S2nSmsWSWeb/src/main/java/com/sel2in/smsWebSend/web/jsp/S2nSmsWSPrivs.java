package com.sel2in.smsWebSend.web.jsp;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;


import com.sel2in.smsWebSend.facade.S2nSmsWSWebserviceFacade;
import com.sel2in.smsWebSend.facade.impl.S2nSmsWSWebserviceFacadeImpl;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.User;
import com.sel2in.smsWebSend.utils.JsonUtils;

public class S2nSmsWSPrivs extends SimpleTagSupport {
	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(S2nSmsWSPrivs.class.getName());
	private S2nSmsWSWebserviceFacade webserviceFacade = new S2nSmsWSWebserviceFacadeImpl();
	private String group;
	
	public void setGroup(String group) {
		this.group = group;
	}

	public void doTag() throws JspException, IOException {
		logger.log(Sel2inLogger.INFO, "Group: " + group);
		try {
			User loggedInUser = (User) getJspContext().getAttribute("CURRENT_USER", PageContext.SESSION_SCOPE);
			Set<String> privs = new HashSet<String>();
			if(group == null) {
				privs = webserviceFacade.getAllPrivileges(loggedInUser);
			} else {
				privs = webserviceFacade.getAllPrivileges(loggedInUser, group);
			}
			String privJson = JsonUtils.objectToJson(privs);
			logger.log(Sel2inLogger.INFO, "privJson: " + privJson);
			getJspContext().getOut().write(privJson);
		} catch (Exception e) {
			logger.log(Sel2inLogger.ERROR, e, e);
			throw new SkipPageException("Exception in S2nSmsWSPrivs " + e);
		}
	}
}
