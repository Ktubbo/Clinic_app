package com.clinic.mapper;

import com.clinic.domain.*;
import com.clinic.domain.dto.AppointmentDto;
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

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
class AppointmentMapperTest {

    @Autowired
    AppointmentMapper appointmentMapper;

    private Customer customer;
    private Employee employee;
    private Treatment treatment;
    private PricingStrategy groupon;
    private LocalDateTime start;
    private Appointment appointment;
    private AppointmentDto appointmentDto;

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

        this.appointmentDto = new AppointmentDto(1L,start,treatment,customer,
                employee,"GROUPON",treatment.getPrice());
    }


    @Test
    void mapToAppointment() {
        //Given
        prepare();
        //When
        Appointment resultAppointment = appointmentMapper.mapToAppointment(appointmentDto);
        //Then
        assertEquals(LocalDateTime.of(2021,2,20,15,30),resultAppointment.getStart());
        assertEquals("John",resultAppointment.getCustomer().getFirstName());
        assertEquals("Brown",resultAppointment.getEmployee().getLastName());
        assertEquals("Botox",resultAppointment.getTreatment().getName());
        assertEquals("GROUPON",resultAppointment.getPricingStrategy());
        assertEquals(BigDecimal.valueOf(150.0),resultAppointment.getPrice());
    }

    @Test
    void mapToAppointmentDto() {
        //Given
        prepare();
        //When
        AppointmentDto resultAppointmentDto = appointmentMapper.mapToAppointmentDto(appointment);
        //Then
        assertEquals(LocalDateTime.of(2021,2,20,15,30),resultAppointmentDto.getStart());
        assertEquals("John",resultAppointmentDto.getCustomer().getFirstName());
        assertEquals("Brown",resultAppointmentDto.getEmployee().getLastName());
        assertEquals("Botox",resultAppointmentDto.getTreatment().getName());
        assertEquals("GROUPON",resultAppointmentDto.getPricingStrategy());
        assertEquals(BigDecimal.valueOf(150.0),resultAppointmentDto.getPrice());
    }

    @Test
    void mapToAppointmentDtoList() {
        //Given
        prepare();
        //When
        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(appointment);
        appointmentList.add(new Appointment());
        List<AppointmentDto> resultList = appointmentMapper.mapToAppointmentDtoList(appointmentList);
        //Then
        assertEquals(2,resultList.size());
    }

    @Test
    void mapToAppointmentList() {
        //Given
        prepare();
        //When
        List<AppointmentDto> appointmentDtoList = new ArrayList<>();
        appointmentDtoList.add(appointmentDto);
        List<Appointment> resultList = appointmentMapper.mapToAppointmentList(appointmentDtoList);
        //Then
        assertEquals(1,resultList.size());
    }
}