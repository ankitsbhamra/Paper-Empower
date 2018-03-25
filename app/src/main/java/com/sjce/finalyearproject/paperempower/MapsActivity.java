package com.sjce.finalyearproject.paperempower;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Button btnReg= (Button) this.findViewById(R.id.collectButton);
    }

    //TODO: get lat and long from registered data - firebase

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng marker1 = new LatLng(12.3369598, 76.5904817);
        mMap.addMarker(new MarkerOptions().position(marker1).title("Marker 1"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker1));

        LatLng marker2 = new LatLng(12.3095255, 76.6073671);
        mMap.addMarker(new MarkerOptions().position(marker2).title("Marker 2"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker2));

        LatLng marker3 = new LatLng(12.3132715,76.6112378);
        mMap.addMarker(new MarkerOptions().position(marker3).title("Marker 3"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker3));

    }
}
