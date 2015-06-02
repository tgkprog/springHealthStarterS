package com.sel2in.smsWebSend.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.sel2in.smsWebSend.dao.DepartmentDao;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.Department;

@Repository
public class DepartmentDaoImpl implements DepartmentDao {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(DepartmentDaoImpl.class.getName());

	protected HibernateTemplate template = null;

	public void setSessionFactory(SessionFactory sessionFactory) {
		template = new HibernateTemplate(sessionFactory);
	}

	public Integer createDepartment(Department dept) throws DataAccessException {
		logger.log(Sel2inLogger.INFO, "Creating Department with name: " + dept.getDeptName());
		try {
			template.saveOrUpdate(dept);
			logger.log(Sel2inLogger.INFO, "Department Created with id: " + dept.getDept_id());
		} catch (DataAccessException e) {
			logger.log(Sel2inLogger.INFO, "DataAccessException: Creating Department with name: " + dept.getDeptName());
			throw e;
		}
		return template.get(Department.class, dept.getDept_id()).getDept_id();
	}

	public Department getDepartment(Integer dept_id) {
		logger.log(Sel2inLogger.INFO, "getDepartment(" + dept_id + ")");
		return template.get(Department.class, dept_id);
	}

	public void updateDepartment(Department dept) {
		logger.log(Sel2inLogger.INFO, "Updating Department with name: " + dept.getDeptName());
		template.update(dept);

	}

	@SuppressWarnings("unchecked")
	public List<Department> listAllDept() {
		logger.log(Sel2inLogger.INFO, "listAllDept");
		List<Department> departmentList = (List<Department>) template.find("from Department");
		logger.log(Sel2inLogger.INFO, "departmentList: " + departmentList);
		return departmentList;
	}

	@Override
	public Department getDept(String name) {
		logger.log(Sel2inLogger.INFO, "getDept(" + name + ")");
		return (Department) template.find("from Department d where d.deptName = ?", name);
	}
}