package com.sel2in.smsWebSend.rpt.service;

import java.util.List;

import com.sel2in.smsWebSend.rpt.model.UserCount;

public interface DoctorReportService {
	
	public List<UserCount> getNumberAptsPerDoctor() throws Exception;

}
