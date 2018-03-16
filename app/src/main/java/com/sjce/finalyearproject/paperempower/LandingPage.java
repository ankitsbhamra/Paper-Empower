package com.sjce.finalyearproject.paperempower;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;


import java.io.Console;

import static android.app.PendingIntent.getActivity;


public class LandingPage extends AppCompatActivity {

        private static final String key="Logged In";
        private static final String val="true";
        SharedPreferences pref;
        private FirebaseAuth fbaseauth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        fbaseauth = FirebaseAuth.getInstance();
        pref=getApplicationContext().getSharedPreferences("Paper Empower",0);
        SharedPreferences.Editor editor=pref.edit();
        if(pref.getString(key,"false").equals("val"))
        {
            Log.d("Motherfucking tag","Inside sharedPref if");
            Intent i=new Intent(this,RegisterOrCollect.class);
            startActivity(i);
            return;
        }
        Button btn= (Button) this.findViewById(R.id.next2);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                Log.d("Motherfucking tag","Inside next2 onclick listener");
                registerVolunteer();
            }
        });

        Button btn2= (Button) this.findViewById(R.id.nextButton);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next(view);
            }
        });
    }
    LinearLayout signUpLL;
    TextView cnfmpwdtv;
    TextView cnfmpwdet;
    Button btn;
    Button btn2;
    EditText pwd;
    EditText email;
    //TODO: Add Button in Scroll View
    //TODO: Make password EditText Input Type to Password type
    //TODO: Add Validation Tests to all fields
    //TODO: Change field TextViews to hints
    //TODO: add progress bar while logging in
    //TODO: add zoom-in feature to map
    //TODO: add an activity after collect - checkbox
    //TODO: add line between markers

    private void next(View v)
    {
        Log.d("motherfucking tag","Inside next()");
        pref=getApplicationContext().getSharedPreferences("Paper Empower",0);
        SharedPreferences.Editor editor=pref.edit();
        signUpLL= (LinearLayout) this.findViewById(R.id.SignUp);
        cnfmpwdtv= (TextView) this.findViewById(R.id.confirmPasswordTextView);
        cnfmpwdet= (TextView) this.findViewById(R.id.confirmPasswordEditText);
        btn= (Button) this.findViewById(R.id.nextButton);
        btn2= (Button) this.findViewById(R.id.next2);
        pwd= (EditText) this.findViewById(R.id.passwordEditText);
        email=(EditText)this.findViewById(R.id.emailIdEditText);
        String pwdSt=String.valueOf(pwd.getText());
        String emailSt= String.valueOf(email.getText());

        Log.d("Moth","In next()"+emailSt+pwdSt);

        fbaseauth.signInWithEmailAndPassword(emailSt, pwdSt).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                pref=getApplicationContext().getSharedPreferences("Paper Empower",0);
                SharedPreferences.Editor editor=pref.edit();

                Log.d("Moth", String.valueOf(task.isSuccessful()));
                if (task.isSuccessful()) {
                    Intent i=new Intent(LandingPage.this,RegisterOrCollect.class);
                    startActivity(i);
                    editor.putString(key,val);
                    editor.commit();
                    return;

                } else if (task.getException() instanceof FirebaseAuthInvalidUserException) {

                    cnfmpwdtv.setVisibility(View.VISIBLE);
                    cnfmpwdet.setVisibility(View.VISIBLE);
                    signUpLL.setVisibility(View.VISIBLE);
//            btn.setText("Sign Up");
                    btn.setVisibility(View.GONE);
                    btn2.setVisibility(View.VISIBLE);
                    Toast.makeText(LandingPage.this, "User Doesn't Exist. Please Sign-up First", Toast.LENGTH_LONG).show();
                }
                else{
                    email.requestFocus();
                    email.setError("The email id is not registered");
                }
            }
        });




    }


    EditText name;
    EditText phNum;
    EditText pwdcmf;
    String nameSt;
    String phNumSt;
    String emailSt;
    String pwdSt;
    String pwdcmfSt;



    private void registerVolunteer()
    {
        Log.d("Motherfucking tag","In registerVolunteer()");
        name= (EditText) this.findViewById(R.id.nameEditText);
        phNum= (EditText) this.findViewById(R.id.phNumberEditText);
        email= (EditText) this.findViewById(R.id.emailIdEditText);
        pwd= (EditText) this.findViewById(R.id.passwordEditText);
        pwdcmf= (EditText) this.findViewById(R.id.confirmPasswordEditText);

        nameSt=String.valueOf(name.getText());
        phNumSt=String.valueOf(phNum.getText());
        emailSt=String.valueOf(email.getText());
        pwdSt=String.valueOf(pwd.getText());
        pwdcmfSt=String.valueOf(pwdcmf.getText());
        if(pwdSt.equals(pwdcmfSt)){

            registerUser(nameSt,phNumSt,emailSt,pwdSt);

        }
        else {
            TextView err= (TextView) this.findViewById(R.id.errorTextView);
            err.setText("Passwords do not match");
            err.setVisibility(View.VISIBLE);

        }
    }

    //TODO : Register User (Add Firebase )
    private void registerUser(String name,String phNum,String email,String passWord)
    {
        pref=getApplicationContext().getSharedPreferences("Paper Empower",0);
        SharedPreferences.Editor editor=pref.edit();
        Log.d("Motherfucking tag",name+phNum+email+passWord);
        editor.putString(key,val);
        editor.commit();
        Intent i=new Intent(this,RegisterOrCollect.class);
        startActivity(i);
    }

}
