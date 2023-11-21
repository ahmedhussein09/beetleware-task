package com.clinic.appointment.dao.repo;

import com.clinic.appointment.dao.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {

    @Query("select a from Appointment a where a.startDate between :startDate and :endDate or a.endDate between :startDate and :endDate")
    List<Appointment> findAllByDateBetween(Date startDate, Date endDate);
    @Query("select a from Appointment a where cast(a.startDate as string ) >= :today and cast(a.startDate as string ) < :nextDay")
    List<Appointment> findAllTodayAppointment(String today, String nextDay);

    List<Appointment> findAllByPatientName(String patientName);
}
