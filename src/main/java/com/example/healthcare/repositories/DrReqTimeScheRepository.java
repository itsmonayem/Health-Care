package com.example.healthcare.repositories;

import com.example.healthcare.entity.DoctorRequestedSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DrReqTimeScheRepository extends JpaRepository<DoctorRequestedSchedule, Long> {
    @Query("select ds from DoctorRequestedSchedule ds where ds.status=:status")
    List<DoctorRequestedSchedule> doctorsByVerification(@Param("status") String status);

    DoctorRequestedSchedule findById(long id);
}
