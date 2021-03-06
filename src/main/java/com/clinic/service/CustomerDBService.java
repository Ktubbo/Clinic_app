package com.clinic.service;

import com.clinic.domain.Customer;
import com.clinic.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomerDBService {

    @Autowired
    private final CustomerRepository repository;

    public List<Customer> getAllCustomers() { return repository.findAll(); }

    public Optional<Customer> getCustomer(final Long customerId) { return repository.findById(customerId); }

    public void deleteCustomer(final Long customerId) { repository.deleteById(customerId); }

    public Customer saveCustomer(Customer customer) { return repository.save(customer); }

    public synchronized List<Customer> getAllCustomers(String stringFilter) {
        ArrayList<Customer> arrayList = new ArrayList<>();
        List<Customer> contacts = repository.findAll();
        for (Customer contact : contacts) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                ex.printStackTrace();
            }
        }
        Collections.sort(arrayList, (o1, o2) -> (int) (o2.getId() - o1.getId()));
        return arrayList;
    }
}
