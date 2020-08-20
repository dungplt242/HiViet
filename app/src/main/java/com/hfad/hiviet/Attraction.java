package com.hfad.hiviet;

import com.google.android.gms.maps.model.LatLng;

import java.io.PrintStream;
import java.util.Scanner;

public class Attraction {
    private int id;
    private LatLng location;
    private String title;
    private String description;
    private String logoFileName;
    //use getResource.getIdentifier(logoFileName, "drawable", getPackageName()) to get id
    private boolean unlocked = false;
    private boolean favorite = false;

    public Attraction(Scanner scan) {
        id = Integer.parseInt(scan.nextLine());
        title = scan.nextLine();
        description = scan.nextLine();
        double lat = Double.parseDouble(scan.nextLine()),
               lng = Double.parseDouble(scan.nextLine());
        location = new LatLng(lat, lng);
        logoFileName = scan.nextLine();
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

    public String getLogoFileName() {
        return logoFileName;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void unlock() {
        unlocked = true;
    }

    public void storeAsUnlocked(PrintStream unlockedFile) {
        unlockedFile.println(id);
    }

    public int getId() {
        return id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void removeFromFavorite() {
        favorite = false;
    }

    public void addToFavorite() {
        favorite = true;
    }
}