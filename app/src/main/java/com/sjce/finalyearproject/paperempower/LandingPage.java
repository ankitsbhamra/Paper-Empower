package com.sjce.finalyearproject.paperempower;

//TODO: Create collect activity with ListView
//TODO: Add Button in Scroll View
//TODO: Make password EditText Input Type to Password type
//TODO: Add Validation Tests to all fields
//TODO: Change field TextViews to hints
//TODO: add progress bar while logging in
//TODO: add zoom-in feature to map
//TODO: add an activity after collect - checkbox
//TODO: add poly-line between markers
//TODO: add area as drop down

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.io.Console;

import static android.app.PendingIntent.getActivity;



public class LandingPage extends AppCompatActivity {

        private static final String key="Logged In";
        private static final String val="true";
        SharedPreferences pref;
        private FirebaseAuth fbaseauth;
        private ProgressDialog progressDialog;
        DatabaseReference usersref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        fbaseauth = FirebaseAuth.getInstance();
        usersref = FirebaseDatabase.getInstance().getReference("users");
        progressDialog = new ProgressDialog(this);
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
    TextView cnfmpwdet;
    Button btn;
    Button btn2;
    EditText pwd;
    EditText email;



    private void next(View v)
    {
        Log.d("motherfucking tag","Inside next()");
        pref=getApplicationContext().getSharedPreferences("Paper Empower",0);
        SharedPreferences.Editor editor=pref.edit();
        signUpLL= (LinearLayout) this.findViewById(R.id.SignUp);
        //cnfmpwdtv= (TextView) this.findViewById(R.id.confirmPasswordTextView);
        cnfmpwdet= (TextView) this.findViewById(R.id.confirmPasswordEditText);
        btn= (Button) this.findViewById(R.id.nextButton);
        btn2= (Button) this.findViewById(R.id.next2);
        pwd= (EditText) this.findViewById(R.id.passwordEditText);
        email=(EditText)this.findViewById(R.id.emailIdEditText);
        String pwdSt=String.valueOf(pwd.getText());
        String emailSt= String.valueOf(email.getText());

        Log.d("Moth","In next()"+emailSt+pwdSt);
        progressDialog.setMessage("Logging In User");
        progressDialog.show();
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
                    Log.d("Motherfucking tag","Inside invalid user");
                    progressDialog.cancel();
                    cnfmpwdet.setVisibility(View.VISIBLE);
                    signUpLL.setVisibility(View.VISIBLE);
//            btn.setText("Sign Up");
                    btn.setVisibility(View.GONE);
                    btn2.setVisibility(View.VISIBLE);
                    Toast.makeText(LandingPage.this, "User Doesn't Exist. Please Sign-up First", Toast.LENGTH_LONG).show();
                }
                else{
                    progressDialog.cancel();
                    pwd.requestFocus();
                    pwd.setError("The Password Entered Is Incorrect");
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
        if(emailSt.isEmpty()){
            email.setError("Email ID is mandatory");
            email.requestFocus();
            return;
        }

        if(pwdSt.isEmpty()){
            pwd.setError("Password is mandatory");
            pwd.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailSt).matches()){
            email.setError("Please Enter a valid Email ID");
            email.requestFocus();
            return;
        }
        if(pwdSt.length() < 6){
            pwd.setError("Password Must be longer than 6 characters");
            pwd.requestFocus();
            return;
        }
        if(!pwdSt.equals(pwdcmfSt)){
            pwdcmf.setError("Passwords do not match");
            pwdcmf.requestFocus();

        }
        else {
            registerUser(nameSt,phNumSt,emailSt,pwdSt);

        }
    }
    String uid;
    //TODO : Register User (Add Firebase)
    private void registerUser(String name,String phNum,String email,String passWord)
    {
        pref=getApplicationContext().getSharedPreferences("Paper Empower",0);
        SharedPreferences.Editor editor=pref.edit();
        Log.d("Motherfucking tag",name+phNum+email+passWord);
        editor.putString(key,val);
        editor.commit();
        progressDialog.setMessage("Signing up user");
        progressDialog.show();
        fbaseauth.createUserWithEmailAndPassword(email,passWord).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Add User Details to Database
                    uid = fbaseauth.getUid();
                    UsersInfo usersInfo = new UsersInfo(nameSt,emailSt,phNumSt);
                    usersref.child(uid).setValue(usersInfo);
                    Toast.makeText(LandingPage.this,"User Registered Successfully!!!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LandingPage.this, RegisterOrCollect.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else if(task.getException() instanceof FirebaseAuthUserCollisionException){
                    Toast.makeText(LandingPage.this,"User Already Exists",Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                }
                else{
                    Toast.makeText(LandingPage.this,"User Registration Failed!!!",Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                }
            }
        });

    }

}
