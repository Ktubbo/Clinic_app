package com.clinic.service;

import com.clinic.domain.*;
import com.clinic.exceptions.BusyCustomerException;
import com.clinic.exceptions.ScheduleNotFoundException;
import com.clinic.exceptions.ShiftNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
class AppointmentDBServiceTest {

    @Autowired
    private AppointmentDBService service;
    @Autowired
    private EmployeeDBService employeeDBService;
    @Autowired
    private ShiftDBService shiftDBService;
    @Autowired
    private CustomerDBService customerDBService;
    @Autowired
    private TreatmentDBService treatmentDBService;

    private Customer customer1;
    private Customer customer2;
    private Employee employee1;
    private Employee employee2;
    private Treatment treatment;
    private PricingStrategy normal;

    void prepare() {

        this.employee1 = new Employee("Mike","Brown");
        this.employee2 = new Employee("David","Johnson");
        Shift shift = new Shift(LocalDateTime.of(2021,2,20,8,0),
                LocalDateTime.of(2021,2,20,16,0),
                employee1);
        this.treatment = new Treatment("Botox", BigDecimal.valueOf(300),
                Duration.of(1, ChronoUnit.HOURS));

        List<Shift> shifts = new ArrayList<>();
        shifts.add(shift);
        employee2.setShift(shifts);
        employee1.setShift(shifts);
        employeeDBService.saveEmployee(employee1);
        employeeDBService.saveEmployee(employee2);
        shiftDBService.saveShift(shift);
        treatmentDBService.saveTreatment(treatment);

        this.customer1 = new Customer("Test_emp1","Test_LN1",
                "12345678910","test@mail.com");
        this.customer2 = new Customer("Test_emp2","Test_LN2",
                "12345678911","somemail@test.com");
        customerDBService.saveCustomer(customer1);
        customerDBService.saveCustomer(customer2);



        this.normal = PricingStrategy.NORMAL;
    }


    @Test
    void saveAndGetAllAppointments() {
        //Given
        prepare();
        int actualSizeOfAppointment = service.getAllAppointments().size();
        Appointment appointment = new Appointment.AppointmentBuilder()
                .customer(customer1)
                .employee(employee1)
                .treatment(treatment)
                .pricingStrategy(normal)
                .start(LocalDateTime.of(2021,2,20,10,0))
                .build();

        //When
        try {
            service.saveAppointment(appointment);
        }
        catch (Exception e) {
            fail();
        }

        //Then
        List<Appointment> appointments = service.getAllAppointments();
        assertEquals(actualSizeOfAppointment+1,appointments.size());
    }

    @Test
    void getAllAppointmentsByCustomer() {
        //Given
        prepare();
        Appointment appointment = new Appointment.AppointmentBuilder()
                .customer(customer1)
                .employee(employee1)
                .treatment(treatment)
                .pricingStrategy(normal)
                .start(LocalDateTime.of(2021,2,20,10,0))
                .build();

        //When
        try {
            service.saveAppointment(appointment);
        }
        catch (Exception e) {
            fail();
        }

        //Then
        List<Appointment> appointments = service
                .getAllAppointmentsByCustomer(customer1);
        assertEquals(1,appointments.size());
    }

    @Test
    void saveIfTreatmentStarsOnShiftStart() {
        //Given
        prepare();
        Appointment appointment = new Appointment.AppointmentBuilder()
                .customer(customer1)
                .employee(employee1)
                .treatment(treatment)
                .pricingStrategy(normal)
                .start(LocalDateTime.of(2021,2,20,8,0))
                .build();

        //When
        try {
            service.saveAppointment(appointment);
        }
        catch (Exception e) {
            fail();
        }
        long id = appointment.getId();
        //Then
        Appointment resultAppointment = service.getAppointment(id).get();
        assertEquals("Botox", resultAppointment.getTreatment().getName());
    }

    @Test
    void saveIfTreatmentEndsOnShiftEnd() {
        //Given
        prepare();
        Appointment appointment = new Appointment.AppointmentBuilder()
                .customer(customer1)
                .employee(employee1)
                .treatment(treatment)
                .pricingStrategy(normal)
                .start(LocalDateTime.of(2021,2,20,15,0))
                .build();

        //When
        try {
            service.saveAppointment(appointment);
        }
        catch (Exception e) {
            fail();
        }
        long id = appointment.getId();
        //Then
        Appointment resultAppointment = service.getAppointment(id).get();
        assertEquals("Botox", resultAppointment.getTreatment().getName());
    }


