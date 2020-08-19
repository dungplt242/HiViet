package com.hfad.hiviet;

import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AttractionList {

    private static AttractionList attractionList = null;
    private List<Attraction> list = new ArrayList<>();

    private AttractionList(Scanner scanner) {
        while (scanner.hasNextLine()) list.add(new Attraction(scanner));
        scanner.close();
    }

    public static void loadData(Scanner scanner) {
        if (attractionList == null) attractionList = new AttractionList(scanner);
    }

    //WARNING: Data must be loaded before calling builder
    public static AttractionList builder() {
        return attractionList;
    }

    public List<Attraction> getList() {
        return list;
    }
}
