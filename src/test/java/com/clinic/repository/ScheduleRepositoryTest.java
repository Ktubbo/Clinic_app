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
class ScheduleRepositoryTest {

    @Autowired
    ScheduleRepository repository;

    @Test
    void saveAndFindAll() {
        //Given
        Schedule schedule1 = new Schedule();
        Schedule schedule2 = new Schedule();
        //When
        repository.save(schedule1);
        repository.save(schedule2);
        //Then
        List<Schedule> schedules = repository.findAll();
        assertEquals(2,schedules.size());
    }

    @Test
    void findById() {
        //Given
        Schedule schedule1 = new Schedule();
        //When
        repository.save(schedule1);
        long id = schedule1.getId();
        //Then
        Optional<Schedule> schedule = repository.findById(id);
        assertEquals(id,schedule.get().getId());
    }

    @Test
    void deleteById() {
        //Given
        Schedule schedule1 = new Schedule();
        //When
        repository.save(schedule1);
        long id = schedule1.getId();
        repository.deleteById(id);
        //Then
        List<Schedule> schedules = repository.findAll();
        assertEquals(0,schedules.size());
    }

}
