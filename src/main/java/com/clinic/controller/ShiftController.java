package com.clinic.controller;

import com.clinic.domain.Shift;
import com.clinic.domain.dto.ShiftDto;
import com.clinic.domain.dto.ShiftDto;
import com.clinic.exceptions.ShiftNotFoundException;
import com.clinic.mapper.ShiftMapper;
import com.clinic.service.ShiftDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ShiftController {

    private final ShiftDBService dbService;
    private final ShiftMapper mapper;

    @RequestMapping(method = RequestMethod.GET, value = "/shifts")
    public List<ShiftDto> getShifts() {
        return mapper.mapToShiftDtoList(dbService.getAllShifts());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/shifts/{shiftId}")
    public ShiftDto getShift(@PathVariable Long shiftId) throws ShiftNotFoundException {
        return mapper.mapToShiftDto(dbService.getShift(shiftId).orElseThrow(ShiftNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/shifts/{shiftId}")
    public void deleteShift(@PathVariable Long shiftId) {
        dbService.deleteShift(shiftId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/shifts", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ShiftDto createShift(@RequestBody ShiftDto shiftDto) {
        return mapper.mapToShiftDto(dbService.saveShift(mapper.mapToShift(shiftDto)));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/shifts", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ShiftDto updateShift(@RequestBody ShiftDto shiftDto) {
        Shift shift = mapper.mapToShift(shiftDto);
        Shift savedShift = dbService.saveShift(shift);
        return mapper.mapToShiftDto(savedShift);
    }
}
