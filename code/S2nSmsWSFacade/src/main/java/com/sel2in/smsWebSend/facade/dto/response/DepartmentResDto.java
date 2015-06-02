package com.sel2in.smsWebSend.facade.dto.response;

import java.util.List;

import com.sel2in.smsWebSend.facade.dto.DepartmentInfoDto;

public class DepartmentResDto extends ResponseDto {
	
	private List<DepartmentInfoDto> departments ;

	public List<DepartmentInfoDto> getDepartments() {
		return departments;
	}

	public void setDepartments(List<DepartmentInfoDto> departments) {
		this.departments = departments;
	}
	
}
