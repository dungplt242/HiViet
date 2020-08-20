package com.hfad.hiviet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

//TODO: IMPLEMENT THIS CLASS
public class AttractionTags {

    private HashMap<String, List<Attraction>> tagMap;
    private static AttractionTags attractionTags = null;

    private AttractionTags() {

    }

    public static AttractionTags builder() {
        return attractionTags;
    }

    public HashMap<String, List<Attraction>> getTagMap() {
        return tagMap;
    }
}
