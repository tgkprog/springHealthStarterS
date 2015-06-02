package com.sel2in.smsWebSend.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;

public class DateUtils {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(DateUtils.class.getName());

	private DateUtils() {
	}

	public static Date toDate(final String format, final String textDate) {
		logger.log(Sel2inLogger.INFO, "toDate()");
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(textDate);
		} catch (ParseException e) {
			logger.log(Sel2inLogger.ERROR, "Exception: " + e.getMessage());
		}
		logger.log(Sel2inLogger.INFO, "date = " + date);
		return date;
	}

	public static String toString(final String format, final Date date) {
		String dateString = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			dateString = sdf.format(date);
		} catch (Exception e) {
			logger.log(Sel2inLogger.ERROR, "Exception: " + e.getMessage());
		}
		logger.log(Sel2inLogger.INFO, "dateString = " + dateString);
		return dateString;
	}

	public static boolean isDateExistInDateRange(Date date1, Date date2, Date targetDate) {
		if (targetDate.after(date1) && targetDate.before(date2)) {
			return true;
		}
		return false;
	}

	public static Date getEndDate(Date startDate, int toHour, int toMin) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.set(Calendar.HOUR_OF_DAY, toHour);
		cal.set(Calendar.MINUTE, toMin);
		return cal.getTime();
	}

}
