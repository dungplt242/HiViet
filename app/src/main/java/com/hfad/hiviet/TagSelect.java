package com.hfad.hiviet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class TagSelect extends AppCompatActivity {

    private GridView gridViewTag;
    private GridTagAdapter adapter;
    private List<AttractionTag> displayTag;

    private AdapterView.OnItemClickListener gridViewOnItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (displayTag.get(i).isSelected())
                displayTag.get(i).unselect();
            else displayTag.get(i).select();
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_select);
        loadTags();
        setGridView();
    }

    private void setGridView() {
        gridViewTag = findViewById(R.id.gridViewTag);
        adapter = new GridTagAdapter(this, R.layout.tag_item, displayTag);
        gridViewTag.setAdapter(adapter);
        gridViewTag.setOnItemClickListener(gridViewOnItemClick);
    }

    private void loadTags() {
        displayTag = TagList.builder().getList();
    }
}