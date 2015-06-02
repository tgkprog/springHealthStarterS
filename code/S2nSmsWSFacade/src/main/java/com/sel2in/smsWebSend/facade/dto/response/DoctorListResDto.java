package com.sel2in.smsWebSend.facade.dto.response;

import java.util.List;

import com.sel2in.smsWebSend.facade.dto.DoctorInfoDto;

public class DoctorListResDto extends ResponseDto{
	
	private Integer departmentId;
	
	private List<DoctorInfoDto> doctors ;
	
	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	
	public List<DoctorInfoDto> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<DoctorInfoDto> doctors) {
		this.doctors = doctors;
	}

}
