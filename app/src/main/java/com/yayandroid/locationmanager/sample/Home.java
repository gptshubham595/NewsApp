package com.yayandroid.locationmanager.sample;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.yayandroid.locationmanager.sample.secondpage.NewsAgriculture;
import com.yayandroid.locationmanager.sample.secondpage.NewsBusiness;
import com.yayandroid.locationmanager.sample.secondpage.NewsEducation;
import com.yayandroid.locationmanager.sample.secondpage.NewsEntertainment;
import com.yayandroid.locationmanager.sample.secondpage.NewsGadgets;
import com.yayandroid.locationmanager.sample.secondpage.NewsInternational;
import com.yayandroid.locationmanager.sample.secondpage.NewsLifestyle;
import com.yayandroid.locationmanager.sample.secondpage.NewsPolitics;
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
public class Home extends AppCompatActivity {
    String pass = "123456789";
    TextView textView;
    private DatabaseReference mDatabase,usersref;
    private FirebaseAuth mAuth;
    ProgressDialog load;
    Dialog dialog;
    TextView heading,shortdesc,text;
    ImageView sports,politics,education,entertainment,lifestyle,gadgets,agriculture,business,international;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        heading=findViewById(R.id.heading);
        load=new ProgressDialog(this);
        shortdesc=findViewById(R.id.shortdesc);
        text=findViewById(R.id.text);

        getheading();
        getshortdescr();
        sports=findViewById(R.id.sports);
        politics=findViewById(R.id.politics);
        education=findViewById(R.id.education);
        entertainment=findViewById(R.id.entertainment);
        lifestyle=findViewById(R.id.lifestyle);
        gadgets=findViewById(R.id.gadgets);
        agriculture=findViewById(R.id.agriculture);
        business=findViewById(R.id.business);
        international=findViewById(R.id.international);
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(getApplicationContext(),NewsSports.class);
                startActivity(a);
            }});

        politics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(getApplicationContext(),NewsPolitics.class);
                startActivity(a);
            }});

        education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(getApplicationContext(),NewsEducation.class);
                startActivity(a);
            }});

        entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(getApplicationContext(),NewsEntertainment.class);
                startActivity(a);
            }});

        lifestyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(getApplicationContext(),NewsLifestyle.class);
                startActivity(a);
            }});
        gadgets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(getApplicationContext(),NewsGadgets.class);
                startActivity(a);
            }});
        agriculture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(getApplicationContext(),NewsAgriculture.class);
                startActivity(a);
            }});

        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(getApplicationContext(),NewsBusiness.class);
                startActivity(a);
            }});
        international.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(getApplicationContext(),NewsInternational.class);
                startActivity(a);
            }});

}

    private void getheading() {

        load.setTitle("Wait");
        load.setMessage("Getting the latest news for you..");
        load.show();

        DatabaseReference mheading = FirebaseDatabase.getInstance().getReference().child("Trending").child("content").child("heading");
        mheading.keepSynced(true);
        // Read from the database
        mheading.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
        load.dismiss();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(!dataSnapshot.exists()){Toast.makeText(getApplicationContext(),"Sorry Nothing Left",Toast.LENGTH_SHORT).show(); }
                else{loadtext();
                    String value = dataSnapshot.getValue(String.class);
                    heading.setText(value);}
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
        }
    private void loadtext() {
      text.setText("Trending News Here");
    }

    private void getshortdescr() {

        DatabaseReference mshortdesc = FirebaseDatabase.getInstance().getReference().child("Trending").child("content").child("shortdesc");
        mshortdesc.keepSynced(true);

        mshortdesc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(!dataSnapshot.exists()){Toast.makeText(getApplicationContext(),"Sorry Nothing Left",Toast.LENGTH_SHORT).show(); }
                else{String value = dataSnapshot.getValue(String.class);
                    shortdesc.setText(value);}
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }
}