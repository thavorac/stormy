package stormy.android_class.gic.com.stormy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
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
    TextView timezone;
    TextView humidity;
    TextView dewPoint;
    TextView summary;
    TextView mIcon;
    TextView time;
    ImageView iconId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //result = (TextView) findViewById(R.id.result);
        temperature = (TextView) findViewById(R.id.temperature);
        timezone = (TextView) findViewById(R.id.timezone);
        humidity = (TextView) findViewById(R.id.humidity);
        dewPoint = (TextView) findViewById(R.id.dewPoint);
        summary = (TextView) findViewById(R.id.summary);
        time = (TextView) findViewById(R.id.time);
        iconId = (ImageView) findViewById(R.id.iconId);

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
                            int sec = data.getJSONObject("currently").getInt("time");
                            String tnote;
                            if((sec/3600%24+7)%12 >= 12) tnote = "pm";
                            else tnote = "am";
                            temperature.setText("" + (int) Math.round(data.getJSONObject("currently").getDouble("temperature")));
                            timezone.setText("" + data.getString("timezone"));
                            time.setText("At " + ((sec/3600%12+7)%13) + ":" + (sec/60%60)+ tnote + " It will be");
                            humidity.setText("" + data.getJSONObject("currently").getDouble("humidity"));
                            dewPoint.setText("" + data.getJSONObject("currently").getDouble("dewPoint") + "%");
                            summary.setText("" + data.getJSONObject("minutely").getString("summary"));
/*
                            mIcon.setText("" + data.getJSONObject("minutely").getString("icon"));
                            if (mIcon.equals("clear-day")) {
                                iconId = R.drawable.clear_day;
                            }
                            else if (mIcon.equals("clear-night")) {
                                iconId = R.drawable.clear_night;
                            }
                            else if (mIcon.equals("rain")) {
                                iconId = R.drawable.rain;
                            }
                            else if (mIcon.equals("snow")) {
                                iconId = R.drawable.snow;
                            }
                            else if (mIcon.equals("sleet")) {
                                iconId = R.drawable.sleet;
                            }
                            else if (mIcon.equals("wind")) {
                                iconId = R.drawable.wind;
                            }
                            else if (mIcon.equals("fog")) {
                                iconId = R.drawable.fog;
                            }
                            else if (mIcon.equals("cloudy")) {
                                iconId = R.drawable.cloudy;
                            }
                            else if (mIcon.equals("partly-cloudy-day")) {
                                iconId = R.drawable.partly_cloudy;
                            }
                            else if (mIcon.equals("partly-cloudy-night")) {
                                iconId = R.drawable.cloudy_night;
                            }
*/
                        } catch (JSONException e) {

                        }
                    }
                });

            }
        });
    }

    private JSONObject getCurrentDetails(String jsonData) throws JSONException {


        JSONObject jsonObject = new JSONObject(jsonData);

        //JSONObject currently = jsonObject.getJSONObject("currently");


        return jsonObject;

    }


}
