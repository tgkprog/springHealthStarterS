package com.sel2in.smsWebSend.services.notifications;

import java.util.List;

import com.sel2in.smsWebSend.model.Appointment;

public interface AppointmentReminder {
	public List<Appointment> appointmentNotificationToPatient() throws Exception;
	void autoAppointmentNotification() throws Exception;
}
