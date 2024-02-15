package com.example.healthcare.dto;

import com.example.healthcare.entities.DoctorExpertise;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DoctorDto {
    @NotEmpty(message = "Designation field should not be empty")
    private String designation;
    private DoctorExpertise doctorExpertise;
}
