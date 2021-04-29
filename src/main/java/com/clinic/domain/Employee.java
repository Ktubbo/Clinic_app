package com.clinic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {

    @Id
    @NotNull
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @OneToMany(mappedBy = "employee")
    private List<Appointment> appointment;

    @ManyToMany
    private List<Service> service;
}
