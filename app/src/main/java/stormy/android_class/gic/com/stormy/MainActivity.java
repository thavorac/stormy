package stormy.android_class.gic.com.stormy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


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
    TextView time;
    ImageView icon;
    String icon_string;
    int iconId;

    ImageView refresh;
    ProgressBar loading;

    ///adfafdjafkaskfdjaksdfhk
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
        icon = (ImageView) findViewById(R.id.iconId);
        refresh = (ImageView) findViewById(R.id.refresh);
        loading = (ProgressBar) findViewById(R.id.loading);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                refresh.setVisibility(View.INVISIBLE);
                loading.setVisibility(View.VISIBLE);
                //Toast.makeText(MainActivity.this,"refresh is clicked!",Toast.LENGTH_SHORT).show();
                final double latitude = 48.867420;
                final double longitude = 2.345839;
                getForecast(latitude, longitude);
            }
        });

        //safasfdkafjlas
        //asfalskfdjlakjfla

        final double latitude = 11.501963;
        final double longitude = 104.872325;

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

                            icon_string = data.getJSONObject("currently").getString("icon");
                            //mIcon.setText("" + data.getJSONObject("minutely").getString("icon"));
                            /*if (icon_string.equals("clear-day")) {
                                iconId = R.mipmap.clear_day;
                            }
                            else if (icon_string.equals("clear-night")) {
                                iconId = R.mipmap.clear_night;
                            }
                            else if (icon_string.equals("rain")) {
                                iconId = R.mipmap.rain;
                            }
                            else if (icon_string.equals("snow")) {
                                iconId = R.mipmap.snow;
                            }
                            else if (icon_string.equals("sleet")) {
                                iconId = R.mipmap.sleet;
                            }
                            else if (icon_string.equals("wind")) {
                                iconId = R.mipmap.wind;
                            }
                            else if (icon_string.equals("fog")) {
                                iconId = R.mipmap.fog;
                            }
                            else if (icon_string.equals("cloudy")) {
                                iconId = R.mipmap.cloudy;
                            }
                            else if (icon_string.equals("partly-cloudy-day")) {
                                iconId = R.mipmap.partly_cloudy;
                            }
                            else if (icon_string.equals("partly-cloudy-night")) {
                                iconId = R.mipmap.cloudy_night;
                            }*/

                            Map<String, Integer> iconIdArray = new HashMap<String, Integer>();
                            iconIdArray.put("clear-day", R.mipmap.clear_day);
                            iconIdArray.put("clear-night", R.mipmap.clear_night);
                            iconIdArray.put("rain", R.mipmap.rain);
                            iconIdArray.put("snow", R.mipmap.snow);
                            iconIdArray.put("sleet", R.mipmap.sleet);
                            iconIdArray.put("wind", R.mipmap.wind);
                            iconIdArray.put("fog", R.mipmap.fog);
                            iconIdArray.put("cloudy", R.mipmap.cloudy);
                            iconIdArray.put("partly-cloudy-day", R.mipmap.partly_cloudy);
                            iconIdArray.put("partly-cloudy-night", R.mipmap.cloudy_night);


                            icon.setImageResource(iconIdArray.get(icon_string));



                        } catch (JSONException e) {

                        }

                        refresh.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.INVISIBLE);
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
