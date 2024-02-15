package com.example.healthcare.service.admin;

import com.example.healthcare.repositories.HospitalRepository;
import com.example.healthcare.entities.Hospital;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HospitalAdminService {
    private final HospitalRepository hospitalRepository;

    public HospitalAdminService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
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
