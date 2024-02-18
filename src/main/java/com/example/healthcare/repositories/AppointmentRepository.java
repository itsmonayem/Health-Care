package com.example.healthcare.repositories;

import com.example.healthcare.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("select a from Appointment a where a.user.id=:user_id")
    List<Appointment> getAppointmentHistoryByUserId(@Param("user_id") long user_id);

    @Query("select a from Appointment a where a.doctor.id=:doctor_id")
    List<Appointment> getAppointmentHistoryByDoctorId(long doctor_id);
}
