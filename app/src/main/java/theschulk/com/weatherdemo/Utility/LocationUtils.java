package theschulk.com.weatherdemo.Utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import theschulk.com.weatherdemo.R;

public class LocationUtils {
    /**
     * Method to determine the last know location of the device using network and gps
     * Returns error message if phone has no last know location
     * @param context
     * @param activity
     * @return String
     */
    public static String getZipCode(Context context, Activity activity) {

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        String gpsLocationProvider = LocationManager.GPS_PROVIDER;
        String netWorkLocationProvider = LocationManager.NETWORK_PROVIDER;

        String lastLocation = "";

        //check for permission in SDK 23 and higher if not given ask for permission
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION},1 );
        } else {

            Location lastKnownLocation = locationManager.getLastKnownLocation(netWorkLocationProvider);

            //check gps if network could not find a last location
            //if no location is found return error string
            if(lastKnownLocation == null){
                lastKnownLocation = locationManager.getLastKnownLocation(gpsLocationProvider);
            } else if (lastKnownLocation == null){
                return context.getString(R.string.location_error);
            }

            //Create gecoder to find zip code from lat long returned in location
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            double lat = lastKnownLocation.getLatitude();
            double lng = lastKnownLocation.getLongitude();

            //Get address postal code from address
            try {
                List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
                lastLocation = addresses.get(0).getPostalCode();
            } catch (IOException e) {
                Log.e("LocationUtils", e.toString());
            }
        }
        return lastLocation;
    }

}
