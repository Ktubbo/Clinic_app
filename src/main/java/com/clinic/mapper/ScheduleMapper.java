package com.clinic.mapper;

import com.clinic.domain.Schedule;
import com.clinic.domain.dto.ScheduleDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleMapper {

    public Schedule mapToSchedule(ScheduleDto scheduleDto) {
        return new Schedule (scheduleDto.getId(),
                scheduleDto.getStart(),
                scheduleDto.getEnd(),
                scheduleDto.getEmployee());
    }

    public ScheduleDto mapToScheduleDto(Schedule schedule) {
        return new ScheduleDto(schedule.getId(),
                schedule.getStart(),
                schedule.getEnd(),
                schedule.getEmployee());
    }

    public List<ScheduleDto> mapToScheduleDtoList(final List<Schedule> scheduleList) {
        return scheduleList.stream().map(this::mapToScheduleDto).collect(Collectors.toList());
    }

    public List<Schedule> mapToScheduleList(final List<ScheduleDto> scheduleDtoList) {
        return scheduleDtoList.stream().map(this::mapToSchedule).collect(Collectors.toList());
    }
}
