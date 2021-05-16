package com.clinic.views.appointments;

import com.clinic.views.main.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;

@Route(value = "appointments", layout = MainView.class)
@PageTitle("Appointment View")
public class AppointmentView extends Div {

    public AppointmentView() {
        addClassName("appointments-view");
        add(new Text("Content placeholder"));
    }

}
