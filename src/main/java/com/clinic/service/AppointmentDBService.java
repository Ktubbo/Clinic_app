package com.clinic.service;

import com.clinic.domain.Appointment;
import com.clinic.domain.Schedule;
import com.clinic.domain.Shift;
import com.clinic.exceptions.ScheduleNotFoundException;
import com.clinic.exceptions.ShiftNotFoundException;
import com.clinic.repository.AppointmentRepository;
import com.clinic.repository.ScheduleRepository;
import com.clinic.repository.ShiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentDBService {

    @Autowired
    private final AppointmentRepository repository;
    @Autowired
    private final ShiftRepository shiftRepository;
    @Autowired
    private final ScheduleRepository scheduleRepository;

    public List<Appointment> getAllAppointments() { return repository.findAll(); }
    public Optional<Appointment> getAppointment(final Long appointmentId) { return repository.findById(appointmentId); }
    public void deleteAppointment(final Long appointmentId) { repository.deleteById(appointmentId); }
    public Appointment saveAppointment(Appointment appointment) throws Exception {
        LocalDateTime start = appointment.getStart();
        LocalDateTime end = appointment.getStart().plus(appointment.getTreatment().getDuration());

        List<Shift> shiftsToCheck = shiftRepository
                .findAllByEmployee(appointment.getEmployee())
                .stream()
                .filter(s -> start.isAfter(s.getStart()))
                .filter(s -> end.isBefore(s.getEnd()))
                .collect(Collectors.toList());

        List<Schedule> schedulesToCheck = scheduleRepository
                .findAllByEmployee(appointment.getEmployee())
                .stream()
                .filter(s -> !(start.isAfter(s.getStart()) && start.isBefore(s.getEnd()))
                            || !(end.isAfter(s.getStart()) && end.isBefore(s.getEnd())))
                .collect(Collectors.toList());

        if (!schedulesToCheck.isEmpty()) {throw new ScheduleNotFoundException();}
        else if (shiftsToCheck.isEmpty()) {throw new ShiftNotFoundException();}
        else {repository.save(appointment);}

        return appointment; }
}
