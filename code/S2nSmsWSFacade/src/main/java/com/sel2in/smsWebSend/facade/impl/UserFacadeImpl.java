package com.sel2in.smsWebSend.facade.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sel2in.smsWebSend.facade.UserFacade;
import com.sel2in.smsWebSend.facade.convert.populator.ConversionException;
import com.sel2in.smsWebSend.facade.convert.populator.UserInfoPopulator;
import com.sel2in.smsWebSend.facade.dto.AppointmentDto;
import com.sel2in.smsWebSend.facade.dto.UserInfoDto;
import com.sel2in.smsWebSend.facade.dto.UserRegDto;
import com.sel2in.smsWebSend.facade.dto.response.ResponseDto;
import com.sel2in.smsWebSend.facade.dto.response.SearchResultResDto;
import com.sel2in.smsWebSend.facade.dto.response.UserRegRespDto;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;
import com.sel2in.smsWebSend.model.Address;
import com.sel2in.smsWebSend.model.PatientRecord;
import com.sel2in.smsWebSend.model.User;
import com.sel2in.smsWebSend.services.UserService;

@Service
@Transactional(rollbackFor=Exception.class)
public class UserFacadeImpl implements UserFacade {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(UserFacadeImpl.class.getName());

	@Autowired(required = true)
	private UserService userService;

	@Autowired(required = true)
	private UserInfoPopulator userInfoPopulator;

	public UserFacadeImpl() {
	}

