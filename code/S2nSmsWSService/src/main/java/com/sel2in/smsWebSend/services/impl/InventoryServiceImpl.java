package com.sel2in.smsWebSend.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.sel2in.smsWebSend.dao.InventoryDao;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.MedicineInventory;
import com.sel2in.smsWebSend.services.InventoryService;
import com.sel2in.smsWebSend.services.dto.InventoryReport;

public class InventoryServiceImpl implements InventoryService {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(InventoryServiceImpl.class.getName());

	@Autowired(required = true)
	private InventoryDao inventoryDao;

	@Autowired(required = true)
	private JavaMailSender mailSender;

	@Autowired(required = true)
	private VelocityEngine velocityEngine;

	public InventoryServiceImpl() {
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public InventoryServiceImpl(InventoryDao inventoryDao) {
		this.inventoryDao = inventoryDao;
	}

	public InventoryDao getInventoryDao() {
		return inventoryDao;
	}

	public void setInventoryDao(InventoryDao inventoryDao) {
		this.inventoryDao = inventoryDao;
	}

	@CacheEvict(value="medicineInventoryCache", allEntries=true)
	public Integer saveMedicine(MedicineInventory medicineInventory) throws Exception {
		logger.log(Sel2inLogger.DEBUG, "saveMedicine() called with id = " + medicineInventory.getId());
		Integer result = null;
		try {
			result = inventoryDao.saveMedicine(medicineInventory);
			logger.log(Sel2inLogger.INFO, "Success: InventoryServiceImpl.saveMedicine()");
		} catch (Exception e) {
			logger.log(Sel2inLogger.ERROR, "Exception: InventoryServiceImpl.saveMedicine()");
			throw new Exception(e.getMessage());
		}
		return result;
	}

	@Cacheable(value="medicineInventoryCache", key="T(com.sel2in.smsWebSend.utils.CacheKeyUtils).generateKey( #p0, 'getMedicineList' )")
	public List<MedicineInventory> getMedicineList(String searchText) throws Exception {
		logger.log(Sel2inLogger.DEBUG, "getMedicineList(): " + searchText);
		List<MedicineInventory> medlist = null;
		try {
			medlist = inventoryDao.getMedicineList(searchText);
			logger.log(Sel2inLogger.INFO, "Success: InventoryServiceImpl.getMedicineList(): " + searchText);
		} catch (Exception e) {
			logger.log(Sel2inLogger.ERROR, "Exception: InventoryServiceImpl.getMedicineList(): " + searchText);
			throw new Exception(e.getMessage());
		}
		return medlist;
	}

	public List<MedicineInventory> doReport() throws Exception {
		logger.log(Sel2inLogger.DEBUG, "doReport()");
		List<MedicineInventory> medlist = null;
		try {
			medlist = inventoryDao.doReport();
			if (medlist != null) {
				final InventoryReport repot = new InventoryReport();
				repot.setSetListObject(medlist);
				sendReport("moh29Dec@gmail.com", "moh29Dec@gmail.com", repot);
			}
			logger.log(Sel2inLogger.INFO, "Success: InventoryServiceImpl.doReport()");
		} catch (Exception e) {
			logger.log(Sel2inLogger.ERROR, "Exception: InventoryServiceImpl.doReport()");
			throw new Exception(e.getMessage());
		}
		return medlist;
	}

	public void sendReport(final String from, final String to, final Object data) {
		logger.log(Sel2inLogger.DEBUG, "sendReport()");
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws VelocityException {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				try {
					message.setFrom(from);
					message.setTo(to);
					message.setSubject("Inventory Updated Report");
					Map<String, Object> model = new HashMap<String, Object>();
					model.put("key", data);
					@SuppressWarnings("deprecation")
					String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "inventoryReportTemplate.vm", model);
					message.setText(text, true);
					logger.log(Sel2inLogger.INFO, "Success: InventoryServiceImpl.sendReport()");
				} catch (MessagingException e) {
					logger.log(Sel2inLogger.ERROR, "MessagingException: InventoryServiceImpl.sendReport()");
					logger.log(Sel2inLogger.ERROR, "" + e);
				}
			}
		};
		this.mailSender.send(preparator);
	}
}