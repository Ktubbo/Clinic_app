package com.clinic.mapper;

import com.clinic.domain.Customer;
import com.clinic.domain.dto.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerMapper {
    
    public Customer mapToCustomer(CustomerDto customerDto) {
        return new Customer (customerDto.getId(),
                customerDto.getFirstName(),
                customerDto.getLastName(),
                customerDto.getPesel(),
                customerDto.getEmail());
    }

    public CustomerDto mapToCustomerDto(Customer customer) {
        return new CustomerDto(customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPesel(),
                customer.getEmail());
    }

    public List<CustomerDto> mapToCustomerDtoList(final List<Customer> customerList) {
        return customerList.stream().map(this::mapToCustomerDto).collect(Collectors.toList());
    }

    public List<Customer> mapToCustomerList(final List<CustomerDto> customerDtoList) {
        return customerDtoList.stream().map(this::mapToCustomer).collect(Collectors.toList());
    }

}
