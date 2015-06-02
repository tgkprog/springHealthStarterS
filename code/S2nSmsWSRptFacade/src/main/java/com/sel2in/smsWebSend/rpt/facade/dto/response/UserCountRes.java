package com.sel2in.smsWebSend.rpt.facade.dto.response;



import java.util.List;

import com.sel2in.smsWebSend.facade.dto.response.ResponseDto;
import com.sel2in.smsWebSend.rpt.model.UserCount;

public class UserCountRes extends ResponseDto {
	
	private List<UserCount> userCountList;

	public List<UserCount> getUserCountList() {
		return userCountList;
	}

	public void setUserCountList(List<UserCount> userCountList) {
		this.userCountList = userCountList;
	}

	
	
}
