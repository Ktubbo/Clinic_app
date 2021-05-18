package com.clinic.domain;

import com.clinic.repository.ScheduleRepository;
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

import static org.junit.Assert.assertEquals;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
class ScheduleEntityTest {

    @Autowired
    ScheduleRepository repository;

    private Schedule schedule;
    private long id;

    public void prepare() {
        Customer customer = new Customer("John","Smith","72120112124");
        Employee employee = new Employee("David","Brown");
        Treatment treatment = new Treatment("Botox", BigDecimal.valueOf(300),
                Duration.of(1, ChronoUnit.HOURS));
        PricingStrategy groupon = PricingStrategy.GROUPON;
        LocalDateTime start = LocalDateTime.of(2021,2,20,15,30);
        LocalDateTime end = LocalDateTime.of(2021,2,20,16,30);

        Appointment appointment = new Appointment.AppointmentBuilder()
                .customer(customer)
                .employee(employee)
                .treatment(treatment)
                .pricingStrategy(groupon)
                .start(start)
                .build();

        this.schedule = new Schedule(start,end,employee,appointment);

        repository.save(schedule);
        this.id = schedule.getId();
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
    void getEnd() {
        //Given
        prepare();
        //When
        //Then
        assertEquals(LocalDateTime.of(2021,2,20,16,30)
                ,repository.findById(id).get().getEnd());
    }

    @Test
    void getEmployee() {
        //Given
        prepare();
        //When
        //Then
        assertEquals("David",repository.findById(id).get()
                .getEmployee().getFirstName());
    }

    @Test
    void getAppointment() {
        //Given
        prepare();
        //When
        //Then
        assertEquals("72120112124",repository.findById(id).get()
                .getAppointment().getCustomer().getPesel());
    }
}

