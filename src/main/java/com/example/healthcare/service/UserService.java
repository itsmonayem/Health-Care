package com.example.healthcare.service;

import com.example.healthcare.entities.Appointment;
import com.example.healthcare.entities.Doctor;
import com.example.healthcare.entities.User;
import com.example.healthcare.repositories.AppointmentRepository;
import com.example.healthcare.repositories.DoctorRepository;
import com.example.healthcare.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    public UserService(AppointmentRepository appointmentRepository, UserRepository userRepository, DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
    }

    public void makeAppointment(long doctor_id, Principal principal) {
        User user = this.userRepository.getUserByUserName(principal.getName());
        Optional<Doctor> doctor = this.doctorRepository.findById(doctor_id);
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor.orElse(new Doctor()));
        appointment.setUser(user);
        appointment.setStatus("pending");
        this.appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentHistory(Principal principal) {
        User user = this.userRepository.getUserByUserName(principal.getName());
        return this.appointmentRepository.getAppointmentHistoryByUserId(user.getId());
    }
}
