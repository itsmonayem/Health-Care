package com.example.healthcare.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
    @GetMapping("/")
    public String home() {
        System.out.println("abd");
        return "doctor/home";
    }
}
