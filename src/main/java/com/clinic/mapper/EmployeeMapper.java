package com.clinic.mapper;

import com.clinic.domain.Employee;
import com.clinic.domain.dto.EmployeeDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeMapper {

    public Employee mapToEmployee(EmployeeDto employeeDto) {
        return new Employee (employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getSchedule(),
                employeeDto.getAppointment(),
                employeeDto.getTreatment());
    }

    public EmployeeDto mapToEmployeeDto(Employee employee) {
        return new EmployeeDto(employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getSchedule(),
                employee.getAppointment(),
                employee.getTreatment());
    }

    public List<EmployeeDto> mapToEmployeeDtoList(final List<Employee> employeeList) {
        return employeeList.stream().map(this::mapToEmployeeDto).collect(Collectors.toList());
    }

    public List<Employee> mapToEmployeeList(final List<EmployeeDto> employeeDtoList) {
        return employeeDtoList.stream().map(this::mapToEmployee).collect(Collectors.toList());
    }
    
}
