package com.clinic.views.treatments;

import com.clinic.domain.Treatment;
import com.clinic.domain.Treatment;
import com.clinic.domain.dto.DurationDto;
import com.clinic.domain.dto.TreatmentDto;
import com.clinic.mapper.TreatmentMapper;
import com.clinic.service.TreatmentDBService;
import com.clinic.views.main.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.dependency.CssImport;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;

@Route(value = "treatments", layout = MainView.class)
@PageTitle("Treatments")
@CssImport("./views/treatments/treatments-view.css")
public class TreatmentsView extends Div {

    private TreatmentDBService treatmentDBService;
    private TreatmentMapper treatmentMapper;
    private Grid<TreatmentDto> grid = new Grid<>(TreatmentDto.class);
    private TextField filterText = new TextField();

    private TextField id = new TextField("ID");
    private TextField name = new TextField("Name");
    private TextField price = new TextField("Price");
    private TextField hours = new TextField("Hours");
    private TextField minutes = new TextField("Minutes");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private Binder<TreatmentDto> binder = new Binder<>(TreatmentDto.class);

    public TreatmentsView(@Autowired TreatmentDBService treatmentDBService, @Autowired TreatmentMapper treatmentMapper) {
        this.treatmentMapper = treatmentMapper;
        this.treatmentDBService = treatmentDBService;
        addClassName("treatments-view");
        updateList();

        grid.setColumns("name","price","duration");
        grid.asSingleSelect().addValueChangeListener(event ->
                binder.setBean(grid.asSingleSelect().getValue()));

        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());

        binder.forField(id).bind(t-> String.valueOf(t.getId()),(t,s) -> t.setId(Long.parseLong(s)));
        binder.forField(name).bind(TreatmentDto::getName,TreatmentDto::setName);
        binder.forField(price).bind(t -> t.getPrice().toPlainString(),(t,s) -> t.setPrice(BigDecimal.valueOf(Integer.parseInt(s))));
        binder.forField(hours).bind(t -> t.getDuration().getHours(),(t,s) -> t.getDuration().setHours(s));
        binder.forField(minutes).bind(t -> t.getDuration().getMinutes(),(t,s) -> t.getDuration().setMinutes(s));

        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        HorizontalLayout duration = new HorizontalLayout(hours,minutes);
        FormLayout form = new FormLayout(name,price,duration,buttons);
        HorizontalLayout mainContent = new HorizontalLayout(grid,form);
        add(filterText,mainContent);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
    }

    public void updateList() {
        grid.setItems(treatmentMapper.mapToTreatmentDtoList(treatmentDBService.getAllTreatments(filterText.getValue())));
    }

    private void save() {
        TreatmentDto treatmentDto = binder.getBean();
        Treatment treatment = treatmentDto==null ? new Treatment(name.getValue(),new BigDecimal(price.getValue()),
                Duration.ofHours(Long.parseLong(hours.getValue())).plusMinutes(Long.parseLong(minutes.getValue()))) :
                treatmentDBService.getTreatment(treatmentDto.getId()).get();
        treatmentDBService.saveTreatment(treatment);
        updateList();
    }

    private void delete() {
        TreatmentDto treatmentDto = binder.getBean();
        treatmentDBService.deleteTreatment(treatmentDto.getId());
        updateList();
    }
}
