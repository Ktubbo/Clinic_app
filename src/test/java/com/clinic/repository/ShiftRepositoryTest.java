package com.clinic.repository;

import com.clinic.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
class ShiftRepositoryTest {

    @Autowired
    ShiftRepository repository;
    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    void saveAndFindAll() {
        //Given
        Shift shift1 = new Shift();
        Shift shift2 = new Shift();
        //When
        repository.save(shift1);
        repository.save(shift2);
        //Then
        List<Shift> shifts = repository.findAll();
        assertEquals(2,shifts.size());
    }

    @Test
    void findById() {
        //Given
        Shift shift1 = new Shift();
        //When
        repository.save(shift1);
        long id = shift1.getId();
        //Then
        Optional<Shift> shift = repository.findById(id);
        assertEquals(id,shift.get().getId());
    }

    @Test
    void deleteById() {
        //Given
        Shift shift1 = new Shift();
        //When
        repository.save(shift1);
        long id = shift1.getId();
        repository.deleteById(id);
        //Then
        List<Shift> shifts = repository.findAll();
        assertEquals(0,shifts.size());
    }

    @Test
    void findAllByEmployee() {
        //Given
        Employee employee = new Employee("David","Brown");
        employeeRepository.save(employee);
        LocalDateTime start = LocalDateTime.of(2021,2,19,15,30);
        LocalDateTime end = LocalDateTime.of(2021,2,19,16,30);
        Shift shift = new Shift(1L,start,end,employee);
        //When
        repository.save(shift);
        //Then
        assertEquals(1,repository.findAllByEmployee(employee).size());
    }
}
