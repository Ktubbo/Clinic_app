package com.clinic.repository;

import com.clinic.domain.Employee;
import com.clinic.domain.Shift;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ShiftRepository extends CrudRepository<Shift,Long> {

    List<Shift> findAll();
    Optional<Shift> findById(Long id);
    Shift save(Shift appointment);
    void deleteById(Long id);
    List<Shift> findAllByEmployee(Employee employee);
}
