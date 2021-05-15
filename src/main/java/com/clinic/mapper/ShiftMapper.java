package com.clinic.mapper;

import com.clinic.domain.Shift;
import com.clinic.domain.dto.ShiftDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShiftMapper {

    public Shift mapToShift(ShiftDto shiftDto) {
        return new Shift(shiftDto.getId(),
                shiftDto.getStart(),
                shiftDto.getEnd(),
                shiftDto.getEmployee());
    }

    public ShiftDto mapToShiftDto(Shift shift) {
        return new ShiftDto(shift.getId(),
                shift.getStart(),
                shift.getEnd(),
                shift.getEmployee());
    }

    public List<ShiftDto> mapToShiftDtoList(final List<Shift> shiftList) {
        return shiftList.stream().map(this::mapToShiftDto).collect(Collectors.toList());
    }

    public List<Shift> mapToShiftList(final List<ShiftDto> shiftDtoList) {
        return shiftDtoList.stream().map(this::mapToShift).collect(Collectors.toList());
    }
}
