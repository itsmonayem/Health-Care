package com.example.healthcare.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String designation;
    private String speciality;
    @OneToOne
    @JoinColumn(name = "expertise_id")
    private DoctorExpertise expertise;
    private boolean isCompleteProfile;
    private boolean isVerified;
    private boolean isDeclined;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @ManyToMany(mappedBy = "doctorList")
    private List<Hospital> hospitalList = new ArrayList<>();
}
