package com.clinic.service;

import com.clinic.domain.Treatment;
import com.clinic.repository.TreatmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TreatmentDBService {

    @Autowired
    private final TreatmentRepository repository;

    public List<Treatment> getAllTreatments() { return repository.findAll(); }
    public Optional<Treatment> getTreatment(final Long treatmentId) { return repository.findById(treatmentId); }
    public void deleteTreatment(final Long treatmentId) { repository.deleteById(treatmentId); }
    public Treatment saveTreatment(Treatment treatment) { return repository.save(treatment); }
}