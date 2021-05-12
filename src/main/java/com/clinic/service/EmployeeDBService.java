package com.clinic.service;

import com.clinic.domain.Employee;
import com.clinic.domain.Treatment;
import com.clinic.exceptions.EmployeeNotFoundException;
import com.clinic.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
                //Logger.getLogger(EmployeeDBService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, (o1, o2) -> (int) (o2.getId() - o1.getId()));
        return arrayList;
    }

    public Employee addTreatment(Employee employee, Treatment treatment) throws EmployeeNotFoundException {
        Optional<Employee> employeeOptional = repository.findById(employee.getId());
        employeeOptional.ifPresent(e -> {e.getTreatment().add(treatment);});
        return repository.save(employeeOptional.orElseThrow(EmployeeNotFoundException::new));
    }

    public void deleteTreatment(Employee employee, Treatment treatment) throws EmployeeNotFoundException {
        Optional<Employee> employeeOptional = repository.findById(employee.getId());
        employeeOptional.ifPresent(e -> {e.getTreatment().remove(treatment);});
        repository.save(employeeOptional.orElseThrow(EmployeeNotFoundException::new));
    }
}
