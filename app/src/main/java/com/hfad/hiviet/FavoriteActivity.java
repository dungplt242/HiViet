package com.hfad.hiviet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private ListView listViewPlaces;
    private ListViewAdapter adapter;
    private List<Attraction> displayList;

    private AdapterView.OnItemClickListener listViewOnItemClick = new AdapterView.OnItemClickListener() {
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
        setContentView(R.layout.favorite_activity);
        loadList();
        setupListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadList();
        setupListView();
    }

    private void loadList() {
        displayList = new ArrayList<>();
        for (Attraction attraction: AttractionList.builder().getList()) {
            if (attraction.isFavorite()) displayList.add(attraction);
        }
    }

    private void setupListView() {
        listViewPlaces = findViewById(R.id.listViewFavorite);
        adapter = new ListViewAdapter(this, R.layout.favorite_thumbnail, displayList);
        listViewPlaces.setAdapter(adapter);
        listViewPlaces.setOnItemClickListener(listViewOnItemClick);
    }
}