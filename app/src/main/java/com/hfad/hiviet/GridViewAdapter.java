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
        MyViewHolder myViewHolder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(layoutID, null, false);
            myViewHolder = assignViewHolder(convertView);
            convertView.setTag(myViewHolder);
        }
        else myViewHolder = (MyViewHolder) convertView.getTag();
        displayAttractionImage(myViewHolder, attractions.get(position));
        displayAttractionName(myViewHolder, attractions.get(position));
        return convertView;
    }

    private MyViewHolder assignViewHolder(View convertView) {
        MyViewHolder myViewHolder = new MyViewHolder();
        myViewHolder.setTextView(convertView.findViewById(R.id.text_name));
        myViewHolder.setImageView(convertView.findViewById(R.id.image_logo));
        return myViewHolder;
    }

    private void displayAttractionImage(MyViewHolder myViewHolder, Attraction attraction) {
        ImageView imageView = myViewHolder.getImageView();
        int logoID = getLogoId(attraction);
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), logoID);
        imageView.setImageBitmap(bmp);
    }

    private int getLogoId(Attraction attraction) {
        if (!attraction.isUnlocked()) return R.drawable.locked_attraction;
        return context.getResources().getIdentifier(
                attraction.getLogoFileName(), "drawable", context.getPackageName());
    }

    private void displayAttractionName(MyViewHolder myViewHolder, Attraction attraction) {
        TextView textView = myViewHolder.getTextView();
        if (attraction.isUnlocked()) textView.setText(attraction.getTitle());
        else textView.setText("LOCKED");
    }

    @Override
    public int getCount() {
        return attractions.size();
    }

    private class MyViewHolder {
        private TextView textView;
        private ImageView imageView;

        public TextView getTextView() {
            return textView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setTextView(View textView) {
            this.textView = (TextView) textView;
        }

        public  void setImageView(View imageView) {
            this.imageView = (ImageView) imageView;
        }
    }
}
