package com.clinic.controller;

import com.clinic.domain.dto.TimeTableDto;
import com.clinic.exceptions.TimeTableNotFoundException;
import com.clinic.mapper.TimeTableMapper;
import com.clinic.service.TimeTableDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TimeTableController {

    private final TimeTableDBService dbService;
    private final TimeTableMapper mapper;

    @RequestMapping(method = RequestMethod.GET, value = "/timeTables")
    public List<TimeTableDto> getTimeTables() {
        return mapper.mapToTimeTableDtoList(dbService.getAllTimeTables());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/timeTables/{timeTableId}")
    public TimeTableDto getTimeTable(@PathVariable Long timeTableId) throws TimeTableNotFoundException {
        return mapper.mapToTimeTableDto(dbService.getTimeTable(timeTableId).orElseThrow(TimeTableNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/timeTables/{timeTableId}")
    public void deleteTimeTable(@PathVariable Long timeTableId) {
        dbService.deleteTimeTable(timeTableId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/timeTables", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TimeTableDto createTimeTable(@RequestBody TimeTableDto timeTableDto) {
        return mapper.mapToTimeTableDto(dbService.saveTimeTable(mapper.mapToTimeTable(timeTableDto)));
    }
}
