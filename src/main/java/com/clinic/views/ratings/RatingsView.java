package com.clinic.views.ratings;

import com.clinic.views.main.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.dependency.CssImport;

@Route(value = "ratings", layout = MainView.class)
@PageTitle("Ratings")
@CssImport("./views/ratings/ratings-view.css")
public class RatingsView extends Div {

    public RatingsView() {
        addClassName("ratings-view");
        add(new Text("Content placeholder"));
    }

}
