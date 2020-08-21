package com.hfad.hiviet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListPlace extends AppCompatActivity {

    private GridView gridViewPlaces;
    private GridViewAdapter adapter;
    private List<Attraction> displayList;
    private List<AttractionTag> selected;

    private AdapterView.OnItemClickListener gridViewOnItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(ListPlace.this, PlaceItemActivity.class);
            intent.putExtra("index", displayList.get(i).getId());
            startActivity(intent);
        }
    };

    private class AttractionComparator implements Comparator<Attraction> {
        @Override
        public int compare(Attraction t1, Attraction t2) {
            return ((Integer)t1.getId()).compareTo((Integer)t2.getId());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_place);
        loadFullList();
        initComponents();
    }

    private void initComponents() {
        selected = new ArrayList<>();
        TagList.builder().resetList();
        updateGridView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addtag, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, TagSelect.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        updateDisplay();
        super.onResume();
    }

    private void updateDisplay() {
        updateSelected();
        displayList = new ArrayList<>(intersectTagAttraction());
        Collections.sort(displayList, new AttractionComparator());
        updateGridView();
    }

    private Set<Attraction> intersectTagAttraction() {
        Set<Attraction> intersectResult = new HashSet<>(AttractionList.builder().getList());
        for (AttractionTag tag: selected)
            intersectResult.retainAll(new HashSet<>(tag.getAttractionBelong()));
        return intersectResult;
    }

    private void updateSelected() {
        selected = new ArrayList<>();
        for (AttractionTag tag: TagList.builder().getList()) {
            if (tag.isSelected()) selected.add(tag);
        }
    }

    private void loadFullList() {
        displayList = AttractionList.builder().getList();
    }

    private void updateGridView() {
        gridViewPlaces = findViewById(R.id.gridViewPlace);
        adapter = new GridViewAdapter(this, R.layout.attraction_thumbnail, displayList);
        gridViewPlaces.setAdapter(adapter);
        gridViewPlaces.setOnItemClickListener(gridViewOnItemClick);
    }
}