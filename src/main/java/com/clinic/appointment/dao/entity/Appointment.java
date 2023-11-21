package com.clinic.appointment.dao.entity;

import com.clinic.appointment.model.AppointmentStatus;
import com.clinic.appointment.model.ReasonOfCancellation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "appointment")
public class Appointment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "patient_name", nullable = false)
    private String patientName;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;


    @Column(name = "status")
    private AppointmentStatus status;

    @Column(name = "reason_of_cancellation")
    @Enumerated(EnumType.STRING)
    private ReasonOfCancellation reasonOfCancellation;
}
