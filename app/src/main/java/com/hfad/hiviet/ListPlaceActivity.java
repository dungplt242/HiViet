package com.hfad.hiviet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ListPlaceActivity extends AppCompatActivity {

    private RecyclerView tagsView;
    private GridView gridViewPlaces;
    private GridViewAdapter adapter;
    private List<Attraction> displayList;

    private AdapterView.OnItemClickListener gridViewOnItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(ListPlaceActivity.this, PlaceItemActivity.class);
            intent.putExtra("index", displayList.get(i).getId());
            startActivity(intent);
        }
    };

    private class AttractionComparator implements Comparator<Attraction> {
        @Override
        public int compare(Attraction t1, Attraction t2) {
            return Integer.compare(t1.getId(), t2.getId());
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
        TagList.builder().resetList();
        updateGridView();
        initTagBar();
    }

    private void initTagBar() {
        tagsView = findViewById(R.id.tagView);
        tagsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        final TagBarAdapter adapter = new TagBarAdapter(TagList.builder().getList(), TagList.builder().getSelectedList());
        tagsView.setAdapter(adapter);
        final Observer<List<Integer>> selectedListObserver = new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> indices) {
                Log.d("[TagBar]", "selected list change observed");
                adapter.notifyDataSetChanged();
                updateDisplay();
            }
        };
        TagList.builder().getSelectedList().observe(this, selectedListObserver);
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
        initTagBar();
        super.onResume();
    }

    private void updateDisplay() {
        displayList = new ArrayList<>(intersectTagAttraction());
        Collections.sort(displayList, new AttractionComparator());
        updateGridView();
    }

    private Set<Attraction> intersectTagAttraction() {
        Set<Attraction> intersectResult = new HashSet<>(AttractionList.builder().getList());
        for (Integer tagIndex: TagList.builder().getSelectedList().getValue()) {
            AttractionTag tag = TagList.builder().getList().getValue().get(tagIndex);
            intersectResult.retainAll(new HashSet<>(tag.getAttractionBelong()));
        }
        return intersectResult;
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
