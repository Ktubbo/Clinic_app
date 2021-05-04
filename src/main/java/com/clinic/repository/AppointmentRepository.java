package com.clinic.repository;

import com.clinic.domain.Appointment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends CrudRepository<Appointment,Long> {

    List<Appointment> findAll();
    Optional<Appointment> findById(Long id);
    Appointment save(Appointment appointment);
    void deleteById(Long id);

}
