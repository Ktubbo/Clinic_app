package com.clinic.domain;

import com.clinic.repository.EmployeeRepository;
import com.clinic.repository.ShiftRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
class EmployeeEntityTest {

    @Autowired
    EmployeeRepository repository;
    @Autowired
    ShiftRepository shiftRepository;

    private Employee employee;
    private long id;

    public void prepare() {
        this.employee = new Employee("David","Brown");
        Customer customer = new Customer("John","Smith","72120112124");

        Treatment treatment = new Treatment("Botox",
                BigDecimal.valueOf(300),Duration.of(1, ChronoUnit.HOURS));

        PricingStrategy groupon = PricingStrategy.GROUPON;
        LocalDateTime start = LocalDateTime.of(2021,2,20,15,30);

        Appointment appointment = new Appointment.AppointmentBuilder()
                .customer(customer)
                .employee(employee)
                .treatment(treatment)
                .pricingStrategy(groupon)
                .start(start)
                .build();

        Shift shift = new Shift(LocalDateTime.of(2021,2,19,8,0),
                LocalDateTime.of(2021,2,19,16,0),
                employee);

        shiftRepository.save(shift);
        List<Treatment> treatments = new ArrayList<>();
        treatments.add(treatment);
        List<Shift> shifts = new ArrayList<>();
        shifts.add(shift);
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);
        employee.setTreatments(treatments);
        employee.setShift(shifts);
        employee.setAppointments(appointments);

        repository.save(employee);
        this.id = employee.getId();
    }

    @Test
    void getFirstName() {
        //Given
        prepare();
        //When&Then
        assertEquals("David",repository.findById(id).get().getFirstName());
    }

    @Test
    void getLastName() {
        //Given
        prepare();
        //When&Then
        assertEquals("Brown",repository.findById(id).get().getLastName());
    }

    @Test
    void getShift() {
        //Given
        prepare();
        //When&Then
        assertEquals(LocalDateTime.of(2021,2,19,8,0),
                repository.findById(id).get().getShift().get(0).getStart());
    }

    @Test
    void getTreatments() {
        //Given
        prepare();
        //When&Then
        assertEquals("Botox",repository.findById(id).get()
                .getTreatments().get(0).getName());
    }

    @Test
    void getAppoitnments() {
        //Given
        prepare();
        //When&Then
        assertEquals("John",repository.findById(id).get().getAppointments().get(0)
                .getCustomer().getFirstName());
    }

    @Test
    void setFirstName() {
        //Given
        prepare();
        //When
        employee.setFirstName("Mike");
        repository.save(employee);
        //Then
        assertEquals("Mike",repository.findById(id).get().getFirstName());
    }

    @Test
    void setLastName() {
        //Given
        prepare();
        //When
        employee.setLastName("Johnson");
        repository.save(employee);
        //Then
        assertEquals("Johnson",repository.findById(id).get().getLastName());
    }

    @Test
    void setShift() {
        //Given
        prepare();
        //When
        List<Shift> shifts = new ArrayList<>();
        employee.setShift(shifts);
        repository.save(employee);
        //Then
        assertEquals(0,repository.findById(id).get().getShift().size());
    }

    @Test
    void setTreatments() {
        //Given
        prepare();
        //When
        List<Treatment> treatments = new ArrayList<>();
        employee.setTreatments(treatments);
        repository.save(employee);
        //Then
        assertEquals(0,repository.findById(id).get().getTreatments().size());
    }

    @Test
    void setAppointments() {
        //Given
        prepare();
        //When
        List<Appointment> appointments = new ArrayList<>();
        employee.setAppointments(appointments);
        repository.save(employee);
        //Then
        assertEquals(0,repository.findById(id).get().getAppointments().size());
    }
}

