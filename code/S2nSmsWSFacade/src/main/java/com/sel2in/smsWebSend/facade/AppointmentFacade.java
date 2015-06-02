package com.sel2in.smsWebSend.facade;

import java.io.File;

import org.springframework.stereotype.Component;

import com.sel2in.smsWebSend.facade.dto.AppointmentInfoDto;
import com.sel2in.smsWebSend.facade.dto.AppointmentRecordDto;
import com.sel2in.smsWebSend.facade.dto.PatientRecordDto;
import com.sel2in.smsWebSend.facade.dto.response.AppointmentHistoryResDto;
import com.sel2in.smsWebSend.facade.dto.response.AppointmentSeachResultResDto;
import com.sel2in.smsWebSend.facade.dto.response.ResponseDto;
import com.sel2in.smsWebSend.facade.dto.response.ViewPatientRecordResDto;
import com.sel2in.smsWebSend.facade.dto.UserRegDto;

@Component("AppointmentFacade") 
public interface AppointmentFacade {

	ResponseDto createUserAndBookAppointment(UserRegDto userRegDTO, AppointmentRecordDto appointmentDto, boolean isDoctorAssociated) throws Exception;

	ResponseDto bookAppointment(final AppointmentRecordDto appointmentDto);

	AppointmentHistoryResDto loadPatientAppointmentHistory(String patientId);
	
	ResponseDto appointmentNotificationToPatient();

	File getPrescriptionAttachmentLocation(final Integer userId,final String formattedDate);
	
	Integer createPatientRecord(final PatientRecordDto patiendRecordDto) throws Exception;

	ViewPatientRecordResDto viewPatientRecord(int appointmentId);

	PatientRecordDto getPatientRecord(Integer patientRecordId);

	AppointmentInfoDto getAppointmentById(int appointmentId);

	File getPrescriptionAttachmentLocationWithoutRoot(Integer patientId, String formattedTodayDateString);

	AppointmentSeachResultResDto searchAppointmentByUserEmailOrMobile(String userId);

	void deleteUserAndAppointments(UserRegDto userRegDto) throws Exception;
	
}
