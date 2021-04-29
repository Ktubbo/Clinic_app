package com.clinic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Appointment {

    @Id
    @NotNull
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "start")
    private LocalDateTime start;

    @Column(name = "duration")
    private Duration duration;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_id")
    private Service service;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "rate")
    private int rate;

    @Column(name = "price")
    private BigDecimal price;
}
