package com.sel2in.smsWebSend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sel2in.smsWebSend.dao.PatientRecordDao;
import com.sel2in.smsWebSend.dao.UserDao;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.PatientRecord;
import com.sel2in.smsWebSend.model.User;
import com.sel2in.smsWebSend.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(UserServiceImpl.class.getName());

	@Autowired(required = true)
	private UserDao userDao;

	@Autowired(required = true)
	private PatientRecordDao patientRecordDao;

	public UserServiceImpl() {
	}

	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public PatientRecordDao getPatientRecordDao() {
		return patientRecordDao;
	}

	public void setPatientRecordDao(PatientRecordDao patientRecordDao) {
		this.patientRecordDao = patientRecordDao;
	}

	@CacheEvict(value="userCache", allEntries=true)
	public Integer createUser(User user) throws Exception {
		logger.log(Sel2inLogger.INFO, "createUser()");
		Integer result = null;
		try {
			result = userDao.createUser(user);
			logger.log(Sel2inLogger.INFO, "Success: createUser()");
		} catch (Exception e) {
			logger.log(Sel2inLogger.ERROR, "Exception: createUser()");
			throw new Exception(e.getMessage());
		}
		return result;
	}

	@Cacheable(value="userCache", key="T(com.sel2in.smsWebSend.utils.CacheKeyUtils).generateKey( #p0, 'getUserById' )")
	public User getUserById(int id) {
		logger.log(Sel2inLogger.INFO, "getUserById(): " + id);
		return userDao.getUserById(id);
	}

	@Cacheable(value="userCache", key="T(com.sel2in.smsWebSend.utils.CacheKeyUtils).generateKey( #p0, 'getUserByEmail' )")
	public User getUserByEmail(String email) {
		logger.log(Sel2inLogger.INFO, "getUserByEmail(): " + email);
		return userDao.getUserByEmail(email);
	}

	@Cacheable(value="userCache", key="T(com.sel2in.smsWebSend.utils.CacheKeyUtils).generateKey( #p0, 'getUserByMobile' )")
	public User getUserByMobile(String mobile) {
		logger.log(Sel2inLogger.INFO, "getUserByMobile(): " + mobile);
		return userDao.getUserByMobile(mobile);
	}

	@Cacheable(value="userCache", key="T(com.sel2in.smsWebSend.utils.CacheKeyUtils).generateKey( 'listUsers' )")
	public List<User> listUsers() {
		logger.log(Sel2inLogger.INFO, "listUsers()");
		return userDao.listUsers();
	}

	@CacheEvict(value="userCache", allEntries=true)
	public void updateUser(User user) {
		logger.log(Sel2inLogger.INFO, "updateUser()");
		userDao.updateUser(user);
	}

	@CacheEvict(value="userCache", allEntries=true)
	public void deleteUser(User user) {
		logger.log(Sel2inLogger.INFO, "deleteUser()");
		userDao.deleteUser(user);
	}

	@CacheEvict(value="userCache", allEntries=true)
	public Integer createAppointment(PatientRecord record) throws Exception {
		logger.log(Sel2inLogger.INFO, "createAppointment()");
		Integer result = null;
		try {
			result = patientRecordDao.createPatientRecord(record);
			logger.log(Sel2inLogger.INFO, "Success: UserServiceImpl.createAppointment()");
		} catch (Exception e) {
			logger.log(Sel2inLogger.ERROR, "Exception: UserServiceImpl.createAppointment()");
			throw new Exception(e.getMessage());
		}
		return result;
	}

	@Cacheable(value="userCache", key="T(com.sel2in.smsWebSend.utils.CacheKeyUtils).generateKey( #p0, #p1, 'isDoctorAvailableForTimeSlot' )")
	public boolean isDoctorAvailableForTimeSlot(String doctor, String timeslot) {
		logger.log(Sel2inLogger.INFO, "isDoctorAvailableForTimeSlot(): " + doctor + ", " + timeslot);
		return false;// patientRecordDao.isDoctorAvailableForTimeSlot(doctor,timeslot);
	}

	@Cacheable(value="userCache", key="T(com.sel2in.smsWebSend.utils.CacheKeyUtils).generateKey( #p0, 'loadPatientAppointHistory' )")
	public List<PatientRecord> loadPatientAppointHistory(int patientId) {
		logger.log(Sel2inLogger.INFO, "loadPatientAppointHistory(): " + patientId);
		// return patientRecordDao.loadPatientAppointHistory(patientId);
		return null;
	}

	@Override
	@Cacheable(value="userCache", key="T(com.sel2in.smsWebSend.utils.CacheKeyUtils).generateKey( #p0, 'searchUsersByEmailOrMobile' )")
	public List<User> searchUsersByEmailOrMobile(String searchText) {
		logger.log(Sel2inLogger.INFO, "searchUsersByEmailOrMobile(): " + searchText);
		List<User> users = userDao.searchUsersByEmailOrMobile(searchText);
		logger.log(Sel2inLogger.INFO, "users = " + users);
		return users;
	}

	@CacheEvict(value = { "addressCache", "appointmentCache",
			"departmentCache", "doctorCache", "genderCache",
			"medicineInventoryCache", "patientCache", "userCache" }, allEntries = true)
	public String clearAllCache() {
		logger.log(Sel2inLogger.INFO, "All Cache Cleared");
		return "Success";
	}

	@CacheEvict(value = { "addressCache" }, allEntries = true)
	public String clearAddressCache() {
		logger.log(Sel2inLogger.INFO, "Address Cache Cleared");
		return "Success";
	}

	@CacheEvict(value = { "appointmentCache" }, allEntries = true)
	public String clearAppointmentCache() {
		logger.log(Sel2inLogger.INFO, "Appointment Cache Cleared");
		return "Success";
	}

	@CacheEvict(value = { "departmentCache" }, allEntries = true)
	public String clearDepartmentCache() {
		logger.log(Sel2inLogger.INFO, "Department Cache Cleared");
		return "Success";
	}

	@CacheEvict(value = { "doctorCache" }, allEntries = true)
	public String clearDoctorCache() {
		logger.log(Sel2inLogger.INFO, "Doctor Cache Cleared");
		return "Success";
	}

	@CacheEvict(value = { "genderCache" }, allEntries = true)
	public String clearGenderCache() {
		logger.log(Sel2inLogger.INFO, "Gender Cache Cleared");
		return "Success";
	}

	@CacheEvict(value = { "medicineInventoryCache" }, allEntries = true)
	public String clearMedicineInventoryCache() {
		logger.log(Sel2inLogger.INFO, "Medicine Inventory Cache Cleared");
		return "Success";
	}

	@CacheEvict(value = { "patientCache" }, allEntries = true)
	public String clearPatientCache() {
		logger.log(Sel2inLogger.INFO, "Patient Cache Cleared");
		return "Success";
	}

	@CacheEvict(value = { "userCache" }, allEntries = true)
	public String clearUserCache() {
		logger.log(Sel2inLogger.INFO, "User Cache Cleared");
		return "Success";
	}

}