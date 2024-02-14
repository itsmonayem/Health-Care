package com.example.healthcare.service;

import com.example.healthcare.dto.DoctorDto;
import com.example.healthcare.entities.Doctor;

import java.security.Principal;

public interface DoctorService {
    void createDoctor(DoctorDto doctorDto, Principal principal);
    Doctor getDoctor(Principal principal);
    boolean isVerified(Principal principal);
    boolean isCompleteProfile(Principal principal);
}
