package com.example.healthcare.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DoctorRequestedSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String startScheduleTime;
    private String endScheduleTime;
    @ManyToOne
    private Doctor doctor;
    @ManyToOne
    private Hospital hospital;
    private String status;
}
