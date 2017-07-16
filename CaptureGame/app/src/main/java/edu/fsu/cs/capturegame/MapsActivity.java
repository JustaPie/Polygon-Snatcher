package edu.fsu.cs.capturegame;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;
import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static final int REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
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
        LatLng FloridaState = new LatLng(30.4419, -84.2971);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(FloridaState, 15));
        mMap.getUiSettings().setAllGesturesEnabled(false);

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
    }
    
        // Function checks to see if user gave permission to obtain location
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
    
        // Dialog to display if user disables location
    private void showMessage(String message, DialogInterface.OnClickListener okListener){
        new AlertDialog.Builder(MapsActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    // Checking for result of permission given by user
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
}
