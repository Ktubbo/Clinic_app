package com.clinic.views.appointments;

import com.clinic.domain.Appointment;
import com.clinic.domain.Employee;
import com.clinic.domain.PricingStrategy;
import com.clinic.domain.dto.AppointmentDto;
import com.clinic.domain.dto.CustomerDto;
import com.clinic.domain.dto.EmployeeDto;
import com.clinic.domain.dto.TreatmentDto;
import com.clinic.exceptions.BusyCustomerException;
import com.clinic.exceptions.ScheduleNotFoundException;
import com.clinic.exceptions.ShiftNotFoundException;
import com.clinic.mapper.AppointmentMapper;
import com.clinic.mapper.CustomerMapper;
import com.clinic.mapper.EmployeeMapper;
import com.clinic.mapper.TreatmentMapper;
import com.clinic.service.AppointmentDBService;
import com.clinic.service.CustomerDBService;
import com.clinic.service.EmployeeDBService;
import com.clinic.service.TreatmentDBService;
import com.clinic.views.main.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Route(value = "setAppointment", layout = MainView.class)
@PageTitle("Appointments")
@CssImport("./views/appointments/appointments-view.css")
public class SetAppointment extends Div {

    private AppointmentDBService appointmentDBService;
    private CustomerDBService customerDBService;
    private EmployeeDBService employeeDBService;
    private TreatmentDBService treatmentDBService;
    private AppointmentMapper appointmentMapper;
    private CustomerMapper customerMapper;
    private EmployeeMapper employeeMapper;
    private TreatmentMapper treatmentMapper;

    private Grid<AppointmentDto> appointmentDtoGrid = new Grid<>(AppointmentDto.class);
    private Grid<CustomerDto> customerDtoGrid = new Grid<>(CustomerDto.class);
    private Grid<EmployeeDto> employeeDtoGrid = new Grid<>(EmployeeDto.class);
    private Grid<TreatmentDto> treatmentDtoGrid = new Grid<>(TreatmentDto.class);

    private Binder<AppointmentDto> appointmentDtoBinder = new Binder<>(AppointmentDto.class);
    private Binder<CustomerDto> customerDtoBinder = new Binder<>(CustomerDto.class);
    private Binder<EmployeeDto> employeeDtoBinder = new Binder<>(EmployeeDto.class);
    private Binder<TreatmentDto> treatmentDtoBinder = new Binder<>(TreatmentDto.class);

    private ComboBox<PricingStrategy> comboBox = new ComboBox<>("Pricing Strategy");
    private Button save = new Button("Save");
    private TextField customerFilter = new TextField();
    private TextField treatmentFilter = new TextField();
    private TextField employeeFilter = new TextField();
    private Text customerEntity = new Text("Pick customer:");
    private Text treatmentEntity = new Text("Pick treatment:");
    private Text employeeEntity = new Text("Pick employee:");
    private DateTimePicker appointmentDate = new DateTimePicker("Appointment date");

