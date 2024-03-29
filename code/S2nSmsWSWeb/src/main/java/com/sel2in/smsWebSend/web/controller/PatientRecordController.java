package com.sel2in.smsWebSend.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sel2in.smsWebSend.facade.AppointmentFacade;
import com.sel2in.smsWebSend.facade.dto.AppointmentInfoDto;
import com.sel2in.smsWebSend.facade.dto.PatientRecordDto;
import com.sel2in.smsWebSend.facade.dto.response.PatientRecordResDto;
import com.sel2in.smsWebSend.facade.dto.response.ViewPatientRecordResDto;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.services.AppSettingService;
import com.sel2in.smsWebSend.utils.DateUtils;
import com.sel2in.smsWebSend.web.utils.ControllerUtils;

@Controller
@RequestMapping(value = "/patientrecord")
public class PatientRecordController {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(PatientRecordController.class.getName());

	@Autowired(required = true)
	private AppointmentFacade appointmentFacade;
	
	@Autowired
	private AppSettingService appSettingService;

	@PreAuthorize("hasRole('patient_create_record')")
	@RequestMapping(value = "createRecord", method = RequestMethod.POST)
	public @ResponseBody PatientRecordResDto createRecord(@RequestParam(value="ufile",required=false) MultipartFile file,
			MultipartHttpServletRequest multireq) {
		logger.log(Sel2inLogger.INFO, "Uploading file is started...");
		PatientRecordDto patientRecordDto = getPatientDtoFromRequest(multireq);
		AppointmentInfoDto appointmentDto = appointmentFacade.getAppointmentById(patientRecordDto.getAppointmentId());
		String formattedTodayDateString = DateUtils.toString(appSettingService.getAppSettingValye("1" , "1", "English", "PRESCRIPTION_DIR_DATE_FORMAT"), new Date());
		logger.log(Sel2inLogger.INFO, "formattedTodayDateString: " + formattedTodayDateString);
		
		File targetFile = null;
		String filename = null;
		if(file != null){ //user does not want to upload reports
			File aptFolder = appointmentFacade.getPrescriptionAttachmentLocation(appointmentDto.getPatientId(), formattedTodayDateString);
			filename = patientRecordDto.getAppointmentId() + "_" + file.getOriginalFilename();
			logger.log(Sel2inLogger.INFO, "filename: " + filename);
			targetFile = new File(aptFolder, filename);
		}
		
		PatientRecordResDto response = new PatientRecordResDto();
		try {
			if(file != null){
				BufferedOutputStream fout = new BufferedOutputStream(new FileOutputStream(targetFile));
				fout.write(file.getBytes());
				fout.close();
				File relativePathFolder = appointmentFacade.getPrescriptionAttachmentLocationWithoutRoot(appointmentDto.getPatientId(), formattedTodayDateString);
				File targetPathFile = new File(relativePathFolder, filename);
				patientRecordDto.setAttach1Path(targetPathFile.getAbsolutePath());
				response.setUploadMediaSuccess(Boolean.TRUE);
			}
			// write patient record
			Integer patientRecordId = appointmentFacade.createPatientRecord(patientRecordDto);
			if (patientRecordId != null) {
				response.setPatientCreationSuccess(Boolean.TRUE);
				response.setErrorNo(0);
				if(file != null){
					response.setSuccessMsg("Uploading report is done AND Creating patient record is done");
				}else{
					response.setSuccessMsg("Creating patient record is done with NO report");
				}
			}
		} catch (FileNotFoundException e) {
			logger.log(Sel2inLogger.ERROR, "Failed to upload file: " + file.getOriginalFilename());
			logger.log(Sel2inLogger.ERROR, "FileNotFoundException: " + e.getMessage());
			logger.log(Sel2inLogger.ERROR, "" + e);
			response.setErrorNo(1);
			response.setErrorMsg("failed to upload " + file.getOriginalFilename() + ": " + e.getMessage());
		} catch (IOException e) {
			logger.log(Sel2inLogger.ERROR, "Failed to upload file: " + file.getOriginalFilename());
			logger.log(Sel2inLogger.ERROR, "IOException:" + e.getMessage());
			logger.log(Sel2inLogger.ERROR, "" + e);
			response.setErrorNo(1);
			response.setErrorMsg("failed to upload " + file.getOriginalFilename() + ": " + e.getMessage());
		} catch (Exception e) {
			logger.log(Sel2inLogger.ERROR, "Error occured while creating patient record");
			logger.log(Sel2inLogger.ERROR, "Exception: " + e.getMessage());
			logger.log(Sel2inLogger.ERROR, "" + e);
			response.setErrorNo(1);
			response.setErrorMsg("Error occured while creating patient record");
		}
		return response;
	}

	@PreAuthorize("hasRole('patient_view_record')")
	@RequestMapping(value = "viewRecord", method = RequestMethod.GET)
	public @ResponseBody ViewPatientRecordResDto viewRecord(@RequestParam("appointmentId") String appointmentIdString, final HttpServletRequest req) {
		logger.log(Sel2inLogger.INFO, "appointmentIdString: " + appointmentIdString);
		ViewPatientRecordResDto response = null;
		try {
			int appointmentId = Integer.parseInt(appointmentIdString);
			response = appointmentFacade.viewPatientRecord(appointmentId);
			// Normalize attachment url for downloading report
			PatientRecordDto patientRecordDto = response.getPatientRecordDto();
			if (patientRecordDto != null) {
				if (patientRecordDto.getAttach1Path() != null) {
					patientRecordDto.setAttach1Path(ControllerUtils.getDownloadReportUrl(req, patientRecordDto.getAttach1Path()));
				}
				if (patientRecordDto.getAttach2Path() != null) {
					patientRecordDto.setAttach2Path(ControllerUtils.getDownloadReportUrl(req, patientRecordDto.getAttach2Path()));
				}
			}
		} catch (NumberFormatException e) {
			response = new ViewPatientRecordResDto();
			response.setErrorNo(1);
			response.setErrorMsg("Invalid AppointmentID for Patient Record");
			logger.log(Sel2inLogger.ERROR, "Invalid AppointmentID for Patient Record");
			logger.log(Sel2inLogger.ERROR, "NumberFormatException: " + e.getMessage());
		}
		return response;
	}

	private PatientRecordDto getPatientDtoFromRequest(MultipartHttpServletRequest request) {
		// req parameter will be in multipart request along with media
		String patientRecordId = request.getParameter("patientRecordId");
		String appointmentId = request.getParameter("appointmentId");
		String summary = request.getParameter("summary");
		String prescription = request.getParameter("prescription");
		logger.log(Sel2inLogger.INFO, "patientRecordId: " + patientRecordId);
		logger.log(Sel2inLogger.INFO, "appointmentId: " + appointmentId);
		logger.log(Sel2inLogger.INFO, "summary: " + summary);
		logger.log(Sel2inLogger.INFO, "prescription: " + prescription);
		
		PatientRecordDto patientRecordDto = new PatientRecordDto();
		patientRecordDto.setSummary(summary);
		patientRecordDto.setPrescription(prescription);
		if (patientRecordId != null) {
			try{
				patientRecordDto.setPatientRecordId(Integer.parseInt(patientRecordId));
			}catch (NumberFormatException e) {}	
		}
		if (appointmentId != null) {
			patientRecordDto.setAppointmentId(Integer.parseInt(appointmentId));
		}
		return patientRecordDto;
	}
}
