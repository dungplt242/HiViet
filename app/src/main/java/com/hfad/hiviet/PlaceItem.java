package com.hfad.hiviet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class PlaceItem extends DialogFragment {

    private ImageView logo;
    private TextView title;
    private TextView description;
    private Attraction item;
    private View view;

    public static PlaceItem newInstance(int index) {
        Bundle args = new Bundle();
        args.putInt("attraction", index);
        PlaceItem fragment = new PlaceItem();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.attraction_item_dialogue, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        int index = getArguments().getInt("attraction");
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        initComponents(index);
        display();
    }

    private void display() {
        if (item.isUnlocked()) {
            displayLogo();
            title.setText(item.getTitle());
            description.setText(item.getDescription());
        }
        else notifyForUnlock();
    }

    private void notifyForUnlock() {
        Bitmap bmp = BitmapFactory.decodeResource(view.getResources(), R.drawable.locked_attraction);
        logo.setImageBitmap(bmp);
        title.setTextColor(Color.GRAY);
        title.setText("Sorry, this attraction is not available");
        description.setText("Select this attraction with less than 100km error to unlock.");
    }

    private void displayLogo() {
        int logoID = view.getResources().getIdentifier(
                item.getLogoFileName(), "drawable", view.getContext().getPackageName());
        Bitmap bmp = BitmapFactory.decodeResource(view.getResources(), logoID);
        logo.setImageBitmap(bmp);
    }

    private void initComponents(int index) {
        logo = view.findViewById(R.id.image_logo);
        title = view.findViewById(R.id.text_name);
        description = view.findViewById(R.id.text_description);
        item = AttractionList.builder().getList().get(index);
    }
}
