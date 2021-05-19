package com.clinic.views.schedules;

import com.clinic.domain.dto.ScheduleDto;
import com.clinic.mapper.ScheduleMapper;
import com.clinic.service.ScheduleDBService;
import com.clinic.views.main.MainView;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.dependency.CssImport;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "timetables", layout = MainView.class)
@PageTitle("TimeTables")
@CssImport("./views/timetables/time-tables-view.css")
public class SchedulesView extends Div {

    private Grid<ScheduleDto> scheduleDtoGrid = new Grid<>(ScheduleDto.class);

    private ScheduleDBService scheduleDBService;
    private ScheduleMapper scheduleMapper;

    public SchedulesView(@Autowired ScheduleDBService scheduleDBService, @Autowired ScheduleMapper scheduleMapper) {
        this.scheduleDBService = scheduleDBService;
        this.scheduleMapper = scheduleMapper;

        scheduleDtoGrid.setColumns("start","end","employee","appointment");

        addClassName("schedules-view");
        add(scheduleDtoGrid);
        update();
    }

    void update() {
        scheduleDtoGrid.setItems(scheduleMapper.mapToScheduleDtoList(scheduleDBService.getAllSchedules()));
    }
}
