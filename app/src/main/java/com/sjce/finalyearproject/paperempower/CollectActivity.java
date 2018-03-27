package com.sjce.finalyearproject.paperempower;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Console;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class CollectActivity extends AppCompatActivity {

    List<HousesInfo> arr=new ArrayList<HousesInfo>();
    int ctr=0;

    //List<HousesInfo> arr1=new ArrayList<HousesInfo>();

    ArrayList<String> arr2 = new ArrayList<String>();
    Dictionary latCheckedArr=new Hashtable();//Dictionary storing key:latitude value
    Dictionary longCheckedArr=new Hashtable();//Dictionary storing key:longitude value
    DatabaseReference houses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        LinearLayout scrollViewLL= (LinearLayout) this.findViewById(R.id.scrollViewLL);
        houses = FirebaseDatabase.getInstance().getReference("houses");
        Log.d("Motherfucking tag","Entering populateArrayList");
        populateArrayList();
        /*Log.d("Motherfucking tag","Entering createElements");
        createElements();*/
        Log.d("Motherfucking tag","Exiting createElements");

        Button collectButton= (Button) this.findViewById(R.id.collectButton);
        collectButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d("Motherfucking tag","Inside collectButton onclick listener");
                validateClick();
                collectevent(v);

            }
        });
    }





    void populateArrayList() {

        houses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //size = dataSnapshot.getChildrenCount();
                for(DataSnapshot housesSnapshot:dataSnapshot.getChildren()){
                    //HousesInfo housesInfo = new HousesInfo();
                    /*
                    housesInfo.setAddress(housesSnapshot.getValue(HousesInfo.class).getAddress());
                    housesInfo.setFullname(housesSnapshot.getValue(HousesInfo.class).getFullname());
                    housesInfo.setPhonenumber(housesSnapshot.getValue(HousesInfo.class).getPhonenumber());
                    housesInfo.setZipcode(housesSnapshot.getValue(HousesInfo.class).getZipcode());
                    housesInfo.setLatitude(housesSnapshot.getValue(HousesInfo.class).getLatitude());
                    housesInfo.setLongitude(housesSnapshot.getValue(HousesInfo.class).getLongitude());
                    */
                    arr.add(housesSnapshot.getValue(HousesInfo.class));
                    Log.d("Motherfucking tag", String.valueOf(arr.size()));
                    Log.d("Motherfuckingtag", String.valueOf(arr.get(ctr).zipcode));
                    ctr++;
                }
                createElements();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CollectActivity.this,"Unable to read from Database",Toast.LENGTH_LONG);
            }
        });



    }





        /*
        HousesInfo hi=new HousesInfo("asdsad","asdsada","23431234","21312312",123.34,345.1,"asdasdsad");
        arr.add(hi);
        arr.add(hi);
        arr.add(hi);
        arr.add(hi);
        arr.add(hi);
        arr.add(hi);
        */



    int cbId=1000;
    int tvId=2000;
    int llId=3000;
    List<String> contentList=new ArrayList<String>();
    Dictionary contentDict=new Hashtable();

    //TODO: NOT DISPLAYING THE HOUSES
    void createElements()
    {
        Log.d("Motherfucking tag","Inside Create elements");
        LinearLayout mainLL= (LinearLayout) this.findViewById(R.id.scrollViewInsideLL);
        Log.d("Motherfucking tag", String.valueOf(arr.size()));
        for(HousesInfo hi:arr)
        {
            String text="";
            Log.d("Motherfucking tag","Inside Create Elements");//NOT ENTERING HERE
            text=text+"Name:"+hi.fullname+"\nAddress:"+hi.address+"\nPhone Number:"+hi.phonenumber;

            TextView tv=new TextView(this);
            tv.setText(text);
            tv.setTextSize(22);
//            tv.setTextColor(0xffffff);
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
            lp.setMargins(0,10,0,10);
            tv.setLayoutParams(lp);
            tv.setId(tvId);
            tvId++;

            CheckBox cb=new CheckBox(this);
            contentDict.put(cb.hashCode(),hi);
            Log.d("Motherfucking tag", String.valueOf(cb.hashCode()));

            cb.setId(cbId);
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if ( isChecked )
                    {
                        // perform logic
                        HousesInfo houInf= (HousesInfo) contentDict.get(buttonView.hashCode());
                        Log.d("Motherfucking tag", String.valueOf(buttonView.hashCode()));
                        arr2.add(houInf.key);//Populating arr2 with only keys of selected houses
                        /*
                        latCheckedArr.put(houInf.key,houInf.latitude);
                        longCheckedArr.put(houInf.key,houInf.longitude);
                        */
                        Log.d("Motherfucking tag", String.valueOf(longCheckedArr.get(houInf.key)));

                    }

                }
            });
            cbId++;
            cb.setText(text);
            LinearLayout ll=new LinearLayout(this);
            ll.setLayoutParams(lp);
            ll.setId(llId);
            llId++;

            ll.addView(cb);
//            ll.addView(tv);

            mainLL.addView(ll);
        }

    }


    void validateClick()
    {

        if(latCheckedArr.isEmpty()&&longCheckedArr.isEmpty())
            Toast.makeText(this,"Please select houses to visit",Toast.LENGTH_LONG).show();
        /*else
        {

        }
        */
    }


    /*
    void sendData()//NOT REQUIRED. DONE IT INSIDE if(isChecked)
    {

//        TODO: Implementation to send data from latCheckedArr and longCheckedArr to mapsActivity and launch mapsActivity

    }
    */
    public void collectevent(View v){
        Intent i=new Intent(this,MapsActivity.class);
        i.putExtra("keyList",arr2);//Passing list of keys of selected house to MapsActivity

        startActivity(i);
    }
}
