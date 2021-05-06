package com.clinic.service;

import com.clinic.domain.Customer;
import com.clinic.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerDBService {

    @Autowired
    private final CustomerRepository repository;

    public List<Customer> getAllCustomers() { return repository.findAll(); }
    public Optional<Customer> getCustomer(final Long customerId) { return repository.findById(customerId); }
    public void deleteCustomer(final Long customerId) { repository.deleteById(customerId); }
    public Customer saveCustomer(Customer customer) { return repository.save(customer); }
}
