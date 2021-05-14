package com.clinic.service;

import com.clinic.domain.Employee;
import com.clinic.domain.Treatment;
import com.clinic.exceptions.EmployeeNotFoundException;
import com.clinic.repository.EmployeeRepository;
import com.clinic.repository.TreatmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
                //Logger.getLogger(EmployeeDBService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, (o1, o2) -> (int) (o2.getId() - o1.getId()));
        return arrayList;
    }

    public Employee addTreatment(Long employeeId, Long treatmentId) throws EmployeeNotFoundException {
        Optional<Employee> employeeOptional = repository.findById(employeeId);
        Optional<Treatment> treatmentOptional = treatmentRepository.findById(treatmentId);
        if(employeeOptional.isPresent() && treatmentOptional.isPresent()) {
/*

            System.out.println("Employee{" +
                    "id=" + employeeOptional.get().getId() +
                    ", firstName='" + employeeOptional.get().getFirstName() + '\'' +
                    ", lastName='" + employeeOptional.get().getLastName() + '\'' +
                    ", schedule=" + employeeOptional.get().getSchedule() +
                    ", appointment=" + employeeOptional.get().getAppointment() +
                    ", treatments=" + employeeOptional.get().getTreatments() +
                    "}\n" +
                    "Treatment{" +
                    "id=" + treatmentOptional.get().getId() +
                    ", name='" + treatmentOptional.get().getName() + '\'' +
                    ", price=" + treatmentOptional.get().getPrice() +
                    ", duration=" + treatmentOptional.get().getDuration() +
                    ", employees=" + treatmentOptional.get().getEmployees() +
                    '}');
*/

            //System.out.println(employeeOptional.get().getTreatments());
            employeeOptional.get().getTreatments().add(treatmentOptional.get());
            //System.out.println(employeeOptional.get().getTreatments());
            Employee employee = employeeOptional.get();
            repository.save(employee);
            System.out.println("Size after saving: " + repository.findById(employeeId).get().getTreatments().size());
        }
        Employee returnEmployee = repository.findById(employeeId).get();
        System.out.println(returnEmployee.getTreatments());
        return returnEmployee;
    }

    public void deleteTreatment(Employee employee, Treatment treatment) throws EmployeeNotFoundException {
        Optional<Employee> employeeOptional = repository.findById(employee.getId());
        employeeOptional.ifPresent(e -> {e.getTreatments().remove(treatment);});
        repository.save(employeeOptional.orElseThrow(EmployeeNotFoundException::new));
    }

    public List<Treatment> showTreatments(Employee employee) {
        return repository.findById(employee.getId()).get().getTreatments();
    }
}
