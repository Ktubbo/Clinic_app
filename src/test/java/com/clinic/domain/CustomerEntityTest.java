package com.clinic.domain;

import com.clinic.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import static org.junit.Assert.assertEquals;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
class CustomerEntityTest {

    @Autowired
    CustomerRepository repository;

    private Customer customer;
    private long id;

    public void prepare() {
        this.customer = new Customer("John","Smith","72120112124","test@mail.com");

        repository.save(customer);
        this.id = customer.getId();
    }

    @Test
    void getFirstName() {
        //Given
        prepare();
        //When
        //Then
        assertEquals("John",repository.findById(id).get().getFirstName());
    }

    @Test
    void getLastName() {
        //Given
        prepare();
        //When
        //Then
        assertEquals("Smith",repository.findById(id).get().getLastName());
    }

    @Test
    void getPesel() {
        //Given
        prepare();
        //When
        //Then
        assertEquals("72120112124",repository.findById(id).get().getPesel());
    }

    @Test
    void getEmail() {
        //Given
        prepare();
        //When
        //Then
        assertEquals("test@mail.com",repository.findById(id).get().getEmail());
    }

    @Test
    void setFirstName() {
        //Given
        prepare();
        //When
        customer.setFirstName("Mike");
        repository.save(customer);
        //Then
        assertEquals("Mike",repository.findById(id).get().getFirstName());
    }

    @Test
    void setLastName() {
        //Given
        prepare();
        //When
        customer.setLastName("Kovalsky");
        repository.save(customer);
        //Then
        assertEquals("Kovalsky",repository.findById(id).get().getLastName());
    }

    @Test
    void setPesel() {
        //Given
        prepare();
        customer.setPesel("75110324578");
        repository.save(customer);
        //When
        //Then
        assertEquals("75110324578",repository.findById(id).get().getPesel());
    }

    @Test
    void setEmail() {
        //Given
        prepare();
        //When
        customer.setEmail("newemail@mail.com");
        repository.save(customer);
        //Then
        assertEquals("newemail@mail.com",repository.findById(id).get().getEmail());
    }

}
