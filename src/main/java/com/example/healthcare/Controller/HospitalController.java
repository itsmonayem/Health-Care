package com.example.healthcare.Controller;

import com.example.healthcare.dto.HospitalDto;
import com.example.healthcare.entity.DoctorRequestedSchedule;
import com.example.healthcare.helper.Message;
import com.example.healthcare.service.HospitalService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/hospital")
public class HospitalController {
    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping("/")
    public String home() {
        return "hospital/home";
    }


    @GetMapping("/checkVerification")
    public String isVerified(Principal principal, Model model) {
        if (this.hospitalService.getHospital(principal) == null) {
            return "redirect:/hospital/completeProfile";
        } else if (this.hospitalService.isVerified(principal)) {
            model.addAttribute("hospitalStatus","Now you can operate your Hospital");
            return "hospital/home";
        } else if (this.hospitalService.isCompleteProfile(principal)){
            model.addAttribute("hospitalStatus","Please wait until Verification");
            return "hospital/home";
        } else {
            return "hospital/home";
        }
    }



    @GetMapping("/completeProfile")
    public String completeProfile(Model model) {
        model.addAttribute("hospitalDto",new HospitalDto());
        return "hospital/completeProfileInfo";
    }

    @PostMapping("/do-complete-profile")
    public String doCompleteProfile(@Valid @ModelAttribute HospitalDto hospitalDto, BindingResult result,
                                    RedirectAttributes redirectAttributes, Principal principal) {
        this.hospitalService.createHospital(hospitalDto,principal);
        redirectAttributes.addFlashAttribute("message",new Message("Hospital Information Updated","alert-success"));
        return "redirect:/hospital/checkVerification";
    }





    @GetMapping("/approve-doctors")
    public String  requestedDoctorsList(Model model){
        List<DoctorRequestedSchedule> unVerifiedDoctors = this.hospitalService.getDoctorsByVerifications("notVerified");
        model.addAttribute("unVerifiedDoctors",unVerifiedDoctors);
        return "hospital/requestedDoctorsList";
    }


    @GetMapping("/doApproveDoctor/{id}")
    public String doApproveDoctors(@PathVariable("id") long id){
        this.hospitalService.setVerificationStatus(id,"approved");
        return "redirect:/hospital/approve-doctors";
    }

    @GetMapping("/doDenyDoctor/{id}")
    public String doDenyDoctor(@PathVariable("id") long id){
        this.hospitalService.setVerificationStatus(id,"denied");
        return "redirect:/hospitals/approve-doctors";
    }


    @GetMapping("/doctors")
    public String approvedDoctors(Model model) {
        List<DoctorRequestedSchedule> approvedDoctors = this.hospitalService.getDoctorsByVerifications("approved");
        model.addAttribute("approvedDoctors",approvedDoctors);
        return "hospital/doctors";
    }
}
