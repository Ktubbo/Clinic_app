package com.clinic.controller;

import com.clinic.domain.dto.CustomerDto;
import com.clinic.exceptions.CustomerNotFoundException;
import com.clinic.mapper.CustomerMapper;
import com.clinic.service.CustomerDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerDBService dbService;
    private final CustomerMapper mapper;

    @RequestMapping(method = RequestMethod.GET, value = "/customers")
    public List<CustomerDto> getCustomers() {
        return mapper.mapToCustomerDtoList(dbService.getAllCustomers());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/customers/{customerId}")
    public CustomerDto getCustomer(@PathVariable Long customerId) throws CustomerNotFoundException {
        return mapper.mapToCustomerDto(dbService.getCustomer(customerId).orElseThrow(CustomerNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/customers/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId) {
        dbService.deleteCustomer(customerId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/customers", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CustomerDto createCustomer(@RequestBody CustomerDto customerDto) {
        return mapper.mapToCustomerDto(dbService.saveCustomer(mapper.mapToCustomer(customerDto)));
    }
}
