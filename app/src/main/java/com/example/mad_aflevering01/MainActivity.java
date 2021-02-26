package com.example.mad_aflevering01;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MyRecyclerViewAdapter adapter;
    String s1[], s2[],s3[],s4[];
    int images[] = {R.drawable.dk, R.drawable.fi, R.drawable.us, R.drawable.au, R.drawable.na, R.drawable.sg, R.drawable.ru, R.drawable.ae, R.drawable.fo, R.drawable.us, R.drawable.fj, R.drawable.jp};
    RecyclerView recyclerView;
    List<WeatherModel> weatherDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        s1 = getResources().getStringArray(R.array.City);
        s2 = getResources().getStringArray(R.array.Temp);
        s3 = getResources().getStringArray(R.array.Weather);
        s4 = getResources().getStringArray(R.array.Humidity);

        GetWeatherData();



        Toast.makeText(this,weatherDataList.get(0).getCity(), Toast.LENGTH_SHORT).show();
        recyclerView = findViewById(R.id.view);
        adapter = new MyRecyclerViewAdapter(this, weatherDataList, images);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button exitBtn = (Button) findViewById(R.id.button);
    }

    private void GetWeatherData() {
        InputStream is = getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, StandardCharsets.UTF_8)
        );

        weatherDataList = new ArrayList<>();
        try {
            //line is read twice to remove header
            String line = reader.readLine();
            line = reader.readLine();

            while (line != null) {
                String[] tokens = line.split(",");
                WeatherModel weatherData = WeatherModelMapper(tokens);
                weatherDataList.add(weatherData);

                line = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private WeatherModel WeatherModelMapper(String[] tokens) {
        WeatherModel data = new WeatherModel();

        data.setCity(tokens[0]);
        data.setCountry(tokens[1]);
        data.setTemp(Double.parseDouble(tokens[2]));
        data.setHumidity(Double.parseDouble(tokens[3]));
        data.setWeather(tokens[4]);

        return  data;
    }
}