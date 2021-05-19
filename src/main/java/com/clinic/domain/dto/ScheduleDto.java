package com.clinic.domain.dto;

import com.clinic.domain.Appointment;
import com.clinic.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleDto {

    private Long id;
    private String start;
    private String end;
    private Employee employee;
    private AppointmentDto appointment;
}
