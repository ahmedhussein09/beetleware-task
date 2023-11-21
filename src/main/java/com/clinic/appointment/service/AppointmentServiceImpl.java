package com.clinic.appointment.service;

import com.clinic.appointment.dao.entity.Appointment;
import com.clinic.appointment.dao.repo.AppointmentRepo;
import com.clinic.appointment.model.AppointmentDto;
import com.clinic.appointment.model.AppointmentStatus;
import com.clinic.appointment.model.ReasonOfCancellation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService{
    private final AppointmentRepo appointmentRepo;
    @Override
    public AppointmentDto addNewAppointment(AppointmentDto appointmentDto) {
       List<Appointment> appointments =  appointmentRepo.findAllByDateBetween(appointmentDto.getStartDate(), appointmentDto.getEndDate());
       if(!appointments.isEmpty()){
           throw new RuntimeException("Slot is not available");
       }else{
           appointmentDto.setStatus(AppointmentStatus.SCHEDULED);
         return toDto(appointmentRepo.save(toEntity(appointmentDto)));
       }
    }

    @Override
    public List<AppointmentDto> getTodayAppointment() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormat.format(new Date());

        LocalDate nextDay = LocalDate.now().plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String nextDateTime = nextDay.format(formatter);

        List<Appointment> appointments = appointmentRepo.findAllTodayAppointment(currentDateTime, nextDateTime);
        return appointments.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public AppointmentDto cancelAppointment(int id, ReasonOfCancellation reason) {
        Appointment appointment = appointmentRepo.findById(id).orElseThrow(() -> new RuntimeException("Appointment doesn't exist!"));
        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointment.setReasonOfCancellation(reason);
        return toDto(appointmentRepo.save(appointment));
    }

    @Override
    public List<AppointmentDto> getPatientAppointment(String patientName) {
        List<Appointment> appointments = appointmentRepo.findAllByPatientName(patientName);
        return appointments.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDto> getAppointmentByDate(String date) {

        LocalDate fromDate;

        try {
            fromDate=new SimpleDateFormat("yyyy-MM-dd").parse(date).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        LocalDate toDate = fromDate.plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Appointment> appointments = appointmentRepo.findAllTodayAppointment(fromDate.format(formatter), toDate.format(formatter));
        return appointments.stream().map(this::toDto).collect(Collectors.toList());
    }

    private Appointment toEntity(AppointmentDto appointmentDto){
        Appointment appointment = new Appointment();
        appointment.setId(appointmentDto.getId());
        appointment.setPatientName(appointmentDto.getPatientName());
        appointment.setStartDate(appointmentDto.getStartDate());
        appointment.setEndDate(appointmentDto.getEndDate());
        appointment.setStatus(appointmentDto.getStatus());
        appointment.setReasonOfCancellation(appointmentDto.getReasonOfCancellation());
        return appointment;
    }
    private AppointmentDto toDto(Appointment appointment){
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setId(appointment.getId());
        appointmentDto.setPatientName(appointment.getPatientName());
        appointmentDto.setStartDate(appointment.getStartDate());
        appointmentDto.setEndDate(appointment.getEndDate());
        appointmentDto.setStatus(appointment.getStatus());
        appointmentDto.setReasonOfCancellation(appointment.getReasonOfCancellation());
        return appointmentDto;
    }
}
