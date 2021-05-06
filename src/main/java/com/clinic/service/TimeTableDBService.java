package com.clinic.service;

import com.clinic.domain.TimeTable;
import com.clinic.repository.TimeTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimeTableDBService {

    @Autowired
    private final TimeTableRepository repository;

    public List<TimeTable> getAllTimeTables() { return repository.findAll(); }
    public Optional<TimeTable> getTimeTable(final Long timeTableId) { return repository.findById(timeTableId); }
    public void deleteTimeTable(final Long timeTableId) { repository.deleteById(timeTableId); }
    public TimeTable saveTimeTable(TimeTable timeTable) { return repository.save(timeTable); }
}
