package com.clinic.controller;


import com.clinic.domain.*;
import com.clinic.domain.dto.AppointmentDto;
import com.clinic.domain.dto.DurationDto;
import com.clinic.domain.dto.ScheduleDto;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(ScheduleController.class)
class ScheduleControllerTest {

    @MockBean
    private ScheduleController controller;
    @Autowired
    private MockMvc mockMvc;

    private AppointmentDto appointment;

    void prepare() {

        this.appointment = new AppointmentDto(1L,
                "30-03-2021 15:30",
                new TreatmentDto("Botox", BigDecimal.valueOf(300), new DurationDto(Duration.of(1, ChronoUnit.HOURS))),
                new Customer("John","Smith","72120112124"),
                new Employee("David","Brown"),"GROUPON", BigDecimal.valueOf(150));

    }

    @Test
    void getSchedules() throws Exception {
        //Given
        prepare();
        ScheduleDto schedule1 = new ScheduleDto(1L,
                "30-03-2021 15:30",
                "30-03-2021 16:30",
                new Employee("David", "Brown"),
                appointment);
        ScheduleDto schedule2 = new ScheduleDto(2L,
                "30-03-2021 15:30",
                "30-03-2021 16:30",
                new Employee("Mike", "Smith"),
                appointment);
        List<ScheduleDto> scheduleList = List.of(schedule1, schedule2);
        when(controller.getSchedules()).thenReturn(scheduleList);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/schedules")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void createSchedule() throws Exception {
        //Given
        prepare();
        ScheduleDto schedule = new ScheduleDto(1L,
                "30-03-2021 15:30",
                "30-03-2021 16:30",
                new Employee("David", "Brown"),
                appointment);
        ScheduleDto createdSchedule = new ScheduleDto(2L,
                "30-03-2021 15:30",
                "30-03-2021 16:30",
                new Employee("Mike", "Smith"),
                appointment);
        when(controller.createSchedule(any(ScheduleDto.class))).thenReturn(createdSchedule);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(schedule);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employee.firstName", Matchers.is("Mike")));
    }

    @Test
    void deleteSchedule() throws Exception {
        //Given
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/schedules/11")
                        .characterEncoding("UTF-8"))
                .andExpect((MockMvcResultMatchers.status().is(200)));
    }

    @Test
    void updateSchedule() throws Exception {
        //Given
        prepare();
        ScheduleDto schedule = new ScheduleDto(1L,
                "30-03-2021 15:30",
                "30-03-2021 16:30",
                new Employee("David", "Brown"),
                appointment);
        ScheduleDto updatedSchedule = new ScheduleDto(2L,
                "30-03-2021 15:30",
                "30-03-2021 16:30",
                new Employee("Mike", "Smith"),
                appointment);
        when(controller.updateSchedule(any(ScheduleDto.class))).thenReturn(updatedSchedule);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(schedule);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employee.firstName", Matchers.is("Mike")));
    }

    @Test
    void getSchedule() throws Exception {
        //Given
        prepare();
        ScheduleDto schedule = new ScheduleDto(1L,
                "30-03-2021 15:30",
                "30-03-2021 16:30",
                new Employee("David", "Brown"),
                appointment);
        when(controller.getSchedule(any(Long.class))).thenReturn(schedule);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/schedules/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employee.firstName", Matchers.is("David")));
    }
}
