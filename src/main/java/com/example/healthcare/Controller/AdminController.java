package com.example.healthcare.Controller;

import com.example.healthcare.entities.Doctor;
import com.example.healthcare.entities.Hospital;
import com.example.healthcare.service.admin.DoctorAdminService;
import com.example.healthcare.service.admin.HospitalAdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final DoctorAdminService doctorAdminService;
    private final HospitalAdminService hospitalAdminService;

    public AdminController(DoctorAdminService doctorAdminService, HospitalAdminService hospitalAdminService) {
        this.doctorAdminService = doctorAdminService;
        this.hospitalAdminService = hospitalAdminService;
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



    @GetMapping("/verify-hospitals")
    public String verifyHospitals(Model model) {
        List<Hospital> unVerifiedHospitalsList = this.hospitalAdminService.unVerifiedHospitals(false);
        model.addAttribute("unVerifiedHospitalsList",unVerifiedHospitalsList);
        return "admin/verify-hospital-page";
    }


    @GetMapping("/approveHospital/{id}")
    public String ApproveHospital(@PathVariable("id") long user_id, Model model) {
        this.hospitalAdminService.approvedHospitals(user_id);
        return "redirect:/admin/verify-hospitals";
    }


    @GetMapping("/hospitals")
    public String hospitals(Model model) {
        List<Hospital> runningHospitalsList = this.hospitalAdminService.unVerifiedHospitals(true);
        model.addAttribute("runningHospitalsList",runningHospitalsList);
        return "admin/hospitals";
    }
}
