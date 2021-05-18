package com.clinic.controller;

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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;



@SpringJUnitWebConfig
@WebMvcTest(TreatmentController.class)
class TreatmentControllerTest {

    @MockBean
    private TreatmentController controller;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getTreatments() throws Exception{
        //Given
        TreatmentDto treatment1 = new TreatmentDto(1L,
                "Cryolipolysis", BigDecimal.valueOf(200),
                new DurationDto("1","30"), new ArrayList<>());

        TreatmentDto treatment2 = new TreatmentDto(1L,
                "Botox", BigDecimal.valueOf(300),
                new DurationDto("1","0"),new ArrayList<>());
        List<TreatmentDto> treatmentList = List.of(treatment1,treatment2);
        when(controller.getTreatments()).thenReturn(treatmentList);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/treatments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void createTreatment() throws Exception{
        //Given
        TreatmentDto treatment = new TreatmentDto(1L,
                "Cryolipolysis", BigDecimal.valueOf(200),
                new DurationDto("1","30"), new ArrayList<>());
        TreatmentDto createdTreatment = new TreatmentDto(1L,
                "Botox", BigDecimal.valueOf(300),
                new DurationDto("1","0"), new ArrayList<>());
        when(controller.createTreatment(any(TreatmentDto.class))).thenReturn(createdTreatment);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(treatment);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/treatments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",Matchers.is("Botox")));
    }

    @Test
    void deleteTreatment() throws Exception{
        //Given
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/treatments/11")
                        .characterEncoding("UTF-8"))
                .andExpect((MockMvcResultMatchers.status().is(200)));
    }

    @Test
    void updateTreatment() throws Exception{
        //Given
        TreatmentDto treatment = new TreatmentDto(1L,
                "Cryolipolysis", BigDecimal.valueOf(200),
                new DurationDto("1","30"), new ArrayList<>());
        TreatmentDto updatedTreatment = new TreatmentDto(1L,
                "Botox", BigDecimal.valueOf(300),
                new DurationDto("1","0"), new ArrayList<>());
        when(controller.updateTreatment(any(TreatmentDto.class))).thenReturn(updatedTreatment);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(treatment);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/treatments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",Matchers.is("Botox")));
    }

    @Test

    void getTreatment() throws Exception {
        //Given
        TreatmentDto treatment = new TreatmentDto(1L,
                "Cryolipolysis", BigDecimal.valueOf(200),
                new DurationDto("1","30"), new ArrayList<>());
        when(controller.getTreatment(any(Long.class))).thenReturn(treatment);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/treatments/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect((MockMvcResultMatchers.status().is(200)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Cryolipolysis")));
    }
}


