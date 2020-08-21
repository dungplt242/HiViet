package com.hfad.hiviet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class PlaceItemActivity extends AppCompatActivity {

    private ImageView logo;
    private TextView title;
    private TextView description;
    private Attraction item;
    private MenuItem addItem;
    private MenuItem deleteItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attraction_item_dialogue);
        Intent intent = getIntent();
        initComponents(intent.getIntExtra("index", -1));
        display();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite, menu);
        this.addItem = menu.findItem(R.id.add);
        this.deleteItem = menu.findItem(R.id.delete);
        checkDisplayOption();
        return super.onCreateOptionsMenu(menu);
    }

    private void checkDisplayOption() {
        if (item.isUnlocked()) {
            if (item.isFavorite()) deleteItem.setVisible(true);
            else addItem.setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add: handleAddFavorite(); break;
            case R.id.delete: handleDeleteFavorite(); break;
            default: return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void handleDeleteFavorite() {
        deleteItem.setVisible(false);
        item.removeFromFavorite();
        addItem.setVisible(true);
        updateFavoriteData();
    }

    private void handleAddFavorite() {
        addItem.setVisible(false);
        item.addToFavorite();
        deleteItem.setVisible(true);
        updateFavoriteData();
    }

    private void updateFavoriteData() {
        try {
            PrintStream favoriteFile = new PrintStream(openFileOutput(
                    getString(R.string.favorite_file_name), MODE_PRIVATE));
            AttractionList.updateFavoriteList(favoriteFile);
            favoriteFile.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void display() {
        if (item.isUnlocked()) {
            displayLogo();
            title.setText(item.getTitle());
            description.setText(item.getDescription());
            description.setMovementMethod(new ScrollingMovementMethod());
        }
        else notifyForUnlock();
    }

    private void notifyForUnlock() {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.locked_attraction);
        logo.setImageBitmap(bmp);
        title.setTextColor(Color.GRAY);
        title.setText("Sorry, this attraction is not available");
        description.setText("Select this attraction with less than 100km error to unlock.");
    }

    private void displayLogo() {
        int logoID = getResources().getIdentifier(item.getLogoFileName(),
                "drawable", getPackageName());
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), logoID);
        logo.setImageBitmap(bmp);
    }

    private void initComponents(int index) {
        logo = findViewById(R.id.image_logo);
        title = findViewById(R.id.text_name);
        description = findViewById(R.id.text_description);
        item = AttractionList.builder().getList().get(index);
    }
}