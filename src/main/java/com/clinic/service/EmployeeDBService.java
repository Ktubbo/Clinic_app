package com.clinic.service;

import com.clinic.domain.Employee;
import com.clinic.domain.Shift;
import com.clinic.domain.Treatment;
import com.clinic.exceptions.EmployeeNotFoundException;
import com.clinic.repository.EmployeeRepository;
import com.clinic.repository.TreatmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeDBService {

    @Autowired
    private final EmployeeRepository repository;

    @Autowired
    private final TreatmentRepository treatmentRepository;

    public List<Employee> getAllEmployees() { return repository.findAll(); }
    
    public Optional<Employee> getEmployee(final Long employeeId) { return repository.findById(employeeId); }
    
    public void deleteEmployee(final Long employeeId) { repository.deleteById(employeeId); }
    
    public Employee saveEmployee(Employee employee) { return repository.save(employee); }

    public synchronized List<Employee> getAllEmployees(String stringFilter) {
        ArrayList<Employee> arrayList = new ArrayList<>();
        List<Employee> employees = repository.findAll();
        for (Employee employee : employees) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || employee.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(employee.clone());
                }
            } catch (CloneNotSupportedException ex) {
                ex.printStackTrace();
            }
        }
        arrayList.sort((o1, o2) -> (int) (o2.getId() - o1.getId()));
        return arrayList;
    }

    public void addTreatment(Long employeeId, Long treatmentId) throws EmployeeNotFoundException {
        Optional<Employee> employeeOptional = repository.findById(employeeId);
        Optional<Treatment> treatmentOptional = treatmentRepository.findById(treatmentId);
        if(employeeOptional.isPresent() && treatmentOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            Treatment treatment = treatmentOptional.get();
            treatment.getEmployees().add(employee);
            employee.getTreatments().add(treatment);
            repository.save(employee);
        }
    }

    public void deleteTreatment(Long employeeId, Long treatmentId) throws EmployeeNotFoundException {
        Optional<Employee> employeeOptional = repository.findById(employeeId);
        Optional<Treatment> treatmentOptional = treatmentRepository.findById(treatmentId);
        if(employeeOptional.isPresent() && treatmentOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            Treatment treatment = treatmentOptional.get();
            treatment.getEmployees().remove(employee);
            employee.getTreatments().remove(treatment);
            repository.save(employee);
        }
    }

    public List<Treatment> showTreatments(Long employeeID) {
        return repository.findById(employeeID).get().getTreatments();
    }
}
