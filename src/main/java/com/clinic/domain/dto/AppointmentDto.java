package com.clinic.domain.dto;

import com.clinic.domain.Customer;
import com.clinic.domain.Employee;
import com.clinic.domain.Treatment;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {

    private Long id;
    private String start;
    private TreatmentDto treatment;
    private Customer customer;
    private Employee employee;
    private String pricingStrategy;
    private BigDecimal price;

    @Override
    public String toString() {
        return treatment + "\n" + customer + "\n" + price;
    }
}
