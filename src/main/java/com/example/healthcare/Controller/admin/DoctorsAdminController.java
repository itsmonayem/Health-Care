package com.example.healthcare.Controller.admin;

import com.example.healthcare.entities.Doctor;
import com.example.healthcare.service.admin.AdminService;
import com.example.healthcare.service.admin.DoctorAdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class DoctorsAdminController {
    private final DoctorAdminService doctorAdminService;

    public DoctorsAdminController(DoctorAdminService doctorAdminService) {
        this.doctorAdminService = doctorAdminService;
    }

    @GetMapping("/")
    public String home() {
        return "admin/home";
    }



    @GetMapping("/verify-doctors")
    public String verifyDoctors(Model model) {
        List<Doctor> unVerifiedDoctorsList = this.doctorAdminService.unVerifiedDoctors(false);
        model.addAttribute("unVerifiedDoctorsList", unVerifiedDoctorsList);
        return "admin/verify-doctor-page";
    }


    @GetMapping("/approveDoctor/{id}")
    public String ApproveDoctor(@PathVariable("id") long user_id, Model model) {
        this.doctorAdminService.approvedDoctor(user_id);
        return "redirect:/admin/verify-doctors";
    }


    @GetMapping("/doctors")
    public String doctors(Model model) {
        List<Doctor> inServiceDoctorsList = this.doctorAdminService.unVerifiedDoctors(true);
        model.addAttribute("inServiceDoctorsList",inServiceDoctorsList);
        return "admin/doctors";
    }
}
