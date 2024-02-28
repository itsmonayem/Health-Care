package com.example.healthcare.dto;

import com.example.healthcare.entity.Hospital;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DrReqTimeScheduleDto {
    private Hospital hospital;
    private String startScheduleTime;
    private String endScheduleTime;
}
