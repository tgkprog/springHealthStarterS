package com.sel2in.smsWebSend.rpt.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.sel2in.smsWebSend.rpt.model.UserCount;

public interface DoctorReportDao {
	
	public  List<UserCount> getNumberAptsPerDoctor() throws DataAccessException, Exception;
	
	

}
