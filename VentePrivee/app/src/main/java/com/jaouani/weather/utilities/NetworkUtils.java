package com.jaouani.weather.utilities;

import android.net.Uri;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by M-A J on 13/12/2017.
 */

public class NetworkUtils {

    private static final String WEATHER_URL =
            "http://api.openweathermap.org/data/2.5/forecast/daily?q=Paris&units=metric&cnt=5&appid=8194ea842a9aef80a798c8ac0c320ec4";

    /**
     * Construct the URL base on WEATHER_URL String
     * @return The URL object of the WEATHER_URL String
     */
    public static URL buildUrl() {
        Uri builtUri = Uri.parse(WEATHER_URL).buildUpon()
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Use of OKHTTP to send te API request and get the JSON response
     * @return API's response in JSON format
     */
    public static String getResponseFromHttpUrl() throws IOException {
        URL url = buildUrl();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
