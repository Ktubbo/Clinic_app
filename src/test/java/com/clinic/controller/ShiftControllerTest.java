package com.clinic.controller;


import com.clinic.domain.Employee;
import com.clinic.domain.dto.ShiftDto;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(ShiftController.class)
class ShiftControllerTest {

    @MockBean
    private ShiftController controller;
    @Autowired
    private MockMvc mockMvc;


    @Test
    void getShifts() throws Exception {
        //Given
        ShiftDto shift1 = new ShiftDto(1L, "19 02 2021", "FRIDAY", "15:30",
                "16:30", new Employee("David", "Brown"));
        ShiftDto shift2 = new ShiftDto(2L, "20 02 2021", "SATURDAY", "16:30",
                "17:30", new Employee("Mike", "Brown"));
        List<ShiftDto> shiftList = List.of(shift1, shift2);
        when(controller.getShifts()).thenReturn(shiftList);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/shifts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void createShift() throws Exception {
        //Given
        ShiftDto shift = new ShiftDto(1L, "19 02 2021", "FRIDAY", "15:30",
                "16:30", new Employee("David", "Brown"));
        ShiftDto createdShift = new ShiftDto(2L, "20 02 2021", "SATURDAY", "16:30",
                "17:30", new Employee("Mike", "Brown"));
        when(controller.createShift(any(ShiftDto.class))).thenReturn(createdShift);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(shift);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/shifts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employee.firstName", Matchers.is("Mike")));
    }

    @Test
    void deleteShift() throws Exception {
        //Given
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/shifts/11")
                        .characterEncoding("UTF-8"))
                .andExpect((MockMvcResultMatchers.status().is(200)));
    }

    @Test
    void updateShift() throws Exception {
        //Given
        ShiftDto shift = new ShiftDto(1L, "19 02 2021", "FRIDAY", "15:30",
                "16:30", new Employee("David", "Brown"));
        ShiftDto updatedShift = new ShiftDto(2L, "20 02 2021", "SATURDAY", "16:30",
                "17:30", new Employee("Mike", "Brown"));
        when(controller.updateShift(any(ShiftDto.class))).thenReturn(updatedShift);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(shift);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/shifts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employee.firstName", Matchers.is("Mike")));
    }

    @Test
    void getShift() throws Exception {
        //Given
        ShiftDto shift = new ShiftDto(1L, "19 02 2021", "FRIDAY", "15:30",
                "16:30", new Employee("David", "Brown"));
        when(controller.getShift(any(Long.class))).thenReturn(shift);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/shifts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employee.firstName", Matchers.is("David")));
    }
}



