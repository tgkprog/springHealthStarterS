package com.sel2in.smsWebSend.dao.test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.sel2in.smsWebSend.dao.AppSettingsDao;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.AppSettingVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:dao-context.xml" })
@TransactionConfiguration(transactionManager = "transactionManager")
public class AppSettingsDaoImplTest {

	static {
		System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
    }
	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(AppSettingsDaoImplTest.class.getName());
	
	@Autowired
	private AppSettingsDao appSettingsDao;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetAppSettingsByIndex() {
		AppSettingVO vo = appSettingsDao.getAppSettingsByIndex(1);
		logger.log(Sel2inLogger.INFO, vo);
		assertEquals("Oman", vo.getValue());
	}
	
	@Test
	public void testGetAppSettingValye() {
		AppSettingVO vo = appSettingsDao.getAppSettingValye("1" , "1", "English", "Res_Root_Absolute");
		logger.log(Sel2inLogger.INFO, vo);
		assertEquals("/data/tomcat/", vo.getValue());
	}

}
