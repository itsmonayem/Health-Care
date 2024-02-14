package com.example.healthcare.service;

import com.example.healthcare.entities.Doctor;

import java.util.List;

public interface AdminService {
    List<Doctor> unVerifiedDoctors(boolean verificationStatus);
    void approvedDoctor(long user_id);
}
