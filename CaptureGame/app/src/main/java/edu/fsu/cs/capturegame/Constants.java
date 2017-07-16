package edu.fsu.cs.capturegame;


import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.List;


public class Constants {

    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = 12 * 60 * 60 * 1000;
    public static final float GEOFENCE_RADIUS_IN_METERS = 700;

    public static final HashMap<String, LatLng> LANDMARKS = new HashMap<String, LatLng>();
    static {
        //Red zone
        LANDMARKS.put("RED", new LatLng(30.438055,-84.2934558));

        //Blue zone
        LANDMARKS.put("BLUE", new LatLng(30.4426985,-84.293134));

        // Yellow zone
        LANDMARKS.put("YELLOW", new LatLng(30.4436605,-84.3018885));

        // Green zone
        LANDMARKS.put("GREEN", new LatLng(30.438055,-84.3018885));
    }
}
