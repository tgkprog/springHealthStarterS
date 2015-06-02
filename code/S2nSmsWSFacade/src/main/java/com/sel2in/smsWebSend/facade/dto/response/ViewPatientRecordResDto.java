package com.sel2in.smsWebSend.facade.dto.response;

import com.sel2in.smsWebSend.facade.dto.PatientRecordDto;

public class ViewPatientRecordResDto extends ResponseDto{
	
	private PatientRecordDto patientRecordDto;

	public PatientRecordDto getPatientRecordDto() {
		return patientRecordDto;
	}

	public void setPatientRecordDto(PatientRecordDto patientRecordDto) {
		this.patientRecordDto = patientRecordDto;
	}
	
}
