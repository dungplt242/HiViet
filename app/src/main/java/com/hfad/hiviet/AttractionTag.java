package com.hfad.hiviet;

import android.graphics.Bitmap;

public class AttractionTag {

    private int ID;
    private String name;
    private int logoID;
    private boolean selected;

    public AttractionTag(int ID, String name, int logoID) {
        this.ID = ID;
        this.name = name;
        this.logoID = logoID;
        this.selected = false;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getLogoID() {
        return logoID;
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
