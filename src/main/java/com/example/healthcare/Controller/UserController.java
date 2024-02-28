package com.example.healthcare.Controller;

import com.example.healthcare.entity.Appointment;
import com.example.healthcare.entity.Doctor;
import com.example.healthcare.service.UserService;
import com.example.healthcare.service.admin.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final AdminService adminService;
    private final UserService userService;

    public UserController(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        System.out.println("Patient");
        return "user/home";
    }

    @GetMapping("/doctors")
    public String viewDoctors(Model model) {
        List<Doctor> doctorsList = this.adminService.findDoctorsByVerificationStatus(true);
        model.addAttribute("doctorsList",doctorsList);
        return "user/doctors";
    }

    @GetMapping("/make-appointment/{id}")
    public String makeAppointment(@PathVariable("id") long doctor_id, Model model, Principal principal){
        this.userService.makeAppointment(doctor_id,principal);
        return "redirect:/user/doctors";
    }



    @GetMapping("/appointment-history")
    public String appointmentHistory(Model model,Principal principal) {
        List<Appointment> appointments = this.userService.getAppointmentHistory(principal);
        model.addAttribute("appointments",appointments);
        return "user/appointment-history";
    }
}
