package com.clinic.repository;

import com.clinic.domain.Appointment;
import com.clinic.domain.Employee;
import com.clinic.domain.Schedule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends CrudRepository<Schedule,Long> {

    List<Schedule> findAll();
    Optional<Schedule> findById(Long id);
    Schedule save(Schedule schedule);
    void deleteById(Long id);
    List<Schedule> findAllByEmployee(Employee employee);
    void deleteByAppointment(Appointment appointment);
}