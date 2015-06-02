package com.sel2in.smsWebSend.facade.impl;

import java.io.File;

import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.services.AppSettingService;

public class ConfigurationHelper {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(ConfigurationHelper.class.getName());

	/*
	 * DO NOT change call to appSettingService.appSettingService(int index). The
	 * value is referred in appServlet.xml for defining as staticResource, for
	 * downloading report.
	 */

	public static final File getFolderPrescriptionPath(final AppSettingService appSettingService , final String username, final String todayDateString) {
		logger.log(Sel2inLogger.INFO, "getFolderPrescriptionPath()");
		File f = new File(appSettingService.getAppSettingValye("1" , "1", "English", "Res_Root_Absolute"), appSettingService.getAppSettingValye("1" , "1", "English", "Attachment_Dir_Location"));
		String attachmentDirPath = username + "/" + todayDateString;
		File mediaDir = new File(f, attachmentDirPath);
		if (false == mediaDir.exists()) {
			logger.log(Sel2inLogger.INFO, "Directory Not found. creating mediaDir ");
			mediaDir.mkdirs();
		} else {
			logger.log(Sel2inLogger.INFO, "Skipping, directory exists");
		}
		return mediaDir;
	}

	public static final File getFolderPrescriptionPathWithoutRoot(final AppSettingService appSettingService , final String username, final String todayDateString) {
		logger.log(Sel2inLogger.INFO, "getFolderPrescriptionPathWithoutRoot()");
		String attachmentDirPath = username + "/" + todayDateString;
		File mediaDir = new File(appSettingService.getAppSettingValye("1" , "1", "English", "Attachment_Dir_Location"), attachmentDirPath);
		return mediaDir;
	}

	public static final String getFileDownloadUrl(final AppSettingService appSettingService , String reportPath) {
		logger.log(Sel2inLogger.INFO, "getFileDownloadUrl(): " + reportPath);
		return appSettingService.getAppSettingValye("1" , "1", "English", "DOWNLOAD_REPORT_PREFIX") + reportPath;
	}

}
