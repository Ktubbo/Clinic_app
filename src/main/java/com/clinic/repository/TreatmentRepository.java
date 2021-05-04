package com.clinic.repository;

import com.clinic.domain.Treatment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TreatmentRepository extends CrudRepository<Treatment,Long> {

    List<Treatment> findAll();
    Optional<Treatment> findById(Long id);
    Treatment save(Treatment treatment);
    void deleteById(Long id);

}