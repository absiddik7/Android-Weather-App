package com.app.weather;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    
    static RecyclerView wList;
    public ArrayList<WeatherData> weatherList;
    private WeatherAdapter adapter;
    TextView place;
    TextView temp;
    EditText searchPlace;
    Button searchBtn;
    String location;
    TextView humidity_label;
    TextView wind_label;
    TextView pressure_label;
    TextView notify_txt;
    TextView today_label;
    TextView forecast_label;
    TextView humidity;
    TextView wind;
    TextView pressure;
    TextView description;
    ImageView weatherIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        place = findViewById(R.id.place);
        temp = findViewById(R.id.temp);
        searchPlace = findViewById(R.id.scr_editTxt);
        searchBtn = findViewById(R.id.src_btn);
        wList = findViewById(R.id.m_temp);

        humidity_label = findViewById(R.id.humidity_label);
        wind_label = findViewById(R.id.wind_label);
        pressure_label = findViewById(R.id.pressure_label);
        notify_txt = findViewById(R.id.notify_txt);
        today_label = findViewById(R.id.today_label);
        forecast_label = findViewById(R.id.forecast_label);
        humidity = findViewById(R.id.humidity);
        wind = findViewById(R.id.wind);
        pressure = findViewById(R.id.pressure);
        description = findViewById(R.id.description);
        weatherIcon = findViewById(R.id.weather_icon);

        weatherList = new ArrayList<>();
        weatherList.clone();
        adapter = new WeatherAdapter(this, weatherList);
        wList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        wList.setAdapter(adapter);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                WeatherForecast getData = new WeatherForecast();
                CurrentWeather getCData = new CurrentWeather();
                location = searchPlace.getText().toString();
                try {
                    String apiUrlCurrent = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&appid=b4329a46471da371c896e2e64afc3596",location);
                    getCData.execute(apiUrlCurrent);
                    String apiUrlForecast = String.format("https://api.openweathermap.org/data/2.5/forecast?q=%s&units=metric&appid=8a4a4562e285d51f8df30386e407fcef", location);
                    getData.execute(apiUrlForecast);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                searchPlace.setText("");
            }
        });
    }

    public class CurrentWeather extends AsyncTask<String, Void, String> {
        String result;
        @Override
        protected String doInBackground(String... urls) {
            result = "";
            URL link;
            HttpURLConnection myConnection = null;
            try {
                link = new URL(urls[0]);
                myConnection = (HttpURLConnection) link.openConnection();
                InputStream in = myConnection.getInputStream();
                InputStreamReader myStreamReader = new InputStreamReader(in);
                int data = myStreamReader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = myStreamReader.read();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject myObject = new JSONObject(result);
                JSONObject main = new JSONObject(myObject.getString("main"));
                JSONObject windOb = new JSONObject(myObject.getString("wind"));
                String todayTemp= main.getString("temp");
                String location= myObject.getString("name");
                String todayHumidity = main.getString("humidity");
                String todayPressure = main.getString("pressure");
                String todayWind = windOb.getString("speed");

                JSONObject c = myObject.getJSONArray("weather").getJSONObject(0);
                String currentWeather = c.getString("description");
                String icon = c.getString("icon");
                String iconUrl = "https://openweathermap.org/img/w/" + icon + ".png";

                humidity_label.setVisibility(View.VISIBLE);
                wind_label.setVisibility(View.VISIBLE);
                pressure_label.setVisibility(View.VISIBLE);
                notify_txt.setVisibility(View.INVISIBLE);
                today_label.setVisibility(View.VISIBLE);
                forecast_label.setVisibility(View.VISIBLE);
                weatherIcon.setVisibility(View.VISIBLE);

                temp.setText((todayTemp) + " \u2103");
                place.setText(location);
                description.setText(currentWeather);
                humidity.setText((todayHumidity) + "%");
                wind.setText((todayWind) + " km/h");
                pressure.setText((todayPressure) + " mbar");
                Glide.with(MainActivity.this)
                        .load(iconUrl)
                        .into(weatherIcon);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class WeatherForecast extends AsyncTask<String, Void, String> {
        String result;

        @Override
        protected String doInBackground(String... urls) {
            result = "";
            URL link;
            HttpURLConnection myConnection = null;
            try {
                link = new URL(urls[0]);
                myConnection = (HttpURLConnection) link.openConnection();
                InputStream in = myConnection.getInputStream();
                InputStreamReader myStreamReader = new InputStreamReader(in);
                int data = myStreamReader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = myStreamReader.read();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject myObject = new JSONObject(result);
                JSONArray list = myObject.getJSONArray("list");
                weatherList.clear();
                for (int i = 0; i < 8; i++) {
                    JSONObject objects = list.getJSONObject(i);
                    JSONObject main = new JSONObject(objects.getString("main"));
                    String date = objects.getString("dt");
                    String temp = main.getString("temp");

                    JSONObject w = objects.getJSONArray("weather").getJSONObject(0);
                    String icons = w.getString("icon");
                    String imgUrl = "https://openweathermap.org/img/w/" + icons + ".png";

                    weatherList.add(new WeatherData(date, temp, imgUrl));
                }
                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}