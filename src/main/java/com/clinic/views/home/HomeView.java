package com.clinic.views.home;

import com.clinic.service.ScheduledCovidService;
import com.clinic.service.ScheduledWeatherService;
import com.clinic.views.main.MainView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.dependency.CssImport;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "home", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Home")
@CssImport("./views/home/home-view.css")
public class HomeView extends Div {

    private ScheduledWeatherService scheduledWeatherService;
    private ScheduledCovidService scheduledCovidService;

    private Label weatherTitle;
    private Label weatherDescription;
    private Label maxWeather;
    private Label minWeather;
    private Label humidity;
    private Label pressure;
    private Label wind;
    private Label feelsLike;

    private Label covidTitle;
    private Label confirmed;
    private Label recovered;
    private Label deaths;

    public HomeView(@Autowired ScheduledWeatherService scheduledWeatherService, @Autowired ScheduledCovidService scheduledCovidService) {
        this.scheduledWeatherService = scheduledWeatherService;
        this.scheduledCovidService = scheduledCovidService;

        scheduledWeatherService.getResponse();
        scheduledCovidService.getResponse();

        try {
            weatherDescription = new Label("Description: " + scheduledWeatherService.getWeather());
            minWeather = new Label("MinTemp: " + scheduledWeatherService.getMain().getInt("temp_min") + "°C");
            maxWeather = new Label("MaxTemp: " + scheduledWeatherService.getMain().getInt("temp_max") + "°C");
            pressure = new Label("Pressure: " + scheduledWeatherService.getMain().getInt("pressure") + "HPa");
            humidity = new Label("Humidity: " + scheduledWeatherService.getMain().getInt("humidity") + "%");
            wind = new Label("Wind: " + scheduledWeatherService.getWind().getInt("speed") + " knots");
            feelsLike = new Label("FeelsLike " + scheduledWeatherService.getMain().getDouble("feels_like") + "°C");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            confirmed = new Label("Confirmed: " + scheduledCovidService.getValue("confirmed"));
            recovered = new Label("Recovered: " + scheduledCovidService.getValue("recovered"));
            deaths = new Label("Deaths: " + scheduledCovidService.getValue("deaths"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        weatherTitle = new Label("Weather in Warsaw");
        covidTitle = new Label("Covid statistics in Poland");

        VerticalLayout weatherLayout = new VerticalLayout(weatherTitle,weatherDescription,minWeather,maxWeather,pressure,humidity,wind,feelsLike);
        VerticalLayout covidLayout = new VerticalLayout(covidTitle,confirmed,recovered,deaths);
        HorizontalLayout mainContent = new HorizontalLayout(weatherLayout,covidLayout);
        addClassName("home-view");
        add(mainContent);
    }

}
