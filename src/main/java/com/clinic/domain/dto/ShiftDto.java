package com.clinic.domain.dto;

import com.clinic.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ShiftDto {

    private Long id;
    private String date;
    private String dayName;
    private String startHour;
    private String endHour;
    private Employee employee;
    }
