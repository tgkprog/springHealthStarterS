package com.sel2in.smsWebSend.service.test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sel2in.smsWebSend.dao.PatientRecordDao;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.PatientRecord;
import com.sel2in.smsWebSend.model.User;
import com.sel2in.smsWebSend.services.AppointmentService;
import com.sel2in.smsWebSend.services.impl.AppointmentServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:service-context.xml")
public class AppointmentServiceTest {

	static {
		System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
    }

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(AppointmentServiceTest.class.getName());
	private static PatientRecordDao patientRecordDao;
	
	private static User mockUser1;
	private static PatientRecord mockPatientRecord;
	
	private AppointmentService appointmentService;
	
	@Before
	public void setUp() throws DataAccessException, Exception{
		logger.log(Sel2inLogger.INFO, "AppointmentServiceTest starts...");
		Sel2inLogFactory.printLogs();
		patientRecordDao = mock(PatientRecordDao.class);
		appointmentService = new AppointmentServiceImpl(patientRecordDao);
		
		mockUser1 = new User("firstname1","lastname1","test1@email.com",new Date(),91, "001", "mph");
		mockPatientRecord = new PatientRecord();
		
		when(patientRecordDao.createPatientRecord(mockPatientRecord)).thenReturn(mockPatientRecord.getId());
	}
	
	@After
	public void tearDown() throws Exception {
		logger.log(Sel2inLogger.INFO, "AppointmentServiceTest ends.");
	}
	
	// implement all test cases for appointment service
	
	@Test
	public void test() throws Exception {
		assertNotNull("AppointmentService test object : ", appointmentService.getClass());
	}
	

}
