package com.sel2in.smsWebSend.services;

import java.util.List;

import com.sel2in.smsWebSend.model.MedicineInventory;

public interface InventoryService {

	Integer saveMedicine(MedicineInventory medicineInventory) throws Exception;

	List<MedicineInventory> getMedicineList(String searchText) throws Exception;
	
	List<MedicineInventory> doReport() throws Exception;

}
