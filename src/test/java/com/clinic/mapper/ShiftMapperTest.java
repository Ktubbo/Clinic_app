package com.clinic.mapper;

import com.clinic.domain.*;
import com.clinic.domain.dto.ShiftDto;
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
class ShiftMapperTest {

    @Autowired
    ShiftMapper mapper;

    private Shift shift;
    private ShiftDto shiftDto;

    void prepare() {

        Employee employee = new Employee("David","Brown");
        LocalDateTime start = LocalDateTime.of(2021,2,19,15,30);
        LocalDateTime end = LocalDateTime.of(2021,2,19,16,30);

        this.shift = new Shift(1L,start,end,employee);
        this.shiftDto = new ShiftDto(1L,"19 02 2021","FRIDAY","15:30","16:30",employee);
    }

    @Test
    void mapToShift() {
        //Given
        prepare();
        //When
        Shift resultShift = mapper.mapToShift(shiftDto);
        //Then
        assertEquals(1,resultShift.getId());
        assertEquals(LocalDateTime.of(2021,2,19,15,30),resultShift.getStart());
        assertEquals(LocalDateTime.of(2021,2,19,16,30),resultShift.getEnd());
        assertEquals("David",resultShift.getEmployee().getFirstName());
    }

    @Test
    void mapToShiftDto() {
        //Given
        prepare();
        //When
        ShiftDto resultShift = mapper.mapToShiftDto(shift);
        //Then
        assertEquals(1,resultShift.getId());
        assertEquals("19 02 2021",resultShift.getDate());
        assertEquals("FRIDAY",resultShift.getDayName());
        assertEquals("15:30",resultShift.getStartHour());
        assertEquals("16:30",resultShift.getEndHour());
        assertEquals("David",resultShift.getEmployee().getFirstName());
    }

    @Test
    void mapToShiftDtoList() {
        //Given
        prepare();
        //When
        List<ShiftDto> resultList = mapper.mapToShiftDtoList(List.of(shift));
        //Then
        assertEquals(1,resultList.size());
    }

    @Test
    void mapToShiftList() {
        //Given
        prepare();
        //When
        List<Shift> resultList = mapper.mapToShiftList(List.of(shiftDto));
        //Then
        assertEquals(1,resultList.size());
    }
}