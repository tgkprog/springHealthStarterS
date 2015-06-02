package com.sel2in.smsWebSend.facade.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sel2in.smsWebSend.facade.AppointmentFacade;
import com.sel2in.smsWebSend.facade.convert.populator.AppointmentPopulator;
import com.sel2in.smsWebSend.facade.convert.populator.AppointmentSearchResultPopulator;
import com.sel2in.smsWebSend.facade.convert.populator.PatientRecordPopulator;
import com.sel2in.smsWebSend.facade.dto.AppointmentRecordDto;
import com.sel2in.smsWebSend.facade.dto.UserRegDto;
import com.sel2in.smsWebSend.facade.dto.response.AppointmentSeachResultResDto;
import com.sel2in.smsWebSend.facade.impl.AppointmentFacadeImpl;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.services.AppointmentService;
import com.sel2in.smsWebSend.services.DepartmentService;
import com.sel2in.smsWebSend.services.DoctorService;
import com.sel2in.smsWebSend.services.UserService;
import com.sel2in.smsWebSend.services.impl.AppointmentServiceImpl;
import com.sel2in.smsWebSend.services.impl.DepartmentServiceImpl;
import com.sel2in.smsWebSend.services.impl.DoctorServiceImpl;
import com.sel2in.smsWebSend.services.impl.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:facade-context.xml" })
public class AppointmentFacadeTest extends AbstractJUnit4SpringContextTests {
	
	static {
		System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
    }

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(AppointmentFacadeTest.class.getName());
	private UserRegDto userRegDto1;
	private Date dateToday;
	private AppointmentRecordDto appointment;
	private String userEmail1;

	private UserService userService;
	private AppointmentService appointmentService;
	private DepartmentService departmentService;
	private DoctorService doctorService;
	private AppointmentPopulator appointmentPopulator;
	private PatientRecordPopulator patientRecordPopulator;
	private AppointmentSearchResultPopulator appointmentSearchResultPopulator;

	private AppointmentFacade appointmentFacadeMock;

	@Before
	public void setUp() throws Exception {
		logger.log(Sel2inLogger.INFO, "AppointmentFacadeTest starts...");
		Sel2inLogFactory.printLogs();
		userEmail1 = "patient1@gmail.com";
		userService = mock(UserServiceImpl.class);
		appointmentService = mock(AppointmentServiceImpl.class);
		departmentService = mock(DepartmentServiceImpl.class);
		doctorService = mock(DoctorServiceImpl.class);
		appointmentPopulator = mock(AppointmentPopulator.class);
		patientRecordPopulator = mock(PatientRecordPopulator.class);
		appointmentSearchResultPopulator = mock(AppointmentSearchResultPopulator.class);

		appointmentFacadeMock = new AppointmentFacadeImpl(userService,
				appointmentService, departmentService, doctorService,
				appointmentPopulator, patientRecordPopulator,
				appointmentSearchResultPopulator);

		userRegDto1 = getUserRegDtoOne();
	}

	@After
	public void tearDown() throws Exception {
		logger.log(Sel2inLogger.INFO, "AppointmentFacadeTest ends.");
	}

	@Test
	public void testSearchAppointmentByUserEmailOrMobile() {
		AppointmentSeachResultResDto responseDto = appointmentFacadeMock.searchAppointmentByUserEmailOrMobile(userEmail1);
		assertNotNull(responseDto);
		assertEquals(responseDto.getErrorNo(), 0);
	}


	private UserRegDto getUserRegDtoOne() {
		UserRegDto userRegDto = new UserRegDto("First", "One", "first@first.com", new Date(), 1, "1234567890", "moh1");
		return userRegDto;
	}
	
	private UserRegDto getUserRegDtoTwo() {
		UserRegDto userRegDto = new UserRegDto("Second", "Two", "second@second.com", new Date(), 91, "9876543210", "moh2");
		return userRegDto;
	}

}
