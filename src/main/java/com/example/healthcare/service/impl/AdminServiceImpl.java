package com.example.healthcare.service.impl;

import com.example.healthcare.dao.DoctorRepository;
import com.example.healthcare.entities.Doctor;
import com.example.healthcare.service.AdminService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminServiceImpl implements AdminService {
    private final DoctorRepository doctorRepository;

    public AdminServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public List<Doctor> unVerifiedDoctors(boolean verificationStatus) {
        return this.doctorRepository.findAllDoctorsByVerification(verificationStatus);
    }

    @Override
    public void approvedDoctor(long user_id) {
        Doctor doctor = this.doctorRepository.getDoctorByUserId(user_id);
        doctor.setVerified(true);
        this.doctorRepository.save(doctor);
    }
}