    @Test
    void dontSaveIfTreatmentStarsBeforeShiftStart() {
        //Given
        prepare();
        Appointment appointment = new Appointment.AppointmentBuilder()
                .customer(customer1)
                .employee(employee1)
                .treatment(treatment)
                .pricingStrategy(normal)
                .start(LocalDateTime.of(2021,2,20,7,59))
                .build();

        //When&Then
        boolean thrown = false;
        try {
            service.saveAppointment(appointment);
        } catch (ShiftNotFoundException e) {
            thrown = true;
        } catch (Exception e) {
            fail();
        }
        assertTrue(thrown);
    }

    @Test
    void dontSaveIfTreatmentEndsAfterShiftEnds() {
        //Given
        prepare();
        Appointment appointment = new Appointment.AppointmentBuilder()
                .customer(customer1)
                .employee(employee1)
                .treatment(treatment)
                .pricingStrategy(normal)
                .start(LocalDateTime.of(2021,2,20,15,1))
                .build();

        //When&Then
        boolean thrown = false;
        try {
            service.saveAppointment(appointment);
        } catch (ShiftNotFoundException e) {
            thrown = true;
        } catch (Exception e) {
            fail();
        }
        assertTrue(thrown);
    }

    @Test
    void dontSaveIfTreatmentStartsInTheMiddleOfExistingTreatment() {
        //Given
        prepare();
        Appointment existingAppointment = new Appointment.AppointmentBuilder()
                .customer(customer1)
                .employee(employee1)
                .treatment(treatment)
                .pricingStrategy(normal)
                .start(LocalDateTime.of(2021,2,20,10,0))
                .build();

        Appointment appointmentToSave = new Appointment.AppointmentBuilder()
                .customer(customer2)
                .employee(employee1)
                .treatment(treatment)
                .pricingStrategy(normal)
                .start(LocalDateTime.of(2021,2,20,10,30))
                .build();

        //When
        try {
            service.saveAppointment(existingAppointment);
        }
        catch (Exception e) {
            fail();
        }

        //Then
        boolean thrown = false;
        try {
            service.saveAppointment(appointmentToSave);
        } catch (ScheduleNotFoundException e) {
            thrown = true;
        } catch (Exception e) {
            fail();
        }
        assertTrue(thrown);
    }

    @Test
    void dontSaveIfTreatmentEndsInTheMiddleOfExistingTreatment() {
        //Given
        prepare();
        Appointment existingAppointment = new Appointment.AppointmentBuilder()
                .customer(customer1)
                .employee(employee1)
                .treatment(treatment)
                .pricingStrategy(normal)
                .start(LocalDateTime.of(2021,2,20,10,0))
                .build();

        Appointment appointmentToSave = new Appointment.AppointmentBuilder()
                .customer(customer2)
                .employee(employee1)
                .treatment(treatment)
                .pricingStrategy(normal)
                .start(LocalDateTime.of(2021,2,20,9,30))
                .build();

        //When
        try {
            service.saveAppointment(existingAppointment);
        }
        catch (Exception e) {
            fail();
        }

        //Then
        boolean thrown = false;
        try {
            service.saveAppointment(appointmentToSave);
        } catch (ScheduleNotFoundException e) {
            thrown = true;
        } catch (Exception e) {
            fail();
        }
        assertTrue(thrown);
    }

    @Test
    void dontSaveIfTreatmentStartsAndEndsAtTheSameTimeAsExistingTreatment() {
        //Given
        prepare();
        Appointment existingAppointment = new Appointment.AppointmentBuilder()
                .customer(customer1)
                .employee(employee1)
                .treatment(treatment)
                .pricingStrategy(normal)
                .start(LocalDateTime.of(2021,2,20,10,0))
                .build();

        Appointment appointmentToSave = new Appointment.AppointmentBuilder()
                .customer(customer1)
                .employee(employee1)
                .treatment(treatment)
                .pricingStrategy(normal)
                .start(LocalDateTime.of(2021,2,20,10,0))
                .build();

        //When
        try {
            service.saveAppointment(existingAppointment);
        }
        catch (Exception e) {
            fail();
        }

        //Then
        boolean thrown = false;
        try {
            service.saveAppointment(appointmentToSave);
        } catch (ScheduleNotFoundException e) {
            thrown = true;
        } catch (Exception e) {
            fail();
        }
        assertTrue(thrown);
    }

