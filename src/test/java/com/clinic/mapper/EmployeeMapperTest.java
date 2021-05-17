package com.clinic.mapper;

import com.clinic.domain.*;
import com.clinic.domain.dto.AppointmentDto;
import com.clinic.domain.dto.EmployeeDto;
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
class EmployeeMapperTest {

    @Autowired
    EmployeeMapper mapper;

    private Customer customer;
    private Employee employee;
    private EmployeeDto employeeDto;
    private Treatment treatment;
    private PricingStrategy groupon;
    private LocalDateTime start;
    private Appointment appointment;
    private Shift shift;

    public void prepare() {
        this.shift = new Shift(start,LocalDateTime.of(2021,2,20,16,30),employee);
        this.customer = new Customer("John","Smith","72120112124");
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

        this.employee = new Employee(1L,
                "David",
                "Smith",
                List.of(shift),
                List.of(appointment),
                List.of(treatment));

        this.employeeDto = new EmployeeDto(1L,
                "Mike",
                "Brown",
                List.of(shift),
                List.of(appointment),
                List.of(treatment));
    }

    @Test
    void mapToEmployee() {
        //Given
        prepare();
        //When
        Employee resultEmployee = mapper.mapToEmployee(employeeDto);
        //Then
        assertEquals(1L,resultEmployee.getId());
        assertEquals("Mike",resultEmployee.getFirstName());
        assertEquals("Brown",resultEmployee.getLastName());
        assertEquals(1,resultEmployee.getShift().size());
        assertEquals(1,resultEmployee.getAppointments().size());
        assertEquals(1,resultEmployee.getTreatments().size());
    }

    @Test
    void mapToEmployeeDto() {
        //Given
        prepare();
        //When
        EmployeeDto resultEmployee = mapper.mapToEmployeeDto(employee);
        //Then
        assertEquals(1L,resultEmployee.getId());
        assertEquals("David",resultEmployee.getFirstName());
        assertEquals("Smith",resultEmployee.getLastName());
        assertEquals(1,resultEmployee.getShift().size());
        assertEquals(1,resultEmployee.getAppointments().size());
        assertEquals(1,resultEmployee.getTreatments().size());
    }

    @Test
    void mapToEmployeeDtoList() {
        //Given
        prepare();
        //When
        List<Employee> employees = List.of(employee);
        List<EmployeeDto> resultList = mapper.mapToEmployeeDtoList(employees);
        //Then
        assertEquals(1,resultList.size());
    }

    @Test
    void mapToEmployeeList() {
        //Given
        prepare();
        //When
        List<EmployeeDto> employees = List.of(employeeDto);
        List<Employee> resultList = mapper.mapToEmployeeList(employees);
        //Then
        assertEquals(1,resultList.size());
    }
}