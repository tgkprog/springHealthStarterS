package com.sel2in.smsWebSend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.sel2in.smsWebSend.dao.AppSettingsDao;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.AppSettingVO;
import com.sel2in.smsWebSend.services.AppSettingService;

public class AppSettingServiceImpl implements AppSettingService {
	
	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(AppSettingServiceImpl.class.getName());

	@Autowired(required = true)
	AppSettingsDao appSettingsDao;

	public AppSettingsDao getAppSettingsDao() {
		return appSettingsDao;
	}

	public void setAppSettingsDao(AppSettingsDao appSettingsDao) {
		this.appSettingsDao = appSettingsDao;
	}

	/*
	 * @Override
	 * 
	 * public List<AppSetting> getServiceByObject(AppSetting
	 * appSettingsObj) {
	 * 
	 * return appSettingsDao.getAppsettingsByObject(appSettingsObj); }
	 * 
	 * @Override
	 * 
	 * public AppSetting getServiceById(String indexNo) {
	 * return
	 * appSettingsDao.getAppsettingsById(indexNo).get(0); // will chage in
	 * future }
	 */

	@Override
	@Cacheable(value="appointmentCache", key="T(com.sel2in.smsWebSend.utils.CacheKeyUtils).generateKey( #p0, 'getAppSetting' )")
	public AppSettingVO getAppSetting(String mainId) {
		logger.log(Sel2inLogger.DEBUG, "getAppSetting()");
		AppSettingVO appSettingVO = new AppSettingVO();
		appSettingVO.setMainId(mainId);
		return appSettingsDao.getAppSettingsById(appSettingVO);
	}

	@Override
	@Cacheable(value="appointmentCache", key="T(com.sel2in.smsWebSend.utils.CacheKeyUtils).generateKey( #p0, 'getAppSettings' )")
	public List<AppSettingVO> getAppSettings(String mainId) {
		logger.log(Sel2inLogger.DEBUG, "getAppSettings()");
		AppSettingVO appSettingVO = new AppSettingVO();
		appSettingVO.setMainId(mainId);
		return appSettingsDao.getAppSettings(appSettingVO);
	}

	@Override
	@Cacheable(value="appointmentCache", key="T(com.sel2in.smsWebSend.utils.CacheKeyUtils).generateKey( #p0, 'getAppSettingsById' )")
	public AppSettingVO getAppSettingsById(String main, String subId, String language, String paramName) {
		logger.log(Sel2inLogger.DEBUG, "getAppSettingsById()");
		return null;// TODO
	}

	@Override
	public String getAppSettingsByIndex(int index) {
		logger.log(Sel2inLogger.DEBUG, "getAppSettingsByIndex()");
		AppSettingVO appSettingVO = appSettingsDao.getAppSettingsByIndex(index);
		logger.log(Sel2inLogger.DEBUG, "appSettingVO: " + appSettingVO);
		return appSettingVO.getValue();
	}

	@Override
	public String getAppSettingValye(String main, String subId, String language, String paramName) {
		logger.log(Sel2inLogger.DEBUG, "getAppSettingsByIndex()");
		AppSettingVO appSettingVO = appSettingsDao.getAppSettingValye(main, subId, language, paramName);
		logger.log(Sel2inLogger.DEBUG, "appSettingVO: " + appSettingVO);
		return appSettingVO.getValue();
	}

}
