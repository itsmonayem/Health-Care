package com.example.healthcare.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Supplier;

@Component
public class MySuccessHandler implements AuthenticationSuccessHandler {


    Supplier<String> stringSupplier = () -> "test";


    String getString () {
        return "test";
    }


    void testString(Supplier<String> test) {
        test.get();
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof UsernamePasswordAuthenticationToken upAt) {
            System.out.println(upAt);
            CustomUserDetails customUserDetails = (CustomUserDetails) upAt.getPrincipal();
            if("ROLE_DOCTOR".equals(customUserDetails.getUser().getRole())){
                response.sendRedirect("/doctor/checkVerification");
            } else if("ROLE_HOSPITAL".equals(customUserDetails.getUser().getRole())){
                response.sendRedirect("/hospital/checkVerification");
            } else if("ROLE_USER".equals(customUserDetails.getUser().getRole())){
                response.sendRedirect("/user/");
            } else if("ROLE_ADMIN".equals(customUserDetails.getUser().getRole())){
                response.sendRedirect("/admin/");
            }
        }

    }
}
