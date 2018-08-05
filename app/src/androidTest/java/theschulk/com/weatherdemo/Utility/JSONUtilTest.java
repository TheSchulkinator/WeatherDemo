package theschulk.com.weatherdemo.Utility;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import theschulk.com.weatherdemo.Utility.JSONUtils;

import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class JSONUtilTest {
    @Test
    public void getCurrentWeatherTemp() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        String jsonResponse = "{\"coord\":{\"lon\":-83.54,\"lat\":42.52},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"base\":\"stations\",\"main\":{\"temp\":90.59,\"pressure\":1019,\"humidity\":34,\"temp_min\":87.8,\"temp_max\":93.2},\"visibility\":16093,\"wind\":{\"speed\":11.41,\"deg\":190,\"gust\":7.7},\"clouds\":{\"all\":40},\"dt\":1533496560,\"sys\":{\"type\":1,\"id\":1460,\"message\":0.0043,\"country\":\"US\",\"sunrise\":1533465078,\"sunset\":1533516486},\"id\":420016302,\"name\":\"Ann Arbor\",\"cod\":200}";

        String expectedTemp = "90.59";

        String actualTemp = JSONUtils.getCurrentWeatherTemp(appContext, jsonResponse);

        assertEquals(expectedTemp, actualTemp);

    }
}
