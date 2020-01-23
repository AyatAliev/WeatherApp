package com.example.weatherapp.ui.onboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.weatherapp.R;

import java.util.ArrayList;

public class OnBoardAdapter extends PagerAdapter {
    private ArrayList<OnBoardItem> data = new ArrayList<>();

    public OnBoardAdapter() {

    }

    public void update(ArrayList<OnBoardItem> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        TextView boardText;
        ImageView boardImage;

        LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_onboard, container, false);

        boardText = itemView.findViewById(R.id.tvTitle);
        boardText.setText(data.get(position).getTitle());

        boardImage = itemView.findViewById(R.id.imgOnBoard);
        boardImage.setImageResource(data.get(position).getImage());

        container.addView(itemView);
        return itemView;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
