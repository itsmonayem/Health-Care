package com.example.healthcare.dto;

import com.example.healthcare.entities.Hospital;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Timer;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DrReqTimeScheduleDto {
    private Hospital hospital;
    private String startScheduleTime;
    private String endScheduleTime;
}
