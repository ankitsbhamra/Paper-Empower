package com.sjce.finalyearproject.paperempower;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by a0b01e0 on 3/12/18.
 */

public class MyLocationListener extends Activity implements LocationListener {
    String TAG="Motherfucking Tag";
    @Override
    public void onLocationChanged(Location loc) {


//        Toast.makeText(getBaseContext(),"Location changed : Lat: " + loc.getLatitude()+ " Lng: " + loc.getLongitude(), Toast.LENGTH_SHORT).show();
        String longitude = "Longitude: " +loc.getLongitude();
        Log.d(TAG, longitude);
        String latitude = "Latitude: " +loc.getLatitude();
        Log.d(TAG, latitude);

        //*----------to get City-Name from coordinates ------------- *//*
        String cityName=null;
        Geocoder gcd = new Geocoder(getBaseContext(),
                Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(loc.getLatitude(), loc
                    .getLongitude(), 1);
            if (addresses.size() > 0)
                System.out.println(addresses.get(0).getLocality());
            cityName=addresses.get(0).getLocality();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String s = longitude+"\n"+latitude +
                "\n\nMy Currrent City is: "+cityName;
        Log.d(TAG,s);
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider,
                                int status, Bundle extras) {
        // TODO Auto-generated method stub
    }
}