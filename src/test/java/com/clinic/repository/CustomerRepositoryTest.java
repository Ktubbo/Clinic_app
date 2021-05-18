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
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository repository;

    @Test
    void saveAndFindAll() {
        //Given
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        //When
        repository.save(customer1);
        repository.save(customer2);
        //Then
        List<Customer> customers = repository.findAll();
        assertEquals(2,customers.size());
    }

    @Test
    void findById() {
        //Given
        Customer customer1 = new Customer();
        //When
        repository.save(customer1);
        long id = customer1.getId();
        //Then
        Optional<Customer> customer = repository.findById(id);
        assertEquals(id,customer.get().getId());
    }

    @Test
    void deleteById() {
        //Given
        Customer customer1 = new Customer();
        //When
        repository.save(customer1);
        long id = customer1.getId();
        repository.deleteById(id);
        //Then
        List<Customer> customers = repository.findAll();
        assertEquals(0,customers.size());
    }
}
