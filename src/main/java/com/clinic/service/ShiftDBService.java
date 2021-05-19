package com.clinic.service;

import com.clinic.domain.Shift;
import com.clinic.domain.Shift;
import com.clinic.repository.ShiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ShiftDBService {

    @Autowired
    private final ShiftRepository repository;

    public List<Shift> getAllShifts() { return repository.findAll(); }

    public Optional<Shift> getShift(final Long shiftId) { return repository.findById(shiftId); }

    public void deleteShift(final Long shiftId) { repository.deleteById(shiftId); }

    public Shift saveShift(Shift shift) { return repository.save(shift); }

    public synchronized List<Shift> getAllShifts(String stringFilter) {
        ArrayList<Shift> arrayList = new ArrayList<>();
        List<Shift> shifts = repository.findAll();
        for (Shift shift : shifts) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || shift.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(shift.clone());
                }
            } catch (CloneNotSupportedException ex) {
                ex.printStackTrace();
            }
        }
        arrayList.sort((o1, o2) -> (int) (o2.getId() - o1.getId()));
        return arrayList;
    }
}