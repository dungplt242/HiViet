package com.hfad.hiviet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class GridTagAdapter extends ArrayAdapter<AttractionTag> {

    private Context context;
    private int layoutID;
    private List<AttractionTag> tags;

    public GridTagAdapter(@NonNull Context context, int resource, @NonNull List<AttractionTag> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutID = resource;
        this.tags = objects;
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
        displayTagImage(myViewHolder, tags.get(position));
        displayTagName(myViewHolder, tags.get(position));
        displayTagBorder(myViewHolder, tags.get(position));
        return convertView;
    }

    private void displayTagBorder(MyViewHolder myViewHolder, AttractionTag tag) {
        if (tag.isSelected())
            myViewHolder.getLinearLayout().setBackground(context.getDrawable(R.drawable.tagborder));
        else
            myViewHolder.getLinearLayout().setBackgroundResource(0);
    }

    private MyViewHolder assignViewHolder(View convertView) {
        MyViewHolder myViewHolder = new MyViewHolder();
        myViewHolder.setTextView(convertView.findViewById(R.id.text_name));
        myViewHolder.setImageView(convertView.findViewById(R.id.image_logo));
        myViewHolder.setLinearLayout(convertView.findViewById(R.id.tag_border));
        return myViewHolder;
    }

    private void displayTagImage(MyViewHolder myViewHolder, AttractionTag tag) {
        ImageView imageView = myViewHolder.getImageView();
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), tag.getLogoID());
        imageView.setImageBitmap(bmp);
    }

    private void displayTagName(MyViewHolder myViewHolder, AttractionTag tag) {
        TextView textView = myViewHolder.getTextView();
        textView.setText(tag.getName());
    }

    @Override
    public int getCount() {
        return tags.size();
    }

    private class MyViewHolder {
        private TextView textView;
        private ImageView imageView;
        private LinearLayout linearLayout;

        public TextView getTextView() {
            return textView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public LinearLayout getLinearLayout() {
            return linearLayout;
        }

        public void setTextView(View textView) {
            this.textView = (TextView) textView;
        }

        public void setImageView(View imageView) {
            this.imageView = (ImageView) imageView;
        }

        public void setLinearLayout(View linearLayout) {
            this.linearLayout = (LinearLayout) linearLayout;
        }
    }
}
