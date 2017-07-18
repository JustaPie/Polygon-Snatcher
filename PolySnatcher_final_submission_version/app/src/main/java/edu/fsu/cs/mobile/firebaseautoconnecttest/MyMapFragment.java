package edu.fsu.cs.mobile.firebaseautoconnecttest;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.Timer;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyMapFragment.OnTimeUpListener} interface
 * to handle interaction events.
 * Use the {@link MyMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyMapFragment extends android.support.v4.app.Fragment
        implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnTimeUpListener timerListener;

    private static final int COLOR_GRAY = 0x55000000;
    private static final int COLOR_GARNET = 0x55ff0000;
    private static final int COLOR_GOLD = 0x55ffff00;
    GoogleMap map;
    Button fsuButton;
    Button initButton;


    Polygon zone1;
    Polygon zone2;
    Polygon zone3;
    Polygon zone4;
    Polygon zone5;
    Polygon zone6;

    Timer timer;

    public MyMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i("MyMapFragment", "MapReady");

        map = googleMap;
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.441878, -84.298489), (float) 14.5));
        map.getUiSettings().setZoomControlsEnabled(true);


/*
        Polygon battleField = map.addPolygon(new PolygonOptions()
                .clickable(false)
                .add(
                        new LatLng(30.4367, -84.3061),
                        new LatLng(30.4452, -84.3061),
                        new LatLng(30.4452, -84.28851),
                        new LatLng(30.4367, -84.28851),
                        new LatLng(30.4367, -84.3061)
                ));
        ;
        battleField.setFillColor(0x55000000);
*/
//initialize map zone colors based on scores in database
        int territoryColors[] = timerListener.initTerritoryColors();
        for (int i : territoryColors)
            Log.i("MyMapFragment", String.valueOf(i));


        zone1 = map.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(30.4367, -84.3061),
                        new LatLng(30.44095, -84.3061),
                        new LatLng(30.44095, -84.3003),
                        new LatLng(30.4367, -84.3003),
                        new LatLng(30.4367, -84.3061)
                ));

                zone1.setFillColor(COLOR_GRAY);


        zone2 = map.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(30.44095, -84.3061),
                        new LatLng(30.4452, -84.3061),
                        new LatLng(30.4452, -84.3003),
                        new LatLng(30.44095, -84.3003),
                        new LatLng(30.44095, -84.3061)
                ));

                zone2.setFillColor(COLOR_GRAY);


        zone3 = map.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(30.4452, -84.3003),
                        new LatLng(30.4452, -84.2943),
                        new LatLng(30.44095, -84.2943),
                        new LatLng(30.44095, -84.3003),
                        new LatLng(30.4452, -84.3003)
                ));

                zone3.setFillColor(COLOR_GRAY);

        zone4 = map.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(30.44095, -84.3003),
                        new LatLng(30.44095, -84.2943),
                        new LatLng(30.4367, -84.2943),
                        new LatLng(30.4367, -84.3003),
                        new LatLng(30.44095, -84.3003)
                ));

                zone4.setFillColor(COLOR_GRAY);

        zone5 = map.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(30.4452, -84.2943),
                        new LatLng(30.4452, -84.28851),
                        new LatLng(30.44095, -84.28851),
                        new LatLng(30.44095, -84.2943),
                        new LatLng(30.4452, -84.2943)
                ));

                zone5.setFillColor(COLOR_GRAY);


        zone6 = map.addPolygon(new PolygonOptions()
        .clickable(true)
        .add(
                new LatLng(30.44095, -84.2943),
                new LatLng(30.44095, -84.28851),
                new LatLng(30.4367, -84.28851),
                new LatLng(30.4367, -84.2943),
                new LatLng(30.44095, -84.2943)
        ));

                zone6.setFillColor(COLOR_GRAY);


        if (ContextCompat.checkSelfPermission(getContext(),
                "android.permission.ACCESS_FINE_LOCATION")
                == PackageManager.PERMISSION_GRANTED) {

            map.setMyLocationEnabled(true);
        }
        else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{"android.permission.ACCESS_FINE_LOCATION"},
                    36);
            if (ContextCompat.checkSelfPermission(getContext(),
                    "android.permission.ACCESS_FINE_LOCATION")
                    == PackageManager.PERMISSION_GRANTED) {

                map.setMyLocationEnabled(true);
            }
            else
                Toast.makeText(getContext(), "Please enable location permissions to use this app", Toast.LENGTH_SHORT).show();

        }
        //Toast.makeText(getContext(), "Please enable location permissions to use this app", Toast.LENGTH_SHORT).show();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyMapFragment newInstance(String param1, String param2) {
        MyMapFragment fragment = new MyMapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);

        //setup map
        if (map == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map_fragment);
            mapFragment.getMapAsync(this);
        }

        fsuButton = (Button) view.findViewById(R.id.fsu_button);
        fsuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getContext(),
                        "android.permission.ACCESS_FINE_LOCATION")
                        == PackageManager.PERMISSION_GRANTED) {

                    map.setMyLocationEnabled(true);

                    LocationManager manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                    Location userLocation = manager.getLastKnownLocation(manager.getBestProvider(new Criteria(), false));
                    if (inZone1(userLocation)){
                        switch (timerListener.onTimeUp("Zone1")){
                            case -1:{
                                zone1.setFillColor(COLOR_GRAY);
                                break;
                            }
                            case MainActivity.GOLD_TEAM:{
                                zone1.setFillColor(COLOR_GOLD);
                                break;
                            }
                            case MainActivity.GARNET_TEAM:{
                                zone1.setFillColor(COLOR_GARNET);
                                break;
                            }
                        }
                    }
                    else if (inZone2(userLocation)){
                        switch (timerListener.onTimeUp("Zone2")){
                            case -1:{
                                zone2.setFillColor(COLOR_GRAY);
                                break;
                            }
                            case MainActivity.GOLD_TEAM:{
                                zone2.setFillColor(COLOR_GOLD);
                                break;
                            }
                            case MainActivity.GARNET_TEAM:{
                                zone2.setFillColor(COLOR_GARNET);
                                break;
                            }
                        }
                    }
                    else if (inZone3(userLocation)){
                        switch (timerListener.onTimeUp("Zone3")){
                            case -1:{
                                zone3.setFillColor(COLOR_GRAY);
                                break;
                            }
                            case MainActivity.GOLD_TEAM:{
                                zone3.setFillColor(COLOR_GOLD);
                                break;
                            }
                            case MainActivity.GARNET_TEAM:{
                                zone3.setFillColor(COLOR_GARNET);
                                break;
                            }
                        }
                    }
                    else if (inZone4(userLocation)){
                        switch (timerListener.onTimeUp("Zone4")){
                            case -1:{
                                zone4.setFillColor(COLOR_GRAY);
                                break;
                            }
                            case MainActivity.GOLD_TEAM:{
                                zone4.setFillColor(COLOR_GOLD);
                                break;
                            }
                            case MainActivity.GARNET_TEAM:{
                                zone4.setFillColor(COLOR_GARNET);
                                break;
                            }
                        }
                    }
                    else if (inZone5(userLocation)){
                        switch (timerListener.onTimeUp("Zone5")){
                            case -1:{
                                zone5.setFillColor(COLOR_GRAY);
                                break;
                            }
                            case MainActivity.GOLD_TEAM:{
                                zone5.setFillColor(COLOR_GOLD);
                                break;
                            }
                            case MainActivity.GARNET_TEAM:{
                                zone5.setFillColor(COLOR_GARNET);
                                break;
                            }
                        }
                    }
                    else if (inZone6(userLocation)){
                        switch (timerListener.onTimeUp("Zone6")){
                            case -1:{
                                zone6.setFillColor(COLOR_GRAY);
                                break;
                            }
                            case MainActivity.GOLD_TEAM:{
                                zone6.setFillColor(COLOR_GOLD);
                                break;
                            }
                            case MainActivity.GARNET_TEAM:{
                                zone6.setFillColor(COLOR_GARNET);
                                break;
                            }
                        }
                    }


                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{"android.permission.ACCESS_FINE_LOCATION"},
                            36);
                }
            }
        });

        initButton = (Button) view.findViewById(R.id.init_button);
        initButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO set colors based on database
                int zoneColors[] = timerListener.initTerritoryColors();
                switch (zoneColors[0]) {
                    case MainActivity.GARNET_TEAM:
                        zone1.setFillColor(COLOR_GARNET);
                        break;
                    case MainActivity.GOLD_TEAM:
                        zone1.setFillColor(COLOR_GOLD);
                        break;
                    default:
                        zone1.setFillColor(COLOR_GRAY);
                        break;
                }

                switch (zoneColors[1]) {
                    case MainActivity.GARNET_TEAM:
                        zone2.setFillColor(COLOR_GARNET);
                        break;
                    case MainActivity.GOLD_TEAM:
                        zone2.setFillColor(COLOR_GOLD);
                        break;
                    default:
                        zone2.setFillColor(COLOR_GRAY);
                        break;
                }
                switch (zoneColors[2]) {
                    case MainActivity.GARNET_TEAM:
                        zone3.setFillColor(COLOR_GARNET);
                        break;
                    case MainActivity.GOLD_TEAM:
                        zone3.setFillColor(COLOR_GOLD);
                        break;
                    default:
                        zone3.setFillColor(COLOR_GRAY);
                        break;
                }
                switch (zoneColors[3]) {
                    case MainActivity.GARNET_TEAM:
                        zone4.setFillColor(COLOR_GARNET);
                        break;
                    case MainActivity.GOLD_TEAM:
                        zone4.setFillColor(COLOR_GOLD);
                        break;
                    default:
                        zone4.setFillColor(COLOR_GRAY);
                        break;
                }
                switch (zoneColors[4]) {
                    case MainActivity.GARNET_TEAM:
                        zone5.setFillColor(COLOR_GARNET);
                        break;
                    case MainActivity.GOLD_TEAM:
                        zone5.setFillColor(COLOR_GOLD);
                        break;
                    default:
                        zone5.setFillColor(COLOR_GRAY);
                        break;
                }
                switch (zoneColors[5]) {
                    case MainActivity.GARNET_TEAM:
                        zone6.setFillColor(COLOR_GARNET);
                        break;
                    case MainActivity.GOLD_TEAM:
                        zone6.setFillColor(COLOR_GOLD);
                        break;
                    default:
                        zone6.setFillColor(COLOR_GRAY);
                        break;
                }
            }

        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnTimeUpListener) {
            timerListener = (OnTimeUpListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTimeUpListener");
        }

    }


    @Override
    public void onDetach() {
        super.onDetach();
        timerListener = null;
    }

    //*** HELPER FUNCTIONS ***

    private boolean inZone1(Location l){

        if (PolyUtil.containsLocation(l.getLatitude(), l.getLongitude(), zone1.getPoints(), false)){
            Log.i("MyMapFragment", "Zone1 contains user");
            return true;
        }
        else
            return false;

        //30.4367 < latitude < 30.44095 &&
        //-84.3003 > longitude > -84.3061
    }

    private boolean inZone2(Location l){

        if (PolyUtil.containsLocation(l.getLatitude(), l.getLongitude(), zone2.getPoints(), false)){
            Log.i("MyMapFragment", "Zone2 contains user");
            return true;
        }
        else
            return false;

        //30.44095 < latitude < 30.4452 &&
        //-84.3003 > longitude > -84.3061
    }

    private boolean inZone3(Location l){

        if (PolyUtil.containsLocation(l.getLatitude(), l.getLongitude(), zone3.getPoints(), false)){
            Log.i("MyMapFragment", "Zone3 contains user");
            return true;
        }
        else
            return false;

        //30.44095 < latitude < 30.4452 &&
        //-84.2943 > longitude > -84.3003
    }

    private boolean inZone4(Location l){

        if (PolyUtil.containsLocation(l.getLatitude(), l.getLongitude(), zone4.getPoints(), false)){
            Log.i("MyMapFragment", "Zone4 contains user");
            return true;
        }
        else
            return false;

        //30.4367 < latitude < 30.44095 &&
        //-84.2943 > longitude > -84.3003
    }
    private boolean inZone5(Location l){
        if (PolyUtil.containsLocation(l.getLatitude(),l.getLongitude(),zone5.getPoints(), false)){
            Log.i("MyMapFragment","Zone5 contains user");
            return true;

        }
        else
            return false;

        //30.44095 < latitude < 30.4452 &&
        //-84.28851 > longitude > -84.2943
    }
    private boolean inZone6(Location l){
        if (PolyUtil.containsLocation(l.getLatitude(), l.getLongitude(), zone6.getPoints(), false)){
            Log.i("MyMapFragment", "Zone6 contains user");
            return true;
        }
        else
            return false;

        //30.4367 < latitude < 30.44095 &&
        //-84.28851 > longitude > -84.2943
    }


    //*** /HELPER FUNCTIONS ***


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTimeUpListener {
        // TODO - Implement a timer to listen to
        int onTimeUp(String zoneKey);
        int[] initTerritoryColors();
        void callInitZones();
    }
}
