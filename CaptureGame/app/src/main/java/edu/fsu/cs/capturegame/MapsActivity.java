package edu.fsu.cs.capturegame;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Map;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<Status> {

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    LatLng currentlocation;
    protected ArrayList<Geofence> mGeofenceList;
    protected GoogleApiClient mGoogleApiClient;
    private Button mAddGeofencesButton;
    public static final int REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mGeofenceList = new ArrayList<Geofence>();

        // Get the geofences used. Geofence data is hard coded in this sample.
        populateGeofenceList();

        // Kick off the request to build GoogleApiClient.
        buildGoogleApiClient();
        //Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng FloridaState = new LatLng(30.44053, -84.297978);
        mMap.addMarker(new MarkerOptions().position(FloridaState).title("Marker at Florida State University"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(FloridaState, 17));
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //User has previously accepted this permission
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        }
        //Checking to see if permission was granted and on
        //Some code taken from developer.android.com
        checkLocationPermission();

        Polygon t1Poly = mMap.addPolygon(new PolygonOptions().add(
                new LatLng(30.440534, -84.297978), new LatLng(30.435576,-84.297978), new LatLng(30.435576, -84.289663), new LatLng(30.440534, -84.288204))
                .strokeColor(Color.RED)
                .fillColor(0x7FFF0000)
                .strokeWidth(4));
        Polygon t2Poly = mMap.addPolygon(new PolygonOptions().add(
                new LatLng(30.440534, -84.297978), new LatLng(30.444863,-84.297978), new LatLng(30.444863, -84.28829), new LatLng(30.440534, -84.28829))
                .strokeColor(Color.BLUE)
                .fillColor(0x7F0000FF)
                .strokeWidth(4));
        Polygon t3Poly = mMap.addPolygon(new PolygonOptions().add(
                new LatLng(30.440534, -84.297978), new LatLng(30.444863,-84.297978), new LatLng(30.448711, -84.305799), new LatLng(30.440534, -84.305799))
                .strokeColor(Color.YELLOW)
                .fillColor(0x7FFFFF00)
                .strokeWidth(4));
        Polygon t4Poly = mMap.addPolygon(new PolygonOptions().add(
                new LatLng(30.440534, -84.297978), new LatLng(30.435576,-84.297978), new LatLng(30.435576, -84.305799), new LatLng(30.440534, -84.305799))
                .strokeColor(Color.GREEN)
                .fillColor(0x7F00FF00)
                .strokeWidth(4));

        //PolyUtil.containsLocation()
    }

    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // In case permission is denied or unavailable, display to user why it's needed
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    showMessage("Location permission needed to use these services!",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                                    {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS},
                                                REQUEST_LOCATION);
                                    }
                                }
                            });
                    return;
                }

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION);
                return;

            }
            else
            {
                mMap.setMyLocationEnabled(true);
            }
        }
    }

    private void showMessage(String message, DialogInterface.OnClickListener okListener){
        new AlertDialog.Builder(MapsActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission granted
                    if (ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    // Permission denied, TT_TT!
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mGoogleApiClient.isConnecting() || !mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnecting() || mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        try {
            LocationServices.GeofencingApi.addGeofences(
                    mGoogleApiClient,
                    getGeofencingRequest(),
                    getGeofencePendingIntent()
            ).setResultCallback(this); // Result processed in onResult().
        } catch (SecurityException securityException) {
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Do something with result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }

    public void populateGeofenceList() {
        for (Map.Entry<String, LatLng> entry : Constants.LANDMARKS.entrySet()) {
            mGeofenceList.add(new Geofence.Builder()
                    .setRequestId(entry.getKey())
                    .setCircularRegion(
                            entry.getValue().latitude,
                            entry.getValue().longitude,
                            Constants.GEOFENCE_RADIUS_IN_METERS
                    )
                    .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                            Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build());
        }

    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling addgeoFences()
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void onResult(Status status) {
        if (status.isSuccess()) {

            if (!mGoogleApiClient.isConnected()) {
                Toast.makeText(this, "Google API Client not connected!", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(
                    this,
                    "Geofences Added",
                    Toast.LENGTH_SHORT
            ).show();

            Circle mCircle = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(30.438055,-84.2934558))
                    .radius(700)
                    .strokeColor(android.R.color.transparent)
                    .fillColor(Color.BLACK));
            Circle mCircle2 = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(30.4426985,-84.293134))
                    .radius(700)
                    .strokeColor(android.R.color.transparent)
                    .fillColor(Color.BLACK));
            Circle mCircle3 = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(30.4436605,-84.3018885))
                    .radius(700)
                    .strokeColor(android.R.color.transparent)
                    .fillColor(Color.BLACK));
            Circle mCircle4 = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(30.438055,-84.3018885))
                    .radius(700)
                    .strokeColor(android.R.color.transparent)
                    .fillColor(Color.BLACK));

        } else {
            // Get the status code for the error and log it using a user-friendly message.
            Toast.makeText(this, "Geofences not added", Toast.LENGTH_SHORT).show();
            //String errorMessage = GeofenceErrorMessages.getErrorString(this,
            //        status.getStatusCode());
        }
    }
}