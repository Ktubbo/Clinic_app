package com.clinic.mapper;

import com.clinic.domain.Appointment;
import com.clinic.domain.PricingStrategy;
import com.clinic.domain.Treatment;
import com.clinic.domain.dto.AppointmentDto;
import com.clinic.domain.dto.DurationDto;
import com.clinic.domain.dto.TreatmentDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentMapper {

    @Autowired
    private TreatmentMapper treatmentMapper;

    public Appointment mapToAppointment(AppointmentDto appointmentDto) {
        return new Appointment.AppointmentBuilder()
                .id(appointmentDto.getId())
                .start(LocalDateTime.parse(appointmentDto.getStart(),DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")))
                .pricingStrategy(PricingStrategy.valueOf(appointmentDto.getPricingStrategy()))
                .treatment(treatmentMapper.mapToTreatment(appointmentDto.getTreatment()))
                .employee(appointmentDto.getEmployee())
                .customer(appointmentDto.getCustomer())
                .build();
    }

    public AppointmentDto mapToAppointmentDto(Appointment appointment) {
        return new AppointmentDto(appointment.getId(),
                appointment.getStart().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")),
                treatmentMapper.mapToTreatmentDto(appointment.getTreatment()),
                appointment.getCustomer(),
                appointment.getEmployee(),
                appointment.getPricingStrategy(),
                appointment.getPrice());
    }

    public List<AppointmentDto> mapToAppointmentDtoList(final List<Appointment> appointmentList) {
        return appointmentList.stream().map(this::mapToAppointmentDto).collect(Collectors.toList());
    }

    public List<Appointment> mapToAppointmentList(final List<AppointmentDto> appointmentDtoList) {
        return appointmentDtoList.stream().map(this::mapToAppointment).collect(Collectors.toList());
    }
    
}
