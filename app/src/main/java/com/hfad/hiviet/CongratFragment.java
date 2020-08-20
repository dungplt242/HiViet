package com.hfad.hiviet;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Timer;
import java.util.TimerTask;

public class CongratFragment extends DialogFragment {

    private ImageView logo;
    private TextView message;
    private TextView description;
    private Attraction item;
    private View view;
    private Timer timer = new Timer();

    public static CongratFragment newInstance(int index) {
        Bundle args = new Bundle();
        args.putInt("attraction", index);
        CongratFragment fragment = new CongratFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.congrat_layout, container);
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
        displayLogo();
        message.setText(String.format(getResources().getString(R.string.congrat), item.getTitle()));
    }

    private void displayLogo() {
        int logoID = view.getResources().getIdentifier(
                item.getLogoFileName(), "drawable", view.getContext().getPackageName());
        Bitmap bmp = BitmapFactory.decodeResource(view.getResources(), logoID);
        logo.setImageBitmap(bmp);
    }

    private void initComponents(int index) {
        logo = view.findViewById(R.id.image_logo);
        message = view.findViewById(R.id.text_name);
        item = AttractionList.builder().getList().get(index);
    }
}