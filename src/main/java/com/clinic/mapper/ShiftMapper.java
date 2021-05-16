package com.clinic.mapper;

import com.clinic.domain.Shift;
import com.clinic.domain.dto.ShiftDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShiftMapper {

    public Shift mapToShift(ShiftDto shiftDto) {
        return new Shift(shiftDto.getId(),
                LocalDateTime.of(LocalDate.parse(shiftDto.getDate()), LocalTime.parse(shiftDto.getStartHour())),
                LocalDateTime.of(LocalDate.parse(shiftDto.getDate()), LocalTime.parse(shiftDto.getStartHour())),
                shiftDto.getEmployee());
    }

    public ShiftDto mapToShiftDto(Shift shift) {
        return new ShiftDto(shift.getId(),
                shift.getStart().format(DateTimeFormatter.ofPattern("dd MM yyyy")),
                shift.getStart().getDayOfWeek().toString(),
                shift.getStart().format(DateTimeFormatter.ofPattern("HH:mm")),
                shift.getEnd().format(DateTimeFormatter.ofPattern("HH:mm")),
                shift.getEmployee());
    }

    public List<ShiftDto> mapToShiftDtoList(final List<Shift> shiftList) {
        return shiftList.stream().map(this::mapToShiftDto).collect(Collectors.toList());
    }

    public List<Shift> mapToShiftList(final List<ShiftDto> shiftDtoList) {
        return shiftDtoList.stream().map(this::mapToShift).collect(Collectors.toList());
    }
}
