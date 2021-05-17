package com.clinic.domain;

import com.clinic.repository.TreatmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
class TreatmentEntityTest {

    @Autowired
    TreatmentRepository repository;

    private Treatment treatment;
    private Employee employee1;
    private Employee employee2;
    private long id;

    public void prepare() {

        this.treatment = new Treatment("Botox", BigDecimal.valueOf(300), Duration.of(1, ChronoUnit.HOURS));
        this.employee1 = new Employee("David","Brown");
        this.employee2 = new Employee("John","Smith");
        repository.save(treatment);
        this.id = treatment.getId();
    }

    @Test
    void getName() {
        //Given
        prepare();
        //When
        //Then
        assertEquals("Botox",repository.findById(id).get().getName());
    }

    @Test
    void getPrice() {
        //Given
        prepare();
        //When
        //Then
        assertEquals(BigDecimal.valueOf(300),repository.findById(id).get().getPrice());
    }

    @Test
    void getDuration() {
        //Given
        prepare();
        //When
        //Then
        assertEquals(Duration.of(1,ChronoUnit.HOURS),repository.findById(id).get().getDuration());
    }

    @Test
    void setName() {
        //Given
        prepare();
        //When
        treatment.setName("Cryolipolysis");
        repository.save(treatment);
        //Then
        assertEquals("Cryolipolysis",repository.findById(id).get().getName());
    }

    @Test
    void setPrice() {
        //Given
        prepare();
        //When
        treatment.setPrice(BigDecimal.valueOf(400));
        repository.save(treatment);
        //Then
        assertEquals(BigDecimal.valueOf(400),repository.findById(id).get().getPrice());
    }

    @Test
    void setDuration() {
        //Given
        prepare();
        //When
        treatment.setPrice(BigDecimal.valueOf(400));
        repository.save(treatment);
        //Then
        assertEquals(BigDecimal.valueOf(400),repository.findById(id).get().getPrice());
    }

    @Test
    void getEmployees() {
        //Given
        prepare();
        //When
        treatment.getEmployees().add(employee1);
        treatment.getEmployees().add(employee2);
        employee1.getTreatments().add(treatment);
        employee2.getTreatments().add(treatment);
        repository.save(treatment);
        //Then
        assertEquals(2,repository.findById(id).get().getEmployees().size());
    }

    @Test
    void setEmployees() {
        //Given
        prepare();
        //When
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee1);
        employeeList.add(employee2);
        treatment.setEmployees(employeeList);
        employee1.getTreatments().add(treatment);
        employee2.getTreatments().add(treatment);
        repository.save(treatment);
        //Then
        assertEquals(2,repository.findById(id).get().getEmployees().size());
    }
}