	public UserFacadeImpl(UserService userService) {
		this.userService = userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@PreAuthorize("hasRole('user_registration')")
	public UserRegRespDto createUser(UserRegDto userRegDTO) {
		logger.log(Sel2inLogger.INFO, "createUser()");
		UserRegRespDto responseDto = new UserRegRespDto();
		User newUser = new User();
		Integer result = null;
		newUser.setFirstName(userRegDTO.getFirstName());
		newUser.setLastName(userRegDTO.getLastName());
		newUser.setEmail(userRegDTO.getEmail());
		newUser.setDob(userRegDTO.getDob());
		newUser.setMobileCode(userRegDTO.getMobileCode());
		newUser.setMobile(userRegDTO.getMobile());
		newUser.setPassword("smsWebSend");
		newUser.setRoleType(userRegDTO.getRoleType());
		newUser.setAddress(userRegDTO.getAddresses());
		Iterator<Address> iterable = userRegDTO.getAddresses().iterator();
		while (iterable.hasNext()) {
			iterable.next().setUser(newUser);
		}
		try {
			result = userService.createUser(newUser);
			logger.log(Sel2inLogger.INFO, "result: " + result);
		} catch (Exception e) {
			logger.log(Sel2inLogger.ERROR, "Exception: createUser()");
			if (e instanceof DataIntegrityViolationException) {
				responseDto.setErrorMsg("Duplicate Entry, Mobile or Email Already Exists");
				logger.log(Sel2inLogger.ERROR, "DataIntegrityViolationException: Duplicate Entry, Mobile or Email Already Exists");
			} else {
				responseDto.setErrorMsg(e.getMessage());
				logger.log(Sel2inLogger.ERROR, "Exception: createUser() - " + e.getMessage());
			}
			responseDto.setErrorNo(1);
		}
		if (result != null) {
			responseDto.setErrorNo(0);
			responseDto.setSuccessMsg("New User is Created");
			logger.log(Sel2inLogger.INFO, "New User is Created");
			responseDto.setRegistrationSuccess(Boolean.TRUE);
			responseDto.setUserId(result);
		}
		return responseDto;
	}

	public User getUserByEmail(String email) {
		logger.log(Sel2inLogger.INFO, "getUserByEmail parameter value is: " + email);
		return userService.getUserByEmail(email);
	}

	@PreAuthorize("hasRole('user_search')")
	public SearchResultResDto searchUserByEmailOrMobile(String searchText) {
		logger.log(Sel2inLogger.INFO, "searchUserByEmailOrMobile()");
		SearchResultResDto response = new SearchResultResDto();
		response.setSearchText(searchText);
		List<User> users = userService.searchUsersByEmailOrMobile(searchText);
		List<UserInfoDto> userDtos = new ArrayList<UserInfoDto>();
		if (users != null && !users.isEmpty()) {
			try {
				for (User user : users) {
					UserInfoDto userInfoDto = new UserInfoDto();
					userInfoPopulator.populate(user, userInfoDto);
					userDtos.add(userInfoDto);
				}
				response.setErrorNo(0);
				response.setSuccessMsg("Searching Done");
				logger.log(Sel2inLogger.INFO, "Searching Done");
			} catch (ConversionException e) {
				logger.log(Sel2inLogger.ERROR, "" + e);
				response.setErrorNo(1);
				response.setErrorMsg("Error While conveting model to dto");
				logger.log(Sel2inLogger.ERROR, "ConversionException: Error While conveting model to dto");
			}
		} else {
			response.setErrorNo(0);
			response.setSuccessMsg("No Result Found");
			logger.log(Sel2inLogger.INFO, "No Result Found");
		}
		response.setResult(userDtos);
		return response;
	}

	public ResponseDto createAppointment(final AppointmentDto appointmentDto) {
		logger.log(Sel2inLogger.INFO, "createAppointment()");
		PatientRecord record = new PatientRecord();
		ResponseDto responseDto = new ResponseDto();
		/*
		if(appointmentDto.getPatientId() != null){
			User patient  = userService.getUserById(appointmentDto.getPatientId());
			if(patient !=null){
				record.setUser(patient);
				record.setDoctorName(appointmentDto.getDoctor());
				record.setDoctorDept(appointmentDto.getDepartment());
				record.setAppointmentDate(appointmentDto.getBookingAppointmentDate());
				record.setAppointmentTime(appointmentDto.getBookedSlot());
				
				boolean isDoctorAvailable = userService.isDoctorAvailableForTimeSlot(appointmentDto.getDoctor(), appointmentDto.getBookedSlot());
				if(isDoctorAvailable){
					String result = null;
					try {
						result = userService.createAppointment(record);
					} catch (Exception e) {
						if(e instanceof DataIntegrityViolationException){
							responseDto.setErrorMsg("Duplicate appointment or other Db issue");
						}else{
							responseDto.setErrorMsg(e.getMessage());
						}
						responseDto.setErrorNo(1);
					}
					if(result!=null) {
						responseDto.setErrorNo(0);
						responseDto.setSuccessMsg("booking Appointment is done successfully");
					}
				}else{
					responseDto.setErrorNo(1);
					responseDto.setErrorMsg("Appointment for Doctor "+appointmentDto.getDoctor() +
							" and timeslot "+appointmentDto.getBookedSlot()+" is already booked.");
				}
				
			}else{
				responseDto.setErrorNo(1);
				responseDto.setErrorMsg("Wrong Patient Id");
			}
		}else{
			responseDto.setErrorNo(1);
			responseDto.setErrorMsg("Patient Id is mandatory for appointment");
		}
		*/
		return responseDto;
	}

	@PreAuthorize("hasRole('cache_clear')")
	public String clearCache(String clearAllCache, String clearCache, String cacheName) {
		String status = null;
		if (clearAllCache != null && !clearAllCache.isEmpty() && !"null".equalsIgnoreCase(clearAllCache)) {
			status = userService.clearAllCache();
		} else if (clearCache != null && !clearCache.isEmpty()) {
			if (cacheName != null && !cacheName.isEmpty()) {
				if ("addressCache".equals(cacheName)) {
					status = userService.clearAddressCache();
				} else if ("appointmentCache".equals(cacheName)) {
					status = userService.clearAppointmentCache();
				} else if ("departmentCache".equals(cacheName)) {
					status = userService.clearDepartmentCache();
				} else if ("doctorCache".equals(cacheName)) {
					status = userService.clearDoctorCache();
				} else if ("genderCache".equals(cacheName)) {
					status = userService.clearGenderCache();
				} else if ("medicineInventoryCache".equals(cacheName)) {
					status = userService.clearMedicineInventoryCache();
				} else if ("patientCache".equals(cacheName)) {
					status = userService.clearPatientCache();
				} else if ("userCache".equals(cacheName)) {
					status = userService.clearUserCache();
				} else {
					logger.log(Sel2inLogger.INFO, "Cache Name Not Found");
				}
			} else {
				logger.log(Sel2inLogger.INFO, "Cache Name Is Empty");
			}
		} else {
			logger.log(Sel2inLogger.INFO, "Cache Not Cleared");
		}
		return status;
	}

}