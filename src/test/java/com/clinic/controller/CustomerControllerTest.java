package com.clinic.controller;

import com.clinic.domain.dto.CustomerDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @MockBean
    private CustomerController controller;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCustomers() throws Exception{
        //Given
        CustomerDto customer1 = new CustomerDto(1L,"John","Smith","72120112124","test@mail.com");
        CustomerDto customer2 = new CustomerDto(2L,"Mike","Brown","75021112345","mail@test.com");
        List<CustomerDto> customerList = List.of(customer1,customer2);
        when(controller.getCustomers()).thenReturn(customerList);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void createCustomer() throws Exception{
        //Given
        CustomerDto customer = new CustomerDto(1L,"John","Smith","72120112124","test@mail.com");
        CustomerDto createdCustomer = new CustomerDto(2L,"Mike","Brown","75021112345","mail@test.com");
        when(controller.createCustomer(any(CustomerDto.class))).thenReturn(createdCustomer);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(customer);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",Matchers.is("Mike")));
    }

    @Test
    void deleteCustomer() throws Exception{
        //Given
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/customers/11")
                        .characterEncoding("UTF-8"))
                .andExpect((MockMvcResultMatchers.status().is(200)));
    }

    @Test
    void updateCustomer() throws Exception{
        //Given
        CustomerDto customer = new CustomerDto(1L,"John","Smith","72120112124","test@mail.com");
        CustomerDto updatedCustomer = new CustomerDto(2L,"Mike","Brown","75021112345","mail@test.com");
        when(controller.updateCustomer(any(CustomerDto.class))).thenReturn(updatedCustomer);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(customer);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",Matchers.is("Mike")));
    }

    @Test
    void getCustomer() throws Exception {
        //Given
        CustomerDto customer = new CustomerDto(1L,"John","Smith","72120112124","test@mail.com");
        when(controller.getCustomer(any(Long.class))).thenReturn(customer);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("John")));
    }
}