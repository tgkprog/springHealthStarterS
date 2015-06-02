package com.sel2in.smsWebSend.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.sel2in.smsWebSend.model.MedicineInventory;

public interface InventoryDao {

	Integer saveMedicine(MedicineInventory medicineInvertory) throws DataAccessException, Exception;

	List<MedicineInventory> getMedicineList(String searchText) throws DataAccessException, Exception;
	
	List<MedicineInventory> doReport() throws DataAccessException, Exception;
	

}
