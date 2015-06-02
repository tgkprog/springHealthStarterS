package com.sel2in.smsWebSend.services;

import java.util.List;

import com.sel2in.smsWebSend.model.PatientRecord;
import com.sel2in.smsWebSend.model.User;

public interface UserService {

	Integer createUser(User user) throws Exception;

	User getUserById(int id);

	User getUserByEmail(String email);

	User getUserByMobile(String mobile);

	List<User> listUsers();

	void updateUser(User user);

	void deleteUser(User user);

	Integer createAppointment(PatientRecord record) throws Exception;

	boolean isDoctorAvailableForTimeSlot(String doctor, String timeslot);

	List<PatientRecord> loadPatientAppointHistory(int patientId);

	List<User> searchUsersByEmailOrMobile(String searchText);

	String clearAllCache();

	String clearAddressCache();

	String clearAppointmentCache();

	String clearDepartmentCache();

	String clearDoctorCache();

	String clearGenderCache();

	String clearMedicineInventoryCache();

	String clearPatientCache();

	String clearUserCache();
}
