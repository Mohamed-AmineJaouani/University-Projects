package com.jaouani.weather;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaouani.weather.utilities.OpenWeatherJsonUtils;
import com.jaouani.weather.utilities.WeatherUtils;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ImageView weatherIcon = (ImageView) findViewById(R.id.weather_icon_detail);
        TextView mDateView = (TextView) findViewById(R.id.tv_date);
        TextView mDescriptionView = (TextView) findViewById(R.id.tv_description);
        TextView mHighLowTemperatureView = (TextView) findViewById(R.id.tv_high_low_temperature);
        TextView mHumidityView = (TextView) findViewById(R.id.tv_humidity);
        TextView mWindView = (TextView) findViewById(R.id.tv_wind);
        TextView mPressureView = (TextView) findViewById(R.id.tv_pressure);

        Intent intent = getIntent();

        if(intent.hasExtra("weather")) {
            Bundle contentValues = intent.getBundleExtra("weather");

            int weatherId = contentValues.getInt(OpenWeatherJsonUtils.WEATHER_ID);
            mDateView.setText(contentValues.getString(OpenWeatherJsonUtils.DATE));
            mDescriptionView.setText(contentValues.getString(OpenWeatherJsonUtils.DESCRIPTION));
            mHighLowTemperatureView.setText(contentValues.getString(OpenWeatherJsonUtils.HIGH_LOW));
            mHumidityView.append(" "+contentValues.getString(OpenWeatherJsonUtils.HUMIDITY));
            mWindView.append(" "+contentValues.getString(OpenWeatherJsonUtils.WIND));
            mPressureView.append(" "+contentValues.getString(OpenWeatherJsonUtils.PRESSURE));

            String uriString = WeatherUtils.getIconResourceForWeatherCondition(weatherId);
            Uri uri = Uri.parse(uriString);
            Picasso.with(this).load(uri).resize(500,500).centerCrop().into(weatherIcon);
        }
    }
}
