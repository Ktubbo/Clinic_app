package com.clinic.controller;

import com.clinic.domain.Appointment;
import com.clinic.domain.dto.AppointmentDto;
import com.clinic.domain.dto.AppointmentDto;
import com.clinic.exceptions.AppointmentNotFoundException;
import com.clinic.mapper.AppointmentMapper;
import com.clinic.service.AppointmentDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AppointmentController {

    private final AppointmentDBService dbService;
    private final AppointmentMapper mapper;

    @RequestMapping(method = RequestMethod.GET, value = "/appointments")
    public List<AppointmentDto> getAppointments() {
        return mapper.mapToAppointmentDtoList(dbService.getAllAppointments());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/appointments/{appointmentId}")
    public AppointmentDto getAppointment(@PathVariable Long appointmentId) throws AppointmentNotFoundException {
        return mapper.mapToAppointmentDto(dbService.getAppointment(appointmentId).orElseThrow(AppointmentNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/appointments/{appointmentId}")
    public void deleteAppointment(@PathVariable Long appointmentId) {
        dbService.deleteAppointment(appointmentId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/appointments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AppointmentDto createAppointment(@RequestBody AppointmentDto appointmentDto) {
        AppointmentDto returnAppointment = new AppointmentDto();
        try {
            returnAppointment = mapper.mapToAppointmentDto(dbService.saveAppointment(mapper.mapToAppointment(appointmentDto)));
        } catch (Exception e) {
            System.out.println(e);
        }
        return returnAppointment;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/appointments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public AppointmentDto updateAppointment(@RequestBody AppointmentDto appointmentDto) {
        Appointment appointment = mapper.mapToAppointment(appointmentDto);
        Appointment returnAppointment = new Appointment();
        try {
            returnAppointment = dbService.saveAppointment(appointment);
        } catch (Exception e) {
            System.out.println(e);
        }
        return mapper.mapToAppointmentDto(returnAppointment);
    }
}
