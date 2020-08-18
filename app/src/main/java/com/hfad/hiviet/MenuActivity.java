package com.hfad.hiviet;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MenuActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private GoogleMap.OnMarkerClickListener markerOnClickListener =
            new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            Class activityClass = (Class)marker.getTag();
            if (activityClass != null) {
                Intent intent = new Intent(MenuActivity.this, activityClass);
                startActivity(intent);
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapSettings();
        prepareAssets();
        setupCamera();
    }

    private void setupCamera() {
        CameraPosition cameraPosition = new CameraPosition.Builder().
                target(new LatLng(16.199493,105.3424463)).zoom(5.6f).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        Toast.makeText(this, "Find Vietnam", Toast.LENGTH_LONG).show();
    }

    private void prepareAssets() {
        Marker center = createMarker(new LatLng(15.444571, 106.584812), R.drawable.game_logo);
        Marker hn = createMarker(new LatLng(21.022736, 105.8019441), R.drawable.start_game);
        Marker hcm = createMarker(new LatLng(10.7546664, 106.415029), R.drawable.list_places);
        hn.setTag(GameActivity.class);
        hcm.setTag(ListPlace.class);
    }

    private Marker createMarker(LatLng latLng, int imageId) {
        BitmapDescriptor bitmapDescriptor = createBitMapDescriptor(imageId);
        return mMap.addMarker(new MarkerOptions().position(latLng).icon(bitmapDescriptor));
    }

    private BitmapDescriptor createBitMapDescriptor(int imageId) {
        Bitmap logo = BitmapFactory.decodeResource(getResources(), imageId);
        logo = Bitmap.createScaledBitmap(logo, logo.getWidth(), logo.getHeight(), false);
        return BitmapDescriptorFactory.fromBitmap(logo);
    }

    private void mapSettings() {
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));
        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.setOnMarkerClickListener(markerOnClickListener);
    }
}