package com.clinic.views.timetables;

import com.clinic.views.main.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.dependency.CssImport;

@Route(value = "timetables", layout = MainView.class)
@PageTitle("TimeTables")
@CssImport("./views/timetables/time-tables-view.css")
public class TimeTablesView extends Div {

    public TimeTablesView() {
        addClassName("time-tables-view");
        add(new Text("Content placeholder"));
    }

}
