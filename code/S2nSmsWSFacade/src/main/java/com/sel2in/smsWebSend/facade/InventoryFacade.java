package com.sel2in.smsWebSend.facade;

import com.sel2in.smsWebSend.facade.dto.MedicineInventoryDto;
import com.sel2in.smsWebSend.facade.dto.response.MedicineInventoryResDto;

public interface InventoryFacade {

	MedicineInventoryResDto saveMedicine(MedicineInventoryDto medicineInventoryDto);

	MedicineInventoryResDto getMedicineList(String searchText);
	
	MedicineInventoryResDto doReport();

}
