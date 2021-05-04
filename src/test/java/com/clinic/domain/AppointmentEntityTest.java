package com.clinic.domain;

import com.clinic.repository.AppointmentRepository;
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

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
class AppointmentEntityTest {

    @Autowired
    AppointmentRepository repository;

    private Customer customer;
    private Employee employee;
    private Treatment treatment;
    private PricingStrategy groupon;
    private LocalDateTime start;
    private Appointment appointment;
    private long id;

    public void prepare() {
        this.customer = new Customer("John","Smith","72120112124");
        this.employee = new Employee("David","Brown");
        this.treatment = new Treatment("Botox", BigDecimal.valueOf(300), Duration.of(1, ChronoUnit.HOURS));
        this.groupon = PricingStrategy.GROUPON;
        this.start = LocalDateTime.of(2021,2,20,15,30);

        this.appointment = new Appointment.AppointmentBuilder()
                .customer(customer)
                .employee(employee)
                .treatment(treatment)
                .pricingStrategy(groupon)
                .start(start)
                .build();

        repository.save(appointment);
        this.id = appointment.getId();
    }

    @Test
    void getCustomer() {
        //Given
        prepare();
        //When
        //Then
        assertEquals("John",repository.findById(id).get().getCustomer().getFirstName());
    }

    @Test
    void getEmployee() {
        //Given
        prepare();
        //When
        //Then
        assertEquals("Brown",repository.findById(id).get().getEmployee().getLastName());
    }

    @Test
    void getTreatment() {
        //Given
        prepare();
        //When
        //Then
        assertEquals("Botox",repository.findById(id).get().getTreatment().getName());
    }

    @Test
    void getStart() {
        //Given
        prepare();
        //When
        //Then
        assertEquals(LocalDateTime.of(2021,2,20,15,30)
                ,repository.findById(id).get().getStart());
    }

    @Test
    void getPrice() {
        //Given
        prepare();
        //When
        //Then
        assertEquals(BigDecimal.valueOf(150.0),repository.findById(id).get().getPrice());
    }

    @Test
    void getPriceWhilePricingStrategyIsNull() {
        //Given
        Appointment appointment = new Appointment.AppointmentBuilder()
                .treatment(new Treatment("Botox", BigDecimal.valueOf(300.0), Duration.of(1, ChronoUnit.HOURS)))
                .build();
        //When
        repository.save(appointment);
        long id = appointment.getId();
        //Then
        assertEquals(BigDecimal.valueOf(300.0),repository.findById(id).get().getPrice());
    }
}