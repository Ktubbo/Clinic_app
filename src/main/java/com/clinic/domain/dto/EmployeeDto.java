package com.clinic.domain.dto;

import com.clinic.domain.Appointment;
import com.clinic.domain.Shift;
import com.clinic.domain.Treatment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeDto {

    private Long id;
    private String firstName;
    private String lastName;
    private List<Shift> shift;
    private List<Appointment> appointments;
    private List<Treatment> treatments;
}
