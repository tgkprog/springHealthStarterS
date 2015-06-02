package com.sel2in.smsWebSend.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sel2in.smsWebSend.facade.DepartmentFacade;
import com.sel2in.smsWebSend.facade.dto.response.DepartmentResDto;
import com.sel2in.smsWebSend.facade.dto.response.DoctorListResDto;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;

@Controller
@RequestMapping(value = "/department")
public class DepartmentController {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(DepartmentController.class.getName());

	@Autowired(required = true)
	private DepartmentFacade departmentFacade;

	@PreAuthorize("hasRole('department_all')")
	@RequestMapping(value = "allDepartment", method = RequestMethod.GET)
	public @ResponseBody DepartmentResDto getAllDepartment() {
		DepartmentResDto response = departmentFacade.getAllDepartment();
		return response;
	}

	@PreAuthorize("hasRole('department_all_doctor_by_department')")
	@RequestMapping(value = "allDoctorByDepartment", method = RequestMethod.GET)
	public @ResponseBody DoctorListResDto getDoctorListByDepartment(final HttpServletRequest req) {
		String departmentId = req.getParameter("departmentId");
		logger.log(Sel2inLogger.INFO, "departmentId: " + departmentId);
		DoctorListResDto response = departmentFacade.loadDoctorsByDepartment(departmentId);
		return response;
	}

	public DepartmentFacade getDepartmentFacade() {
		return departmentFacade;
	}

	public void setDepartmentFacade(DepartmentFacade departmentFacade) {
		this.departmentFacade = departmentFacade;
	}

}
