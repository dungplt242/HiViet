package com.hfad.hiviet;

import com.google.android.gms.maps.model.LatLng;

import java.util.Scanner;

public class Attraction {
    private LatLng location;
    private String title;
    private String description;
    private int logoID;

    public Attraction(double lat, double lng) {
        this.location = new LatLng(lat, lng);
    }

    public Attraction(Scanner scan) {
        title = scan.nextLine();
        description = scan.nextLine();
        double lat = Double.parseDouble(scan.nextLine()),
               lng = Double.parseDouble(scan.nextLine());
        location = new LatLng(lat, lng);
    }

    public LatLng getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getLogoID() {
        return logoID;
    }
}
