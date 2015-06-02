package com.sel2in.smsWebSend.facade;

import org.springframework.stereotype.Service;

import com.sel2in.smsWebSend.facade.dto.response.DepartmentResDto;
import com.sel2in.smsWebSend.facade.dto.response.DoctorListResDto;

@Service
public interface DepartmentFacade {

	DepartmentResDto getAllDepartment();

	DoctorListResDto loadDoctorsByDepartment(String departmentId);

}
