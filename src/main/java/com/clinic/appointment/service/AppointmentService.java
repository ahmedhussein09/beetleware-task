package com.clinic.appointment.service;

import com.clinic.appointment.model.AppointmentDto;
import com.clinic.appointment.model.ReasonOfCancellation;

import java.util.List;

public interface AppointmentService {
    AppointmentDto addNewAppointment(AppointmentDto appointmentDto);

    List<AppointmentDto> getTodayAppointment();

    AppointmentDto cancelAppointment(int id, ReasonOfCancellation reason);

    List<AppointmentDto> getPatientAppointment(String patientName);

    List<AppointmentDto> getAppointmentByDate(String date);
}
