package theschulk.com.weatherdemo.Utility;


import android.content.Context;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import theschulk.com.weatherdemo.BuildConfig;
import theschulk.com.weatherdemo.R;

public class NetworkUtils {

    /**
     * Build a Url for the current weather with the current zip code passed in
     * @param context
     * @param zipCode
     * @return
     */
    public static URL buildCurrentWeatherURL(Context context, String zipCode){

        String postfixZipCode = zipCode + context.getString(R.string.zip_postfix);

        //Build URI with non translatable strings
        Uri builtUri = Uri.parse(context.getString(R.string.base_url)).buildUpon().
                appendEncodedPath(context.getString(R.string.current_path)).
                appendQueryParameter(context.getString(R.string.zip_key), postfixZipCode).
                appendQueryParameter(context.getString(R.string.mode_key), context.getString(R.string.mode_value)).
                appendQueryParameter(context.getString(R.string.units_key), context.getString(R.string.units_value)).
                appendQueryParameter(context.getString(R.string.appid_key), BuildConfig.openWeatherApiKey).
                build();

        //parse URI to URL
        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;

    }

    /**
     * Build a Url for a weather forecast with the current zip code passed in
     * @param context
     * @param zipCode
     * @return
     */
    public static URL buildForecastWeatherURL(Context context, String zipCode){

        String postfixZipCode = zipCode + context.getString(R.string.zip_postfix);

        //Build URI with non translatable strings
        Uri builtUri = Uri.parse(context.getString(R.string.base_url)).buildUpon().
                appendEncodedPath(context.getString(R.string.forecast_path)).
                appendEncodedPath(context.getString(R.string.forecast_daily_path)).
                appendQueryParameter(context.getString(R.string.zip_key), postfixZipCode).
                appendQueryParameter(context.getString(R.string.mode_key), context.getString(R.string.mode_value)).
                appendQueryParameter(context.getString(R.string.units_key), context.getString(R.string.units_value)).
                appendQueryParameter(context.getString(R.string.day_count_key), context.getString(R.string.day_count_value)).
                appendQueryParameter(context.getString(R.string.appid_key), BuildConfig.openWeatherApiKey).
                build();

        //parse URI to URL
        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;

    }


    /**
     * HTTPConnection method that will be called in an ASYNC task to fetch data from the API
     * @param url
     * @return
     * @throws IOException
     */
    public static String getResponseFromHTTPUrl (URL url) throws IOException {

        HttpURLConnection weatherUrlConnection = (HttpURLConnection) url.openConnection();

        try{

            InputStream weatherInputStream = weatherUrlConnection.getInputStream();

            Scanner weatherScanner = new Scanner(weatherInputStream);
            weatherScanner.useDelimiter("\\A");

            boolean hasInput = weatherScanner.hasNext();
            if (hasInput){
                return weatherScanner.next();
            } else {
                return null;
            }
        } finally {
            weatherUrlConnection.disconnect();
        }

    }

}