    public SetAppointment(@Autowired AppointmentDBService appointmentDBService,
                          @Autowired CustomerDBService customerDBService,
                          @Autowired EmployeeDBService employeeDBService,
                          @Autowired TreatmentDBService treatmentDBService,
                          @Autowired AppointmentMapper appointmentMapper,
                          @Autowired CustomerMapper customerMapper,
                          @Autowired EmployeeMapper employeeMapper,
                          @Autowired TreatmentMapper treatmentMapper) {
        this.appointmentDBService = appointmentDBService;
        this.customerDBService = customerDBService;
        this.employeeDBService = employeeDBService;
        this.treatmentDBService = treatmentDBService;
        this.appointmentMapper = appointmentMapper;
        this.customerMapper = customerMapper;
        this.employeeMapper = employeeMapper;
        this.treatmentMapper = treatmentMapper;

        addClassName("appointments-view");
        appointmentDtoGrid.setColumns("start","treatment","customer","employee","price");
        customerDtoGrid.setColumns("firstName","lastName","pesel");
        employeeDtoGrid.setColumns("firstName","lastName");
        treatmentDtoGrid.setColumns("name","duration");

        comboBox.setItems(PricingStrategy.GROUPON,PricingStrategy.FRIENDS,PricingStrategy.NORMAL);

        customerFilter.setPlaceholder("Customer filter...");
        customerFilter.setClearButtonVisible(true);
        customerFilter.setValueChangeMode(ValueChangeMode.EAGER);
        customerFilter.addValueChangeListener(e -> customerUpdate());

        treatmentFilter.setPlaceholder("Treatment filter...");
        treatmentFilter.setClearButtonVisible(true);
        treatmentFilter.setValueChangeMode(ValueChangeMode.EAGER);
        treatmentFilter.addValueChangeListener(e -> treatmentUpdate());

        employeeFilter.setPlaceholder("Employee filter...");
        employeeFilter.setClearButtonVisible(true);
        employeeFilter.setValueChangeMode(ValueChangeMode.EAGER);
        employeeFilter.addValueChangeListener(e -> employeeUpdate());

        appointmentDtoGrid.asSingleSelect().addValueChangeListener(event ->
                appointmentDtoBinder.setBean(appointmentDtoGrid.asSingleSelect().getValue()));
        customerDtoGrid.asSingleSelect().addValueChangeListener(event ->
                customerDtoBinder.setBean(customerDtoGrid.asSingleSelect().getValue()));
        employeeDtoGrid.asSingleSelect().addValueChangeListener(event ->
                employeeDtoBinder.setBean(employeeDtoGrid.asSingleSelect().getValue()));
        treatmentDtoGrid.asSingleSelect().addValueChangeListener(event -> {
                    treatmentDtoBinder.setBean(treatmentDtoGrid.asSingleSelect().getValue());
                    employeeUpdate();
                });


        VerticalLayout form = new VerticalLayout(appointmentDate,comboBox,save);

        VerticalLayout customerLayout = new VerticalLayout(customerFilter,customerEntity,customerDtoGrid);
        VerticalLayout treatmentLayout = new VerticalLayout(treatmentFilter,treatmentEntity,treatmentDtoGrid);
        VerticalLayout employeeLayout = new VerticalLayout(employeeFilter,employeeEntity,employeeDtoGrid);

        HorizontalLayout firstRow = new HorizontalLayout(customerLayout,treatmentLayout,form);
        HorizontalLayout secondRow = new HorizontalLayout(employeeLayout);

        add(firstRow,secondRow);
        customerUpdate();
        treatmentUpdate();

        save.addClickListener(event -> save());
    }

    public void customerUpdate() {
        customerDtoGrid.setItems(customerMapper.mapToCustomerDtoList(customerDBService.getAllCustomers(customerFilter.getValue())));
    }

    public void treatmentUpdate() {
        treatmentDtoGrid.setItems(treatmentMapper.mapToTreatmentDtoList(treatmentDBService.getAllTreatments(treatmentFilter.getValue())));
    }

    public void employeeUpdate() {
        List<EmployeeDto> employeeDtoList = treatmentDtoBinder.getBean()==null ? new ArrayList<>() :
                employeeMapper.mapToEmployeeDtoList(treatmentDBService.getTreatment(treatmentDtoBinder.getBean().getId()).get().getEmployees());
        employeeDtoGrid.setItems(filterEmployeeList(employeeDtoList,employeeFilter.getValue()));
    }

    public List<EmployeeDto> filterEmployeeList(List<EmployeeDto> employeeDtos, String stringFilter) {
        ArrayList<EmployeeDto> arrayList = new ArrayList<>();
        for (EmployeeDto employee : employeeDtos) {
            boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                    || employee.toString().toLowerCase().contains(stringFilter.toLowerCase());
            if (passesFilter) {
                arrayList.add(employee);
            }
            arrayList.sort((o1, o2) -> (int) (o2.getId() - o1.getId()));
        }
        return arrayList;
    }

    private void save() {

        EmployeeDto employeeDto = employeeDtoBinder.getBean();
        CustomerDto customerDto = customerDtoBinder.getBean();
        TreatmentDto treatmentDto = treatmentDtoBinder.getBean();
        PricingStrategy pricingStrategy = comboBox.getValue();
        Appointment appointment = new Appointment.AppointmentBuilder()
                .start(appointmentDate.getValue())
                .treatment(treatmentMapper.mapToTreatment(treatmentDto))
                .customer(customerMapper.mapToCustomer(customerDto))
                .employee(employeeMapper.mapToEmployee(employeeDto))
                .pricingStrategy(pricingStrategy)
                .build();

        try {
            appointmentDBService.saveAppointment(appointment);

        } catch (ScheduleNotFoundException e) {
            Notification notification = new Notification("This employee is busy at this time.");
            notification.open();
        } catch (ShiftNotFoundException e) {
            Notification notification = new Notification("This employee doesn't work at this time.");
            notification.open();
        } catch (BusyCustomerException e) {
            Notification notification = new Notification("This customer has another appointment at this time.");
            notification.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
