package com.clinic.service;

import com.clinic.domain.Employee;
import com.clinic.domain.Treatment;
import com.clinic.exceptions.EmployeeNotFoundException;
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
class EmployeeDBServiceTest {

    @Autowired
    private EmployeeDBService service;
    @Autowired
    private TreatmentDBService treatmentDBService;

    private Employee employee1;
    private Employee employee2;
    private Employee employee3;
    private Treatment treatment1;
    private Treatment treatment2;

    void prepare() {
        this.employee1 = new Employee("Mike","Brown");
        this.employee2 = new Employee("John","Smith");
        this.employee3 = new Employee("David","Gambro");
        this.treatment1 = new Treatment("Botox",new BigDecimal(300),
                Duration.of(30, ChronoUnit.MINUTES));
        this.treatment2 = new Treatment("Cryolipolysis",new
                BigDecimal(250),Duration.of(1,ChronoUnit.HOURS));
    }

    @Test
    void saveAndGetAllEmployees() {
        //Given
        prepare();
        int actualSizeOfEmployee = service.getAllEmployees().size();
        //When
        service.saveEmployee(employee1);
        service.saveEmployee(employee2);
        //Then
        List<Employee> employees = service.getAllEmployees();
        assertEquals(actualSizeOfEmployee+2,employees.size());
    }

    @Test
    void getEmployee() {
        //Given
        prepare();
        //When
        service.saveEmployee(employee1);
        long id = employee1.getId();
        //Then
        Employee resultEmployee = service.getEmployee(id).get();
        assertEquals("Mike", resultEmployee.getFirstName());
    }

    @Test
    void deleteEmployee() {
        //Given
        prepare();
        int actualSizeOfEmployee = service.getAllEmployees().size();
        //When
        service.saveEmployee(employee1);
        long id = employee1.getId();
        //Then
        service.deleteEmployee(id);
        int resultSizeOfEmployee = service.getAllEmployees().size();
        assertEquals(actualSizeOfEmployee, resultSizeOfEmployee);
    }

    @Test
    void addTreatment() {
        //Given
        prepare();
        //When
        service.saveEmployee(employee1);
        treatmentDBService.saveTreatment(treatment1);
        treatmentDBService.saveTreatment(treatment2);
        Long employeeId = employee1.getId();
        Long treatment1Id = treatment1.getId();
        Long treatment2Id = treatment2.getId();
        try {
            service.addTreatment(employeeId,treatment1Id);
            service.addTreatment(employeeId,treatment2Id);
        } catch (EmployeeNotFoundException e) {
            fail();
        }
        //Then
        Employee resultEmployee = service.getEmployee(employeeId).get();
        assertEquals(resultEmployee.getTreatments().size(),2);
    }

    @Test
    void deleteTreatment() {
        //Given
        prepare();
        //When
        service.saveEmployee(employee1);
        treatmentDBService.saveTreatment(treatment1);
        treatmentDBService.saveTreatment(treatment2);
        Long employeeId = employee1.getId();
        Long treatment1Id = treatment1.getId();
        Long treatment2Id = treatment2.getId();
        try {
            service.addTreatment(employeeId,treatment1Id);
            service.addTreatment(employeeId,treatment2Id);
        } catch (EmployeeNotFoundException e) {
            fail();
        }
        //Then
        try {
            service.deleteTreatment(employeeId,treatment1Id);
        } catch (EmployeeNotFoundException e) {
            fail();
        }
        Employee resultEmployee = service.getEmployee(employeeId).get();
        assertEquals(resultEmployee.getTreatments().size(),1);
    }

    @Test
    void showTreatments() {
        //Given
        prepare();
        //When
        service.saveEmployee(employee1);
        treatmentDBService.saveTreatment(treatment1);
        treatmentDBService.saveTreatment(treatment2);
        Long employeeId = employee1.getId();
        Long treatment1Id = treatment1.getId();
        Long treatment2Id = treatment2.getId();
        try {
            service.addTreatment(employeeId,treatment1Id);
            service.addTreatment(employeeId,treatment2Id);
        } catch (EmployeeNotFoundException e) {
            fail();
        }
        //Then
        List<Treatment> treatmentList = service.showTreatments(employeeId);
        assertEquals(treatmentList.size(),2);
    }


    @Test
    void getAllEmployeesWithFilter() {
        //Given
        prepare();
        String filterString = "Bro";
        //Then
        service.saveEmployee(employee1);
        service.saveEmployee(employee2);
        service.saveEmployee(employee3);
        //When
        List<Employee> resultList = service.getAllEmployees(filterString);
        assertEquals(2,resultList.size());
    }
}
