package com.jaouani.weather.utilities;

import android.content.Context;
import android.os.Bundle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpURLConnection;

/**
 * Created by M-A J on 13/12/2017.
 */

public class OpenWeatherJsonUtils {

    public static final String WEATHER_ID = "id";
    public static final String DATE = "date";
    public static final String DESCRIPTION = "description";
    public static final String HIGH_LOW = "high_low";
    public static final String HUMIDITY = "humidity";
    public static final String PRESSURE = "pressure";
    public static final String WIND = "wind";

    /**
     * Parse the String in JSON format and extract wanted information
     * @param context Android context
     * @param forecastJsonStr String response in JSON format
     * @return a bundle of the needed information
     * @throws JSONException
     */
    public static Bundle[] getSimpleWeatherStringsFromJson(Context context, String forecastJsonStr)
            throws JSONException {

        final String OWM_LIST = "list";

        final String OWM_TEMPERATURE = "temp";
        final String OWM_PRESSURE = "pressure";
        final String OWM_HUMIDITY = "humidity";
        final String OWM_WINDSPEED = "speed";
        final String OWM_WIND_DIRECTION = "deg";

        final String OWM_MAX = "max";
        final String OWM_MIN = "min";

        final String OWM_WEATHER = "weather";
        final String OWM_MESSAGE_CODE = "cod";

        JSONObject forecastJson = new JSONObject(forecastJsonStr);

        if (forecastJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = forecastJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

        JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);

        long localDate = System.currentTimeMillis();
        long utcDate = WeatherDateUtils.getUTCDateFromLocal(localDate);
        long startDay = WeatherDateUtils.normalizeDate(utcDate);

        Bundle[] weatherContentValues = new Bundle[weatherArray.length()];

        for (int i = 0; i < weatherArray.length(); i++) {
            String date;
            String highAndLow;

            /* These are the values that will be collected */
            long dateTimeMillis;
            double pressure;
            int humidity;
            double windSpeed;
            double windDirection;
            String wind;
            double high;
            double low;
            int weatherId;
            String description;

            /* Get the JSON object representing the day */
            JSONObject dayForecast = weatherArray.getJSONObject(i);

            dateTimeMillis = startDay + WeatherDateUtils.DAY_IN_MILLIS * i;
            date = WeatherDateUtils.getFriendlyDateString(context, dateTimeMillis, false);
            pressure = dayForecast.getDouble(OWM_PRESSURE);
            humidity = dayForecast.getInt(OWM_HUMIDITY);
            windSpeed = dayForecast.getDouble(OWM_WINDSPEED);
            windDirection = dayForecast.getDouble(OWM_WIND_DIRECTION);
            wind = WeatherUtils.getFormattedWind(context, windSpeed, windDirection);

            JSONObject weatherObject =
                    dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
            weatherId = weatherObject.getInt(WEATHER_ID);
            description = WeatherUtils.getStringForWeatherCondition(context, weatherId);

            JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
            high = temperatureObject.getDouble(OWM_MAX);
            low = temperatureObject.getDouble(OWM_MIN);
            highAndLow = WeatherUtils.formatHighLows(context, high, low);

            Bundle weatherValues = new Bundle();
            weatherValues.putString(DATE, date);
            weatherValues.putString(DESCRIPTION, description);
            weatherValues.putString(HUMIDITY, humidity+"%");
            weatherValues.putString(PRESSURE, pressure+" hPa");
            weatherValues.putString(WIND, wind);
            weatherValues.putString(HIGH_LOW, highAndLow);
            weatherValues.putInt(WEATHER_ID, weatherId);

            weatherContentValues[i] = weatherValues;
        }
        return weatherContentValues;
    }
}
