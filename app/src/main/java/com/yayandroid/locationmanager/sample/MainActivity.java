package com.yayandroid.locationmanager.sample;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.yayandroid.locationmanager.sample.activity.SampleActivity;

import com.yayandroid.locationmanager.sample.fragment.SampleFragmentActivity;
import com.yayandroid.locationmanager.sample.secondpage.NewsSports;
import com.yayandroid.locationmanager.sample.service.SampleServiceActivity;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.yayandroid.locationmanager.sample.R;
import com.yayandroid.locationmanager.sample.thirdpage.NewsGadgetsFull;
import com.yayandroid.locationmanager.sample.thirdpage.NewsSportsFull;

import java.util.HashMap;

import maes.tech.intentanim.CustomIntent;
public class MainActivity extends AppCompatActivity {
    String pass = "123456789";
    TextView textView;
    private DatabaseReference mDatabase,usersref;
    private FirebaseAuth mAuth;
    Dialog dialog;
    TextView heading;

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        String location = super.getIntent().getExtras().getString("key");
        mAuth = FirebaseAuth.getInstance();

        String id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID) + "@gmail.com";
        String aid = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);
        if (location != null) {
            register_user(aid, id, pass, location);
        } else {
            register_user1(aid, id, pass);
        }

        if (isFirstRun) {
            //Toast.makeText(getApplicationContext(), "Registering You!!", Toast.LENGTH_LONG).show();
dialog.setContentView(R.layout.instruction_dialog);
            dialog.show();

            if (location != null) {
                register_user(aid, id, pass, location);
            } else {
                register_user1(aid, id, pass);
            }
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                    .putBoolean("isFirstRun", false).apply();
        }
        loginUser(id,pass);
        //heading.setText(location);
//Intent a=new Intent(getApplicationContext(),NewsSports.class);startActivity(a);

    }
//===========================
    public void startService(View v) {

 
        Intent serviceIntent = new Intent(this, ExampleService.class);
        serviceIntent.putExtra("inputExtra", "Hello");
 
        ContextCompat.startForegroundService(this, serviceIntent);
    }
    public void stopService(View v) {
        Intent serviceIntent = new Intent(this, ExampleService.class);
        stopService(serviceIntent);
    }

//=========================
    private void register_user1(final String display_name, final String email, final String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    loginUser(email, password);
                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    String device_token = FirebaseInstanceId.getInstance().getToken();

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("name", display_name);
                    userMap.put("device_token", device_token);

                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //Toast.makeText(getApplicationContext(), "Registered!!", Toast.LENGTH_LONG).show();
                                finish();

                            }

                        }
                    });


                } else {


                    //Toast.makeText(getApplicationContext(), "Sorry", Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    private void register_user(final String display_name, final String email, final String password, final String location) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    loginUser(email, password);
                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    String device_token = FirebaseInstanceId.getInstance().getToken();

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("name", display_name);
                    userMap.put("location:", location);
                    userMap.put("device_token", device_token);

                    mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //Toast.makeText(getApplicationContext(), "Registered!!", Toast.LENGTH_LONG).show();
                                finish();

                            }

                        }
                    });


                } else {


                    //Toast.makeText(getApplicationContext(), "Sorry.", Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    private void loginUser(String id, String password) {

        mAuth.signInWithEmailAndPassword(id, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //Toast.makeText(getApplicationContext(), "Logged 1st step", Toast.LENGTH_SHORT).show();
                    String current_user_id = mAuth.getCurrentUser().getUid();
                    String deviceToken = FirebaseInstanceId.getInstance().getToken();
                    DatabaseReference mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
                    mUserDatabase.child(current_user_id).child("device_token").setValue(deviceToken).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                      //      Toast.makeText(getApplicationContext(), "Logged  In", Toast.LENGTH_SHORT).show();
                            Intent a=new Intent(getApplicationContext(),NewsSports.class);
                            startActivity(a);
                            finish();
                        }
                    });
                } else {
                    String task_result = task.getException().getMessage().toString();
                    //Toast.makeText(getApplicationContext(), "Error : " + task_result, Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    }