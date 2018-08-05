package theschulk.com.weatherdemo.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

        permissionCheck();

        currentTempTextView = findViewById(R.id.tv_current_temp);
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

    public void permissionCheck(){
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

    }

    //Make call only if permission granted
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    lastKnownZip = LocationUtils.getZipCode(this, this);
                    new FetchCurrentWeatherTask().execute(lastKnownZip);
                } else {
                    currentTempTextView.setText(R.string.location_error);
                }
                return;
            }
        }
    }
}
