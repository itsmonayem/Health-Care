package com.example.healthcare.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Doctor {
    @Id
    private long id;
    private String designation;
    private String speciality;
    private boolean isCompleteProfile;
    private boolean isVerified;
    private boolean isDeclined;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
