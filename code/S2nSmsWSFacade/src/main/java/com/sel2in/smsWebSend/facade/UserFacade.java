package com.sel2in.smsWebSend.facade;

import org.springframework.stereotype.Component;

import com.sel2in.smsWebSend.facade.dto.AppointmentDto;
import com.sel2in.smsWebSend.facade.dto.UserRegDto;
import com.sel2in.smsWebSend.facade.dto.response.ResponseDto;
import com.sel2in.smsWebSend.facade.dto.response.SearchResultResDto;
import com.sel2in.smsWebSend.facade.dto.response.UserRegRespDto;
import com.sel2in.smsWebSend.model.User;


@Component("UserFacade") 
public interface UserFacade {

	UserRegRespDto createUser(UserRegDto userRegDTO);

	User getUserByEmail(String email);

	SearchResultResDto searchUserByEmailOrMobile(String emailOrMobile);

	ResponseDto createAppointment(final AppointmentDto appointmentDto);

	String clearCache(String clearAllCache, String clearCache, String cacheName);

}
