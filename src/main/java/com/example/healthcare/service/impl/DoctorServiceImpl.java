package com.example.healthcare.service.impl;

import com.example.healthcare.dao.DoctorRepository;
import com.example.healthcare.dao.UserRepository;
import com.example.healthcare.dto.DoctorDto;
import com.example.healthcare.entities.Doctor;
import com.example.healthcare.entities.User;
import com.example.healthcare.service.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class DoctorServiceImpl implements DoctorService {
    private final ModelMapper modelMapper;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    public DoctorServiceImpl(ModelMapper modelMapper, DoctorRepository doctorRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createDoctor(DoctorDto doctorDto, Principal principal) {
        Doctor doctor = this.modelMapper.map(doctorDto, Doctor.class);
        doctor.setUser(this.userRepository.getUserByUserName(principal.getName()));
        doctor.setCompleteProfile(true);
        this.doctorRepository.save(doctor);
    }

    @Override
    public Doctor getDoctor(Principal principal) {
        User user = this.userRepository.getUserByUserName(principal.getName());
        return this.doctorRepository.getDoctorByUserId(user.getId());
    }


    @Override
    public boolean isVerified(Principal principal) {
        User user = this.userRepository.getUserByUserName(principal.getName());
        Doctor doctor = this.doctorRepository.getDoctorByUserId(user.getId());
        return doctor.isVerified();
    }

    @Override
    public boolean isCompleteProfile(Principal principal) {
        User user = this.userRepository.getUserByUserName(principal.getName());
        Doctor doctor = this.doctorRepository.getDoctorByUserId(user.getId());
        return doctor.isCompleteProfile();
    }
}
