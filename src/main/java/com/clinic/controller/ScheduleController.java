package com.clinic.controller;

import com.clinic.domain.dto.ScheduleDto;
import com.clinic.exceptions.ScheduleNotFoundException;
import com.clinic.mapper.ScheduleMapper;
import com.clinic.service.ScheduleDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ScheduleController {

    private final ScheduleDBService dbService;
    private final ScheduleMapper mapper;

    @RequestMapping(method = RequestMethod.GET, value = "/timeTables")
    public List<ScheduleDto> getSchedules() {
        return mapper.mapToScheduleDtoList(dbService.getAllSchedules());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/timeTables/{timeTableId}")
    public ScheduleDto getSchedule(@PathVariable Long timeTableId) throws ScheduleNotFoundException {
        return mapper.mapToScheduleDto(dbService.getSchedule(timeTableId).orElseThrow(ScheduleNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/timeTables/{timeTableId}")
    public void deleteSchedule(@PathVariable Long timeTableId) {
        dbService.deleteSchedule(timeTableId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/timeTables", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ScheduleDto createSchedule(@RequestBody ScheduleDto scheduleDto) {
        return mapper.mapToScheduleDto(dbService.saveSchedule(mapper.mapToSchedule(scheduleDto)));
    }
}
