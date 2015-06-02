package com.sel2in.smsWebSend.services.notifications.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.sel2in.smsWebSend.dao.InventoryDao;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.MedicineInventory;
import com.sel2in.smsWebSend.services.dto.InventoryReport;
import com.sel2in.smsWebSend.services.notifications.LowInventoryLevelNotifier;

public class LowInventoryLevelNotificationImpl implements LowInventoryLevelNotifier {
	
	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(LowInventoryLevelNotificationImpl.class.getName());

	@Autowired(required = true)
	private InventoryDao inventoryDao;

	@Autowired(required = true)
	private JavaMailSender mailSender;

	@Autowired(required = true)
	private VelocityEngine velocityEngine;

	@Override
	public List<MedicineInventory> processInventoryReport() throws Exception {
		logger.log(Sel2inLogger.DEBUG, "LowInventoryLevelNotificationImpl.processInventoryReport()");
		List<MedicineInventory> medlist = null;
		try {
			medlist = inventoryDao.doReport();
			if (medlist != null) {
				logger.log(Sel2inLogger.DEBUG, "Medicine Inventory list: " + medlist);
				final InventoryReport repot = new InventoryReport();
				repot.setSetListObject(medlist);
				sendReport("moh29Dec@gmail.com", "moh29Dec@gmail.com", repot);
			}
			logger.log(Sel2inLogger.INFO, "Success: LowInventoryLevelNotificationImpl.processInventoryReport()");
		} catch (Exception e) {
			logger.log(Sel2inLogger.ERROR, "Exception: LowInventoryLevelNotificationImpl.processInventoryReport()");
			throw new Exception(e.getMessage());
		}
		return medlist;
	}

	@Override
	public void autoProcessInventoryReport() {
		logger.log(Sel2inLogger.DEBUG, "LowInventoryLevelNotificationImpl.processInventoryReport()");
		logger.log(Sel2inLogger.INFO, "lowInventorylevel Notification report Started executing...");
		List<MedicineInventory> medlist = null;
		try {
			medlist = inventoryDao.doReport();
			if (medlist != null) {
				logger.log(Sel2inLogger.DEBUG, "Scheduler List Medicine Inventory list: " + medlist);
				final InventoryReport repot = new InventoryReport();
				repot.setSetListObject(medlist);
				sendReport("moh29Dec@gmail.com", "moh29Dec@gmail.com", repot);
			}
			logger.log(Sel2inLogger.INFO, "Success: LowInventoryLevelNotificationImpl.processInventoryReport()");
		} catch (Exception e) {
			logger.log(Sel2inLogger.ERROR, "Exception: LowInventoryLevelNotificationImpl.processInventoryReport()");
			logger.log(Sel2inLogger.ERROR, "" + e);
		}
	}

	public void sendReport(final String from, final String to, final Object data) {
		logger.log(Sel2inLogger.DEBUG, "LowInventoryLevelNotificationImpl.sendReport()");
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws VelocityException {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				try {
					message.setFrom(from);
					message.setTo(to);
					message.setSubject("Inventory Notification");
					Map<String, Object> model = new HashMap<String, Object>();
					model.put("key", data);
					@SuppressWarnings("deprecation")
					String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "inventoryReportTemplate.vm", model);
					message.setText(text, true);
					logger.log(Sel2inLogger.INFO, "Success: LowInventoryLevelNotificationImpl.sendReport()");
				} catch (MessagingException e) {
					logger.log(Sel2inLogger.ERROR, "MessagingException: LowInventoryLevelNotificationImpl.sendReport()");
					logger.log(Sel2inLogger.ERROR, "" + e);
				}
			}
		};
		this.mailSender.send(preparator);
	}
}