package com.sel2in.smsWebSend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sel2in.smsWebSend.dao.DepartmentDao;
import com.sel2in.smsWebSend.dao.DoctorDao;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.Department;
import com.sel2in.smsWebSend.model.Doctor;
import com.sel2in.smsWebSend.services.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	
	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(DepartmentServiceImpl.class.getName());

	@Autowired(required = true)
	private DepartmentDao departmentDao;

	@Autowired(required = true)
	private DoctorDao doctorDao;

	public DepartmentServiceImpl() {
	}

	public DepartmentServiceImpl(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	@Override
	@Cacheable(value="departmentCache", key="T(com.sel2in.smsWebSend.utils.CacheKeyUtils).generateKey( 'listAllDept' )")
	public List<Department> listAllDept() {
		logger.log(Sel2inLogger.DEBUG, "listAllDept()");
		return departmentDao.listAllDept();
	}

	@Override
	@Cacheable(value="doctorCache", key="T(com.sel2in.smsWebSend.utils.CacheKeyUtils).generateKey( #p0, 'loadDoctorsByDepartment' )")
	public List<Doctor> loadDoctorsByDepartment(int departmentId) {
		logger.log(Sel2inLogger.DEBUG, "loadDoctorsByDepartment(): " + departmentId);
		return doctorDao.loadDoctorsByDepartment(departmentId);
	}

	public DepartmentDao getDepartmentDao() {
		return departmentDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public DoctorDao getDoctorDao() {
		return doctorDao;
	}

	public void setDoctorDao(DoctorDao doctorDao) {
		this.doctorDao = doctorDao;
	}
}