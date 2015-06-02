package com.sel2in.smsWebSend.facade.dto.response;

import java.util.List;

import com.sel2in.smsWebSend.facade.dto.UserInfoDto;

public class SearchResultResDto extends ResponseDto{
	
	private String searchText;
	
	private List<UserInfoDto> result;
	
	public List<UserInfoDto> getResult() {
		return result;
	}

	public void setResult(List<UserInfoDto> result) {
		this.result = result;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

}
