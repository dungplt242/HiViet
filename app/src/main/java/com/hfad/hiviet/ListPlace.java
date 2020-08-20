package com.hfad.hiviet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class ListPlace extends AppCompatActivity {

    private GridView gridViewPlaces;
    private GridViewAdapter adapter;
    private List<Attraction> displayList;

    private AdapterView.OnItemClickListener gridViewOnItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            PlaceItem dialogueFragmentItem = PlaceItem.newInstance(i);
            dialogueFragmentItem.show(fragmentManager, null);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_place);
        loadList();
        setupGridView();
        //TODO: ADD DIRECTION FEATURES
    }

    private void loadList() {
        displayList = AttractionList.builder().getList();
    }

    private void setupGridView() {
        gridViewPlaces = findViewById(R.id.gridViewPlace);
        adapter = new GridViewAdapter(this, R.layout.attraction_thumbnail, displayList);
        gridViewPlaces.setAdapter(adapter);
        gridViewPlaces.setOnItemClickListener(gridViewOnItemClick);
    }
}