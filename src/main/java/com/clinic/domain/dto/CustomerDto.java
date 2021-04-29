package com.clinic.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String pesel;
    private String email;
}
