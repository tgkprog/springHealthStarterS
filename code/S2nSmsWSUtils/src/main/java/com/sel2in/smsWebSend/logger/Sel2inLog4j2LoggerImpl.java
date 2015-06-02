package com.sel2in.smsWebSend.logger;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Sel2inLog4j2LoggerImpl implements Sel2inLogger {
	private transient final Logger logger;

	public Sel2inLog4j2LoggerImpl(String className) {
		System.setProperty("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
		logger = LogManager.getLogger(className);
	}

	public void log(final int level, Object message) {
		message = logger.getName() + ": " + message;
		switch (level) {
		case 1:
			logger.trace(message);
			break;
		case 2:
			logger.info(message);
			break;
		case 3:
			logger.debug(message);
			break;
		case 4:
			logger.warn(message);
			break;
		case 5:
			logger.error(message);
			break;
		default:
			logger.warn("No Logger Level Found");
		}
	}

	public void log(final int level, Object message, final Throwable thrower) {
		message = logger.getName() + ": " + message;
		switch (level) {
		case 1:
			logger.trace(message, thrower);
			break;
		case 2:
			logger.info(message, thrower);
			break;
		case 3:
			logger.debug(message, thrower);
			break;
		case 4:
			logger.warn(message, thrower);
			break;
		case 5:
			logger.error(message, thrower);
			break;
		default:
			logger.warn("No Logger Level Found");
		}
	}

}