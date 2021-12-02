package com.app.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    static TextView place;
    static RecyclerView wList;
    public ArrayList<WeatherData> weatherList;
    private WeatherAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        place = findViewById(R.id.place);
        wList = findViewById(R.id.m_temp);

        weatherList = new ArrayList<>();
        adapter = new WeatherAdapter(this,weatherList);
        wList.setLayoutManager(new LinearLayoutManager(this));
        wList.setAdapter(adapter);


        Weathers getData = new Weathers();
        //getData.execute("https://api.openweathermap.org/data/2.5/forecast?q=London&units=metric&appid=b4329a46471da371c896e2e64afc3596");
        getData.execute("https://api.openweathermap.org/data/2.5/forecast?q=Dhaka&appid=8a4a4562e285d51f8df30386e407fcef");

    }

    private class Weathers extends AsyncTask<String,Void,String> {
        String result;
        @Override
        protected String doInBackground(String... urls) {
            result ="";
            URL link;
            HttpURLConnection myConnection = null;
            try {
                link = new URL(urls[0]);
                myConnection = (HttpURLConnection)link.openConnection();
                InputStream in = myConnection.getInputStream();
                InputStreamReader myStreamReader = new InputStreamReader(in);
                int data = myStreamReader.read();
                while (data!=-1){
                    char current = (char)data;
                    result+=current;
                    data = myStreamReader.read();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);

            try{
                JSONObject myObject = new JSONObject(result);
                JSONArray list = myObject.getJSONArray("list");
                JSONObject city= new JSONObject(myObject.getString("city"));
                String location = city.getString("name");


                for (int i = 0; i < list.length(); i++) {
                    JSONObject objects = list.getJSONObject(i);
                    JSONObject main = new JSONObject(objects.getString("main"));
                    String date = objects.getString("dt");
                    String temp = main.getString("temp");
                    weatherList.add(new WeatherData(date,temp));
                }
                adapter.notifyDataSetChanged();

                MainActivity.place.setText(location);
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
}