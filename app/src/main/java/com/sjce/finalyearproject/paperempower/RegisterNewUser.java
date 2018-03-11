package com.sjce.finalyearproject.paperempower;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RegisterNewUser extends AppCompatActivity {
    private LocationManager locationManager ;
//    LocationManager locationManager;
    double longitudeBest, latitudeBest;
    double longitudeGPS, latitudeGPS;
    double longitudeNetwork, latitudeNetwork;
    TextView lat;
    TextView lon;
    LinearLayout ll;




    private final LocationListener locationListenerBest=new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            longitudeBest = location.getLongitude();
            latitudeBest = location.getLatitude();

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lat=this.findViewById(R.id.lat);
        lon=this.findViewById(R.id.lon);
        ll=this.findViewById(R.id.coordinatesLinearLayout);
        Button btn = this.findViewById(R.id.getCoordinatesButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCoordinates();
            }
        });


    }

    private boolean checkLocation()
    {

        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();

    }

    @SuppressLint("MissingPermission")
    private void getCoordinates() {
        Log.d("Motherfucking Tag","Inside getCoordinates");
        Log.d("Motherfucking Tag",String.valueOf(isLocationEnabled()));
        if (!checkLocation())
            return;

//        locationManager.requestLocationUpdates(
//                LocationManager.GPS_PROVIDER, 2 * 60 * 1000, 10, locationListenerGPS);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String provider = locationManager.getBestProvider(criteria, true);
//        Log.d("Motherfucking Tag",provider);
        if(provider != null) {
            Log.d("Motherfucking tsg","Provider not null");
            locationManager.requestLocationUpdates(provider, 2 * 1000, 10, locationListenerBest);
//            String latTx= (String) lat.getText();
//            String lonTx=(String)lon.getText();
            ll.setVisibility(View.VISIBLE);
            Log.d("Motherfucking tag", String.valueOf(latitudeBest));
            lat.setText("Latitude:"+String.valueOf(latitudeBest));
            lon.setText("Longitude"+String.valueOf(longitudeBest));
//            locationManager.removeUpdates(locationListenerBest);
            return;
    }
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }


    private boolean isLocationEnabled() {

        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));

    }

}
