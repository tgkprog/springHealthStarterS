package com.sel2in.smsWebSend.facade;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.sel2in.smsWebSend.model.User;

@Component("S2nSmsWSWebserviceFacade")
public interface S2nSmsWSWebserviceFacade {

	Set<String> getAllRoles(User loggedInUser);

	Set<String> getAllPrivileges(User loggedInUser);
	
	Set<String> getAllPrivileges(User loggedInUser, String group);

	boolean hasPrivilege(User loggedInUser, String privilege);

	boolean hasAllPrivileges(User loggedInUser, List<String> privileges);

}
