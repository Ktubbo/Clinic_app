package com.clinic.controller;

import com.clinic.domain.dto.EmployeeDto;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @MockBean
    private EmployeeController controller;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getEmployees() throws Exception{
        //Given
        EmployeeDto employee1 = new EmployeeDto(1L,"John","Smith",
                new ArrayList<>(), new ArrayList<>(),new ArrayList<>());
        EmployeeDto employee2 = new EmployeeDto(2L,"Mike","Brown",
                new ArrayList<>(),new ArrayList<>(),new ArrayList<>());List<EmployeeDto> employeeList = List.of(employee1,employee2);
        when(controller.getEmployees()).thenReturn(employeeList);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void createEmployee() throws Exception{
        //Given
        EmployeeDto employee = new EmployeeDto(1L,"John","Smith",
                new ArrayList<>(), new ArrayList<>(),new ArrayList<>());
        EmployeeDto createdEmployee = new EmployeeDto(2L,"Mike","Brown",
                new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
        when(controller.createEmployee(any(EmployeeDto.class))).thenReturn(createdEmployee);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(employee);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",Matchers.is("Mike")));
    }

    @Test
    void deleteEmployee() throws Exception{
        //Given
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/employees/11")
                        .characterEncoding("UTF-8"))
                .andExpect((MockMvcResultMatchers.status().is(200)));
    }

    @Test
    void updateEmployee() throws Exception{
        //Given
        EmployeeDto employee = new EmployeeDto(1L,"John","Smith",
                new ArrayList<>(), new ArrayList<>(),new ArrayList<>());
        EmployeeDto updatedEmployee = new EmployeeDto(2L,"Mike","Brown",
                new ArrayList<>(),new ArrayList<>(),new ArrayList<>());when(controller.updateEmployee(any(EmployeeDto.class))).thenReturn(updatedEmployee);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(employee);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",Matchers.is("Mike")));
    }

    @Test
    void getEmployee() throws Exception {
        //Given
        EmployeeDto employee = new EmployeeDto(1L, "John","Smith",
                new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
        when(controller.getEmployee(any(Long.class))).thenReturn(employee);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("John")));
    }
}