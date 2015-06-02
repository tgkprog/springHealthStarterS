package com.sel2in.smsWebSend.facade.convert.populator;

import org.springframework.beans.factory.annotation.Autowired;

import com.sel2in.smsWebSend.facade.dto.DoctorInfoDto;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.Doctor;
import com.sel2in.smsWebSend.services.DepartmentService;

public class DoctorToDto implements Populator<DoctorInfoDto, Doctor> {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(DoctorToDto.class.getName());

	@Autowired(required = true)
	private DepartmentService departmentService;

	@Override
	public void populate(DoctorInfoDto src, Doctor t) throws ConversionException {
		logger.log(Sel2inLogger.DEBUG, "populating...");
		t.setDoc_id(src.getDoctorId());

		// dept = null;//TODO departmentService.getDept(stirng name)
		// t.setDept(dept);

		// User u = new User();
		// u.setFirstName(firstName);
		// src.getName()
	}

}
