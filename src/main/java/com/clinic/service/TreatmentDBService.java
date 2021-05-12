package com.clinic.service;

import com.clinic.domain.Treatment;
import com.clinic.repository.TreatmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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

    public synchronized List<Treatment> getAllTreatments(String stringFilter) {
        ArrayList<Treatment> arrayList = new ArrayList<>();
        List<Treatment> treatments = repository.findAll();
        for (Treatment treatment : treatments) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || treatment.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(treatment.clone());
                }
            } catch (CloneNotSupportedException ex) {
                //Logger.getLogger(TreatmentDBService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, (o1, o2) -> (int) (o2.getId() - o1.getId()));
        return arrayList;
    }
}