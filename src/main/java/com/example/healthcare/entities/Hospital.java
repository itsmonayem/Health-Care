package com.example.healthcare.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String location;
    private boolean isCompleteProfile;
    private boolean isVerified;
    private boolean isDeclined;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ManyToMany
    @JoinTable(name = "hospital_doctor", joinColumns =
            {@JoinColumn(name = "hospital_id")},
            inverseJoinColumns = {@JoinColumn(name = "doctor_id")})
    private List<Doctor> doctorList = new ArrayList<>();
}
