package com.sel2in.smsWebSend.rpt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.rpt.dao.DoctorReportDao;
import com.sel2in.smsWebSend.rpt.model.UserCount;
import com.sel2in.smsWebSend.rpt.service.DoctorReportService;

public class DoctorReportServiceImpl implements DoctorReportService {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(DoctorReportServiceImpl.class.getName());

	@Autowired(required = true)
	private DoctorReportDao doctorReportDao;

	public DoctorReportServiceImpl() {
	}

	public DoctorReportServiceImpl(DoctorReportDao doctorReportDao) {
		this.doctorReportDao = doctorReportDao;
	}

	public DoctorReportDao getDoctorReportDao() {
		return doctorReportDao;
	}

	public void setDoctorReportDao(DoctorReportDao doctorReportDao) {
		this.doctorReportDao = doctorReportDao;
	}

	@Override
	public List<UserCount> getNumberAptsPerDoctor() throws Exception {
		logger.log(Sel2inLogger.INFO, "getNumberAptsPerDoctor()");
		List<UserCount> userList = null;
		try {
			userList = doctorReportDao.getNumberAptsPerDoctor();
			logger.log(Sel2inLogger.INFO, "Success: getNumberAptsPerDoctor()");
		} catch (Exception e) {
			logger.log(Sel2inLogger.ERROR, "Exception: " + e.getMessage());
			throw new Exception(e.getMessage());
		}
		logger.log(Sel2inLogger.INFO, "userList: " + userList);
		return userList;
	}

}
