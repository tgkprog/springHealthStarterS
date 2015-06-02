package com.sel2in.smsWebSend.web.jsp;

import java.io.IOException;
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

public class S2nSmsWSRoles extends SimpleTagSupport {
	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(S2nSmsWSRoles.class.getName());
	private S2nSmsWSWebserviceFacade webserviceFacade = new S2nSmsWSWebserviceFacadeImpl();

	public void doTag() throws JspException, IOException {
		try {
			User loggedInUser = (User) getJspContext().getAttribute("CURRENT_USER", PageContext.SESSION_SCOPE);
			Set<String> roles = webserviceFacade.getAllRoles(loggedInUser);
			String roleJson = JsonUtils.objectToJson(roles);
			logger.log(Sel2inLogger.INFO, "roleJson: " + roleJson);
			getJspContext().getOut().write(roleJson);
		} catch (Exception e) {
			throw new SkipPageException("Exception in S2nSmsWSRoles");
		}
	}
}
