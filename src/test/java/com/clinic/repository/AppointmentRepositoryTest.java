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
class AppointmentRepositoryTest {

    @Autowired
    AppointmentRepository repository;

    @Test
    void saveAndFindAll() {
    //Given
    Appointment appointment1 = new Appointment();
    Appointment appointment2 = new Appointment();
    //When
    repository.save(appointment1);
    repository.save(appointment2);
    //Then
    List<Appointment> appointments = repository.findAll();
    assertEquals(2,appointments.size());
    }

    @Test
    void findById() {
        //Given
        Appointment appointment1 = new Appointment();
        //When
        repository.save(appointment1);
        long id = appointment1.getId();
        //Then
        Optional<Appointment> appointment = repository.findById(id);
        assertEquals(id,appointment.get().getId());
    }

    @Test
    void deleteById() {
        //Given
        Appointment appointment1 = new Appointment();
        //When
        repository.save(appointment1);
        long id = appointment1.getId();
        repository.deleteById(id);
        //Then
        List<Appointment> appointments = repository.findAll();
        assertEquals(0,appointments.size());
    }
}