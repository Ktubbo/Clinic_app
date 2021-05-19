package com.clinic.service;

import com.clinic.domain.Appointment;
import com.clinic.domain.Customer;
import com.clinic.domain.Schedule;
import com.clinic.domain.Shift;
import com.clinic.exceptions.BusyCustomerException;
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
import java.util.ArrayList;
import java.util.Collections;
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

    public List<Appointment> getAllAppointmentsByCustomer(Customer customer) {
        return repository.findAllByCustomer(customer); }

    public Optional<Appointment> getAppointment(final Long appointmentId) { return
            repository.findById(appointmentId); }

    public void deleteAppointment(final Long appointmentId) {
        scheduleRepository.deleteByAppointment(repository.findById(appointmentId).get());
        repository.deleteById(appointmentId);
    }

    public Appointment saveAppointment(Appointment appointment) throws Exception {

        LocalDateTime start = appointment.getStart();
        LocalDateTime end = appointment.getStart().plus(appointment.getTreatment().getDuration());
        Appointment resultAppointment;

        boolean shiftsToCheck = !shiftRepository.findAllByEmployee(appointment.getEmployee()).isEmpty() &&
                shiftRepository.findAllByEmployee(appointment.getEmployee()).stream()
                .noneMatch(s -> ((start.isAfter(s.getStart()) || start.equals(s.getStart()))
                        && (end.isBefore(s.getEnd()) || end.equals(s.getEnd()))));

        boolean schedulesToCheck = false;
        if(!scheduleRepository.findAllByEmployee(appointment.getEmployee()).isEmpty()) {
            schedulesToCheck = scheduleRepository
                    .findAllByEmployee(appointment.getEmployee())
                    .stream()
                    .anyMatch(sch -> (start.isAfter(sch.getStart()) && start.isBefore(sch.getEnd()))
                            || (end.isAfter(sch.getStart()) && end.isBefore(sch.getEnd()))
                            || (start.equals(sch.getStart()) && end.equals(sch.getEnd())));
        }

        boolean customersToCheck = repository.findAllByCustomer(appointment.getCustomer())
                .stream()
                .anyMatch(app -> (start.isAfter(app.getStart()) && start.isBefore(app.getStart()
                        .plus(app.getTreatment().getDuration()))
                        || (end.isAfter(app.getStart()) && end.isBefore(app.getStart()
                        .plus(app.getTreatment().getDuration())))
                        || (start.equals(app.getStart()) && end.equals(app.getStart()
                        .plus(app.getTreatment().getDuration())))));

        if (schedulesToCheck) { throw new ScheduleNotFoundException(); }
        else if (shiftsToCheck) { throw new ShiftNotFoundException(); }
        else if (customersToCheck) { throw new BusyCustomerException(); }
        else {
            resultAppointment = repository.save(appointment);
            scheduleRepository.save(new Schedule(start,end,appointment.getEmployee(),appointment));
        }

        return resultAppointment; }

    public synchronized List<Appointment> getAllAppointments(String stringFilter) {
        ArrayList<Appointment> arrayList = new ArrayList<>();
        List<Appointment> contacts = repository.findAll();
        for (Appointment contact : contacts) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                ex.printStackTrace();
            }
        }
        Collections.sort(arrayList, (o1, o2) -> (int) (o2.getId() - o1.getId()));
        return arrayList;
    }
}
