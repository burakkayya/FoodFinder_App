package com.example.foodfinder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class adaptor3 extends BaseAdapter {
    List<Food> favoritesList;
    LayoutInflater favoritesInflater;

    public adaptor3(Activity activity, List<Food> myFavoritesList) {
        this.favoritesList = myFavoritesList;
        favoritesInflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return favoritesList.size();
    }

    @Override
    public Object getItem(int i) {
        return favoritesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View lineView;
        lineView=favoritesInflater.inflate(R.layout.ingredient,null);
        ImageView imageView = lineView.findViewById(R.id.imageIngredient);
        TextView textView = lineView.findViewById(R.id.textIngredientName);

        Food fav = favoritesList.get(i);
        textView.setText(fav.getName());
        Glide.with(lineView).load(favoritesList.get(i).getImage()).into(imageView);
        //imageView.setImageResource(R.drawable.ingredients);


        return lineView;
    }
}
