package com.clinic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee implements Cloneable{

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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "shift_id")
    private List<Shift> shift = new ArrayList<>();

    @OneToMany(
            mappedBy = "employee",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Appointment> appointments = new ArrayList<>();

    @ManyToMany(
            fetch = FetchType.EAGER,
            mappedBy = "employees",
            cascade = CascadeType.PERSIST
    )
    private List<Treatment> treatments = new ArrayList<>();

    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public Employee clone() throws CloneNotSupportedException {
        return (Employee) super.clone();
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
