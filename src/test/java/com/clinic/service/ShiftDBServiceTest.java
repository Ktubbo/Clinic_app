package com.clinic.service;

import com.clinic.domain.Employee;
import com.clinic.domain.Shift;
import com.clinic.exceptions.ShiftNotFoundException;
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
class ShiftDBServiceTest {

    @Autowired
    private ShiftDBService service;

    private Shift shift;

    void prepare() {
        Employee employee = new Employee("David","Brown");
        LocalDateTime start = LocalDateTime.of(2021,2,19,15,30);
        LocalDateTime end = LocalDateTime.of(2021,2,19,16,30);
        this.shift = new Shift(start,end,employee);
    }

    @Test
    void saveAndGetAllShifts() {
        //Given
        prepare();
        int actualSizeOfShift = service.getAllShifts().size();
        //When
        service.saveShift(shift);
        service.saveShift(new Shift());
        //Then
        List<Shift> shifts = service.getAllShifts();
        assertEquals(actualSizeOfShift+2,shifts.size());
    }

    @Test
    void getShift() {
        //Given
        prepare();
        //When
        service.saveShift(shift);
        long id = shift.getId();
        //Then
        Shift resultShift = service.getShift(id).get();
        assertEquals("David", resultShift.getEmployee().getFirstName());
    }

    @Test
    void deleteShift() {
        //Given
        prepare();
        int actualSizeOfShift = service.getAllShifts().size();
        //When
        service.saveShift(shift);
        long id = shift.getId();
        //Then
        service.deleteShift(id);
        int resultSizeOfShift = service.getAllShifts().size();
        assertEquals(actualSizeOfShift, resultSizeOfShift);
    }
}





