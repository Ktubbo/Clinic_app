package com.clinic.views.appointments;

import com.clinic.views.main.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.dependency.CssImport;

@Route(value = "appointments", layout = MainView.class)
@PageTitle("Appointments")
@CssImport("./views/appointments/appointments-view.css")
public class AppointmentsView extends Div {

    public AppointmentsView() {
        addClassName("appointments-view");
        add(new Text("Content placeholder"));
    }

}
