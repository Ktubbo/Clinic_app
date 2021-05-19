package com.clinic.mapper;


import com.clinic.domain.Appointment;
import com.clinic.domain.PricingStrategy;
import com.clinic.domain.Schedule;
import com.clinic.domain.Treatment;
import com.clinic.domain.dto.AppointmentDto;
import com.clinic.domain.dto.DurationDto;
import com.clinic.domain.dto.ScheduleDto;
import com.clinic.domain.dto.TreatmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleMapper {

    @Autowired
    private AppointmentMapper appointmentMapper;

    public Schedule mapToSchedule(ScheduleDto scheduleDto) {
        return new Schedule(scheduleDto.getId(),
                LocalDateTime.parse(scheduleDto.getStart(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                LocalDateTime.parse(scheduleDto.getEnd(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                scheduleDto.getEmployee(),
                appointmentMapper.mapToAppointment(scheduleDto.getAppointment())
                );
    }

    public ScheduleDto mapToScheduleDto(Schedule schedule) {
        return new ScheduleDto(schedule.getId(),
                schedule.getStart().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                schedule.getEnd().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                schedule.getEmployee(),appointmentMapper.mapToAppointmentDto(schedule.getAppointment())
                );
    }

    public List<ScheduleDto> mapToScheduleDtoList(final List<Schedule> scheduleList) {
        return scheduleList.stream().map(this::mapToScheduleDto).collect(Collectors.toList());
    }

    public List<Schedule> mapToScheduleList(final List<ScheduleDto> scheduleDtoList) {
        return scheduleDtoList.stream().map(this::mapToSchedule).collect(Collectors.toList());
    }
    
}