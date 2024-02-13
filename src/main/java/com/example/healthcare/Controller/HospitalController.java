package com.example.healthcare.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hospital")
public class HospitalController {
    @GetMapping("/")
    public String home() {
        return "hospital/home";
    }
}
