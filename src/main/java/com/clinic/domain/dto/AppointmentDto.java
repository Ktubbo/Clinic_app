package com.clinic.domain.dto;

import com.clinic.domain.Customer;
import com.clinic.domain.Employee;
import com.clinic.domain.Service;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AppointmentDto {

    private Long id;
    private LocalDateTime start;
    private Duration duration;
    private Service service;
    private Customer customer;
    private Employee employee;
    private int rate;
    private BigDecimal price;
}
