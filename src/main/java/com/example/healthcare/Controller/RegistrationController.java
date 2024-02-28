package com.example.healthcare.Controller;

import com.example.healthcare.dto.UserDto;
import com.example.healthcare.entity.User;
import com.example.healthcare.helper.Message;
import com.example.healthcare.service.UserRegService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {
    private final ModelMapper modelMapper;
    private final UserRegService userRegService;

    public RegistrationController(ModelMapper modelMapper, UserRegService userRegService) {
        this.modelMapper = modelMapper;
        this.userRegService = userRegService;
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "signup";
    }

    @PostMapping("/do-register")
    public String doRegister(@Valid @ModelAttribute UserDto userDto, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("message",new Message("Error","alert-danger"));
            model.addAttribute("userDto",userDto);
            return "signup";
        } else {
            User user = modelMapper.map(userDto, User.class);
            userRegService.createUser(user);
            redirectAttributes.addFlashAttribute("message",new Message("Success","alert-success"));
            return "redirect:/signup";
        }
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
