package com.example.healthcare.repositories;

import com.example.healthcare.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    @Query("select h from Hospital  h where h.user.id=:user_id")
    Hospital getHospitalByUserId(@Param("user_id") long user_id);

    @Query("select h from Hospital h where h.isVerified=:isVerified")
    List<Hospital> findAllHospitalsByVerification(@Param("isVerified") boolean idVerified);
}
