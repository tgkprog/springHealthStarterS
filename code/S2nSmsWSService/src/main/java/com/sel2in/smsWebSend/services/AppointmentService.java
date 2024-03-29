package com.sel2in.smsWebSend.services;

import java.util.Date;
import java.util.List;

import com.sel2in.smsWebSend.model.Appointment;
import com.sel2in.smsWebSend.model.Doctor;
import com.sel2in.smsWebSend.model.PatientRecord;

public interface AppointmentService {

	Integer createPatientRecord(PatientRecord record) throws Exception;

	boolean checkDoctorAvailability(Doctor doctor, String timeslot);

	List<PatientRecord> getRecordsByPatient(String PatientName);

	List<Appointment> getAllAppointment(Date date,Integer doctorId) throws Exception;
	
	List<Appointment> getAllAppointmentByPatient(Integer patientId) throws Exception;

	boolean createAppointment(Appointment appointment);

	int getDayStartHour(Doctor doctor);

	int getDayStartMinute(Doctor doctor);

	int getDayEndHour(Doctor doctor);

	int getDayEndMinute(Doctor doctor);
	
	List<Appointment> appointmentNotificationToPatient() throws Exception;

	PatientRecord getPatientRecordByAppointmentId(int appointmentId);

	PatientRecord getPatientRecordById(Integer id);

	Appointment getAppointmentById(int id);

	List<Appointment> getAppointmentByUserEmailOrMobile(String userId);
	
	void deleteAppointments(List<Appointment> appointments);
}
