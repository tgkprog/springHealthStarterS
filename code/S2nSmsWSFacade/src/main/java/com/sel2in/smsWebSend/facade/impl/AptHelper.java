package com.sel2in.smsWebSend.facade.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.sel2in.smsWebSend.facade.dto.AppointmentDto;
import com.sel2in.smsWebSend.facade.dto.appointment.SlotInfoDto;
import com.sel2in.smsWebSend.facade.dto.response.AppointmentLanes;
import com.sel2in.smsWebSend.logger.Sel2inLogFactory;
import com.sel2in.smsWebSend.logger.Sel2inLogger;

@Service
@CacheConfig(cacheManager = "facadeCacheManager")
public class AptHelper {

	private static final Sel2inLogger logger = Sel2inLogFactory.getLoggerInstance(AptHelper.class.getName());

	// TODO cache & read only free apts
	public AppointmentLanes getScduleFromService(String doc, Date dt) {
		int w = dt.getDate() % 2;// testing data not real
		AppointmentLanes al = new AppointmentLanes();
		List<AppointmentDto> la = new ArrayList<AppointmentDto>();
		List<AppointmentDto> lb = new ArrayList<AppointmentDto>();
		if (w == 0) {
			// lst = this.aptService.getSchedule( date, DoctorInfoDto doctor)
			la.add(make(7, 30, 7, 45, "d1", "p1", true, "OP"));
			la.add(make(7, 45, 15, 45, "d1", null, false, "Free"));
			lb.add(make(7, 30, 8, 0, "d1", null, false, "OP"));
			lb.add(make(8, 0, 15, 45, "d1", null, true, "Free"));
		} else if (w == 1) {
			la.add(make(7, 30, 15, 45, "d1", null, true, "Free"));
			lb.add(make(7, 30, 15, 45, "d1", null, true, "Free"));
		}
		List<List<AppointmentDto>> ll = new ArrayList<List<AppointmentDto>>();
		ll.add(la);
		ll.add(lb);
		al.setLaneApts(ll);
		return al;
	}

	private AppointmentDto make(int hrs, int min, int endHr, int endMin, String docId, String patId, boolean booked, String bookType) {
		AppointmentDto a1 = new AppointmentDto();
		a1.setBooked(booked);
		a1.setBookType(bookType);
		Date dt = new Date();
		dt.setHours(hrs);
		dt.setMinutes(min);
		a1.setAppointmentDate(dt);
		a1.setAppointmentEndHour(endHr);
		a1.setAppointmentEndMin(endMin);
		a1.setDoctor(docId);
		a1.setPatient(patId);
		return a1;
	}

	@Cacheable("slotCache")
	public SlotInfoDto getSlotInfo(String status, int fromHour, int fromMin, int toHour, int toMin, int lane) {
		SlotInfoDto slot = new SlotInfoDto(status, fromHour, fromMin, toHour, toMin, lane);
		logger.log(Sel2inLogger.INFO, "Creating SlotInfo lane: " + lane + ", hour: " + fromHour + ", min: " + fromMin);
		return slot;
	}
}
