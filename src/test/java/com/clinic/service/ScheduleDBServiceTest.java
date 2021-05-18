package com.clinic.service;

import com.clinic.domain.*;
import com.clinic.exceptions.ScheduleNotFoundException;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
class ScheduleDBServiceTest {

    @Autowired
    private ScheduleDBService service;
    @Autowired
    private AppointmentDBService appointmentDBService;
    @Autowired
    private EmployeeDBService employeeDBService;
    @Autowired
    private CustomerDBService customerDBService;

    private Schedule schedule;

    void prepare() {
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

        customerDBService.saveCustomer(customer);
        employeeDBService.saveEmployee(employee);

        try {
            appointmentDBService.saveAppointment(appointment);
        } catch (Exception e) {
            System.out.println(e);
        }

        this.schedule = new Schedule(start,end,employee,appointment);
    }

    @Test
    void saveAndGetAllSchedules() {
        //Given
        prepare();
        int actualSizeOfSchedule = service.getAllSchedules().size();
        //When
        service.saveSchedule(schedule);
        service.saveSchedule(new Schedule());
        //Then
        List<Schedule> schedules = service.getAllSchedules();
        assertEquals(actualSizeOfSchedule+2,schedules.size());
    }

    @Test
    void getSchedule() {
        //Given
        prepare();
        //When
        service.saveSchedule(schedule);
        long id = schedule.getId();
        //Then
        Schedule resultSchedule = service.getSchedule(id).get();
        assertEquals("David", resultSchedule.getEmployee().getFirstName());
    }

    @Test
    void deleteSchedule() {
        //Given
        prepare();
        int actualSizeOfSchedule = service.getAllSchedules().size();
        //When
        service.saveSchedule(schedule);
        long id = schedule.getId();
        //Then
        service.deleteSchedule(id);
        int resultSizeOfSchedule = service.getAllSchedules().size();
        assertEquals(actualSizeOfSchedule, resultSizeOfSchedule);
    }
}




