package theschulk.com.weatherdemo.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.net.URL;
import java.util.List;

import theschulk.com.weatherdemo.Adapter.FiveDayForecastAdapter;
import theschulk.com.weatherdemo.Model.ForecastModel;
import theschulk.com.weatherdemo.R;
import theschulk.com.weatherdemo.Utility.JSONUtils;
import theschulk.com.weatherdemo.Utility.LocationUtils;
import theschulk.com.weatherdemo.Utility.NetworkUtils;

public class ForecastActivity extends AppCompatActivity {

    RecyclerView.LayoutManager layoutManager;
    FiveDayForecastAdapter fiveDayForecastAdapter;
    RecyclerView forecastRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        fiveDayForecastAdapter = new FiveDayForecastAdapter(this);

        String lastKnownZip = LocationUtils.getZipCode(this, this);
        new FetchForecastWeatherTask().execute(lastKnownZip);

        forecastRecyclerView = findViewById(R.id.rv_daily_forecast);

        layoutManager = new LinearLayoutManager(this);
        forecastRecyclerView.setLayoutManager(layoutManager);
        forecastRecyclerView.setAdapter(fiveDayForecastAdapter);
    }

    public class FetchForecastWeatherTask extends AsyncTask<String, Void, List<ForecastModel>> {

        @Override
        protected List<ForecastModel> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            String zipCode = params[0];
            URL forecastURL = NetworkUtils.
                    buildForecastWeatherURL(ForecastActivity.this, zipCode);

            try {

                String jsonResponse = NetworkUtils.getResponseFromHTTPUrl(forecastURL);
                List<ForecastModel> forecast = JSONUtils.
                        getFiveDayForecast( jsonResponse);
                return forecast;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<ForecastModel> forecast) {
            if(forecast != null){
                fiveDayForecastAdapter.setAdapterData(forecast);
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
