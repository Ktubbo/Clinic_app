package com.clinic.domain.dto;

import com.clinic.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class TreatmentDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private List<Employee> employee;
}