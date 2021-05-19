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
public class ScheduledCovidService {

    private OkHttpClient client;
    private JSONArray jsonResponse;

    @Scheduled(cron = "0 0 8 * * *")
    public void getResponse() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://covid-19-data.p.rapidapi.com/country?name=Poland")
                .get()
                .addHeader("x-rapidapi-key", "99b08d291fmsh395d42e1367297dp1d71cfjsnf8250f35a6f1")
                .addHeader("x-rapidapi-host", "covid-19-data.p.rapidapi.com")
                .build();

        try {
            Response response = client.newCall(request).execute();
            this.jsonResponse = new JSONArray(Objects.requireNonNull(response.body()).string());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public int getValue(String value) throws JSONException {
        int restult = 0;
        for (int i = 0; i < jsonResponse.length(); i++) {
            JSONObject obj = jsonResponse.getJSONObject(i);
            restult = obj.getInt(value);
        }
        return restult;
    }
}

