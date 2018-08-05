package theschulk.com.weatherdemo.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.net.URL;

import theschulk.com.weatherdemo.R;
import theschulk.com.weatherdemo.Utility.JSONUtils;
import theschulk.com.weatherdemo.Utility.LocationUtils;
import theschulk.com.weatherdemo.Utility.NetworkUtils;

public class CurrentWeatherActivity extends AppCompatActivity {

    TextView currentTempTextView;
    TextView weatherTitle;

    String lastKnownZip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_weather);

        lastKnownZip = LocationUtils.getZipCode(this, this);

        //error check if zip code empty
        if(lastKnownZip.equals(getString(R.string.location_error)) || lastKnownZip == ""){
            currentTempTextView.setText(R.string.location_error);
        } else {
            new FetchCurrentWeatherTask().execute(lastKnownZip);
        }

    }

    public class FetchCurrentWeatherTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String zipCode = params[0];
            URL currentWeatherURL = NetworkUtils.
                    buildCurrentWeatherURL(CurrentWeatherActivity.this, zipCode);

            try {

                String jsonResponse = NetworkUtils.getResponseFromHTTPUrl(currentWeatherURL);
                String currentTemp = JSONUtils.
                        getCurrentWeatherTemp(jsonResponse);
                return currentTemp;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String temp) {
            if(temp != null){
                currentTempTextView = findViewById(R.id.tv_current_temp);
                weatherTitle = findViewById(R.id.tv_current_weather_title);

                String titleString = getString(R.string.current_weather_title) +
                        "(" + lastKnownZip + ")";
                String tempString = temp + (char) 0x00B0 + getString(R.string.fahrenheit);

                currentTempTextView.setText(tempString);
                weatherTitle.setText(titleString);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch(item.getItemId()){
            case R.id.menu_current:
                intent = new Intent(this, CurrentWeatherActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_forecast:
                intent = new Intent(this, ForecastActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
