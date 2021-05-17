package com.clinic.mapper;

import com.clinic.domain.Customer;
import com.clinic.domain.dto.CustomerDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
class CustomerMapperTest {

    @Autowired
    CustomerMapper customerMapper;

    private Customer customer;
    private CustomerDto customerDto;

    void prepare() {
        this.customer = new Customer("John","Smith","72120112124","test@mail.com");
        this.customerDto = new CustomerDto("Mike","Brown","75010412345","somemail@test.com");
    }

    @Test
    void mapToCustomer() {
        //Given
        prepare();
        //When
        Customer resultCustomer = customerMapper.mapToCustomer(customerDto);
        //Then
        assertEquals("Mike",resultCustomer.getFirstName());
        assertEquals("Brown",resultCustomer.getLastName());
        assertEquals("75010412345",resultCustomer.getPesel());
        assertEquals("somemail@test.com",resultCustomer.getEmail());
    }

    @Test
    void mapToCustomerDto() {
        //Given
        prepare();
        //When
        CustomerDto resultCustomerDto = customerMapper.mapToCustomerDto(customer);
        //Then
        assertEquals("John",resultCustomerDto.getFirstName());
        assertEquals("Smith",resultCustomerDto.getLastName());
        assertEquals("72120112124",resultCustomerDto.getPesel());
        assertEquals("test@mail.com",resultCustomerDto.getEmail());
    }

    @Test
    void mapToCustomerDtoList() {
        //Given
        prepare();
        //When
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        List<CustomerDto> resultList = customerMapper.mapToCustomerDtoList(customers);
        //Then
        assertEquals(1,resultList.size());
    }

    @Test
    void mapToCustomerList() {
        //Given
        prepare();
        //When
        List<CustomerDto> customersDto = new ArrayList<>();
        customersDto.add(customerDto);
        List<Customer> resultList = customerMapper.mapToCustomerList(customersDto);
        //Then
        assertEquals(1,resultList.size());
    }
}