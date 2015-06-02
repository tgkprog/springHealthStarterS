package com.sel2in.smsWebSend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sel2in.smsWebSend.dao.DoctorDao;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.Doctor;
import com.sel2in.smsWebSend.services.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {
	
	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(DoctorServiceImpl.class.getName());

	@Autowired(required = true)
	private DoctorDao doctorDao;

	@Override
	@Cacheable(value="doctorCache", key="T(com.sel2in.smsWebSend.utils.CacheKeyUtils).generateKey( #p0, 'getDoctorById' )")
	public Doctor getDoctorById(Integer doctorId) {
		logger.log(Sel2inLogger.INFO, "getDoctorById(): " + doctorId);
		return doctorDao.getDoctorById(doctorId);
	}

	public DoctorDao getDoctorDao() {
		return doctorDao;
	}

	public void setDoctorDao(DoctorDao doctorDao) {
		this.doctorDao = doctorDao;
	}
}