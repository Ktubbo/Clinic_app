package com.clinic.domain.dto;

import com.clinic.domain.Appointment;
import com.clinic.domain.Schedule;
import com.clinic.domain.Treatment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class EmployeeDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Schedule schedule;
    private List<Appointment> appointment;
    private List<Treatment> treatment;
}
