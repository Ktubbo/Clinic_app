package com.clinic.domain.dto;

import com.clinic.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ShiftDto {

    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Employee employee;
}
