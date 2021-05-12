package com.clinic.mapper;

import com.clinic.domain.Treatment;
import com.clinic.domain.dto.DurationDto;
import com.clinic.domain.dto.TreatmentDto;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TreatmentMapper {
    public Treatment mapToTreatment(TreatmentDto treatmentDto) {
        return new Treatment (treatmentDto.getId(),
                treatmentDto.getName(),
                treatmentDto.getPrice(),
                treatmentDto.getDuration().toDuration(),
                treatmentDto.getEmployee());
    }

    public TreatmentDto mapToTreatmentDto(Treatment treatment) {
        return new TreatmentDto(treatment.getId(),
                treatment.getName(),
                treatment.getPrice(),
                new DurationDto(treatment.getDuration()),
                treatment.getEmployee());
    }

    public List<TreatmentDto> mapToTreatmentDtoList(final List<Treatment> treatmentList) {
        return treatmentList.stream().map(this::mapToTreatmentDto).collect(Collectors.toList());
    }

    public List<Treatment> mapToTreatmentList(final List<TreatmentDto> treatmentDtoList) {
        return treatmentDtoList.stream().map(this::mapToTreatment).collect(Collectors.toList());
    }
    
}
