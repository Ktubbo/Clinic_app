package com.clinic.controller;

import com.clinic.domain.dto.EmployeeDto;
import com.clinic.exceptions.EmployeeNotFoundException;
import com.clinic.mapper.EmployeeMapper;
import com.clinic.service.EmployeeDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final EmployeeDBService dbService;
    private final EmployeeMapper mapper;

    @RequestMapping(method = RequestMethod.GET, value = "/employees")
    public List<EmployeeDto> getEmployees() {
        return mapper.mapToEmployeeDtoList(dbService.getAllEmployees());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/employees/{employeeId}")
    public EmployeeDto getEmployee(@PathVariable Long employeeId) throws EmployeeNotFoundException {
        return mapper.mapToEmployeeDto(dbService.getEmployee(employeeId).orElseThrow(EmployeeNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/employees/{employeeId}")
    public void deleteEmployee(@PathVariable Long employeeId) {
        dbService.deleteEmployee(employeeId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/employees", consumes = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) {
        return mapper.mapToEmployeeDto(dbService.saveEmployee(mapper.mapToEmployee(employeeDto)));
    }
}
