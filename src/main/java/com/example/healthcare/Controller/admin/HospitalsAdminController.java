package com.example.healthcare.Controller.admin;

import com.example.healthcare.entities.Hospital;
import com.example.healthcare.service.admin.AdminService;
import com.example.healthcare.service.admin.HospitalAdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class HospitalsAdminController {
    private final HospitalAdminService hospitalAdminService;

    public HospitalsAdminController(HospitalAdminService hospitalAdminService) {
        this.hospitalAdminService = hospitalAdminService;
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
