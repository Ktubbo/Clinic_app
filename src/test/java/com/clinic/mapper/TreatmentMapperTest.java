package com.clinic.mapper;

import com.clinic.domain.Employee;
import com.clinic.domain.Treatment;
import com.clinic.domain.dto.DurationDto;
import com.clinic.domain.dto.TreatmentDto;
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
class TreatmentMapperTest {

    @Autowired
    TreatmentMapper mapper;

    private Treatment treatment;
    private TreatmentDto treatmentDto;
    private List<Employee> employeeList;

    void prepare() {

        employeeList = List.of(new Employee("Mike", "Brown"),new Employee("John","Smith"));

        this.treatment = new Treatment(1L,
                "Botox",
                BigDecimal.valueOf(300),
                Duration.of(1, ChronoUnit.HOURS),
                employeeList);

        this.treatmentDto = new TreatmentDto(1L,
                "Cryolipolysis",
                BigDecimal.valueOf(200),
                new DurationDto("1","30"),
                employeeList);
    }

    @Test
    void mapToTreatment() {
        //Given
        prepare();
        //When
        Treatment resultTreatment = mapper.mapToTreatment(treatmentDto);
        //Then
        assertEquals(1L,resultTreatment.getId());
        assertEquals("Cryolipolysis",resultTreatment.getName());
        assertEquals(BigDecimal.valueOf(200),resultTreatment.getPrice());
        assertEquals(Duration.of(1,ChronoUnit.HOURS).plusMinutes(30),resultTreatment.getDuration());
        assertEquals(2,resultTreatment.getEmployees().size());
    }

    @Test
    void mapToTreatmentDto() {
        //Given
        prepare();
        //When
        TreatmentDto resultTreatment = mapper.mapToTreatmentDto(treatment);
        //Then
        assertEquals(1L,resultTreatment.getId());
        assertEquals("Botox",resultTreatment.getName());
        assertEquals(BigDecimal.valueOf(300),resultTreatment.getPrice());
        assertEquals(new DurationDto("01","00").toString(),resultTreatment.getDuration().toString());
        assertEquals(2,resultTreatment.getEmployees().size());
    }

    @Test
    void mapToTreatmentDtoList() {
        //Given
        prepare();
        //When
        List<TreatmentDto> resultList = mapper.mapToTreatmentDtoList(List.of(treatment));
        //Then
        assertEquals(1,resultList.size());
    }

    @Test
    void mapToTreatmentList() {
        //Given
        prepare();
        //When
        List<Treatment> resultList = mapper.mapToTreatmentList(List.of(treatmentDto));
        //Then
        assertEquals(1,resultList.size());
    }
}