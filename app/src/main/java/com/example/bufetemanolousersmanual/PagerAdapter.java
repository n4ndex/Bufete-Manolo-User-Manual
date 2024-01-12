package com.example.bufetemanolousersmanual;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PagerAdapter extends androidx.viewpager.widget.PagerAdapter {

    private Context context;
    private String[] texts;
    private int[] imageIds;

    public PagerAdapter(Context context, String[] texts, int[] imageIds) {
        this.context = context;
        this.texts = texts;
        this.imageIds = imageIds;
    }

    @Override
    public int getCount() {
        return texts != null ? texts.length : 0;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.pager_adapter, container, false);

        TextView textView = itemView.findViewById(R.id.pager_Description);
        ImageView imageView = itemView.findViewById(R.id.imageView);

        textView.setText(texts[position]);
        imageView.setImageResource(imageIds[position]);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setTextsAndImages(String[] texts, int[] imageIds) {
        this.texts = texts;
        this.imageIds = imageIds;
    }
}