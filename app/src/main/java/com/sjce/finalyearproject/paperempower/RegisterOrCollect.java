package com.sjce.finalyearproject.paperempower;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterOrCollect extends AppCompatActivity {

    private FirebaseAuth mAuth;

    //TODO: Import volunteer name after welcome
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_or_collect);
        mAuth = FirebaseAuth.getInstance();
        Button btnReg= (Button) this.findViewById(R.id.registerButton);
        Button btnCol= (Button) this.findViewById(R.id.collectButton);
        Button btnLog= (Button) this.findViewById(R.id.logoutButton);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(RegisterOrCollect.this,LandingPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
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
//        Intent i=new Intent(this,MapsActivity.class); TODO:CHANGE HERE
        Log.d("Motherfuckingtag","Inside Collect Event");
        Intent i=new Intent(this,CollectActivity.class);
        Toast.makeText(this,"Choose houses to collect from",Toast.LENGTH_LONG).show();
        startActivity(i);

    }

}
