package com.clinic.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Service
public class ScheduledWeatherService {

        private OkHttpClient client;
        private Response response;
        private JSONObject jsonResponse;

        @Scheduled(cron = "0 0 8 * * *")
        public void getResponse() {
        OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                .url("https://community-open-weather-map.p.rapidapi.com/weather?q=Warsaw%2Cpl&lang=null&units=metric")
                .get()
                .addHeader("x-rapidapi-key", "99b08d291fmsh395d42e1367297dp1d71cfjsnf8250f35a6f1")
                .addHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                .build();

                try {
                        Response response = client.newCall(request).execute();
                        this.jsonResponse = new JSONObject(Objects.requireNonNull(response.body()).string());

                } catch (IOException | JSONException e) {
                        e.printStackTrace();
                }

        }

        public String getWeather() throws JSONException {
                String weather = "";
                JSONArray array = jsonResponse.getJSONArray("weather");
                for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        weather = obj.getString("description");
                }
                return weather;
        }


        public JSONObject getMain() throws JSONException {
        return jsonResponse.getJSONObject("main");
        }

        public JSONObject getWind() throws JSONException {
        return jsonResponse.getJSONObject("wind");
        }

}


