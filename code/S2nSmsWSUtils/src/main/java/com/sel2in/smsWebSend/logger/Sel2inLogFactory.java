package com.sel2in.smsWebSend.logger;

import java.util.logging.Logger;

public class Sel2inLogFactory {

	public static Sel2inLogger getLoggerInstance(final String className) {
		Sel2inLogger logger = new Sel2inLog4j2LoggerImpl(className);
		return logger;
	}

	public static Sel2inLogger getLoggerInstance(final String className, final String contractName) {
		Sel2inLogger logger = new Sel2inLog4j2LoggerImpl(className);
		return logger;
	}
	
	public static void printLogs() {
		printSlf4jLogs();
		printLog4j2Logs();
		printJulLogs();
	}
	
	public static void printSlf4jLogs() {
		final Sel2inLogger logger = new Sel2inSlf4jLoggerImpl(Sel2inLogFactory.class.getName());
		logger.log(Sel2inLogger.INFO,  "--------- Slf4j INFO  Log ---------");
		logger.log(Sel2inLogger.DEBUG, "--------- Slf4j DEBUG Log ---------");
		logger.log(Sel2inLogger.WARN,  "--------- Slf4j WARN  Log ---------");
		logger.log(Sel2inLogger.ERROR, "--------- Slf4j ERROR Log ---------");
	}
	
	public static void printLog4j2Logs() {
		final Sel2inLogger logger = new Sel2inLog4j2LoggerImpl(Sel2inLogFactory.class.getName());
		logger.log(Sel2inLogger.INFO,  "--------- Log4j2 INFO  Log ---------");
		logger.log(Sel2inLogger.DEBUG, "--------- Log4j2 DEBUG Log ---------");
		logger.log(Sel2inLogger.WARN,  "--------- Log4j2 WARN  Log ---------");
		logger.log(Sel2inLogger.ERROR, "--------- Log4j2 ERROR Log ---------");
	}
	
	public static void printJulLogs() {
		final Logger jul = Logger.getLogger("com/sel2in/smsWebSend/logger/Sel2inLogFactory.getName()");
		jul.info(   "--------- J.U.L. INFO    Log ---------");
		jul.warning("--------- J.U.L. WARNING Log ---------");
		jul.severe( "--------- J.U.L. SEVERE  Log ---------");
	}
}