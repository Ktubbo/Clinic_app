package com.clinic.repository;

import com.clinic.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
class TreatmentRepositoryTest {

    @Autowired
    TreatmentRepository repository;

    @Test
    void saveAndFindAll() {
        //Given
        Treatment treatment1 = new Treatment();
        Treatment treatment2 = new Treatment();
        //When
        repository.save(treatment1);
        repository.save(treatment2);
        //Then
        List<Treatment> treatments = repository.findAll();
        assertEquals(2,treatments.size());
    }

    @Test
    void findById() {
        //Given
        Treatment treatment1 = new Treatment();
        //When
        repository.save(treatment1);
        long id = treatment1.getId();
        //Then
        Optional<Treatment> treatment = repository.findById(id);
        assertEquals(id,treatment.get().getId());
    }

    @Test
    void deleteById() {
        //Given
        Treatment treatment1 = new Treatment();
        //When
        repository.save(treatment1);
        long id = treatment1.getId();
        repository.deleteById(id);
        //Then
        List<Treatment> treatments = repository.findAll();
        assertEquals(0,treatments.size());
    }
}
