package com.sel2in.smsWebSend.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import com.sel2in.smsWebSend.facade.DepartmentFacade;
import com.sel2in.smsWebSend.facade.dto.DepartmentInfoDto;
import com.sel2in.smsWebSend.facade.dto.DoctorInfoDto;
import com.sel2in.smsWebSend.facade.dto.response.DepartmentResDto;
import com.sel2in.smsWebSend.facade.dto.response.DoctorListResDto;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.Department;
import com.sel2in.smsWebSend.model.Doctor;
import com.sel2in.smsWebSend.services.DepartmentService;

@Transactional(rollbackFor=Exception.class)
public class DepartmentFacadeImpl implements DepartmentFacade {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(DepartmentFacadeImpl.class.getName());

	@Autowired(required = true)
	private DepartmentService departmentService;

	@PreAuthorize("hasRole('department_all')")
	public DepartmentResDto getAllDepartment() {
		logger.log(Sel2inLogger.INFO, "getAllDepartment()");
		DepartmentResDto response = new DepartmentResDto();
		try {
			List<Department> departments = departmentService.listAllDept();
			List<DepartmentInfoDto> departmentRecords = new ArrayList<DepartmentInfoDto>();
			for (Department dept : departments) {
				DepartmentInfoDto departmentRecord = new DepartmentInfoDto();
				departmentRecord.setDepartmentId(dept.getDept_id());
				departmentRecord.setDepartmentName(dept.getDeptName());
				departmentRecords.add(departmentRecord);
			}
			response.setDepartments(departmentRecords);
			response.setErrorNo(0);
			response.setSuccessMsg("getting All DepartmentList Done");
			logger.log(Sel2inLogger.INFO, "Success: getAllDepartment()");
		} catch (Exception e) {
			response.setErrorNo(1);
			response.setErrorMsg("Error while getting All DepartmentList");
			logger.log(Sel2inLogger.ERROR, "Exception: getAllDepartment()");
		}
		logger.log(Sel2inLogger.INFO, "response = " + response);
		return response;
	}

	@PreAuthorize("hasRole('department_all_doctor_by_department')")
	public DoctorListResDto loadDoctorsByDepartment(String departmentIdString) {
		logger.log(Sel2inLogger.INFO, "loadDoctorsByDepartment()");
		DoctorListResDto response = new DoctorListResDto();
		try {
			int departmentId = Integer.parseInt(departmentIdString);
			response.setDepartmentId(departmentId);
			List<Doctor> doctors = departmentService.loadDoctorsByDepartment(departmentId);
			// populator
			List<DoctorInfoDto> docInfos = new ArrayList<DoctorInfoDto>();
			for (Doctor doc : doctors) {
				DoctorInfoDto docInfo = new DoctorInfoDto(doc.getDoc_id());
				docInfo.setName(doc.getUser().getFullName());
				docInfos.add(docInfo);
			}
			response.setDoctors(docInfos);
			logger.log(Sel2inLogger.INFO, "Success: loadDoctorsByDepartment()");
		} catch (NumberFormatException e) {
			response.setErrorNo(1);
			response.setErrorMsg("Deparment Id is in wrong format:" + departmentIdString);
			logger.log(Sel2inLogger.ERROR, "NumberFormatException: loadDoctorsByDepartment()");
		}
		logger.log(Sel2inLogger.INFO, "response = " + response);
		return response;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

}
