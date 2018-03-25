package com.sjce.finalyearproject.paperempower;

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

import java.io.Console;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class CollectActivity extends AppCompatActivity {

    List<HousesInfo> arr=new ArrayList<HousesInfo>();
    Dictionary latCheckedArr=new Hashtable();//Dictionary storing key:latitude value
    Dictionary longCheckedArr=new Hashtable();//Dictionary storing key:longitude value
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        LinearLayout scrollViewLL= (LinearLayout) this.findViewById(R.id.scrollViewLL);
        populateArrayList();
        createElements(arr);
        Button collectButton= (Button) this.findViewById(R.id.collectButton);
        collectButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d("Motherfucking tag","Inside collectButton onclick listener");
                validateClick();

            }
        });
    }

    void populateArrayList()
    {

        //TODO: populate arr with data from firebase
        HousesInfo hi=new HousesInfo("asdsad","asdsada","23431234","21312312",123.34,345.1,"asdasdsad");
        arr.add(hi);
        arr.add(hi);
        arr.add(hi);
        arr.add(hi);
        arr.add(hi);
        arr.add(hi);


    }

    int cbId=1000;
    int tvId=2000;
    int llId=3000;
    List<String> contentList=new ArrayList<String>();
    Dictionary contentDict=new Hashtable();


    void createElements(List<HousesInfo> arr)
    {

        LinearLayout mainLL= (LinearLayout) this.findViewById(R.id.scrollViewInsideLL);
        for(HousesInfo hi:arr)
        {
            String text="";
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
                        latCheckedArr.put(houInf.key,houInf.latitude);
                        longCheckedArr.put(houInf.key,houInf.longitude);

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
        else
        {
            sendData();
        }

    }

    void sendData()
    {

//        TODO: Implementation to send data from latCheckedArr and longCheckedArr to mapsActivity and launch mapsActivity

    }
}
