package com.example.healthcare.Controller;

import com.example.healthcare.dao.UserRepository;
import com.example.healthcare.dto.DoctorDto;
import com.example.healthcare.entities.Doctor;
import com.example.healthcare.helper.Message;
import com.example.healthcare.service.DoctorService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("doctorStatus","Welcome");
        return "doctor/home";
    }


    @RequestMapping("/checkVerification")
    public String isVerified(Principal principal, Model model) {
        if (this.doctorService.getDoctor(principal) == null) {
            return "redirect:/doctor/completeProfile";
        } else if (this.doctorService.isVerified(principal)) {
            model.addAttribute("doctorStatus","Congratulation you are in Service");
            return "doctor/home";
        } else if (this.doctorService.isCompleteProfile(principal)){
            model.addAttribute("doctorStatus","Please keep patient and wait until you are verified by a admin");
            return "doctor/home";
        } else {
            return "redirect:/doctor/";
        }
    }




    @GetMapping("/completeProfile")
    public String completeProfile(Model model) {
        model.addAttribute("doctorDto",new DoctorDto());
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
        return "redirect:/doctor/completeProfile";
    }
}
