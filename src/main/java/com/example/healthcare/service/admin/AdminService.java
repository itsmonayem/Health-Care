package com.example.healthcare.service.admin;

import com.example.healthcare.repositories.DoctorRepository;
import com.example.healthcare.repositories.HospitalRepository;
import com.example.healthcare.entity.Doctor;
import com.example.healthcare.entity.Hospital;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminService {
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;

    public AdminService(DoctorRepository doctorRepository, HospitalRepository hospitalRepository) {
        this.doctorRepository = doctorRepository;
        this.hospitalRepository = hospitalRepository;
    }


    public List<Doctor> findDoctorsByVerificationStatus(boolean verificationStatus) {
        return this.doctorRepository.findAllDoctorsByVerification(verificationStatus);
    }

    public void approvedDoctor(long user_id) {
        Doctor doctor = this.doctorRepository.getDoctorByUserId(user_id);
        doctor.setVerified(true);
        this.doctorRepository.save(doctor);
    }

    public List<Hospital> unVerifiedHospitals(boolean verificationStatus) {
        return this.hospitalRepository.findAllHospitalsByVerification(verificationStatus);
    }

    public void approvedHospitals(long user_id) {
        Hospital hospital = this.hospitalRepository.getHospitalByUserId(user_id);
        hospital.setVerified(true);
        this.hospitalRepository.save(hospital);
    }
}
