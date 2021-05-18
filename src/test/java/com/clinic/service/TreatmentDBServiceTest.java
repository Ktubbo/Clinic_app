package com.clinic.service;

import com.clinic.domain.Treatment;
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
class TreatmentDBServiceTest {

    @Autowired
    private TreatmentDBService service;

    private Treatment treatment1;
    private Treatment treatment2;
    private Treatment treatment3;

    void prepare() {
        this.treatment1 = new Treatment("Botox",new BigDecimal(300),
                Duration.of(30, ChronoUnit.MINUTES));
        this.treatment2 = new Treatment("Cryolipolysis",new
                BigDecimal(250),Duration.of(1,ChronoUnit.HOURS));
        this.treatment3 = new Treatment("Depilation",new BigDecimal(250),
                Duration.of(2, ChronoUnit.HOURS));
    }

    @Test
    void saveAndGetAllTreatments() {
        //Given
        prepare();
        int actualSizeOfTreatment = service.getAllTreatments().size();
        //When
        service.saveTreatment(treatment1);
        service.saveTreatment(treatment2);
        //Then
        List<Treatment> treatments = service.getAllTreatments();
        assertEquals(actualSizeOfTreatment+2,treatments.size());
    }

    @Test
    void getTreatment() {
        //Given
        prepare();
        //When
        service.saveTreatment(treatment1);
        long id = treatment1.getId();
        //Then
        Treatment resultTreatment = service.getTreatment(id).get();
        assertEquals("Botox", resultTreatment.getName());
    }

    @Test
    void deleteTreatment() {
        //Given
        prepare();
        int actualSizeOfTreatment = service.getAllTreatments().size();
        //When
        service.saveTreatment(treatment1);
        long id = treatment1.getId();
        //Then
        service.deleteTreatment(id);
        int resultSizeOfTreatment = service.getAllTreatments().size();
        assertEquals(actualSizeOfTreatment, resultSizeOfTreatment);
    }

    @Test
    void getAllTreatmentsWithFilter() {
        //Given
        prepare();
        String filterString = "Bot";
        //Then
        service.saveTreatment(treatment1);
        service.saveTreatment(treatment2);
        service.saveTreatment(treatment3);
        //When
        List<Treatment> resultList = service.getAllTreatments(filterString);
        assertEquals(1,resultList.size());
    }
}



