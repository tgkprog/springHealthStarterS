package com.sel2in.smsWebSend.rpt.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.rpt.facade.DoctorReportFacade;
import com.sel2in.smsWebSend.rpt.facade.dto.response.UserCountRes;
import com.sel2in.smsWebSend.rpt.model.UserCount;
import com.sel2in.smsWebSend.rpt.service.DoctorReportService;

@Transactional(rollbackFor=Exception.class)
public class DoctorReportFacadeImpl implements DoctorReportFacade {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(DoctorReportFacadeImpl.class.getName());

	@Autowired(required = true)
	private DoctorReportService doctorReportService;

	@Override
	public UserCountRes getNumberAptsPerDoctor() {
		logger.log(Sel2inLogger.INFO, "getNumberAptsPerDoctor()");
		UserCountRes userCountRes = new UserCountRes();
		try {
			List<UserCount> list = doctorReportService.getNumberAptsPerDoctor();
			if (list != null && list.size() > 0) {
				userCountRes.setUserCountList(list);
			}
			logger.log(Sel2inLogger.INFO, "Success: getNumberAptsPerDoctor()");
		} catch (Exception e) {
			logger.log(Sel2inLogger.ERROR, "Exception: getNumberAptsPerDoctor()");
			if (e instanceof DataIntegrityViolationException) {
				userCountRes.setErrorNo(1);
				userCountRes.setErrorMsg("Getting User count List Fail");
				logger.log(Sel2inLogger.ERROR, "DataIntegrityViolationException: Getting User count List Fail");
			} else {
				userCountRes.setErrorMsg(e.getMessage());
				userCountRes.setErrorNo(0);
				logger.log(Sel2inLogger.ERROR, "Exception: " + e.getMessage());
			}
		}
		return userCountRes;
	}

}
