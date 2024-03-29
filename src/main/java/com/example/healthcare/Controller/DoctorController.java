package com.example.healthcare.Controller;

import com.example.healthcare.dto.DoctorDto;
import com.example.healthcare.dto.DrReqTimeScheduleDto;
import com.example.healthcare.entity.Appointment;
import com.example.healthcare.entity.DoctorExpertise;
import com.example.healthcare.entity.Hospital;
import com.example.healthcare.helper.Message;
import com.example.healthcare.service.DoctorService;
import com.example.healthcare.service.admin.AdminService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorService doctorService;
    private final AdminService adminService;

    public DoctorController(DoctorService doctorService, AdminService adminService) {
        this.doctorService = doctorService;
        this.adminService = adminService;
    }

    @GetMapping("/")
    public String home(Model model) {
        return "doctor/home";
    }


    @RequestMapping("/checkVerification")
    public String isVerified(Principal principal, Model model) {
        if (this.doctorService.getDoctor(principal) == null) {
            return "redirect:/doctor/completeProfile";
        } else if (this.doctorService.isVerified(principal)) {
            model.addAttribute("doctorStatus","Congratulation you are in Service");
            return "redirect:/doctor/";
        } else if (this.doctorService.isCompleteProfile(principal)){
            model.addAttribute("doctorStatus","Please keep patient and wait until you are verified by a admin");
            return "doctor/home";
        } else {
            return "redirect:/doctor/";
        }
    }




    @GetMapping("/completeProfile")
    public String completeProfile(Model model) {
        List<DoctorExpertise> doctorExpertises = this.doctorService.doctorExpertises();
        model.addAttribute("doctorDto",new DoctorDto());
        model.addAttribute("expertiseList",doctorExpertises);
        return "doctor/completeProfileInfo";
    }



//    Complete Doctors Profile with appropriate details
    @PostMapping("/do-complete-profile")
    public String doCompleteProfile(@Valid @ModelAttribute DoctorDto doctorDto, BindingResult result,
                                    Model model, RedirectAttributes redirectAttributes,Principal principal) {
        if(result.hasErrors()) {
            model.addAttribute("message",new Message("Enter All Data Correctly","alert-danger"));
            return "doctor/completeProfileInfo";
        }

        this.doctorService.createDoctor(doctorDto,principal);
        redirectAttributes.addFlashAttribute("message",new Message("You Have submitted your data. wait until it is verified.","alert-success"));
        return "redirect:/doctor/checkVerification";
    }







//    Hospital Process
    @GetMapping("/apply-hospital")
    public String applyInHospital(Model model) {
        List<Hospital> hospitalList = this.adminService.unVerifiedHospitals(true);
        model.addAttribute("timeSchedule",new DrReqTimeScheduleDto());
        model.addAttribute("hospitalsList",hospitalList);
        return "doctor/applyInHospital";
    }

    @PostMapping("/doApplyHospital")
    public String doApplyHospital(@ModelAttribute DrReqTimeScheduleDto timeScheduleDto,RedirectAttributes redirectAttributes,Principal principal)
    {
        this.doctorService.requestToHospital(timeScheduleDto,principal);
        redirectAttributes.addFlashAttribute("message",new Message("Success","alert-success"));
        return "redirect:/doctor/";
    }


    @GetMapping("/pending-patients")
    public String getPendingPatients(Model model,Principal principal) {
        List<Appointment> appointments = this.doctorService.getPendingAppointments(principal);
        model.addAttribute("appointments",appointments);
        return "doctor/appointment-history";
    }
}
