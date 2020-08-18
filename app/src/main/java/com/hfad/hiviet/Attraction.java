package com.hfad.hiviet;

import com.google.android.gms.maps.model.LatLng;

public class Attraction {
    LatLng location;
    Attraction(double lat, double lng) {
        this.location = new LatLng(lat, lng);
    }
    LatLng getLocation() {
        return location;
    }
}
