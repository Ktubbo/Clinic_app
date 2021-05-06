package com.clinic.controller;

import com.clinic.domain.dto.TreatmentDto;
import com.clinic.exceptions.TreatmentNotFoundException;
import com.clinic.mapper.TreatmentMapper;
import com.clinic.service.TreatmentDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TreatmentController {

    private final TreatmentDBService dbService;
    private final TreatmentMapper mapper;

    @RequestMapping(method = RequestMethod.GET, value = "/treatments")
    public List<TreatmentDto> getTreatments() {
        return mapper.mapToTreatmentDtoList(dbService.getAllTreatments());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/treatments/{treatmentId}")
    public TreatmentDto getTreatment(@PathVariable Long treatmentId) throws TreatmentNotFoundException {
        return mapper.mapToTreatmentDto(dbService.getTreatment(treatmentId).orElseThrow(TreatmentNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/treatments/{treatmentId}")
    public void deleteTreatment(@PathVariable Long treatmentId) {
        dbService.deleteTreatment(treatmentId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/treatments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TreatmentDto createTreatment(@RequestBody TreatmentDto treatmentDto) {
        return mapper.mapToTreatmentDto(dbService.saveTreatment(mapper.mapToTreatment(treatmentDto)));
    }
}
