package com.sel2in.smsWebSend.facade.dto.response;

import java.util.List;

import com.sel2in.smsWebSend.facade.dto.MedicineInventoryDto;

public class MedicineInventoryResDto extends ResponseDto{
	
	private int id;
	
	private List<MedicineInventoryDto> list;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<MedicineInventoryDto> getList() {
		return list;
	}

	public void setList(List<MedicineInventoryDto> list) {
		this.list = list;
	}

	
	
	
	
	
	
	
}
