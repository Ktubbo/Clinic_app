package com.clinic.mapper;

import com.clinic.domain.*;
import com.clinic.domain.dto.ScheduleDto;
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
class ScheduleMapperTest {

    @Autowired
    ScheduleMapper mapper;

    private Schedule schedule;
    private ScheduleDto scheduleDto;

    void prepare() {
        Customer customer = new Customer("John","Smith","72120112124");
        Employee employee = new Employee("David","Brown");
        Treatment treatment = new Treatment("Botox", BigDecimal.valueOf(300), Duration.of(1, ChronoUnit.HOURS));
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

        this.schedule = new Schedule(1L,start,end,employee,appointment);
        this.scheduleDto = new ScheduleDto(1L,start,end,employee,appointment);
    }

    @Test
    void mapToSchedule() {
        //Given
        prepare();
        //When
        Schedule resultSchedule = mapper.mapToSchedule(scheduleDto);
        //Then
        assertEquals(1,resultSchedule.getId());
        assertEquals(LocalDateTime.of(2021,2,20,15,30),resultSchedule.getStart());
        assertEquals(LocalDateTime.of(2021,2,20,16,30),resultSchedule.getEnd());
        assertEquals("David",resultSchedule.getEmployee().getFirstName());
        assertEquals("Smith",resultSchedule.getAppointment().getCustomer().getLastName());
    }

    @Test
    void mapToScheduleDto() {
        //Given
        prepare();
        //When
        ScheduleDto resultSchedule = mapper.mapToScheduleDto(schedule);
        //Then
        assertEquals(1,resultSchedule.getId());
        assertEquals(LocalDateTime.of(2021,2,20,15,30),resultSchedule.getStart());
        assertEquals(LocalDateTime.of(2021,2,20,16,30),resultSchedule.getEnd());
        assertEquals("David",resultSchedule.getEmployee().getFirstName());
        assertEquals("Smith",resultSchedule.getAppointment().getCustomer().getLastName());
    }

    @Test
    void mapToScheduleDtoList() {
        //Given
        prepare();
        //When
        List<ScheduleDto> resultList = mapper.mapToScheduleDtoList(List.of(schedule));
        //Then
        assertEquals(1,resultList.size());
    }

    @Test
    void mapToScheduleList() {
        //Given
        prepare();
        //When
        List<Schedule> resultList = mapper.mapToScheduleList(List.of(scheduleDto));
        //Then
        assertEquals(1,resultList.size());
    }
}