package com.clinic.repository;

import com.clinic.domain.TimeTable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TimeTableRepository  extends CrudRepository<TimeTable,Long> {

    List<TimeTable> findAll();
    Optional<TimeTable> findById(Long id);
    TimeTable save(TimeTable timeTable);
    void deleteById(Long id);

}