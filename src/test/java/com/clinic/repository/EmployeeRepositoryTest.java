package com.clinic.repository;

import com.clinic.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository repository;

    @Test
    void saveAndFindAll() {
        //Given
        int actualSize = repository.findAll().size();
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        //When
        repository.save(employee1);
        repository.save(employee2);
        //Then
        List<Employee> employees = repository.findAll();
        assertEquals(2+actualSize,employees.size());
    }

    @Test
    void findById() {
        //Given
        Employee employee1 = new Employee();
        //When
        repository.save(employee1);
        long id = employee1.getId();
        //Then
        Optional<Employee> employee = repository.findById(id);
        assertEquals(id,employee.get().getId());
    }

    @Test
    void deleteById() {
        //Given
        int actualSize = repository.findAll().size();
        Employee employee1 = new Employee();
        //When
        repository.save(employee1);
        long id = employee1.getId();
        repository.deleteById(id);
        //Then
        List<Employee> employees = repository.findAll();
        assertEquals(actualSize,employees.size());
    }
}
