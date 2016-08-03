package stormy.android_class.gic.com.stormy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView result;
    JSONObject data;
    TextView temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //result = (TextView) findViewById(R.id.result);
        temperature = (TextView) findViewById(R.id.temperature);

        //safasfdkafjlas
        //asfalskfdjlakjfla

        final double latitude = 37.8267;
        final double longitude = -122.423;

        getForecast(latitude, longitude);
    }

    private void getForecast(double latitude, double longitude) {
        //https://api.forecast.io/forecast/ed4055eb786aa951aa801892630fa90a/37.8267,-122.423
        //https://api.forecast.io/forecast/ed4055eb786aa951aa801892630fa90a/37.8267,-122.423
        String apiKey = "ed4055eb786aa951aa801892630fa90a";
        String forecastUrl = "https://api.forecast.io/forecast/" + apiKey + "/" + latitude + "," + longitude;

        Log.e("Stormy", forecastUrl);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(forecastUrl).build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.setText("Error!");
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    data = getCurrentDetails(response.body().string());
                } catch (JSONException e) {

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            temperature.setText("" + (int) Math.round(data.getDouble("temperature")));
                        } catch (JSONException e) {

                        }
                    }
                });

            }
        });
    }

    private JSONObject getCurrentDetails(String jsonData) throws JSONException {


        JSONObject jsonObject = new JSONObject(jsonData);

        JSONObject currently = jsonObject.getJSONObject("currently");


        return currently;

    }
}
