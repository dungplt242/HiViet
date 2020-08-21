package com.hfad.hiviet;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.io.FileNotFoundException;
import java.util.Scanner;

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
        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            loadAttractionData();
            loadUnlockedAttraction();
            loadFavoriteAttraction();
            loadTags();
            return null;
        }

        private void loadTags() {
            Scanner scanner = new Scanner(getResources().openRawResource(R.raw.tags_data));
            TagList.builder().loadData(scanner);
            scanner.close();
        }

        private void loadFavoriteAttraction() {
            try {
                Scanner scanner = new Scanner(openFileInput(
                        getString(R.string.favorite_file_name)));
                AttractionList.loadFavorite(scanner);
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void loadUnlockedAttraction() {
            try {
                Scanner scanner = new Scanner(openFileInput(
                        getString(R.string.unlocked_file_name)));
                AttractionList.loadUnlocked(scanner);
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void loadAttractionData() {
            Scanner scanner = new Scanner(getResources().openRawResource(R.raw.attraction_data));
            AttractionList.loadData(scanner);
            scanner.close();
        }
    }

    private void setupCamera() {
        CameraPosition cameraPosition = new CameraPosition.Builder().
                target(new LatLng(15.444571, 106.584812)).zoom(5.6f).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void prepareAssets() {
        Marker center = createMarker(new LatLng(15.444571, 106.584812), R.drawable.game_logo);
        Marker hn = createMarker(new LatLng(21.022736, 105.8019441), R.drawable.start_game);
        Marker cmu = createMarker(new LatLng(9.0579187,104.5073392), R.drawable.list_places);
        Marker dalat = createMarker(new LatLng(11.9039022, 108.3806817), R.drawable.favorite);
        hn.setTag(GameActivity.class);
        cmu.setTag(ListPlaceActivity.class);
        dalat.setTag(FavoriteActivity.class);
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
        mMap.setOnMarkerClickListener(markerOnClickListener);
    }
}