package com.clinic.appointment.controller;

import com.clinic.appointment.model.AppointmentDto;
import com.clinic.appointment.model.ReasonOfCancellation;
import com.clinic.appointment.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentServiceImpl;

    @PostMapping("/")
    public ResponseEntity<AppointmentDto> addNewAppointment(@RequestBody AppointmentDto appointmentDto){
        return new ResponseEntity<>(appointmentServiceImpl.addNewAppointment(appointmentDto), HttpStatus.CREATED);
    }
    @GetMapping("/today")
    public ResponseEntity<List<AppointmentDto>> getTodayAppointment(){
        return new ResponseEntity<>(appointmentServiceImpl.getTodayAppointment(), HttpStatus.OK);
    }
    @PutMapping("/cancel")
    public ResponseEntity<AppointmentDto> cancelAppointment(@RequestParam("id") int id,
                                                            @RequestParam("reason") String reason){
        return new ResponseEntity<>(appointmentServiceImpl.cancelAppointment(id, ReasonOfCancellation.valueOf(reason)), HttpStatus.OK);
    }
    @GetMapping("/{patientName}")
    public ResponseEntity<List<AppointmentDto>> getPatientAppointment(@PathVariable String patientName){
        return new ResponseEntity<>(appointmentServiceImpl.getPatientAppointment(patientName), HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<AppointmentDto>> getAppointmentByDate(@RequestParam("date") String date){
        return new ResponseEntity<>(appointmentServiceImpl.getAppointmentByDate(date), HttpStatus.OK);
    }

}
