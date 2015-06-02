package com.sel2in.smsWebSend.facade.convert.populator;

import com.sel2in.smsWebSend.facade.dto.UserInfoDto;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.User;

public class UserInfoPopulator implements Populator<User, UserInfoDto> {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(UserInfoPopulator.class.getName());

	@Override
	public void populate(User source, UserInfoDto target) throws ConversionException {
		logger.log(Sel2inLogger.DEBUG, "populating...");
		target.setEmail(source.getEmail());
		target.setUserId(source.getId());
		target.setFirstName(source.getFirstName());
		target.setLastName(source.getLastName());
		target.setMobile(source.getMobile());
		target.setRoleType(source.getRoleType());
		if (source.getRoleType().equals("Doctor") || source.getRoleType().equals("Staff") || source.getRoleType().equals("Admin")) {
			target.setAdmin(Boolean.TRUE);
		}
	}

}
