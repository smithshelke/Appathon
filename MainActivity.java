package com.example.smith.appathon;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smith.appathon.Sources.Geometry;
import com.example.smith.appathon.Sources.Source;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.style.layers.CircleLayer;
import com.mapbox.mapboxsdk.style.layers.PropertyValue;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.services.android.telemetry.location.LocationEnginePriority;
import com.mapbox.services.android.telemetry.location.LostLocationEngine;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import static com.mapbox.mapboxsdk.style.layers.Filter.all;
import static com.mapbox.mapboxsdk.style.layers.Filter.gte;
import static com.mapbox.mapboxsdk.style.layers.Filter.lt;
import static com.mapbox.mapboxsdk.style.layers.Filter.neq;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleBlur;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleRadius;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private MapView mapView;
    private MapboxMap mMap;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    String lat;
    String provider;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;
    // Each point range gets a different fill color.
    final int[][] layers = new int[][]{
            new int[]{150, Color.parseColor("#E55E5E")},
            new int[]{20, Color.parseColor("#F9886C")},
            new int[]{0, Color.parseColor("#FBB03B")}
    };
    DatabaseReference mDatabaseReference;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1Ijoic21pdGhzaGVsa2UiLCJhIjoiY2pmZGt2NTlqNGl1bDMzcGRkeTRocHA5cCJ9.NybgvIUgQU6IJYdu1LjSHg");
        setContentView(R.layout.activity_main);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mMap = mapboxMap;
                getAllSources();
                //addClusteredGeoJsonSource(mapboxMap);
            }
        });
    }

        public void addSourceLocationToFirebase(View view){
            DatabaseReference featuresReference = mDatabaseReference.child("features");
            Location location = new Location(LocationManager.GPS_PROVIDER);
            location.setLatitude(12.9717800);
            location.setLongitude(79.1589040);
            location.setAltitude(0);

            Source source = new Source();
            Geometry g = new Geometry();
            ArrayList<Double> arrayList = new ArrayList<>();
            arrayList.add(location.getLatitude());
            arrayList.add(location.getLongitude());
            arrayList.add(location.getAltitude());
            g.setCoordinates(arrayList);
            source.setGeometry(g);
            featuresReference.push().setValue(source);
        }
    public void getAllSources() {
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                JSONObject json = new JSONObject((Map) dataSnapshot.getValue());
                Log.d(TAG, "onDataChange: JSON " + json.toString());
                addCustomSource(mMap, json.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

   /* public Location getCurrentLocation() {

        LostLocationEngine locationEngine = new LostLocationEngine(MainActivity.this);
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.setInterval(5000);
        locationEngine.activate();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        Location lastLocation = locationEngine.getLastLocation();
        return lastLocation;
    }*/

    @Override
    public void onLocationChanged(Location location) {
       /* txtLat = (TextView) findViewById(R.id.textview1);
        txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());*/
        Source source = new Source();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

   /* private void addClusteredGeoJsonSource(MapboxMap mapboxMap) {

        // Add a new source from our GeoJSON data and set the 'cluster' option to true.
        try {
            mapboxMap.addSource(
                    // Point to GeoJSON data.
                    new GeoJsonSource("earthquakes",
                            new URL("https://www.mapbox.com/mapbox-gl-js/assets/earthquakes.geojson"),
                            new GeoJsonOptions()
                                    .withCluster(true)
                                    .withClusterMaxZoom(15) // Max zoom to cluster points on
                                    .withClusterRadius(20) // Use small cluster radius for the heatmap look
                    )
            );
        } catch (MalformedURLException malformedUrlException) {
            Log.e("heatmapActivity", "Check the URL " + malformedUrlException.getMessage());
        }

        // Use the earthquakes source to create four layers:
        // three for each cluster category, and one for unclustered points

        CircleLayer unclustered = new CircleLayer("unclustered-points", "earthquakes");
        unclustered.setProperties(
                circleColor(Color.parseColor("#FBB03B")),
                circleRadius(40f),
                circleBlur(1f));
        unclustered.setFilter(neq("cluster", true));
        //  mapboxMap.addLayer(unclustered, "building");
        mapboxMap.addLayer(unclustered);

        for (int i = 0; i < layers.length; i++) {

            CircleLayer circles = new CircleLayer("cluster-" + i, "earthquakes");
            circles.setProperties(
                    circleColor(layers[i][1]),
                    circleRadius(40f),
                    circleBlur(1f)
            );
            circles.setFilter(i == 0 ? gte("point_count", layers[i][0]) : all(gte("point_count", layers[i][0]), lt("point_count", layers[i - 1][0]))
            );
            //mapboxMap.addLayer(circles, "building");
            mapboxMap.addLayer(circles);
        }
    }*/

    public void addCustomSource(MapboxMap mapboxMap, String json) {
        mapboxMap.addSource(
                // Point to GeoJSON data.
                new GeoJsonSource("earthquakes",
                        json,
                        new GeoJsonOptions()
                                .withCluster(true)
                                .withClusterMaxZoom(15) // Max zoom to cluster points on
                                .withClusterRadius(20) // Use small cluster radius for the heatmap look
                )
        );
        CircleLayer unclustered = new CircleLayer("unclustered-points", "earthquakes");
        unclustered.setProperties(
                circleColor(Color.parseColor("#FBB03B")),
                circleRadius(70f),
                circleBlur(1f));
        unclustered.setFilter(neq("cluster", true));
        //  mapboxMap.addLayer(unclustered, "building");
        mapboxMap.addLayer(unclustered);

        for (int i = 0; i < layers.length; i++) {

            CircleLayer circles = new CircleLayer("cluster-" + i, "earthquakes");
            circles.setProperties(
                    circleColor(layers[i][1]),
                    circleRadius(70f),
                    circleBlur(1f)
            );
            circles.setFilter(i == 0 ? gte("point_count", layers[i][0]) : all(gte("point_count", layers[i][0]), lt("point_count", layers[i - 1][0]))
            );
            //mapboxMap.addLayer(circles, "building");
            mapboxMap.addLayer(circles);
        }
    }
}
