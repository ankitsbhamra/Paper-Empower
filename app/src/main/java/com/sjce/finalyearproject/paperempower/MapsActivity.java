package com.sjce.finalyearproject.paperempower;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    List<HousesInfo> housesInfo = new ArrayList<HousesInfo>();//LIST THAT CONTAINS ALL THE SELECTED HOUSES
    DatabaseReference houses;
    int ctr = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        houses = FirebaseDatabase.getInstance().getReference("houses");
        final ArrayList<String> keyList = (ArrayList<String>) getIntent().getSerializableExtra("keyList");
        houses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    HousesInfo hi = ds.getValue(HousesInfo.class);
                    if(keyList.contains(hi.key)){
                        housesInfo.add(hi);
                        //Log.d("Motherfucking tag",String.valueOf(housesInfo.get(ctr).latitude));
                        ctr++;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //housesInfo populated with selected houses
        //Log.d("Motherfucking tag", keyList);
        mapFragment.getMapAsync(this);
        Button btnReg= (Button) this.findViewById(R.id.collectButton);
    }

    //TODO: get lat and long from registered data - firebase as JSON objects and parse to getview

/*
    public View getView(int position, View convertView, ViewGroup Parent)
    {
        final ViewHolder holder=new ViewHolder();
        View view=convertView;
        if (view==null) {
            convertView= inflater.inflate(R.layout.layout_row, null);
        }



        holder.textdistance=(TextView) convertView.findViewById(R.id.textView_Distance);

        holder.position = position;


        if(data.size()<=0)
        {

            Toast.makeText(activity, "No data", Toast.LENGTH_LONG).show();
        }
        else
        {
   **new ThumbnailTask(position, holder)
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);**
        }
        return convertView;
        }
   //Thumbnail Class
 private static class ThumbnailTask extends AsyncTask {
    private int mPosition;
    private ViewHolder mHolder;

    public ThumbnailTask(int position, ViewHolder holder) {
        mPosition = position;
        mHolder = holder;
    }



@Override
protected Object doInBackground(Object... params) {
    // TODO Auto-generated method stub
    String Distance="3";
    String uri="https://maps.googleapis.com/maps/api/distancematrix/json?  origins=(Your source Addres like XYZ,South Delhi-110064),&mode=deriving&sensor=false&key=(Your API Key)";


    String result= GET(uri);
    Log.d("result","res="+result);

    try {

        //jArray = new JSONObject(result);
        JSONObject object = (JSONObject) new JSONTokener(result).nextValue();
        JSONArray array = object.getJSONArray("rows");
        // Log.d("JSON","array: "+array.toString());

        //Routes is a combination of objects and arrays
        JSONObject rows = array.getJSONObject(0);
        //Log.d("JSON","routes: "+routes.toString());
        JSONArray elements = rows.getJSONArray("elements");
        // Log.d("JSON","legs: "+legs.toString());

        JSONObject steps = elements.getJSONObject(0);
        //Log.d("JSON","steps: "+steps.toString());

        JSONObject distance = steps.getJSONObject("distance");
        Log.d("JSON","distance: "+distance.toString());

        Distance = distance.getString("text");
        Log.d("final value","tot dis="+Distance);
        JSONObject duration=steps.getJSONObject("duration");
        // MyDuration=duration.getString("text");


    }
    catch(JSONException e)
    {
        Log.d("JSONEXCeption","my exce"+e.getMessage());
    }
    return Distance;
}



    @Override
    protected void onPostExecute(Object result) {
        // TODO Auto-generated method stub
        mHolder.textdistance.setText(result.toString());
    }


}

    //method to execute Url

    private static  String GET(String url)
    {
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));


            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();



            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);

//     Log.i("Result",result);

            else
                result = "Did not work!";
        } catch (Exception e) {
            Log.d("InputStream", "hello"+e);
        }

        return result;




    }


    private static  String convertInputStreamToString(InputStream inputStream) throws   IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

*/

//TODO: remove demo markers
/*    @Override
    public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnPolylineClickListener, GoogleMap.OnPolygonClickListener {
*/


    public void addMarkers(){
        Log.d("Motherfucking tag",housesInfo.get(0).fullname);

        for(int i=0;i<housesInfo.size();i++){

            LatLng marker = new LatLng(housesInfo.get(i).latitude, housesInfo.get(i).longitude);
            Log.d("Motherfucking tag", String.valueOf(housesInfo.get(i).latitude));
            Log.d("Motherfucking tag", String.valueOf(housesInfo.get(i).longitude));
            mMap.addMarker(new MarkerOptions().position(marker).title(housesInfo.get(i).phonenumber));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));

        }
    }
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        addMarkers();

        /*LatLng marker1 = new LatLng(12.3369598, 76.5904817);
        mMap.addMarker(new MarkerOptions().position(marker1).title("Marker 1"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker1));

        LatLng marker2 = new LatLng(12.3095255, 76.6073671);
        mMap.addMarker(new MarkerOptions().position(marker2).title("Marker 2"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker2));

        LatLng marker3 = new LatLng(12.3132715, 76.6112378);
        mMap.addMarker(new MarkerOptions().position(marker3).title("Marker 3"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker3));*/

//        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions().clickable(true).add(marker1, marker2, marker3));
//          mMap.setOnPolylineClickListener(this);
//        googleMap.setOnPolygonClickListener(this);

        //TODO: Remove the selected marker - on click of marker/demo button on maps

        /*Map<String, Marker> markers = new HashMap();

ref.addChildEventListener(new ChildEventListener() {
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        UsersActive uA = dataSnapshot.getValue(UsersActive.class);

        Marker uAmarker = mMap.addMarker(markerOptions);
        markers.put(dataSnapshot.getKey(), uAmarker);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        UsersActive uA = dataSnapshot.getValue(UsersActive.class);

            if (markers.contains(dataSnapshot.getKey())) {
            Marker marker = markers.get(dataSnapshot.getKey());

            marker.remove();
            // or
            // marker.setPosition(newPosition);
        }

        Marker uAmarker = mMap.addMarker(markerOptions);
        markers.put(dataSnapshot.getKey(), uAmarker);
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        if (markers.contains(dataSnapshot.getKey())) {
            Marker marker = markers.get(dataSnapshot.getKey());
            marker.remove();
        }
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

});
*/
    }
//}

}
