package com.example.healthcare.service.admin;

import com.example.healthcare.repositories.DoctorRepository;
import com.example.healthcare.entities.Doctor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorAdminService {
    private final DoctorRepository doctorRepository;

    public DoctorAdminService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }


    public List<Doctor> unVerifiedDoctors(boolean verificationStatus) {
        return this.doctorRepository.findAllDoctorsByVerification(verificationStatus);
    }

    public void approvedDoctor(long user_id) {
        Doctor doctor = this.doctorRepository.getDoctorByUserId(user_id);
        doctor.setVerified(true);
        this.doctorRepository.save(doctor);
    }
}
