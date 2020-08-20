package com.hfad.hiviet;

import java.util.ArrayList;
import java.util.List;

public class TagList {

    private static TagList tagList = null;
    private List<AttractionTag> list = new ArrayList<>( );

    private TagList() {
        list.add(new AttractionTag(0, "A", R.drawable.attraction_logo_ba_be));
        list.add(new AttractionTag(1, "B", R.drawable.attraction_logo_ba_be));
        list.add(new AttractionTag(2, "C", R.drawable.attraction_logo_ba_be));
        list.add(new AttractionTag(3, "D", R.drawable.attraction_logo_ba_be));
        list.add(new AttractionTag(5, "E", R.drawable.attraction_logo_ba_be));
    }

    public static TagList builder() {
        if (tagList == null) tagList = new TagList();
        return tagList;
    }

    public List<AttractionTag> getList() {
        return list;
    }

    public void resetList() {
        for (AttractionTag tag: list) tag.unselect();
    }
}
