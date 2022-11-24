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
import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class adaptor extends BaseAdapter {
    List<Ingredient> ingredientList;
    LayoutInflater ingredientInflater;

    public adaptor(Activity activity, List<Ingredient> myIngredientList) {
        this.ingredientList = myIngredientList;
        ingredientInflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return ingredientList.size();
    }

    @Override
    public Object getItem(int i) {
        return ingredientList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View lineView;
        lineView=ingredientInflater.inflate(R.layout.ingredient,null);
        ImageView imageView = lineView.findViewById(R.id.imageIngredient);
        TextView textView = lineView.findViewById(R.id.textIngredientName);

        Ingredient ingredient = ingredientList.get(i);
        textView.setText(ingredient.getName());
        Glide.with(lineView).load(ingredientList.get(i).getImage()).into(imageView);
        //imageView.setImageResource(R.drawable.ingredients);


        return lineView;
    }



}
