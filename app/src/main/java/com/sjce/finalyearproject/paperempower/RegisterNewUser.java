package com.sjce.finalyearproject.paperempower;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RegisterNewUser extends AppCompatActivity {

    LinearLayout ll;
    TextView lat;
    TextView lon;
    Button coord;
    LocationManager locationManager;
    LocationListener locationListener;

    private static final String TAG = "Motherfucking tag";
    private Boolean flag = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);

        ll = this.findViewById(R.id.coordinatesLinearLayout);
        lat = this.findViewById(R.id.lat);
        lon = this.findViewById(R.id.lon);
        coord = this.findViewById(R.id.getCoordinatesButton);
        //if you want to lock screen for always Portrait mode
        //  setRequestedOrientation(ActivityInfo
        //        .SCREEN_ORIENTATION_PORTRAIT);


        coord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCoord();
            }
        });

        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

    }


    @SuppressLint("NewApi")
    public void getCoord() {
        flag = displayGpsStatus();
        if (flag) {

            Log.v(TAG, "getCoord");


            ll.setVisibility(View.VISIBLE);
            locationListener = new MyLocationListener();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARS002111111111111111111111111000000000000000000000000000000000000000000000100100011111111111111000000000000000000000000000000000001011010000011111111111111111111111111111111111111111111111140E_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.


                ///========================///
                //=====FIX CODE HERE======//
                // /======================///
                ActivityCompat.requestPermissions(this,);
                return;
            }
            locationManager.requestLocationUpdates(LocationManager
                    .GPS_PROVIDER, 5000, 10, locationListener);

        } else {
            alertbox("Gps Status!!", "Your GPS is: OFF");
        }

    }

    /*----Method to Check GPS is enable or disable ----- */
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getBaseContext()
                .getContentResolver();
        boolean gpsStatus = Settings.Secure
                .isLocationProviderEnabled(contentResolver,
                        LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }

    /*----------Method to create an AlertBox ------------- */
    protected void alertbox(String title, String mymessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Device's GPS is Disable")
                .setCancelable(false)
                .setTitle("** Gps Status **")
                .setPositiveButton("Gps On",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // finish the current activity
                                // AlertBoxAdvance.this.finish();
                                Intent myIntent = new Intent(
                                        Settings.ACTION_SECURITY_SETTINGS);
                                startActivity(myIntent);
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // cancel the dialog box
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /*----------Listener class to get coordinates ------------- */
    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {

            editLocation.setText("");
            pb.setVisibility(View.INVISIBLE);
            Toast.makeText(getBaseContext(),"Location changed : Lat: " +
                            loc.getLatitude()+ " Lng: " + loc.getLongitude(),
                    Toast.LENGTH_SHORT).show();
            String longitude = "Longitude: " +loc.getLongitude();
            Log.v(TAG, longitude);
            String latitude = "Latitude: " +loc.getLatitude();
            Log.v(TAG, latitude);

    /*----------to get City-Name from coordinates ------------- */
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
            editLocation.setText(s);
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

}




/*
*  private LocationManager locationManager ;
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
        lat=findViewById(R.id.lat);
        lon=findViewById(R.id.lon);
        ll=findViewById(R.id.coordinatesLinearLayout);
        Button btn = this.findViewById(R.id.getCoordinatesButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll=findViewById(R.id.coordinatesLinearLayout);
                ll.setVisibility(View.VISIBLE);
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
        provider="asdsa";

        Log.d("Motherfucking tsg",provider);
//        Log.d("Motherfucking Tag",provider);
        if(provider != null) {
            Log.d("Motherfucking tsg","Provider not null");
            locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 2 * 1000, 10, locationListenerBest);
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
*
*
*
* */