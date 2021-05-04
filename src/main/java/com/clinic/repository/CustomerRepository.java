package com.clinic.repository;

import com.clinic.domain.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer,Long> {

    List<Customer> findAll();
    Optional<Customer> findById(Long id);
    Customer save(Customer customer);
    void deleteById(Long id);

}
