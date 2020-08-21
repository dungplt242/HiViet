package com.hfad.hiviet;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TagList {

    private static TagList tagList = null;

    private MutableLiveData<List<AttractionTag>> listAllTags;
    private MutableLiveData<List<Integer>> listSelectedTags;

    private TagList() {
        listAllTags = new MutableLiveData<List<AttractionTag>>(new ArrayList<AttractionTag>());
        listSelectedTags = new MutableLiveData<List<Integer>>(new ArrayList<Integer>());
    }

    public static TagList builder() {
        if (tagList == null) tagList = new TagList();
        return tagList;
    }

    public void loadData(Scanner scanner) {
        while (scanner.hasNextLine()) {
            listAllTags.getValue().add(new AttractionTag(scanner));
        }
        listSelectedTags.getValue().clear();
    }

    public void select(int i) {
        if (!listAllTags.getValue().get(i).isSelected()) {
            listAllTags.getValue().get(i).select();
            listSelectedTags.getValue().add(i);
        }
    }

    public void unselect(int i) {
        if (listAllTags.getValue().get(i).isSelected()) {
            listAllTags.getValue().get(i).unselect();
            Log.d("[TagList]", "Unselecting " + i);
            listSelectedTags.getValue().remove(Integer.valueOf(i));
        }
    }

    public void toggle(int i) {
        if (listAllTags.getValue().get(i).isSelected())
            unselect(i);
        else
            select(i);
    }

    public MutableLiveData<List<AttractionTag>> getList() {
        return listAllTags;
    }

    public MutableLiveData<List<Integer>> getSelectedList() {
        return listSelectedTags;
    }

    public void resetList() {
        for (AttractionTag tag: listAllTags.getValue()) tag.unselect();
        listSelectedTags.getValue().clear();
    }
}
