package com.hfad.hiviet;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class AttractionTag {

    private int ID;
    private String name;
    private String logoFileName;
    private List<Attraction> attractionBelong;
    private boolean selected;

    public AttractionTag(int ID, String name, int logoID) {
        this.ID = ID;
        this.name = name;
        this.selected = false;
    }

    public AttractionTag(Scanner scanner) {
        ID = Integer.parseInt(scanner.nextLine());
        name = scanner.nextLine();
        logoFileName = scanner.nextLine();
        attractionBelong = new ArrayList<>();
        String temp;
        while (scanner.hasNextLine()) {
            temp = scanner.nextLine();
            if (temp.isEmpty())
                break;
            Attraction attractionItem = AttractionList.builder().getItem(
                    Integer.parseInt(temp));
            attractionItem.setBelongToTag(ID);
            attractionBelong.add(attractionItem);
        }
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getLogoFileName() {
        return logoFileName;
    }

    public boolean isSelected() {
        return selected;
    }

    public void select() {
        selected = true;
    }

    public void unselect() {
        selected = false;
    }
}
