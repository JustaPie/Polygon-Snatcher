package edu.fsu.cs.capturegame;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;


public class GeofenceTransitionsIntentService extends IntentService {
    protected static final String TAG = "GeofenceTransitionsIS";
    List<LatLng> redZone;
    List<LatLng> blueZone;
    List<LatLng> yellowZone;
    List<LatLng> greenZone;

    public GeofenceTransitionsIntentService() {
        super(TAG);  // use TAG to name the IntentService worker thread

        redZone = new ArrayList<LatLng>();
        blueZone = new ArrayList<LatLng>();
        yellowZone = new ArrayList<LatLng>();
        greenZone = new ArrayList<LatLng>();

        redZone.add(new LatLng(30.440534, -84.297978));
        redZone.add(new LatLng(30.435576,-84.297978));
        redZone.add(new LatLng(30.435576, -84.289663));
        redZone.add(new LatLng(30.440534, -84.288204));

        blueZone.add(new LatLng(30.440534, -84.297978));
        blueZone.add(new LatLng(30.444863,-84.297978));
        blueZone.add(new LatLng(30.444863, -84.28829));
        blueZone.add(new LatLng(30.440534, -84.28829));

        yellowZone.add(new LatLng(30.440534, -84.297978));
        yellowZone.add(new LatLng(30.444863,-84.297978));
        yellowZone.add(new LatLng(30.448711, -84.305799));
        yellowZone.add(new LatLng(30.440534, -84.305799));

        greenZone.add(new LatLng(30.440534, -84.297978));
        greenZone.add(new LatLng(30.435576,-84.297978));
        greenZone.add(new LatLng(30.435576, -84.305799));
        greenZone.add(new LatLng(30.440534, -84.305799));
    }

    /*Reference http://io2015codelabs.appspot.com/codelabs/geofences#5 */
    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent event = GeofencingEvent.fromIntent(intent);
        if (event.hasError()) {
            Log.e(TAG, "GeofencingEvent Error: " + event.getErrorCode());
            return;
        }

        String description = getGeofenceTransitionDetails(event);
        LatLng red = new LatLng(30.438055,-84.2934558);

        //REMOVE 'RED' AND ADD CURRENT USER LOCATION

        if(PolyUtil.containsLocation(red, redZone, false))
        {
            sendNotification("Red");
        }
        if(PolyUtil.containsLocation(red, blueZone, false))
        {
            sendNotification("Blue");
        }
        if(PolyUtil.containsLocation(red, yellowZone, false))
        {
            sendNotification("Yellow");
        }
        if(PolyUtil.containsLocation(red, greenZone, false))
        {
            sendNotification("Green");
        }

    }

    private static String getGeofenceTransitionDetails(GeofencingEvent event) {
        String transitionString =
                GeofenceStatusCodes.getStatusCodeString(event.getGeofenceTransition());
        List triggeringIDs = new ArrayList();
        for (Geofence geofence : event.getTriggeringGeofences()) {
            triggeringIDs.add(geofence.getRequestId());
        }
        return String.format("%s: %s", transitionString, TextUtils.join(", ", triggeringIDs));
    }

    private void sendNotification(String notificationDetails) {
        // Create an explicit content Intent that starts MainActivity.
        Intent notificationIntent = new Intent(getApplicationContext(), MapsActivity.class);

        // Get a PendingIntent containing the entire back stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MapsActivity.class).addNextIntent(notificationIntent);
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        // Define the notification settings.
        builder.setColor(Color.RED)
                .setContentTitle("You are in the " + notificationDetails + " zone!")
                .setContentText("Capture the flag!")
                .setContentIntent(notificationPendingIntent)
                .setSmallIcon(R.mipmap.notification_icon)
                .setAutoCancel(true);

        // Fire and notify the built Notification.
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }
}
