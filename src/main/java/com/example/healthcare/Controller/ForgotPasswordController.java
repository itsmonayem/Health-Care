package com.example.healthcare.Controller;


import com.example.healthcare.entity.User;
import com.example.healthcare.helper.Message;
import com.example.healthcare.repositories.UserRepository;
import com.example.healthcare.service.EmailSenderService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.Random;

@Controller
public class ForgotPasswordController {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailSenderService emailService;

    public ForgotPasswordController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, EmailSenderService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgotPassword/giveEmail";
    }

    @PostMapping("/generateOTP")
    public String generateOTP(@RequestParam String username, RedirectAttributes redirectAttributes, Model model, HttpSession session) throws MessagingException, UnsupportedEncodingException {
        System.out.println("Email: " + username);
        User user = this.userRepository.getUserByUserName(username);
        if(user == null) {
            redirectAttributes.addFlashAttribute("message",new Message("This username is not available","alert-danger"));
            return "redirect:/login";
        }
        int randomOTP = (int) (Math.random() * 8999) + 1000;
        String subject = "Request for password change";
        String body = "<p>You have made a request for password change. If you have not done this, then you can ignore it</p>" +
                "<h1>OTP: " + randomOTP + "</h1>";

        this.emailService.sendEmail(username,subject,body);

        System.out.println("Your OTP is: " + randomOTP);
        session.setAttribute("user",user);
        session.setAttribute("OTP",randomOTP);
        return "forgotPassword/giveOTP";
    }

    @PostMapping("/createPassword")
    public String createPassword(@RequestParam int clientOTP, RedirectAttributes redirectAttributes, Model model, HttpSession session) {
        int serverOTP = (int) session.getAttribute("OTP");
        if (serverOTP != clientOTP) {
            redirectAttributes.addFlashAttribute("message",new Message("OTP is not matched","alert-danger"));
            return "redirect:/login";
        }
        return "forgotPassword/create-password";
    }


    @PostMapping("/confirmPassword")
    public String confirmPassword(@RequestParam String password, @RequestParam String confirmPassword,
                                  RedirectAttributes redirectAttributes, Model model, HttpSession session) {
        if (!Objects.equals(password, confirmPassword)) {
            redirectAttributes.addFlashAttribute("message",new Message("password is not matched","alert-danger"));
            return "redirect:/createPassword";
        }
        User user = (User) session.getAttribute("user");
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        redirectAttributes.addFlashAttribute("message",new Message("Password Changed Successfully","alert-success"));
        return "redirect:/login";
    }

}
