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
    
    @Test
    void saveAndGetAllEmployees() {
        //Given
        int actualSizeOfEmployee = service.getAllEmployees().size();
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        //When
        service.saveEmployee(employee1);
        service.saveEmployee(employee2);
        //Then
        List<Employee> employees = service.getAllEmployees();
        assertEquals(actualSizeOfEmployee+2,employees.size());
    }

    /*@Test
    void getEmployee() {
    }

    @Test
    void deleteEmployee() {
    }

    @Test
    void testGetAllEmployees() {
    }*/

    @Test
    void addTreatment() {
        //Given
        Employee employee = new Employee();
        Treatment treatment1 = new Treatment();
        Treatment treatment2 = new Treatment();
        //When
        service.saveEmployee(employee);
        treatmentDBService.saveTreatment(treatment1);
        treatmentDBService.saveTreatment(treatment2);
        Long employeeId = employee.getId();
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

    /*@Test
    void deleteTreatment() {
    }*/

    @Test
    void showTreatments() {
        //Given
        Employee employee = new Employee();
        Treatment treatment1 = new Treatment("Botox",new BigDecimal(300), Duration.of(30, ChronoUnit.MINUTES));
        Treatment treatment2 = new Treatment("Cryolipolysis",new BigDecimal(250),Duration.of(1,ChronoUnit.HOURS));
        //When
        service.saveEmployee(employee);
        treatmentDBService.saveTreatment(treatment1);
        treatmentDBService.saveTreatment(treatment2);
        Long employeeId = employee.getId();
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
}