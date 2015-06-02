package com.sel2in.smsWebSend.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.sel2in.smsWebSend.dao.DoctorDao;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.Doctor;

@Repository
public class DoctorDaoImpl implements DoctorDao {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(DoctorDaoImpl.class.getName());

	protected HibernateTemplate template = null;

	public void setSessionFactory(SessionFactory sessionFactory) {
		template = new HibernateTemplate(sessionFactory);
	}

	public Integer createDoctor(Doctor doctor) throws DataAccessException {
		logger.log(Sel2inLogger.INFO, "Creating Doctor with id: {}" + doctor.getDoc_id());
		try {
			template.saveOrUpdate(doctor);
			logger.log(Sel2inLogger.INFO, "Doctor Created with id: " + doctor.getDoc_id());
		} catch (DataAccessException e) {
			logger.log(Sel2inLogger.ERROR, "DataAccessException: Creating Doctor with id: " + doctor.getDoc_id());
			throw e;
		}
		return template.get(Doctor.class, doctor.getDoc_id()).getDoc_id();
	}

	public Doctor getDoctorById(Integer docId) {
		logger.log(Sel2inLogger.INFO, "getDoctorById(" + docId + ")");
		return template.get(Doctor.class, docId);
	}

	@SuppressWarnings("unchecked")
	public List<Doctor> loadDoctorsByDepartment(int departmentId) {
		logger.log(Sel2inLogger.INFO, "loadDoctorsByDepartment(" + departmentId + ")");
		List<Doctor> doctors = (List<Doctor>) template.find("from Doctor d where d.dept.id = ?", departmentId);
		logger.log(Sel2inLogger.INFO, "doctors: " + doctors);
		return doctors;
	}

	@SuppressWarnings("unchecked")
	public Doctor getDoctorByEmail(String email) {
		logger.log(Sel2inLogger.INFO, "getDoctorByEmail(" + email + ")");
		List<Doctor> doctors = (List<Doctor>) template.find("from Doctor d where d.user.email = ?", email);
		logger.log(Sel2inLogger.INFO, "doctors: " + doctors);
		if (doctors != null && !doctors.isEmpty()) {
			return doctors.get(0);
		} else {
			logger.log(Sel2inLogger.WARN, "No Doctor Found with email id: " + email);
			return null;
		}
	}
}