package com.clinic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Treatment implements Cloneable{

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

    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "Employee_Treatment",
            joinColumns = { @JoinColumn(name = "treatment_id")},
            inverseJoinColumns = { @JoinColumn(name = "employee_id")}
    )
    private List<Employee> employees = new ArrayList<>();

    public Treatment(String name, BigDecimal price, Duration duration) {
        this.name = name;
        this.price = price;
        this.duration = duration;
    }

    @Override
    public Treatment clone() throws CloneNotSupportedException {
        return (Treatment) super.clone();
    }

    @Override
    public String toString() {
        return id + " " + name;
    }
}
