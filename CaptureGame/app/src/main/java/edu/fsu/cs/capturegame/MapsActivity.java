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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

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


            boolean contain = PolyUtil.containsLocation()

    }
}
