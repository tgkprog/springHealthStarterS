package com.sel2in.smsWebSend.web.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.sel2in.smsWebSend.facade.UserFacade;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.Privilege;
import com.sel2in.smsWebSend.model.Role;
import com.sel2in.smsWebSend.model.User;

@Repository
public class CustomUserDetailsService implements UserDetailsService {
	
	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(CustomUserDetailsService.class.getName());

    @Autowired
    private UserFacade userFacade;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
    	logger.log(Sel2inLogger.INFO, "loadUserByUsername() = " + name);
        User user = userFacade.getUserByEmail(name);
        if(user == null){
        	throw new UsernameNotFoundException("No user found");
        }
        logger.log(Sel2inLogger.INFO, "loadUserByUsername() = " + user);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            for (Privilege privilege : role.getPrivileges()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(privilege.getName()));
            }
        }
        UserDetails userDetails = new CustomUserDetails(user, grantedAuthorities);
        return userDetails;
    }
}
