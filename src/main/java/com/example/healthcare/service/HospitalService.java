package com.example.healthcare.service;

import com.example.healthcare.entity.DoctorRequestedSchedule;
import com.example.healthcare.repositories.DrReqTimeScheRepository;
import com.example.healthcare.repositories.HospitalRepository;
import com.example.healthcare.repositories.UserRepository;
import com.example.healthcare.dto.HospitalDto;
import com.example.healthcare.entity.Hospital;
import com.example.healthcare.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;

@Component
public class HospitalService {
    private final HospitalRepository hospitalRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final DrReqTimeScheRepository drReqTimeScheRepository;

    public HospitalService(HospitalRepository hospitalRepository, ModelMapper modelMapper,
                           UserRepository userRepository, DrReqTimeScheRepository drReqTimeScheRepository) {
        this.hospitalRepository = hospitalRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.drReqTimeScheRepository = drReqTimeScheRepository;
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

    public void setVerificationStatus(long id,String status) {
        DoctorRequestedSchedule ds = this.drReqTimeScheRepository.findById(id);
        ds.setStatus(status);
        this.drReqTimeScheRepository.save(ds);
    }
    public List<DoctorRequestedSchedule> getDoctorsByVerifications(String string) {
        return this.drReqTimeScheRepository.doctorsByVerification(string);
    }
}
