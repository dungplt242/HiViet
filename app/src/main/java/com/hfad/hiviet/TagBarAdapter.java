package com.hfad.hiviet;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TagBarAdapter extends RecyclerView.Adapter<TagBarAdapter.MyViewHolder> {
    private List<AttractionTag> tags;
    private List<Integer> indices;
    private MutableLiveData<List<AttractionTag>> liveTags;
    private MutableLiveData<List<Integer>> liveIndices;
    public TagBarAdapter(MutableLiveData<List<AttractionTag>> tagList, MutableLiveData<List<Integer>> tagIndices) {
        tags = tagList.getValue();
        indices = tagIndices.getValue();
        liveTags = tagList;
        liveIndices = tagIndices;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final TextView textView = (TextView) LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.tag_text_item, parent, false);
        return new MyViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.textView.setText(tags.get(indices.get(position)).getName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("[ViewHolder]", "Unselect " + position);
                tags.get(indices.get(position)).unselect();
                indices.remove(position);
                liveTags.setValue(liveTags.getValue());
                liveIndices.setValue(liveIndices.getValue());
            }
        });
    }

    @Override
    public int getItemCount() {
        return indices.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public MyViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }
}
