package com.clinic.mapper;


import com.clinic.domain.TimeTable;
import com.clinic.domain.dto.TimeTableDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeTableMapper {
    public TimeTable mapToTimeTable(TimeTableDto timeTableDto) {
        return new TimeTable (timeTableDto.getId(),
                timeTableDto.getStart(),
                timeTableDto.getEnd(),
                timeTableDto.getEmployee(),
                timeTableDto.getAppointment());
    }

    public TimeTableDto mapToTimeTableDto(TimeTable timeTable) {
        return new TimeTableDto(timeTable.getId(),
                timeTable.getStart(),
                timeTable.getEnd(),
                timeTable.getEmployee(),
                timeTable.getAppointment());
    }

    public List<TimeTableDto> mapToTimeTableDtoList(final List<TimeTable> timeTableList) {
        return timeTableList.stream().map(this::mapToTimeTableDto).collect(Collectors.toList());
    }

    public List<TimeTable> mapToTimeTableList(final List<TimeTableDto> timeTableDtoList) {
        return timeTableDtoList.stream().map(this::mapToTimeTable).collect(Collectors.toList());
    }
    
}
