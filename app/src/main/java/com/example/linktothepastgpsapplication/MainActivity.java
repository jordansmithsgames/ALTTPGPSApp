package com.example.linktothepastgpsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements LocationListener {

    final int PERMISSION_REQUEST_CODE=0;

    boolean seenZelda;
    boolean gotMasterSword;

    Location HyruleCastle;
    Location LostWoods;
    Location GanonsTower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HyruleCastle = new Location(LocationManager.GPS_PROVIDER);
        HyruleCastle.setLongitude(-82.338012);
        HyruleCastle.setLatitude(29.6462017);

        LostWoods = new Location(LocationManager.GPS_PROVIDER);
        LostWoods.setLongitude(-82.343303);
        LostWoods.setLatitude(29.648796);

        GanonsTower = new Location(LocationManager.GPS_PROVIDER);
        GanonsTower.setLongitude(-82.3511307);
        GanonsTower.setLatitude(29.6494172);

        boolean permissionGranted=
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;

        if(permissionGranted){

            //start Location Services
            Log.d("Example","User granted permissions before. Start GPS now");
            startGPS();
        }
        else{

            //We need to request permissions
            Log.d("Example","User never granted permissions before. Request now");
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE);

        }
    }

    public void startGPS(){

        boolean permissionGranted=
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;

        if(permissionGranted) {
            LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){

        if(requestCode==PERMISSION_REQUEST_CODE){

            if(grantResults[0]==PackageManager.PERMISSION_DENIED){
                //The user clicked on the DENY button
                Log.d("Example","User denied permissions just now. Exit");
                finish();
            }
            else{
                //The user clicked on the ALLOW button
                Log.d("Example","User granted permissions right now. Start GPS now");
                startGPS();
            }
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location.distanceTo(HyruleCastle) < 20) { // Within 20 meters
            // Switch to Hyrule Castle Activity
            seenZelda = true;
            Intent hyruleCastle = new Intent(getBaseContext(), HyruleCastle.class);
            hyruleCastle.putExtra("context", "You arrive at Hyrule Castle and find Princess Zelda! She tells you to head to Lost Woods to find the legendary Master Sword to kill Ganon, before the evil wizard destroys the world!");
            hyruleCastle.putExtra("mission", "Go to Lost Woods to find the Master Sword!");
            startActivity(hyruleCastle);
        }
        else if (location.distanceTo(LostWoods) < 20) { // Within 20 meters
            Intent lostWoods = new Intent(getBaseContext(), LostWoods.class);
            String context = "You somehow end up in the Lost Woods forest! After walking around lost for a few hours, you finally make it out of the forest back to where you entered it.";
            String mission = "Go back!";
            if (seenZelda) {
                gotMasterSword = true;
                context = "You scramble around the enchanted forest for a while, but you finally find the Master Sword! Now go to Ganon's Tower to kill Ganon!";
                mission = "Go to Ganon's Tower!";
            }
            lostWoods.putExtra("context", context);
            lostWoods.putExtra("mission", mission);
            startActivity(lostWoods);
        }
        else if (location.distanceTo(GanonsTower) < 20) { // Within 20 meters
            Intent ganonsTower = new Intent(getBaseContext(), GanonsTower.class);
            String context = "You arrive at Ganon's Tower, but his minions quickly overpower you and you barely make it out alive with your life!";
            String mission = "Go back!";
            if (gotMasterSword) {
                context = "You climb the wretched Tower and fight back the forces of evil with your Master Sword. You finally battle Ganon, and just narrowly kill him with a final blow!";
                mission = "You Win!";
            }
            ganonsTower.putExtra("context", context);
            ganonsTower.putExtra("mission", mission);
            startActivity(ganonsTower);
        }
     }

}