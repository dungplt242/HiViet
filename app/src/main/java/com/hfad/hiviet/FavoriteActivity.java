package com.hfad.hiviet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private GridView gridViewPlaces;
    private GridViewAdapter adapter;
    private List<Attraction> displayList;

    private AdapterView.OnItemClickListener gridViewOnItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(FavoriteActivity.this, PlaceItemActivity.class);
            intent.putExtra("index", displayList.get(i).getId());
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_place);
        loadList();
        setupGridView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadList();
        setupGridView();
    }

    private void loadList() {
        displayList = new ArrayList<>();
        for (Attraction attraction: AttractionList.builder().getList()) {
            if (attraction.isFavorite()) displayList.add(attraction);
        }
    }

    private void setupGridView() {
        gridViewPlaces = findViewById(R.id.gridViewPlace);
        adapter = new GridViewAdapter(this, R.layout.attraction_thumbnail, displayList);
        gridViewPlaces.setAdapter(adapter);
        gridViewPlaces.setOnItemClickListener(gridViewOnItemClick);
    }
}