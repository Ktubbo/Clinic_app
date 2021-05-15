package com.clinic.service;

import com.clinic.domain.Shift;
import com.clinic.repository.ShiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShiftDBService {

    @Autowired
    private final ShiftRepository repository;

    public List<Shift> getAllShifts() { return repository.findAll(); }
    public Optional<Shift> getShift(final Long shiftId) { return repository.findById(shiftId); }
    public void deleteShift(final Long shiftId) { repository.deleteById(shiftId); }
    public Shift saveShift(Shift shift) { return repository.save(shift); }
}