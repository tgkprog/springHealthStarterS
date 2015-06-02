package com.sel2in.smsWebSend.services;

import java.util.List;

import com.sel2in.smsWebSend.model.Department;
import com.sel2in.smsWebSend.model.Doctor;

public interface DepartmentService {

	List<Department> listAllDept();

	List<Doctor> loadDoctorsByDepartment(int departmentId);

}
