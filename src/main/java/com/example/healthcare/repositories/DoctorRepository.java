package com.example.healthcare.repositories;

import com.example.healthcare.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    Doctor getDoctorByUserId(@Param("user_id") long user_id);

    @Query("select d from Doctor d where d.isVerified=:isVerified")
    List<Doctor> findAllDoctorsByVerification(@Param("isVerified") boolean isVerified);
}
