package com.sel2in.smsWebSend.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import com.sel2in.smsWebSend.facade.InventoryFacade;
import com.sel2in.smsWebSend.facade.dto.MedicineInventoryDto;
import com.sel2in.smsWebSend.facade.dto.response.MedicineInventoryResDto;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.MedicineInventory;
import com.sel2in.smsWebSend.services.InventoryService;

@Transactional(rollbackFor=Exception.class)
public class InventoryFacadeImpl implements InventoryFacade {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(InventoryFacadeImpl.class.getName());

	@Autowired(required = true)
	private InventoryService inventoryService;

	public InventoryService getInventoryService() {
		return inventoryService;
	}

	public void setInventoryService(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	@PreAuthorize("hasRole('inventory_add_edit_medicine')")
	public MedicineInventoryResDto saveMedicine(MedicineInventoryDto medicineInventoryDto) {
		logger.log(Sel2inLogger.INFO, "saveMedicine()");
		Integer result = null;
		MedicineInventory inventory = new MedicineInventory();
		MedicineInventoryResDto medicineInventoryResDto = new MedicineInventoryResDto();
		// Copy the data MedicineInventoryDto to MedicineInventory
		if (medicineInventoryDto.getId() > 0) {
			inventory.setId(medicineInventoryDto.getId());
		}
		inventory.setMedicineName(medicineInventoryDto.getMedicineName());
		inventory.setThresoldQuantity(medicineInventoryDto.getThresoldQuantity());
		inventory.setAvailableQuantity(medicineInventoryDto.getAvailableQuantity());
		try {
			result = inventoryService.saveMedicine(inventory);
			logger.log(Sel2inLogger.INFO, "Save Medicine Result = " + result);
		} catch (Exception e) {
			logger.log(Sel2inLogger.ERROR, "Exception: saveMedicine()");
			if (e instanceof DataIntegrityViolationException) {
				medicineInventoryResDto.setErrorNo(1);
				medicineInventoryResDto.setErrorMsg("Duplicate Entry, Mobile or Email Already Exists");
				logger.log(Sel2inLogger.ERROR, "DataIntegrityViolationException: Duplicate Entry, Mobile or Email Already Exists");
			} else {
				medicineInventoryResDto.setErrorMsg(e.getMessage());
				medicineInventoryResDto.setErrorNo(0);
				logger.log(Sel2inLogger.ERROR, "Exception: saveMedicine() - " + e.getMessage());
			}
		}
		if (result != null) {
			if (medicineInventoryResDto.getId() > 0 && medicineInventoryDto.getId() == result) {
				medicineInventoryResDto.setSuccessMsg("New Inventory Edited");
				logger.log(Sel2inLogger.INFO, "New Inventory Edited");
			} else {
				medicineInventoryResDto.setId(result);
				medicineInventoryResDto.setErrorNo(0);
				medicineInventoryResDto.setSuccessMsg("New Inventory Added");
				logger.log(Sel2inLogger.INFO, "New Inventory Added");
			}
		}
		return medicineInventoryResDto;
	}

	@PreAuthorize("hasRole('inventory_get_medicine_list')")
	public MedicineInventoryResDto getMedicineList(String searchText) {
		logger.log(Sel2inLogger.INFO, "getMedicineList()");
		MedicineInventoryResDto inventoryResDto = null;
		List<MedicineInventoryDto> miList = null;
		MedicineInventoryDto mi = null;
		try {
			inventoryResDto = new MedicineInventoryResDto();
			List<MedicineInventory> medList = inventoryService.getMedicineList(searchText);
			logger.log(Sel2inLogger.INFO, "medList: " + medList);
			if (medList != null && medList.size() > 0) {
				miList = new ArrayList<MedicineInventoryDto>();
				for (MedicineInventory med : medList) {
					mi = new MedicineInventoryDto();
					mi.setId(med.getId());
					mi.setMedicineName(med.getMedicineName());
					mi.setThresoldQuantity(med.getThresoldQuantity());
					mi.setAvailableQuantity(med.getAvailableQuantity());
					miList.add(mi);
				}
				if (miList != null && miList.size() > 0) {
					inventoryResDto.setList(miList);
				}
			}
			logger.log(Sel2inLogger.INFO, "Success: getMedicineList()");
		} catch (Exception e) {
			logger.log(Sel2inLogger.ERROR, "Exception: getMedicineList()");
			if (e instanceof DataIntegrityViolationException) {
				inventoryResDto.setErrorNo(1);
				inventoryResDto.setErrorMsg("Search Medicine List Fail");
				logger.log(Sel2inLogger.ERROR, "DataIntegrityViolationException: " + e.getMessage());
			} else {
				inventoryResDto.setErrorMsg(e.getMessage());
				inventoryResDto.setErrorNo(0);
				logger.log(Sel2inLogger.ERROR, "Exception: " + e.getMessage());
			}
		}
		return inventoryResDto;
	}

	@PreAuthorize("hasRole('inventory_send_report')")
	public MedicineInventoryResDto doReport() {
		logger.log(Sel2inLogger.INFO, "doReport()");
		MedicineInventoryResDto response = new MedicineInventoryResDto();
		try {
			inventoryService.doReport();
			response.setErrorNo(0);
			response.setSuccessMsg("Report Sent");
			logger.log(Sel2inLogger.INFO, "Report Sent");
		} catch (Exception e) {
			logger.log(Sel2inLogger.ERROR, "" + e);
			response.setErrorNo(1);
			response.setErrorMsg("Got Exception");
			logger.log(Sel2inLogger.ERROR, "Exception: doReport() - " + e.getMessage());
		}
		return response;
	}

}
