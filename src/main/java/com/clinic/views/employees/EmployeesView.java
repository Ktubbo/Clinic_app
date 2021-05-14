package com.clinic.views.employees;

import com.clinic.domain.Employee;
import com.clinic.domain.dto.DurationDto;
import com.clinic.domain.dto.EmployeeDto;
import com.clinic.domain.dto.TreatmentDto;
import com.clinic.exceptions.EmployeeNotFoundException;
import com.clinic.mapper.EmployeeMapper;
import com.clinic.mapper.TreatmentMapper;
import com.clinic.service.EmployeeDBService;
import com.clinic.service.TreatmentDBService;
import com.clinic.views.main.MainView;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.dependency.CssImport;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Route(value = "employees", layout = MainView.class)
@PageTitle("Employees")
@CssImport("./views/employees/employees-view.css")
public class EmployeesView extends Div {

    private EmployeeDBService employeeDBService;
    private EmployeeMapper employeeMapper;
    private TreatmentDBService treatmentDBService;
    private TreatmentMapper treatmentMapper;

    private Grid<EmployeeDto> grid = new Grid<>(EmployeeDto.class);
    private Grid<TreatmentDto> treatmentGrid = new Grid<>(TreatmentDto.class);
    private Grid<TreatmentDto> employeeTreatments = new Grid<>(TreatmentDto.class);

    private TextField filterText = new TextField();

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button addTreatment = new Button("Add Treatment");
    private Button deleteTreatment = new Button("Delete Treatment");
    private Button showTreatments = new Button("Show Treatments");

    private Binder<EmployeeDto> binder = new Binder<>(EmployeeDto.class);
    private Binder<TreatmentDto> treatmentBinder = new Binder<>(TreatmentDto.class);

    public EmployeesView(@Autowired EmployeeDBService employeeDBService, @Autowired EmployeeMapper employeeMapper,
                         @Autowired TreatmentDBService treatmentDBService, @Autowired TreatmentMapper treatmentMapper) {
        this.employeeDBService = employeeDBService;
        this.employeeMapper = employeeMapper;
        this.treatmentDBService = treatmentDBService;
        this.treatmentMapper = treatmentMapper;

        addClassName("employees-view");
        updateList();

        grid.setColumns("firstName", "lastName");
        treatmentGrid.setColumns("name","price","duration");
        employeeTreatments.setColumns("name","price","duration");
        employeeTreatments.setMaxWidth(500F, Unit.PIXELS);

        grid.asSingleSelect().addValueChangeListener(event -> binder.setBean(grid.asSingleSelect().getValue()));

        treatmentGrid.asSingleSelect().addValueChangeListener(event -> treatmentBinder.setBean(treatmentGrid.asSingleSelect().getValue()));

        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());

        binder.forField(firstName).bind(EmployeeDto::getFirstName,EmployeeDto::setFirstName);
        binder.forField(lastName).bind(EmployeeDto::getLastName,EmployeeDto::setLastName);

        HorizontalLayout employeeButtons = new HorizontalLayout(save, delete);
        HorizontalLayout treatmentButtons = new HorizontalLayout(addTreatment,deleteTreatment,showTreatments);
        VerticalLayout buttons = new VerticalLayout(employeeButtons,treatmentButtons);
        FormLayout form = new FormLayout(firstName, lastName, buttons);
        HorizontalLayout mainContent = new HorizontalLayout(grid,treatmentGrid,form);
        add(filterText,mainContent,employeeTreatments);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addTreatment.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        showTreatments.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
        addTreatment.addClickListener(event -> addTreatment());
        deleteTreatment.addClickListener(event -> deleteTreatment());
        showTreatments.addClickListener(event -> showTreatmentsList());
    }

    public void updateList() {
        grid.setItems(employeeMapper.mapToEmployeeDtoList(employeeDBService.getAllEmployees(filterText.getValue())));
        treatmentGrid.setItems(treatmentMapper.mapToTreatmentDtoList(treatmentDBService.getAllTreatments()));
    }

    private void save() {
        EmployeeDto employeeDto = binder.getBean();
        Employee employee = employeeDto==null ? new Employee(firstName.getValue(),lastName.getValue()) :
                employeeDBService.getEmployee(employeeDto.getId()).get();
        employeeDBService.saveEmployee(employee);
        updateList();
    }

    private void delete() {
        EmployeeDto employeeDto = binder.getBean();
        employeeDBService.deleteEmployee(employeeDto.getId());
        updateList();
    }

    private void addTreatment() {
        Long employeeId = binder.getBean().getId();
        Long treatmentId = treatmentBinder.getBean().getId();

        try {
            employeeDBService.addTreatment(employeeId,treatmentId);
        } catch (EmployeeNotFoundException e) {
            Notification notification = new Notification("Employee not Found");
            notification.open();
        }
        updateList();
    }

    private void deleteTreatment() {
        EmployeeDto employeeDto = binder.getBean();
        TreatmentDto treatmentDto = treatmentBinder.getBean();

        try {
            employeeDBService.deleteTreatment(employeeMapper.mapToEmployee(employeeDto),
                    treatmentMapper.mapToTreatment(treatmentDto));
        } catch (EmployeeNotFoundException e) {
            Notification notification = new Notification("Employee not Found");
            notification.open();
        }
    }

    private void showTreatmentsList() {
        EmployeeDto employeeDto = binder.getBean();
            employeeTreatments.setItems(treatmentMapper
                    .mapToTreatmentDtoList(employeeDBService
                            .showTreatments(employeeMapper
                                    .mapToEmployee(employeeDto))));
            Notification notification = new Notification("Number of treatments: "
                    + employeeDBService.showTreatments(employeeMapper.mapToEmployee(employeeDto)).size() + "\n" +
                    "ID: " + employeeDto.getId());
            notification.open();
    }
}
