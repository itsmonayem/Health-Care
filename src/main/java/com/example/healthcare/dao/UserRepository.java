package com.example.healthcare.dao;

import com.example.healthcare.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.email=:email")
    User getUserByUserName(@Param("email") String email);
}
