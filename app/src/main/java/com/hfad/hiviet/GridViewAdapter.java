package com.hfad.hiviet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Attr;

import java.util.List;

public class GridViewAdapter extends ArrayAdapter<Attraction> {

    private Context context;
    private int layoutID;
    private List<Attraction> attractions;

    public GridViewAdapter(@NonNull Context context,
                           int layoutID, @NonNull List<Attraction> attractions) {
        super(context, layoutID, attractions);
        this.context = context;
        this.layoutID = layoutID;
        this.attractions = attractions;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(layoutID, null, false);
        }
        displayAttractionImage(convertView, attractions.get(position));
        displayAttractionName(convertView, attractions.get(position));
        return convertView;
    }

    private void displayAttractionImage(View convertView, Attraction attraction) {
        ImageView imageView = convertView.findViewById(R.id.image_logo);
        int logoID = getLogoId(attraction);
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), logoID);
        imageView.setImageBitmap(bmp);
    }

    private int getLogoId(Attraction attraction) {
        if (!attraction.isUnlocked()) return R.drawable.locked_attraction;
        return context.getResources().getIdentifier(
                attraction.getLogoFileName(), "drawable", context.getPackageName());
    }

    private void displayAttractionName(View convertView, Attraction attraction) {
        TextView textView = convertView.findViewById(R.id.text_name);
        if (attraction.isUnlocked()) textView.setText(attraction.getTitle());
        else textView.setText("LOCKED");
    }

    @Override
    public int getCount() {
        return attractions.size();
    }
}
