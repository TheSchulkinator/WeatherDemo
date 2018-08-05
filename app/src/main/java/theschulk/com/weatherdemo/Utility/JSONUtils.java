package theschulk.com.weatherdemo.Utility;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import theschulk.com.weatherdemo.Model.ForecastModel;

public class JSONUtils {

    /**
     * Get current Temperature out of returned string
     * @param JsonString
     * @return
     */
    public static String getCurrentWeatherTemp(String JsonString){
        //values to be parsed out of JSON
        final String MAIN = "main";
        final String TEMP = "temp";

        //actual value of temp
        String currentTemp = "";

        //create JSON to get current Temp
        JSONObject mainObject = null;
        try {
            JSONObject json = new JSONObject(JsonString);
            mainObject = json.getJSONObject(MAIN);
            currentTemp = mainObject.getString(TEMP);
        } catch (JSONException e){
            Log.e("JSONUtils", e.toString());
        }

        return currentTemp;
    }

    /**
     * Get five day forecast from JSON api call
     * @param JsonString
     * @return
     */
    public static List<ForecastModel> getFiveDayForecast(String JsonString){
        final String LIST = "list";
        final String TEMP = "temp";
        final String WEATHER = "weather";
        final String UNIX_DATE = "dt";
        final String LOW_TEMP = "min";
        final String MAX_TEMP = "max";
        final String ICON = "icon";

        List<ForecastModel> fiveDayForecast = new ArrayList<>();

        //Get Json List Object
        try {
            JSONObject json = new JSONObject(JsonString);
            JSONArray listJSONArray = json.getJSONArray(LIST);

            for (int i = 0; i <= listJSONArray.length(); i++){

                ForecastModel forecast = new ForecastModel();
                JSONObject currentJson = listJSONArray.getJSONObject(i);
                JSONObject mainJson = currentJson.getJSONObject(TEMP);
                JSONArray weatherJSONArray = currentJson.getJSONArray(WEATHER);
                JSONObject weatherJSONObject = weatherJSONArray.getJSONObject(0);

                forecast.setForecastDate(currentJson.getString(UNIX_DATE));
                forecast.setHighTemp(mainJson.getString(MAX_TEMP));
                forecast.setLowTemp(mainJson.getString(LOW_TEMP));
                forecast.setWeatherIcon(weatherJSONObject.getString(ICON));

                fiveDayForecast.add(forecast);

            }

        } catch (JSONException e){
            Log.e("JSONUtils", e.toString());
        }

        return fiveDayForecast;
    }
}
