package com.sel2in.smsWebSend.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;


import com.sel2in.smsWebSend.model.Department;

public interface DepartmentDao {

	Integer createDepartment(Department dept) throws DataAccessException, Exception;

	Department getDepartment(Integer dept_id);

	void updateDepartment(Department dept);

	List<Department> listAllDept();

	Department getDept(String name);

}
