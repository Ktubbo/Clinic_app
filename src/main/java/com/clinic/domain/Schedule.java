package com.clinic.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Schedule")
public class Schedule {

    @Id
    @NotNull
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "start")
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime start;

    @Column(name = "end")
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime end;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    public Schedule(LocalDateTime start, LocalDateTime end, Employee employee, Appointment appointment) {
        this.start = start;
        this.end = end;
        this.employee = employee;
        this.appointment = appointment;
    }
}
