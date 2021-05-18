package com.clinic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Shift implements Cloneable{

    @Id
    @NotNull
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "start")
    private LocalDateTime start;

    @Column(name = "end")
    private LocalDateTime end;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Override
    public Shift clone() throws CloneNotSupportedException {
        return (Shift) super.clone();
    }

    @Override
    public String toString() {
        return employee.getFirstName() + " " + employee.getLastName();
    }

    public Shift(LocalDateTime start, LocalDateTime end, Employee employee) {
        this.start = start;
        this.end = end;
        this.employee = employee;
    }
}
