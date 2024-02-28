package com.example.healthcare;

import com.example.healthcare.repositories.UserRepository;
import com.example.healthcare.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class HealthcareApplication implements CommandLineRunner {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public HealthcareApplication(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(HealthcareApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(this.userRepository.findAll().isEmpty()) {
			User user = new User();
			user.setName("Admin");
			user.setEmail("abc@dsi.com");
			user.setPassword(this.passwordEncoder.encode("123"));
			user.setRole("ROLE_ADMIN");
			user.setContact_no("123456");
			this.userRepository.save(user);
		}
	}
}
