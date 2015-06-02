package com.sel2in.smsWebSend.services.notifications;

import java.util.List;

import com.sel2in.smsWebSend.model.MedicineInventory;

public interface LowInventoryLevelNotifier {
	List<MedicineInventory> processInventoryReport() throws Exception;
	void autoProcessInventoryReport();
}
