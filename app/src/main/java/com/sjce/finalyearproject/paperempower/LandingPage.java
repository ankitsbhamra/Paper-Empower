package com.sjce.finalyearproject.paperempower;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Console;

import static android.app.PendingIntent.getActivity;

public class LandingPage extends AppCompatActivity {

        private static final String key="Logged In";
        private static final String val="true";
        SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        pref=getApplicationContext().getSharedPreferences("Paper Empower",0);
        SharedPreferences.Editor editor=pref.edit();
        if(pref.getString(key,"false").equals(val))
        {
            Log.d("Motherfucking tag","Inside sharedPref if");
            Intent i=new Intent(this,RegisterOrCollect.class);
            startActivity(i);
            return;
        }
        Button btn=this.findViewById(R.id.next2);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d("Motherfucking tag","Inside next2 onclick listener");
                registerVolunteer();
            }
        });

        Button btn2=this.findViewById(R.id.nextButton);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next(view);
            }
        });
    }

    private void next(View v)
    {
        Log.d("motherfucking tag","Inside showSignUp()");
        pref=getApplicationContext().getSharedPreferences("Paper Empower",0);
        SharedPreferences.Editor editor=pref.edit();
        EditText username=this.findViewById(R.id.usernameEditText);
        Log.d("motherfucking tag", String.valueOf(username.getText()));
        String usernameText=String.valueOf(username.getText());
        Button btn=this.findViewById(R.id.nextButton);
        Button btn2=this.findViewById(R.id.next2);
        EditText pwd=this.findViewById(R.id.passwordEditText);
        String pwdSt=String.valueOf(pwd.getText());

        if(!isRegistered(usernameText))
        {
            LinearLayout signUpLL= this.findViewById(R.id.SignUp);
            TextView cnfmpwdtv=this.findViewById(R.id.confirmPasswordTextView);
            TextView cnfmpwdet=this.findViewById(R.id.confirmPasswordEditText);
            cnfmpwdtv.setVisibility(View.VISIBLE);
            cnfmpwdet.setVisibility(View.VISIBLE);
            signUpLL.setVisibility(View.VISIBLE);
//            btn.setText("Sign Up");
           btn.setVisibility(View.GONE);
           btn2.setVisibility(View.VISIBLE);

        }
        else{
            if(isCorrectPassword(usernameText,pwdSt))
            {
                editor.putString(key,val);
                editor.commit();
            }
        }



    }


    private void registerVolunteer()
    {
        Log.d("Motherfucking tag","In registerVolunteer()");
        EditText name=this.findViewById(R.id.nameEditText);
        EditText phNum=this.findViewById(R.id.phNumberEditText);
        EditText email=this.findViewById(R.id.emailIdEditText);
        EditText usrnm=this.findViewById(R.id.usernameEditText);
        EditText pwd=this.findViewById(R.id.passwordEditText);
        EditText pwdcmf=this.findViewById(R.id.confirmPasswordEditText);

        String nameSt=String.valueOf(name.getText());
        String phNumSt=String.valueOf(phNum.getText());
        String emailSt=String.valueOf(email.getText());
        String usrnmSt=String.valueOf(usrnm.getText());
        String pwdSt=String.valueOf(pwd.getText());
        String pwdcmfSt=String.valueOf(pwdcmf.getText());
        if(pwdSt.equals(pwdcmfSt)){

            registerUser(nameSt,phNumSt,emailSt,usrnmSt,pwdSt);

        }
        else {
            TextView err=this.findViewById(R.id.errorTextView);
            err.setText("Passwords do not match");
            err.setVisibility(View.VISIBLE);

        }
    }

    private void registerUser(String name,String phNum,String email,String userName,String passWord)
    {
        pref=getApplicationContext().getSharedPreferences("Paper Empower",0);
        SharedPreferences.Editor editor=pref.edit();
        Log.d("Motherfucking tag",name+phNum+email+userName+passWord);
        editor.putString(key,val);
        editor.commit();
        Intent i=new Intent(this,RegisterOrCollect.class);
        startActivity(i);
    }

    boolean isRegistered(String name){
        Log.d("Motherfucking tag",name);
        return false;
    }

    boolean isCorrectPassword(String usrNm,String pwd)
    {
        return true;
    }
}
