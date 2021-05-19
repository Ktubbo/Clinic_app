package com.clinic.controller;


import com.clinic.domain.Customer;
import com.clinic.domain.Employee;
import com.clinic.domain.Treatment;
import com.clinic.domain.dto.AppointmentDto;
import com.clinic.domain.dto.DurationDto;
import com.clinic.domain.dto.TreatmentDto;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(AppointmentController.class)
class AppointmentControllerTest {

    @MockBean
    private AppointmentController controller;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAppointments() throws Exception {
        //Given
        AppointmentDto appointment1 = new AppointmentDto(1L,
                "30-03-2021 15:30",
                new TreatmentDto("Botox", BigDecimal.valueOf(300), new DurationDto(Duration.of(1, ChronoUnit.HOURS))),
                new Customer("John","Smith","72120112124"),
                new Employee("David","Brown"),"GROUPON", BigDecimal.valueOf(150));
        AppointmentDto appointment2 = new AppointmentDto(2L,
                "30-03-2021 16:30",
                new TreatmentDto("Botox", BigDecimal.valueOf(300), new DurationDto(Duration.of(1, ChronoUnit.HOURS))),
                new Customer("John","Smith","72120112124"),
                new Employee("David","Brown"),"GROUPON", BigDecimal.valueOf(150));

        List<AppointmentDto> appointmentList = List.of(appointment1,appointment2);
        when(controller.getAppointments()).thenReturn(appointmentList);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/appointments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void createAppointment() throws Exception{
        //Given
        AppointmentDto appointment = new AppointmentDto(1L,
                "30-03-2021 15:30",
                new TreatmentDto("Botox", BigDecimal.valueOf(300), new DurationDto(Duration.of(1, ChronoUnit.HOURS))),
                new Customer("John","Smith","72120112124"),
                new Employee("David","Brown"),"GROUPON", BigDecimal.valueOf(150));
        AppointmentDto createdAppointment = new AppointmentDto(2L,
                "30-03-2021 16:30",
                new TreatmentDto("Botox", BigDecimal.valueOf(300), new DurationDto(Duration.of(1, ChronoUnit.HOURS))),
                new Customer("John","Smith","72120112124"),
                new Employee("David","Brown"),"GROUPON", BigDecimal.valueOf(150));

        when(controller.createAppointment(any(AppointmentDto.class))).thenReturn(createdAppointment);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(appointment);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employee.firstName",Matchers.is("David")));
    }

    @Test
    void deleteAppointment() throws Exception{
        //Given
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/appointments/11")
                        .characterEncoding("UTF-8"))
                .andExpect((MockMvcResultMatchers.status().is(200)));
    }

    @Test
    void updateAppointment() throws Exception{
        //Given
        AppointmentDto appointment = new AppointmentDto(1L,
                "30-03-2021 15:30",
                new TreatmentDto("Botox", BigDecimal.valueOf(300), new DurationDto(Duration.of(1, ChronoUnit.HOURS))),
                new Customer("John","Smith","72120112124"),
                new Employee("David","Brown"),"GROUPON", BigDecimal.valueOf(150));
        AppointmentDto updatedAppointment = new AppointmentDto(2L,
                "30-03-2021 16:30",
                new TreatmentDto("Botox", BigDecimal.valueOf(300), new DurationDto(Duration.of(1, ChronoUnit.HOURS))),
                new Customer("John","Smith","72120112124"),
                new Employee("David","Brown"),"GROUPON", BigDecimal.valueOf(150));

        when(controller.updateAppointment(any(AppointmentDto.class))).thenReturn(updatedAppointment);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(appointment);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employee.firstName",Matchers.is("David")));
    }

    @Test
    void getAppointment() throws Exception {
        //Given
        AppointmentDto appointment = new AppointmentDto(1L,
                "30-03-2021 15:30",
                new TreatmentDto("Botox", BigDecimal.valueOf(300), new DurationDto(Duration.of(1, ChronoUnit.HOURS))),
                new Customer("John","Smith","72120112124"),
                new Employee("David","Brown"),"GROUPON", BigDecimal.valueOf(150));
        when(controller.getAppointment(any(Long.class))).thenReturn(appointment);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/appointments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employee.firstName", Matchers.is("David")));
    }
}


