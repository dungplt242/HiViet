package com.hfad.hiviet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

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
        setupGridView();
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
        updateDisplay();
        return super.onOptionsItemSelected(item);
    }

    private void updateDisplay() {
        updateSelected();
        if (selected.isEmpty()) loadFullList();
        else {
            //get lists, cast to set then intersect
        }
        adapter.notifyDataSetChanged();
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

    private void setupGridView() {
        gridViewPlaces = findViewById(R.id.gridViewPlace);
        adapter = new GridViewAdapter(this, R.layout.attraction_thumbnail, displayList);
        gridViewPlaces.setAdapter(adapter);
        gridViewPlaces.setOnItemClickListener(gridViewOnItemClick);
    }
}