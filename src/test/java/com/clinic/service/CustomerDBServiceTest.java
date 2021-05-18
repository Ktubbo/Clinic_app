package com.clinic.service;

import com.clinic.domain.Customer;
import com.clinic.domain.Treatment;
import com.clinic.exceptions.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
class CustomerDBServiceTest {

    @Autowired
    private CustomerDBService service;

    private Customer customer1;
    private Customer customer2;
    private Customer customer3;

    void prepare() {
        this.customer1 = new Customer("Mike","Brown",
                "72120112124","test@mail.com");
        this.customer2 = new Customer("John","Smith",
                "75010412345","somemail@test.com");
        this.customer3 = new Customer("David","Gambro","12345678910");

    }

    @Test
    void saveAndGetAllCustomers() {
        //Given
        prepare();
        int actualSizeOfCustomer = service.getAllCustomers().size();
        //When
        service.saveCustomer(customer1);
        service.saveCustomer(customer2);
        //Then
        List<Customer> customers = service.getAllCustomers();
        assertEquals(actualSizeOfCustomer+2,customers.size());
    }

    @Test
    void getCustomer() {
        //Given
        prepare();
        //When
        service.saveCustomer(customer1);
        long id = customer1.getId();
        //Then
        Customer resultCustomer = service.getCustomer(id).get();
        assertEquals("Mike", resultCustomer.getFirstName());
    }

    @Test
    void deleteCustomer() {
        //Given
        prepare();
        int actualSizeOfCustomer = service.getAllCustomers().size();
        //When
        service.saveCustomer(customer1);
        long id = customer1.getId();
        //Then
        service.deleteCustomer(id);
        int resultSizeOfCustomer = service.getAllCustomers().size();
        assertEquals(actualSizeOfCustomer, resultSizeOfCustomer);
    }

    @Test
    void getAllCustomersWithFilter() {
        //Given
        prepare();
        String filterString = "Bro";
        //Then
        service.saveCustomer(customer1);
        service.saveCustomer(customer2);
        service.saveCustomer(customer3);
        //When
        List<Customer> resultList = service.getAllCustomers(filterString);
        assertEquals(2,resultList.size());
    }
}
