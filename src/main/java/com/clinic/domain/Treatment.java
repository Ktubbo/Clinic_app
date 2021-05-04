package com.clinic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Treatment {

    @Id
    @NotNull
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "price")
    @NotNull
    private BigDecimal price;

    @Column(name = "duration")
    private Duration duration;

    @ManyToMany
    private List<Employee> employee;

    public Treatment(String name, BigDecimal price, Duration duration) {
        this.name = name;
        this.price = price;
        this.duration = duration;
    }
}
