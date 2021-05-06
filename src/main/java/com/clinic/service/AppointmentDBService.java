package com.clinic.service;

import com.clinic.domain.Appointment;
import com.clinic.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentDBService {

    @Autowired
    private final AppointmentRepository repository;

    public List<Appointment> getAllAppointments() { return repository.findAll(); }
    public Optional<Appointment> getAppointment(final Long appointmentId) { return repository.findById(appointmentId); }
    public void deleteAppointment(final Long appointmentId) { repository.deleteById(appointmentId); }
    public Appointment saveAppointment(Appointment appointment) { return repository.save(appointment); }
}
