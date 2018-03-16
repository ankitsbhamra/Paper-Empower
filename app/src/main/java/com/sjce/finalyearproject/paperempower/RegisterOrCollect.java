package com.sjce.finalyearproject.paperempower;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class RegisterOrCollect extends AppCompatActivity {

    //TODO: Add Logout Button
    //TODO: Add Volunteer name at the top
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_or_collect);
        Button btnReg= (Button) this.findViewById(R.id.registerButton);
        Button btnCol= (Button) this.findViewById(R.id.collectButton);
        btnReg.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d("Motherfucking tag","Inside next2 onclick listener");
                registerEvent(v);
            }
        });

        btnCol.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    collectEvent(v);
                }
    });
    }

    public void registerEvent(View v){
        Intent i=new Intent(this,RegisterNewUser.class);
        startActivity(i);
    }

    public void collectEvent(View v){
        Intent i=new Intent(this,MapsActivity.class);
        startActivity(i);
    }

}
