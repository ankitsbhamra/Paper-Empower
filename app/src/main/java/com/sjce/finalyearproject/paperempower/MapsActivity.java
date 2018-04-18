package com.sjce.finalyearproject.paperempower;
//TODO: Style Selected Marker and add Marker for current location
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private ChildEventListener childEventListener;
    List<HousesInfo> housesInfo = new ArrayList<HousesInfo>();//LIST THAT CONTAINS ALL THE SELECTED HOUSES
    DatabaseReference houses;
    private ProgressDialog progressDialog;
    int ctr = 0;
    String num;
    Button btndemo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Log.d("MotherFucking tag","Inside onCreate");
        progressDialog = new ProgressDialog(this);
        btndemo= (Button) this.findViewById(R.id.demoButton);
        houses = FirebaseDatabase.getInstance().getReference("houses");
        final ArrayList<String> keyList = (ArrayList<String>) getIntent().getSerializableExtra("keyList");
        //Log.d("MotherFucking Tag",keyList.get(0));
        mapFragment.getMapAsync(this);
        houses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("MotherFucking Tag","Inside onDataChange");
                housesInfo.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    HousesInfo hi = ds.getValue(HousesInfo.class);
                    if((keyList.contains(hi.key))&&(hi.completed==false)){
                        housesInfo.add(hi);
                        //Log.d("Motherfucking tag",String.valueOf(housesInfo.get(ctr).fullname));
                        ctr++;
                    }
                }
                addMarkers();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MapsActivity.this,"Unable to read from Database",Toast.LENGTH_LONG);
                Log.d("Motherfucking tag","Inside onCancelled");
            }
        });
        btndemo.setOnClickListener(new View.OnClickListener(){
                                      public void onClick(View v){
                                          Log.d("Motherfucking tag","Inside demo button");
                                          progressDialog.setMessage("Please Wait");
                                          progressDialog.show();
                                          removeMarkers();
                                      }
                                  });


        //housesInfo populated with selected houses
        Log.d("Motherfucking tag", "Inside OnCreate2");

        //Button btnReg= (Button) this.findViewById(R.id.collectButton);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        housesInfo.clear();
        Log.d("MotherFucking Tag","Inside onBackPressed");
        Intent intent = new Intent(this,RegisterOrCollect.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }






    public void addMarkers(){
        Log.d("Motherfucking tag","Inside addMarkers");
        if(progressDialog.isShowing()){
            progressDialog.cancel();
        }
        for(HousesInfo hi2:housesInfo){
            Log.d("Motherfucking tag",hi2.phonenumber);
            LatLng marker = new LatLng(hi2.latitude,hi2.longitude);
            mMap.addMarker(new MarkerOptions().position(marker).title(hi2.phonenumber));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));

        }
    }
    public void removeMarkers (){
        String ckey="999";
        Log.d("Motherfucking tag","Inside remove marker function");

        Log.d("Motherfucking tag",num);
        for(HousesInfo hi:housesInfo){
            if(hi.phonenumber.equals(num)){
                ckey=hi.key;
                Log.d("Motherfucking tag","Number Found");
                Log.d("Motherfucking tag",hi.phonenumber);
                //housesInfo.remove(hi);
                break;
            }
        }
        houses.child(ckey).child("completed").setValue(true);
        Log.d("Motherfucking tag","Data Changed");
        mMap.clear();
        //progressDialog.cancel();
    }

    public void onMapReady(GoogleMap googleMap) {
        Log.d("Mother Fucking Tag", "Inside OnMapready");
        mMap = googleMap;
        mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                btndemo.setVisibility(View.VISIBLE);
                num=marker.getTitle();
                Log.d("Motherfucking tag",num);
                return false;
            }
        });

    }






//}

}
