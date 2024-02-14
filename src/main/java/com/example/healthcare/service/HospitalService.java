package com.example.healthcare.service;

import com.example.healthcare.dao.HospitalRepository;
import com.example.healthcare.dao.UserRepository;
import com.example.healthcare.dto.HospitalDto;
import com.example.healthcare.entities.Hospital;
import com.example.healthcare.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class HospitalService {
    private final HospitalRepository hospitalRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public HospitalService(HospitalRepository hospitalRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.hospitalRepository = hospitalRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public void createHospital(HospitalDto hospitalDto, Principal principal) {
        Hospital hospital = this.modelMapper.map(hospitalDto, Hospital.class);
        hospital.setCompleteProfile(true);
        hospital.setUser(this.userRepository.getUserByUserName(principal.getName()));
        this.hospitalRepository.save(hospital);
    }

    public Hospital getHospital(Principal principal) {
        User user = this.userRepository.getUserByUserName(principal.getName());
        return this.hospitalRepository.getHospitalByUserId(user.getId());
    }

    public boolean isVerified(Principal principal) {
        User user = this.userRepository.getUserByUserName(principal.getName());
        Hospital hospital = this.hospitalRepository.getHospitalByUserId(user.getId());
        return hospital.isVerified();
    }

    public boolean isCompleteProfile(Principal principal) {
        User user = this.userRepository.getUserByUserName(principal.getName());
        Hospital hospital = this.hospitalRepository.getHospitalByUserId(user.getId());
        return hospital.isCompleteProfile();
    }
}
