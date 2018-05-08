package com.sjce.finalyearproject.paperempower;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class RegisterNewUser extends AppCompatActivity {

    LinearLayout ll;
    TextView latitude;
    TextView longitude;
    EditText zipcode;
    EditText name;
    EditText door;
    EditText number;
    EditText main;
    EditText cross;
    EditText block;
    EditText additionalDetails;
    Button coord,register;
    LocationManager locationManager;
    LocationListener locationListener;
    Spinner area;
    double lat, lng;
    //String arr[] = {"AIISH Layout","Bogadi","CFTRI","Dattagalli","Gangotri Layout","Gokulam","JP Nagar","KC Layout","Krishnamurthypuram","Kuvempunagar","Lingambudipalya","Nivedithanagar","Prashanth Nagar","Ramakrishna Nagar","Sawaswathipuram","TK Layout","Vijashreepura","Vijaya Nagar Colony","Srirampura"};
    DatabaseReference housesref;
    DatabaseReference arearef;
    List<AreaInfo> areaList=new ArrayList<>();
    List<String> areaString=new ArrayList<>();
    ArrayAdapter<String> areaAdapter;

    private static final String TAG = "Motherfucking tag";
    private Boolean flag = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_user);
        housesref = FirebaseDatabase.getInstance().getReference();
        arearef = FirebaseDatabase.getInstance().getReference("areas");
        ll = (LinearLayout) this.findViewById(R.id.coordinatesLinearLayout);
        latitude = (TextView) this.findViewById(R.id.lat);
        longitude = (TextView) this.findViewById(R.id.lon);
        zipcode = (EditText) this.findViewById(R.id.zipcodeEditText);
        name = (EditText) this.findViewById(R.id.nameEditText);
        coord = (Button) this.findViewById(R.id.getCoordinatesButton);
        register = (Button) this.findViewById(R.id.registerHouseButton);
        //if you want to lock screen for always Portrait mode
        //  setRequestedOrientation(ActivityInfo
        //        .SCREEN_ORIENTATION_PORTRAIT);
        arearef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Mother","Inside onDatachange-area");
                //size = dataSnapshot.getChildrenCount();
                for(DataSnapshot areaSnapshot:dataSnapshot.getChildren()){
                    //HousesInfo housesInfo = new HousesInfo();
                    /*
                    housesInfo.setAddress(housesSnapshot.getValue(HousesInfo.class).getAddress());
                    housesInfo.setFullname(housesSnapshot.getValue(HousesInfo.class).getFullname());
                    housesInfo.setPhonenumber(housesSnapshot.getValue(HousesInfo.class).getPhonenumber());
                    housesInfo.setZipcode(housesSnapshot.getValue(HousesInfo.class).getZipcode());
                    housesInfo.setLatitude(housesSnapshot.getValue(HousesInfo.class).getLatitude());
                    housesInfo.setLongitude(housesSnapshot.getValue(HousesInfo.class).getLongitude());
                    */
                    AreaInfo areaInfo=areaSnapshot.getValue(AreaInfo.class);
                    Log.d("Moth",areaInfo.name);
                    areaList.add(areaInfo);

                }

            Log.d("Mother", String.valueOf(areaList.size()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(RegisterNewUser.this,"Unable to read from Database",Toast.LENGTH_LONG);
            }
        });

        for(AreaInfo ai:areaList)
            areaString.add(ai.name);

        area= (Spinner) this.findViewById(R.id.areaDropDownSpinner);
        areaAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,areaString);
        area.setAdapter(areaAdapter);
        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        coord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Inside onClick");
                ll.setVisibility(View.VISIBLE);
                getCoordNETOWRK();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Inside onClick of Register");
                //ll.setVisibility(View.VISIBLE);
                registerHouse();
            }
        });


    }
    //App was crashing if register house clicked before getting coordinates,fixed by setting sentinel values
    public void registerHouse(){
        zipcode = (EditText) this.findViewById(R.id.zipcodeEditText);
        name = (EditText) this.findViewById(R.id.nameEditText);
        number = (EditText) this.findViewById(R.id.phNumberEditText);
        door = (EditText) this.findViewById(R.id.doorEditText);
        main= (EditText) findViewById(R.id.mainEditText);
        cross= (EditText) findViewById(R.id.crossEditText);
        block= (EditText) findViewById(R.id.blockEditText);
        additionalDetails= (EditText) findViewById(R.id.additionalEditText);
        area= (Spinner) this.findViewById(R.id.areaDropDownSpinner);
        double lat=-999;
        double lon=-999;
        String latitudeStr;
        String longitudeStr;
        latitudeStr = (String) latitude.getText();
        latitudeStr=latitudeStr.trim();
        longitudeStr = (String) longitude.getText();
        longitudeStr=longitudeStr.trim();
        Log.d("Motherfucking tag 1",latitudeStr);
        Log.d("Motherfucking tag 2",longitudeStr);

        if(!latitudeStr.equals("Latitude:")&&!longitudeStr.equals("Longitude:"))
        {
            lat=Double.parseDouble(latitudeStr.substring(4));
            lon=Double.parseDouble(longitudeStr.substring(4));
        }
        //TODO: String values are not getting extracted from Edit Texts. Being Stored in some weird format in FireBase
        String zip = zipcode.getText().toString().trim();
        String fullname = name.getText().toString().trim();
        String mainStr = main.getText().toString().trim();
        String crossStr = cross.getText().toString().trim();
        String blockStr = block.getText().toString().trim();
        String additionalDetailsStr = additionalDetails.getText().toString().trim();
        String num = number.getText().toString().trim();
        String doorStr = door.getText().toString().trim();
        String areaSt=area.getSelectedItem().toString();
        String areaKey=null;

        for(AreaInfo ai:areaList)
            if((ai.name).equals(areaSt))
                areaKey=ai.key;

        Log.d("Motherfucking tag",fullname);
        if(areaKey==null)
        {
            area.requestFocus();
            Toast.makeText(this,"Select an area",Toast.LENGTH_LONG).show();
            return;
        }
        if(fullname.isEmpty()){
            name.setError("Name Cannot Be Empty");
            name.requestFocus();
            return;
        }
        if((zip.isEmpty())||(zip.length()!=6)){
            zipcode.setError("Zip Code Must Be 6 digits");
            zipcode.requestFocus();
            return;
        }
        if(mainStr.isEmpty()){
            main.setError("Address Cannot Be Empty");
            main.requestFocus();
            return;
        }
        if(crossStr.isEmpty()){
            cross.setError("Address Cannot Be Empty");
            cross.requestFocus();
            return;
        }
        if(blockStr.isEmpty()){
            block.setError("Address Cannot Be Empty");
            block.requestFocus();
            return;
        }
        if(additionalDetailsStr.isEmpty()){
            additionalDetailsStr="";
            return;
        }
        if((num.isEmpty())||(num.length()!=10)){
            number.setError("Phone Number Must Be 10 Digits");
            number.requestFocus();
            return;
        }
        if(lat==-999||lon==-999)
        {
            Toast.makeText(this,"Coordinates could not be registered, please try again",Toast.LENGTH_LONG).show();
            return;
        }
        String key = housesref.child("houses").push().getKey();
        String date = "Not Collected";
        HousesInfo housesInfo = new HousesInfo(fullname,areaKey,doorStr,mainStr,crossStr,blockStr,additionalDetailsStr,num,zip,date,lat,lon,key);
        //TODO: Code to add an area to the database. Add UI for this
        /*for(int i=0;i<19;i++) {
            Log.d("Motherfucking tag","Inside for loop");
            String key1 = arearef.child("areas").push().getKey();
            String name = arr[i];
            AreaInfo areaInfo = new AreaInfo(name,key1);
            arearef.child("areas").child(key1).setValue(areaInfo);
        }
        */
        housesref.child("houses").child(key).setValue(housesInfo);

        Toast.makeText(RegisterNewUser.this, "House Registered!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(RegisterNewUser.this,RegisterOrCollect.class);
        startActivity(intent);
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
                    zipcode.setText(postalCode);

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
        getCoordNETOWRK();
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
                    String longSt= String.valueOf(loc.getLongitude());
                    if(longSt.length()>6)
                    longSt=longSt.substring(0,6);
                    String longitude = "Longitude: " +longSt;
                    Log.d(TAG, longitude);
                    String latSt= String.valueOf(loc.getLatitude());
                    if(latSt.length()>6)
                    latSt=latSt.substring(0,6);
                    String latitude = "Latitude: " +latSt;
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
                    String latSt= String.valueOf(loc.getLatitude());
                    if(latSt.length()>6)
                        latSt=latSt.substring(0,6);
                    latitude.setText("Lat:"+latSt);
                    String longSt= String.valueOf(loc.getLongitude());
                    if(longSt.length()>6)
                        longSt=longSt.substring(0,6);
                    longitude.setText("Lon:"+longSt);
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