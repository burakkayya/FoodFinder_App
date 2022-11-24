package com.example.foodfinder;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class adaptor2 extends BaseAdapter {

    List<Food> foodList;
    LayoutInflater foodInflater;
    public adaptor2(Activity activity, List<Food> myFoodList) {
        this.foodList= myFoodList;
        foodInflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int i) {
        return foodList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View lineView;
        lineView=foodInflater.inflate(R.layout.food,null);
        ImageView imageView = lineView.findViewById(R.id.imageFood);
        TextView textView = lineView.findViewById(R.id.textFoodName);

        Food food = foodList.get(i);
        textView.setText(food.getName());
        Glide.with(lineView).load(foodList.get(i).getImage()).into(imageView);
        //imageView.setImageURI(Uri.parse(food.getImage().toString()));

        /*ImageButton buttonFav= lineView.findViewById(R.id.buttonFav);
        buttonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(food.getFavStatus()=="0") {
                    food.setFavStatus("1");
                    buttonFav.setImageResource(R.drawable.fav);
                    favFoods.add(food);
                }
                else{
                    food.setFavStatus("0");
                    buttonFav.setImageResource(R.drawable.notfav);
                    favFoods.remove(food);
                }

            }
        });*/

        return lineView;
    }
}
