package com.clinic.domain.dto;

import com.clinic.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TreatmentDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private DurationDto duration;
    private List<Employee> employee;

    public TreatmentDto(String name, BigDecimal price, DurationDto duration) {
        this.name = name;
        this.price = price;
        this.duration = duration;
    }
}
