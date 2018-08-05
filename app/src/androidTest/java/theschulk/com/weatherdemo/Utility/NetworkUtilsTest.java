package theschulk.com.weatherdemo.Utility;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;


import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URL;

import theschulk.com.weatherdemo.test.BuildConfig;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class NetworkUtilsTest {

    Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void buildCurrentWeatherURL() {

        String expected = "http://api.openweathermap.org/data/2.5/weather?zip=48393%2Cus&mode=JSON&units=imperial&appid="
                + BuildConfig.openWeatherApiKey;

        URL actualURL = NetworkUtils.buildCurrentWeatherURL(appContext, "48393");
        String actual = actualURL.toString();

        assertEquals(expected, actual);

    }

    @Test
    public void buildForecastWeatherURL() {

        String expected = "http://api.openweathermap.org/data/2.5/forecast/daily?zip=48393%2Cus&mode=JSON&units=imperial&cnt=5&appid="
                + BuildConfig.openWeatherApiKey;

        URL actualURL = NetworkUtils.buildForecastWeatherURL(appContext, "48393");
        String actual = actualURL.toString();

        assertEquals(expected, actual);
    }
}