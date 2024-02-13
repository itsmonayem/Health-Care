package com.example.healthcare.config;

import com.example.healthcare.dao.UserRepository;
import com.example.healthcare.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.getUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not found any user");
        }
        return new CustomUserDetails(user);
    }
}
