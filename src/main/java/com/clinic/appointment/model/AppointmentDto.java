package com.clinic.appointment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class AppointmentDto {
    private int id;
    private String patientName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Africa/Cairo")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="Africa/Cairo")
    private Date endDate;
    private AppointmentStatus status;
    private ReasonOfCancellation reasonOfCancellation;
}
