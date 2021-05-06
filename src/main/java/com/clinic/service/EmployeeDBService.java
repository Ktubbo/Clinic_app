package com.clinic.service;

import com.clinic.domain.Employee;
import com.clinic.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeDBService {

    @Autowired
    private final EmployeeRepository repository;

    public List<Employee> getAllEmployees() { return repository.findAll(); }
    public Optional<Employee> getEmployee(final Long employeeId) { return repository.findById(employeeId); }
    public void deleteEmployee(final Long employeeId) { repository.deleteById(employeeId); }
    public Employee saveEmployee(Employee employee) { return repository.save(employee); }
}
