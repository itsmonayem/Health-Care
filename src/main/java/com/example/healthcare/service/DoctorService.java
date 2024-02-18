package com.example.healthcare.service;

import com.example.healthcare.dto.DrReqTimeScheduleDto;
import com.example.healthcare.entities.*;
import com.example.healthcare.repositories.*;
import com.example.healthcare.dto.DoctorDto;
import com.example.healthcare.service.admin.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;

@Component
public class DoctorService {
    private final ModelMapper modelMapper;
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final DoctorExpertiseRepository expertiseRepository;
    private final DrReqTimeScheRepository timeScheduleRepository;
    private final AppointmentRepository appointmentRepository;

    public DoctorService(ModelMapper modelMapper, DoctorRepository doctorRepository, UserRepository userRepository, DoctorExpertiseRepository expertiseRepository, DrReqTimeScheRepository timeScheduleRepository, AppointmentRepository appointmentRepository) {
        this.modelMapper = modelMapper;
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.expertiseRepository = expertiseRepository;
        this.timeScheduleRepository = timeScheduleRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public void createDoctor(DoctorDto doctorDto, Principal principal) {
        Doctor doctor = this.modelMapper.map(doctorDto, Doctor.class);
        doctor.setUser(this.userRepository.getUserByUserName(principal.getName()));
        doctor.setCompleteProfile(true);
        this.doctorRepository.save(doctor);
    }

    public Doctor getDoctor(Principal principal) {
        User user = this.userRepository.getUserByUserName(principal.getName());
        return this.doctorRepository.getDoctorByUserId(user.getId());
    }


    public boolean isVerified(Principal principal) {
        User user = this.userRepository.getUserByUserName(principal.getName());
        Doctor doctor = this.doctorRepository.getDoctorByUserId(user.getId());
        return doctor.isVerified();
    }


    public boolean isCompleteProfile(Principal principal) {
        User user = this.userRepository.getUserByUserName(principal.getName());
        Doctor doctor = this.doctorRepository.getDoctorByUserId(user.getId());
        return doctor.isCompleteProfile();
    }

    public List<DoctorExpertise> doctorExpertises() {
        return this.expertiseRepository.findAll();
    }


    public void requestToHospital(DrReqTimeScheduleDto timeScheduleDto,Principal principal) {
        DoctorRequestedSchedule schedule = this.modelMapper.map(timeScheduleDto,DoctorRequestedSchedule.class);
        User user = this.userRepository.getUserByUserName(principal.getName());
        Doctor doctor = this.doctorRepository.getDoctorByUserId(user.getId());
        schedule.setDoctor(doctor);
        schedule.setStatus("notVerified");
        this.timeScheduleRepository.save(schedule);
    }


    public List<Appointment> getPendingAppointments(Principal principal) {
        User user = this.userRepository.getUserByUserName(principal.getName());
        Doctor doctor = this.doctorRepository.getDoctorByUserId(user.getId());
        return this.appointmentRepository.getAppointmentHistoryByDoctorId(doctor.getId());
    }
}