    @Test
    void dontSaveIfCustomerTreatmentStartsInTheMiddleOfExistingTreatment() {
        //Given
        prepare();
        Appointment existingAppointment = new Appointment.AppointmentBuilder()
                .customer(customer1)
                .employee(employee1)
                .treatment(treatment)
                .pricingStrategy(normal)
                .start(LocalDateTime.of(2021,2,20,10,0))
                .build();

        Appointment appointmentToSave = new Appointment.AppointmentBuilder()
                .customer(customer1)
                .employee(employee2)
                .treatment(treatment)
                .pricingStrategy(normal)
                .start(LocalDateTime.of(2021,2,20,10,30))
                .build();

        //When
        try {
            service.saveAppointment(existingAppointment);
        }
        catch (Exception e) {
            fail();
        }

        //Then
        boolean thrown = false;
        try {
            service.saveAppointment(appointmentToSave);
        } catch (BusyCustomerException e) {
            thrown = true;
        } catch (Exception e) {
            fail();
        }
        assertTrue(thrown);
    }

    @Test
    void dontSaveIfCustomerTreatmentEndsInTheMiddleOfExistingTreatment() {
        //Given
        prepare();
        Appointment existingAppointment = new Appointment.AppointmentBuilder()
                .customer(customer1)
                .employee(employee1)
                .treatment(treatment)
                .pricingStrategy(normal)
                .start(LocalDateTime.of(2021,2,20,10,0))
                .build();

        Appointment appointmentToSave = new Appointment.AppointmentBuilder()
                .customer(customer1)
                .employee(employee2)
                .treatment(treatment)
                .pricingStrategy(normal)
                .start(LocalDateTime.of(2021,2,20,9,30))
                .build();

        //When
        try {
            service.saveAppointment(existingAppointment);
        }
        catch (Exception e) {
            fail();
        }

        //Then
        boolean thrown = false;
        try {
            service.saveAppointment(appointmentToSave);
        } catch (BusyCustomerException e) {
            thrown = true;
        } catch (Exception e) {
            fail();
        }
        assertTrue(thrown);
    }

    @Test
    void dontSaveIfCustomerIsAlreadyAtExistingTreatment() {
        //Given
        prepare();
        Appointment existingAppointment = new Appointment.AppointmentBuilder()
                .customer(customer1)
                .employee(employee1)
                .treatment(treatment)
                .pricingStrategy(normal)
                .start(LocalDateTime.of(2021,2,20,10,0))
                .build();

        Appointment appointmentToSave = new Appointment.AppointmentBuilder()
                .customer(customer1)
                .employee(employee2)
                .treatment(treatment)
                .pricingStrategy(normal)
                .start(LocalDateTime.of(2021,2,20,10,0))
                .build();

        //When
        try {
            service.saveAppointment(existingAppointment);
        }
        catch (Exception e) {
            fail();
        }

        //Then
        boolean thrown = false;
        try {
            service.saveAppointment(appointmentToSave);
        } catch (BusyCustomerException e) {
            thrown = true;
        } catch (Exception e) {
            fail();
        }
        assertTrue(thrown);
    }

    @Test
    void getAppointment() {
        //Given
        prepare();
        Appointment appointment = new Appointment.AppointmentBuilder()
                .customer(customer1)
                .employee(employee1)
                .treatment(treatment)
                .pricingStrategy(normal)
                .start(LocalDateTime.of(2021,2,20,10,0))
                .build();

        //When
        try { service.saveAppointment(appointment); }
        catch (Exception e) { fail(); }
        long id = appointment.getId();

        //Then
        Appointment resultAppointment = service.getAppointment(id).get();
        assertEquals("Botox", resultAppointment.getTreatment().getName());
    }

    @Test
    void deleteAppointment() {
        //Given
        prepare();
        int actualSizeOfAppointment = service.getAllAppointments().size();

        Appointment appointment = new Appointment.AppointmentBuilder()
                .customer(customer1)
                .employee(employee1)
                .treatment(treatment)
                .pricingStrategy(normal)
                .start(LocalDateTime.of(2021,2,20,10,0))
                .build();

        //When
        try { service.saveAppointment(appointment); }
        catch (Exception e) { fail(); }
        long id = appointment.getId();
        //Then
        service.deleteAppointment(id);
        int resultSizeOfAppointment = service.getAllAppointments().size();
        assertEquals(actualSizeOfAppointment, resultSizeOfAppointment);
    }
}




