package com.clinic.views.appointments;

import com.clinic.domain.dto.AppointmentDto;
import com.clinic.mapper.AppointmentMapper;
import com.clinic.service.AppointmentDBService;
import com.clinic.views.main.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "appointments", layout = MainView.class)
@PageTitle("Appointment View")
public class AppointmentView extends Div {

    private Grid<AppointmentDto> appointmentDtoGrid = new Grid<>(AppointmentDto.class);
    private TextField filterText = new TextField();

    private AppointmentDBService appointmentDBService;
    private AppointmentMapper appointmentMapper;

    public AppointmentView(@Autowired AppointmentDBService appointmentDBService, @Autowired AppointmentMapper appointmentMapper) {
        this.appointmentDBService = appointmentDBService;
        this.appointmentMapper = appointmentMapper;

        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> update());

        appointmentDtoGrid.setColumns("customer","employee","treatment","start","price");

        addClassName("appointments-view");
        add(filterText,appointmentDtoGrid);
        update();
    }

    void update() {
        appointmentDtoGrid.setItems(appointmentMapper.mapToAppointmentDtoList(appointmentDBService.getAllAppointments(filterText.getValue())));
    }
}
