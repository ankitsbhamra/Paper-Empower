package com.sjce.finalyearproject.paperempower;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.support.v7.app.AlertDialog;
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

import static android.content.ContentValues.TAG;

public class RegisterNewUser extends AppCompatActivity {

    LinearLayout ll;
    TextView latitude;
    TextView longitude;
    Button coord;
    LocationManager locationManager;
    LocationListener locationListener;
    double lat, lng;


    private static final String TAG = "Motherfucking tag";
    private Boolean flag = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);

        ll = (LinearLayout) this.findViewById(R.id.coordinatesLinearLayout);
        latitude = (TextView) this.findViewById(R.id.lat);
        longitude = (TextView) this.findViewById(R.id.lon);
        coord = (Button) this.findViewById(R.id.getCoordinatesButton);
        //if you want to lock screen for always Portrait mode
        //  setRequestedOrientation(ActivityInfo
        //        .SCREEN_ORIENTATION_PORTRAIT);

        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        coord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Inside onClick");
                ll.setVisibility(View.VISIBLE);
                getCoordGPS();
            }
        });


    }


    public void getCoordNETOWRK() {
        Log.d(TAG, "Inside getCoord()");
        String provider = LocationManager.NETWORK_PROVIDER;

        if (ActivityCompat.checkSelfPermission(RegisterNewUser.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(RegisterNewUser.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterNewUser.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            Log.d(TAG, "Inside if");
            return;
        } else {

            Log.d(TAG, "Inside else");
            Location loc = locationManager.getLastKnownLocation(provider);
            Log.d(TAG, String.valueOf(loc));
            if (loc != null) {
                lat = loc.getLatitude();
                lng = loc.getLongitude();
                String txt = "Latitude:" + Double.toString(lat) +
                        "\nLongitude:" + Double.toString(lng);
                //float distance= haversine(lat,lng,13.003062,77.564293);
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());
                Log.d(TAG, "Inside location check");


                try {
                    Log.d(TAG, "Getting location");

                    addresses = geocoder.getFromLocation(lat, lng, 1);
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
                    Log.d(TAG, address + ":" + city + ":" + state + ":" + country + ":" + postalCode + ":" + knownName);
                    latitude.setText("Latitude:" + lat);
                    longitude.setText("Longitude:" + lng);

                    //textview.setText((CharSequence) addresses);// Here 1 represent max location result to returned, by documents it recommended 1 to 5
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {
                latitude.setText("Latitude: NA");
                longitude.setText("Longitude: NA");
            }


        }


    }

    public void ifChecker()
    {
        getCoordGPS();
    }

    public void getCoordGPS() {
        Log.d(TAG,"Inside getCoordGPS");
        flag = displayGpsStatus();
        Log.d(TAG, String.valueOf(flag));
        if (flag) {
//            locationListener = new MyLocationListener();

            locationListener=new LocationListener() {
                @Override
                public void onLocationChanged(Location loc) {
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
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };
            if (ActivityCompat.checkSelfPermission(RegisterNewUser.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(RegisterNewUser.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(RegisterNewUser.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                Log.d(TAG, "Inside if");
                ifChecker();
            } else {
                Log.d(TAG,"Inside else");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
                Location loc=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(loc==null)
                    Log.d(TAG, "loc is null");
                else{
                    Log.d(TAG, "Value of loc:"+String.valueOf(loc.getLatitude()));
                    latitude.setText("Lat:"+loc.getLatitude());
                    longitude.setText("Lat:"+loc.getLongitude());
                }
            }

        } else {
            alertbox("Gps Status!!", "Your GPS is: OFF");
        }

    }

    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getBaseContext()
                .getContentResolver();
        boolean gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }

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
}








/*    @SuppressLint("NewApi")
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
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},);
                return;
            }
            locationManager.requestLocationUpdates(LocationManager
                    .GPS_PROVIDER, 5000, 10, locationListener);

        } else {
            alertbox("Gps Status!!", "Your GPS is: OFF");
        }

    }

    *//*----Method to Check GPS is enable or disable ----- *//*
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

    *//*----------Method to create an AlertBox ------------- *//*
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

    *//*----------Listener class to get coordinates ------------- *//*
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

    *//*----------to get City-Name from coordinates ------------- *//*
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
    }*/






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