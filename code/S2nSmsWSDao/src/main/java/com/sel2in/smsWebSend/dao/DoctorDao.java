package com.sel2in.smsWebSend.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.sel2in.smsWebSend.model.Doctor;

public interface DoctorDao {

	Integer createDoctor(Doctor doctor) throws DataAccessException, Exception;

	Doctor getDoctorById(Integer DocId);

	Doctor getDoctorByEmail(String email);

	List<Doctor> loadDoctorsByDepartment(int departmentId);

}
