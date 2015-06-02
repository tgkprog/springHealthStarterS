package com.sel2in.smsWebSend.dao;

import java.util.Date;

import org.springframework.dao.DataAccessException;

import com.sel2in.smsWebSend.model.PatientRecord;

public interface PatientRecordDao {

	Integer createPatientRecord(PatientRecord record) throws DataAccessException;

	boolean isDoctorAvailable(String doctor, Date date, int startHour, int StartMin);

	PatientRecord getPatientRecordByAppointmentId(int appointmentId);

	PatientRecord getPatientRecordById(Integer id);

}
