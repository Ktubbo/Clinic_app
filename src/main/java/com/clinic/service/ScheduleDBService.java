package com.clinic.service;

import com.clinic.domain.Customer;
import com.clinic.domain.Schedule;
import com.clinic.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleDBService {

    @Autowired
    private final ScheduleRepository repository;

    public List<Schedule> getAllSchedules() { return repository.findAll(); }
    public Optional<Schedule> getSchedule(final Long scheduleId) { return repository.findById(scheduleId); }
    public void deleteSchedule(final Long scheduleId) { repository.deleteById(scheduleId); }
    public Schedule saveSchedule(Schedule schedule) { return repository.save(schedule); }

}
