package com.yayandroid.locationmanager.sample.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.yayandroid.locationmanager.base.LocationBaseActivity;
import com.yayandroid.locationmanager.configuration.Configurations;
import com.yayandroid.locationmanager.configuration.LocationConfiguration;
import com.yayandroid.locationmanager.constants.FailType;
import com.yayandroid.locationmanager.constants.ProcessType;
import com.yayandroid.locationmanager.sample.MainActivity;
import com.yayandroid.locationmanager.sample.R;
import com.yayandroid.locationmanager.sample.SamplePresenter;
import com.yayandroid.locationmanager.sample.SamplePresenter.SampleView;
import com.yayandroid.locationmanager.sample.secondpage.NewsSports;

import java.io.IOException;

import maes.tech.intentanim.CustomIntent;

public class SampleActivity extends LocationBaseActivity implements SampleView {

    private ProgressDialog progressDialog;
    TextView locationText;
    public static String location;
    private SamplePresenter samplePresenter;
    Thread a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_display_layout);

        locationText = (TextView) findViewById(R.id.locationText);
        samplePresenter = new SamplePresenter(this);
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            getLocation();
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                    .putBoolean("isFirstRun", false).apply();
        }else{

            a=new Thread(){
                @Override
                public void run() {
                    try{
                       // sleep(2000);
                    }catch (Exception e){e.printStackTrace();}
                    finally {
                        Intent a=new Intent(getApplicationContext(),NewsSports.class);
                        startActivity(a);
                        CustomIntent.customType(SampleActivity.this,"fadein-to-fadeout");
                    }
                }
            };
            a.start();
        }
        }





    @Override
    protected void onDestroy() {
        super.onDestroy();

        samplePresenter.destroy();

        a=new Thread(){
            @Override
            public void run() {
                try{

                }catch (Exception e){e.printStackTrace();}
                finally {
                    Intent a=new Intent(getApplicationContext(),MainActivity.class);
                    a.putExtra("key",locationText.toString());
                    startActivity(a);
                    CustomIntent.customType(SampleActivity.this,"fadein-to-fadeout");
                }
            }
        };
        a.start();
    }

    @Override
    public LocationConfiguration getLocationConfiguration() {
        return Configurations.defaultConfiguration("Give me the permission!", "Would you mind to turn GPS on?");
    }

    @Override
    public void onLocationChanged(Location location) {
        samplePresenter.onLocationChanged(location);
    }

    @Override
    public void onLocationFailed(@FailType int failType) {
        samplePresenter.onLocationFailed(failType);
    }

    @Override
    public void onProcessTypeChanged(@ProcessType int processType) {
        samplePresenter.onProcessTypeChanged(processType);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getLocationManager().isWaitingForLocation()
                && !getLocationManager().isAnyDialogShowing()) {
            displayProgress();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissProgress();
    }

    private void displayProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
            progressDialog.setMessage("Getting location...");
        }

        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public String getText() {
        return locationText.getText().toString();
    }

    @Override
    public void setText(String text) {
        locationText.setText(text);
    }

    @Override
    public void updateProgress(String text) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.setMessage(text);
        }
    }

    @Override
    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();

            a=new Thread(){
                @Override
                public void run() {
                    try{

                    }catch (Exception e){e.printStackTrace();}
                    finally {
                        Intent a=new Intent(getApplicationContext(),MainActivity.class);
                        a.putExtra("key",locationText.toString());
                        startActivity(a);
                        CustomIntent.customType(SampleActivity.this,"fadein-to-fadeout");
                    }
                }
            };
            a.start();
        }
    }

}