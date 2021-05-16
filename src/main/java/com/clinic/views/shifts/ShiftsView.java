package com.clinic.views.shifts;

import com.clinic.domain.Employee;
import com.clinic.domain.Shift;
import com.clinic.domain.dto.EmployeeDto;
import com.clinic.domain.dto.ShiftDto;
import com.clinic.domain.dto.ShiftDto;
import com.clinic.mapper.EmployeeMapper;
import com.clinic.mapper.ShiftMapper;
import com.clinic.service.EmployeeDBService;
import com.clinic.service.ShiftDBService;
import com.clinic.views.main.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Route(value = "schedules", layout = MainView.class)
@PageTitle("Schedules")
@CssImport("./views/schedules/schedules-view.css")
public class ShiftsView extends Div {

    private ShiftDBService shiftDBService;
    private EmployeeDBService employeeDBService;
    private ShiftMapper shiftMapper;
    private EmployeeMapper employeeMapper;

    private Grid<ShiftDto> grid = new Grid<>(ShiftDto.class);
    private Grid<EmployeeDto> employeeGrid = new Grid<>(EmployeeDto.class);
    private TextField filterText = new TextField();

    private DatePicker date = new DatePicker("Date");
    private TextField startHour = new TextField("Start Hour");
    private TextField endHour = new TextField("End Hour");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    
    private Binder<ShiftDto> binder = new Binder<>(ShiftDto.class);
    private Binder<EmployeeDto> employeeBinder = new Binder<>(EmployeeDto.class);

    public ShiftsView(@Autowired ShiftDBService shiftDBService,
                      @Autowired EmployeeDBService employeeDBService,
                      @Autowired ShiftMapper shiftMapper,
                      @Autowired EmployeeMapper employeeMapper) {
        
        this.shiftDBService = shiftDBService;
        this.employeeDBService = employeeDBService;
        this.shiftMapper = shiftMapper;
        this.employeeMapper = employeeMapper;
        addClassName("schedules-view");

        grid.setColumns("date","dayName","employee","startHour","endHour");
        grid.getColumns().get(1).setAutoWidth(true);
        grid.getColumns().get(2).setAutoWidth(true);
        grid.getColumns().get(3).setAutoWidth(true);
        employeeGrid.setColumns("firstName", "lastName");
        employeeGrid.setMinWidth(300,Unit.PIXELS);
        grid.setMinWidth(650, Unit.PIXELS);

        grid.asSingleSelect().addValueChangeListener(event -> {
            binder.setBean(grid.asSingleSelect().getValue());
        });

        employeeGrid.asSingleSelect().addValueChangeListener(event -> {
            employeeBinder.setBean(employeeGrid.asSingleSelect().getValue());
        });

        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());

        binder.forField(date).bind(s -> LocalDate.parse(s.getDate(),DateTimeFormatter.ofPattern("dd MM yyyy")),(t,s) -> t.setDate(s.format(DateTimeFormatter.ofPattern("dd MM yyyy"))));
        binder.forField(startHour).bind(ShiftDto::getStartHour,ShiftDto::setStartHour);
        binder.forField(endHour).bind(ShiftDto::getEndHour,ShiftDto::setEndHour);

        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        VerticalLayout shifts = new VerticalLayout(date,startHour,endHour);
        VerticalLayout form = new VerticalLayout(shifts,buttons);
        HorizontalLayout mainContent = new HorizontalLayout(grid,employeeGrid,form);
        add(filterText,mainContent);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
        updateList();
    }

    public void updateList() {
        grid.setItems(shiftMapper.mapToShiftDtoList(shiftDBService.getAllShifts(filterText.getValue())));
        employeeGrid.setItems(employeeMapper.mapToEmployeeDtoList(employeeDBService.getAllEmployees()));
    }

    private void save() {
        ShiftDto shiftDto = binder.getBean();
        Shift shift;

        Notification emptyEmployeeBean = new Notification("Pick employee to add new shift");
        emptyEmployeeBean.setPosition(Notification.Position.MIDDLE);

        LocalTime sHour = LocalTime.of(Integer.parseInt(startHour.getValue()),0);
        LocalTime eHour = LocalTime.of(Integer.parseInt(endHour.getValue()),0);

        try {
            if(shiftDto==null) {
                shift = new Shift(LocalDateTime.of(date.getValue(),sHour),
                        LocalDateTime.of(date.getValue(),eHour),
                        employeeMapper.mapToEmployee(employeeBinder.getBean()));
            } else {
                shift = shiftDBService.getShift(shiftDto.getId()).get();
                shift.setStart(LocalDateTime.of(date.getValue(),sHour));
                shift.setEnd(LocalDateTime.of(date.getValue(),eHour));
            }
            shiftDBService.saveShift(shift);
        } catch (NullPointerException e) {
            emptyEmployeeBean.open();
        }
        updateList();
    }

    private void delete() {
        ShiftDto shiftDto = binder.getBean();
        shiftDBService.deleteShift(shiftDto.getId());
        updateList();
    }
}
