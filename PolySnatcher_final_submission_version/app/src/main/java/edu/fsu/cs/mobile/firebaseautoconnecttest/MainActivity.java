package edu.fsu.cs.mobile.firebaseautoconnecttest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements MainFragment.OnButtonClickedListener,
        GameFragment.OnFragmentInteractionListener,
        MyMapFragment.OnTimeUpListener {

    public static final int GRAY = 2;
    public static final int GARNET_TEAM = 1;
    public static final int GOLD_TEAM = 0;

    Zone zone1;
    Zone zone2;
    Zone zone3;
    Zone zone4;
    Zone zone5;
    Zone zone6;
    int team;

    @Override
    public int onTimeUp(String zoneKey) {
        //increment the player's team score in the database. return true for Garnet lead, False for Gold lead
        if (team == GARNET_TEAM){
            incrementGarnetScore(zoneKey);
            return getZoneHolder(zoneKey);
        }
        if (team == GOLD_TEAM){
            incrementGoldScore(zoneKey);
            return getZoneHolder(zoneKey);
        }


        Log.i("MainActivity", "Invalid Team Detected!");
        return -1;
    }

    //interface method to init zone colors in MyMapFragment
    @Override
    public int[] initTerritoryColors() {
        int zoneHolders[] = new int[6];

        for (int i = 0; i < 6; i++){
            zoneHolders[i] = GRAY;
        }

        if (zone1.getgarnetteamscore() > zone1.getgoldteamscore())
            zoneHolders[0] = GARNET_TEAM;
        else if (zone1.getgoldteamscore() > zone1. getgarnetteamscore())
            zoneHolders[0] = GOLD_TEAM;

        if (zone2.getgarnetteamscore() > zone2.getgoldteamscore())
            zoneHolders[1] = GARNET_TEAM;
        else if (zone2.getgoldteamscore() > zone2. getgarnetteamscore())
            zoneHolders[1] = GOLD_TEAM;

        if (zone3.getgarnetteamscore() > zone3.getgoldteamscore())
            zoneHolders[2] = GARNET_TEAM;
        else if (zone3.getgoldteamscore() > zone3. getgarnetteamscore())
            zoneHolders[2] = GOLD_TEAM;

        if (zone4.getgarnetteamscore() > zone4.getgoldteamscore())
            zoneHolders[3] = GARNET_TEAM;
        else if (zone4.getgoldteamscore() > zone4. getgarnetteamscore())
            zoneHolders[3] = GOLD_TEAM;

        if (zone5.getgarnetteamscore() > zone5.getgoldteamscore())
            zoneHolders[4] = GARNET_TEAM;
        else if (zone5.getgoldteamscore() > zone5. getgarnetteamscore())
            zoneHolders[4] = GOLD_TEAM;

        if (zone6.getgarnetteamscore() > zone6.getgoldteamscore())
            zoneHolders[5] = GARNET_TEAM;
        else if (zone6.getgoldteamscore() > zone6. getgarnetteamscore())
            zoneHolders[5] = GOLD_TEAM;

        return zoneHolders;

    }

    @Override
    public void callInitZones() {
        Log.i("MainActivity", "Calling initZones");
        initZones();
    }

    @Override
    public void onButtonClicked(View v, String email, String password) {


        if (v.getId() == R.id.login_button) {

            if (!email.equals("") && !password.equals("")) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            Log.i("MainActivity", task.getException().toString());
                            return;

                        }

                        initZones();

                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_frame, GameFragment.newInstance(new MyUser(auth.getCurrentUser().getEmail(), "ID+PLACEHOLDER")))
                                .commit();

                    }
                });
                return;
            } else {
                Toast.makeText(this, "Empty Fields", Toast.LENGTH_SHORT).show();
                return;
            }
        } else if (v.getId() == R.id.register_button) {

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } else if (v.getId() == R.id.start_button) {

            if (!email.equals("") && !password.equals("")) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            Log.i("MainActivity", task.getException().toString());
                            return;

                        }

                        initZones();
                        //updateDatabase();

                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        //TODO - properly implement team saving
                        team = assignTeam();

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.main_frame, MyMapFragment.newInstance("THIS IS", "A TEST"))
                                .commit();

                    }
                });
                return;
            } else {
                Toast.makeText(this, "Empty Fields", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }


    @Override
    public void onFragmentInteraction(View v, String id) {
        if (v.getId() == R.id.submit_button)
            pushToDatabase(auth.getCurrentUser(), id);
        else if (v.getId() == R.id.garnet_button) {
            incrementGarnetScore(id);
        } else if (v.getId() == R.id.gold_button) {
            incrementGoldScore(id);
        } else if (v.getId() == R.id.zone_button) {
            getZoneInfo(id);
        } else if (v.getId() == R.id.logout_button) {
            Log.i("MainActivity", "Signing Out...");
            auth.signOut();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame, MainFragment.newInstance())
                    .commit();
        }
    }


    FirebaseAuth auth;
    GoogleApiClient mGoogleApiClient;
    Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialize default zones
        zone1 = new Zone();
        zone2 = new Zone();
        zone3 = new Zone();
        zone4 = new Zone();
        zone5 = new Zone();
        zone6 = new Zone();

        auth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(MainActivity.this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(MainActivity.this, "CONNECTION FAILED", Toast.LENGTH_SHORT).show();
                    }
                } /* OnConnectionFailedListener*/)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        FragmentManager fManager = getSupportFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();
        //Check if anyone is already logged in
        if (auth.getCurrentUser() != null) {

            initZones();
            team = assignTeam();

            fTransaction.add(R.id.main_frame,
                    MyMapFragment.newInstance("THIS IS", "A TEST"));

        } else
            fTransaction.add(R.id.main_frame, MainFragment.newInstance());

        fTransaction.commit();

    }

    private void login() {
        Toast.makeText(this, auth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
    }

    //TODO - GET RID OF THIS TEST METHOD
    private void pushToDatabase(FirebaseUser user, String data) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        MyUser userObject = new MyUser(user.getEmail(), data);
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("/users/smtanooki@gmail.com", userObject);
        reference.updateChildren(m);
    }

    private void getZoneInfo(String zoneKey) {

        switch (zoneKey) {
            case "Zone1": {
                Log.i("MainActivity", zone1.getgarnetteamscore() + " " +
                        zone1.getgoldteamscore());
                break;
            }
            case "Zone2": {
                Log.i("MainActivity", zone2.getgarnetteamscore() + " " +
                        zone2.getgoldteamscore());
                break;
            }
            case "Zone3": {
                Log.i("MainActivity", zone3.getgarnetteamscore() + " " +
                        zone3.getgoldteamscore());
                break;
            }
            case "Zone4": {
                Log.i("MainActivity", zone4.getgarnetteamscore() + " " +
                        zone4.getgoldteamscore());
                break;
            }
            case "Zone5": {
                Log.i("MainActivity", zone5.getgarnetteamscore() + " " +
                        zone5.getgoldteamscore());
                break;
            }
            case "Zone6": {
                Log.i("MainActivity", zone6.getgarnetteamscore() + " " +
                        zone6.getgoldteamscore());
                break;
            }
            default: {
                Toast.makeText(this, "Invalid Key", Toast.LENGTH_SHORT).show();
                return;
            }


        }

    }

    private void incrementGarnetScore(String zoneKey) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Zone newZone = new Zone();
        Log.i("MainActivity", "incrementGarnetScore");
        switch (zoneKey) {
            case "Zone1": {
                newZone.setgarnetscore(zone1.getgarnetteamscore() + 20);
                newZone.setgoldscore(zone1.getgoldteamscore());
                break;
            }
            case "Zone2": {
                newZone.setgarnetscore(zone2.getgarnetteamscore() + 20);
                newZone.setgoldscore(zone2.getgoldteamscore());
                break;
            }
            case "Zone3": {
                newZone.setgarnetscore(zone3.getgarnetteamscore() + 20);
                newZone.setgoldscore(zone3.getgoldteamscore());
                break;
            }
            case "Zone4": {
                newZone.setgarnetscore(zone4.getgarnetteamscore() + 20);
                newZone.setgoldscore(zone4.getgoldteamscore());
                break;
            }
            case "Zone5": {
                newZone.setgarnetscore(zone5.getgarnetteamscore() + 20);
                newZone.setgoldscore(zone5.getgoldteamscore());
                break;
            }
            case "Zone6": {
                newZone.setgarnetscore(zone6.getgarnetteamscore() + 20);
                newZone.setgoldscore(zone6.getgoldteamscore());
                break;
            }
            default: {
                Toast.makeText(this, "Invalid Key", Toast.LENGTH_SHORT).show();
                return;
            }

        }

        Map<String, Object> m = new HashMap<String, Object>();
        m.put(zoneKey, newZone);
        reference.updateChildren(m);


    }

    private void incrementGoldScore(String zoneKey) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Zone newZone = new Zone();
        Log.i ("MainActivity","IncrementGoldScore");
        switch (zoneKey) {
            case "Zone1": {
                newZone.setgarnetscore(zone1.getgarnetteamscore());
                newZone.setgoldscore(zone1.getgoldteamscore() + 20);
                break;
            }
            case "Zone2": {
                newZone.setgarnetscore(zone2.getgarnetteamscore());
                newZone.setgoldscore(zone2.getgoldteamscore() + 20);
                break;
            }
            case "Zone3": {
                newZone.setgarnetscore(zone3.getgarnetteamscore());
                newZone.setgoldscore(zone3.getgoldteamscore() + 20);
                break;
            }
            case "Zone4": {
                newZone.setgarnetscore(zone4.getgarnetteamscore());
                newZone.setgoldscore(zone4.getgoldteamscore() + 20);
                break;
            }
            case "Zone5": {
                newZone.setgarnetscore(zone5.getgarnetteamscore());
                newZone.setgoldscore(zone5.getgoldteamscore() + 20);
                break;
            }
            case "Zone6": {
                newZone.setgarnetscore(zone6.getgarnetteamscore());
                newZone.setgoldscore(zone6.getgoldteamscore() + 20);
                break;
            }
            default: {
                Toast.makeText(this, "Invalid Key", Toast.LENGTH_SHORT).show();
                return;
            }

        }

        Map<String, Object> m = new HashMap<String, Object>();
        m.put(zoneKey, newZone);
        reference.updateChildren(m);
    }

    private void updateDatabase(){
        Log.i("MainActivity", "Updating database...");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        MyUser userObject = new MyUser("Update", "Database");
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("UPDATE", userObject);
        reference.updateChildren(m);
    }

    //check to see which team owns which zone
    private int getZoneHolder(String zoneKey){
        switch (zoneKey){
            case "Zone1":{

                if (zone1.getgarnetteamscore() > zone1.getgoldteamscore())
                    return GARNET_TEAM;
                if (zone1.getgoldteamscore() > zone1.getgarnetteamscore())
                    return GOLD_TEAM;
                else
                    return -1; //neutral

            }
            case "Zone2":{

                if (zone2.getgarnetteamscore() > zone2.getgoldteamscore())
                    return GARNET_TEAM;
                if (zone2.getgoldteamscore() > zone2.getgarnetteamscore())
                    return GOLD_TEAM;
                else
                    return -1; //neutral
            }
            case "Zone3":{
                if (zone3.getgarnetteamscore() > zone3.getgoldteamscore())
                    return GARNET_TEAM;
                if (zone3.getgoldteamscore() > zone3.getgarnetteamscore())
                    return GOLD_TEAM;
                else
                    return -1; //neutral
            }
            case "Zone4":{
                if (zone4.getgarnetteamscore() > zone4.getgoldteamscore())
                    return GARNET_TEAM;
                if (zone4.getgoldteamscore() > zone4.getgarnetteamscore())
                    return GOLD_TEAM;
                else
                    return -1; //neutral
            }
            case "Zone5":{
                if (zone5.getgarnetteamscore() > zone5.getgoldteamscore())
                    return GARNET_TEAM;
                if (zone5.getgoldteamscore() > zone5.getgarnetteamscore())
                    return GOLD_TEAM;
                else
                    return -1; //neutral
            }
            case "Zone6":{
                if (zone1.getgarnetteamscore() > zone1.getgoldteamscore())
                    return GARNET_TEAM;
                if (zone1.getgoldteamscore() > zone1.getgarnetteamscore())
                    return GOLD_TEAM;
                else
                    return -1; //neutral
            }
        }
        return -2; //control should never reach this point
    }

    private int assignTeam(){
        int team;

        team = Math.abs(new Random().nextInt() % 2);
        Log.i("Main Activity", "Assigned team is" + String.valueOf(team));
        if (team == GARNET_TEAM) {
            Toast.makeText(MainActivity.this, "You are on Team Garnet", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, "You are on Team Gold", Toast.LENGTH_SHORT).show();
        }
        return team;
    }



    private void initZones(){
        //Load zone data from database
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
                    Zone currentZone = zoneSnapshot.getValue(Zone.class);
                    switch (zoneSnapshot.getKey()) {
                        case ("Zone1"): {

                            zone1.setgarnetscore(currentZone.getgarnetteamscore());
                            zone1.setgoldscore(currentZone.getgoldteamscore());
                            Log.i("MainActivity", "Zone1:  " + String.valueOf(zone1.garnetteamscore) + " "
                                    + String.valueOf(zone1.goldteamscore));
                            break;
                        }
                        case ("Zone2"): {
                            zone2.setgarnetscore(currentZone.getgarnetteamscore());
                            zone2.setgoldscore(currentZone.getgoldteamscore());
                            Log.i("MainActivity", "Zone2:  " + String.valueOf(zone2.garnetteamscore) + " "
                                    + String.valueOf(zone2.goldteamscore));
                            break;
                        }
                        case ("Zone3"): {
                            zone3.setgarnetscore(currentZone.getgarnetteamscore());
                            zone3.setgoldscore(currentZone.getgoldteamscore());
                            Log.i("MainActivity", "Zone3:  " + String.valueOf(zone3.garnetteamscore) + " "
                                    + String.valueOf(zone3.goldteamscore));
                            break;
                        }
                        case ("Zone4"): {
                            zone4.setgarnetscore(currentZone.getgarnetteamscore());
                            zone4.setgoldscore(currentZone.getgoldteamscore());
                            Log.i("MainActivity", "Zone4:  " + String.valueOf(zone4.garnetteamscore) + " "
                                    + String.valueOf(zone4.goldteamscore));
                            break;
                        }
                        case ("Zone5"): {
                            zone5.setgarnetscore(currentZone.getgarnetteamscore());
                            zone5.setgoldscore(currentZone.getgoldteamscore());
                            Log.i("MainActivity", "Zone5:  " + String.valueOf(zone5.garnetteamscore) + " "
                                    + String.valueOf(zone5.goldteamscore));
                            break;
                        }
                        case ("Zone6"): {
                            zone6.setgarnetscore(currentZone.getgarnetteamscore());
                            zone6.setgoldscore(currentZone.getgoldteamscore());
                            Log.i("MainActivity", "Zone6:  " + String.valueOf(zone6.garnetteamscore) + " "
                                    + String.valueOf(zone6.goldteamscore));
                            break;
                        }
                        default:
                            //Log.i ("MainActivity", "No zones were initialized!");
                            break;
                    }
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Main Activity", "Database read cancelled");
            }
        });
    }

}

