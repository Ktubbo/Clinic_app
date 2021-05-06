package com.clinic.mapper;

import com.clinic.domain.Appointment;
import com.clinic.domain.PricingStrategy;
import com.clinic.domain.dto.AppointmentDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentMapper {

    public Appointment mapToAppointment(AppointmentDto appointmentDto) {
        return new Appointment.AppointmentBuilder()
                .id(appointmentDto.getId())
                .start(appointmentDto.getStart())
                .pricingStrategy(PricingStrategy.valueOf(appointmentDto.getPricingStrategy()))
                .treatment(appointmentDto.getTreatment())
                .employee(appointmentDto.getEmployee())
                .customer(appointmentDto.getCustomer())
                .build();
    }

    public AppointmentDto mapToAppointmentDto(Appointment appointment) {
        return new AppointmentDto(appointment.getId(),
                appointment.getStart(),
                appointment.getTreatment(),
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