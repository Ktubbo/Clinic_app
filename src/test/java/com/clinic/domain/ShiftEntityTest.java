package com.clinic.domain;

import com.clinic.repository.ShiftRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
class ShiftEntityTest {

    @Autowired
    ShiftRepository repository;

    private Shift shift;
    private long id;

    public void prepare() {

        Employee employee = new Employee("David","Brown");
        LocalDateTime start = LocalDateTime.of(2021,2,19,15,30);
        LocalDateTime end = LocalDateTime.of(2021,2,19,16,30);

        this.shift = new Shift(start,end,employee);

        repository.save(shift);
        this.id = shift.getId();
    }

    @Test
    void getStart() {
        //Given
        prepare();
        //When&Then
        assertEquals(LocalDateTime.of(2021,2,19,15,30),repository.findById(id)
                .get().getStart());
    }

    @Test
    void getEnd() {
        //Given
        prepare();
        //When&Then
        assertEquals(LocalDateTime.of(2021,2,19,16,30),repository.findById(id)
                .get().getEnd());
    }

    @Test
    void getEmployee() {
        //Given
        prepare();
        //When&Then
        assertEquals("Brown",repository.findById(id)
                .get().getEmployee().getLastName());
    }

    @Test
    void setStart() {
        //Given
        prepare();
        //When
        shift.setStart(LocalDateTime.of(2021,2,19,15,0));
        repository.save(shift);
        //Then
        assertEquals(LocalDateTime.of(2021,2,19,15,0),repository.findById(id)
                .get().getStart());
    }

    @Test
    void setEnd() {
        //Given
        prepare();
        //When
        shift.setEnd(LocalDateTime.of(2021,2,19,16,0));
        repository.save(shift);
        //Then
        assertEquals(LocalDateTime.of(2021,2,19,16,0),repository.findById(id)
                .get().getEnd());
    }

    @Test
    void setEmployee() {
        //Given
        prepare();
        shift.setEmployee(new Employee("Mike","Johnson"));
        repository.save(shift);
        //When
        //Then
        assertEquals("Mike",repository.findById(id).get()
                .getEmployee().getFirstName());
    }
}

