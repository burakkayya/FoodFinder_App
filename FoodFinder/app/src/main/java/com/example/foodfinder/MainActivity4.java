package com.example.foodfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity4 extends AppCompatActivity {
    ListView listView;
    String webApiUrl;
    List<Food> foods=new ArrayList<>();
    int i;
    List<String> favList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        listView=findViewById(R.id.favoritesList);

        adaptor3 customAdaptor3 = new adaptor3(this,foods);
        listView.setAdapter(customAdaptor3);

        Bundle extras2 = getIntent().getExtras();
        favList= (List<String>) extras2.getStringArrayList("list");
        //Toast.makeText(this, String.valueOf(favList.size()), Toast.LENGTH_SHORT).show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),MainActivity3.class);
                intent.putExtra("Id", foods.get(i).getId());
                startActivity(intent);
            }
        });
        for(i=0;i<favList.size();i++) {
            Food tmpfood = new Food();
            tmpfood.setId(favList.get(i));
            webApiUrl = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/" + favList.get(i) + "/information";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(webApiUrl).get().
                    addHeader("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                    .addHeader("X-RapidAPI-Key", "5126b72c36msh1d01c73ff9dbbf7p1befb0jsna417675ce0ac")
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(MainActivity4.this, "ERROR FAILURE", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (response.isSuccessful()) {
                        String resp = response.body().string();
                        MainActivity4.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                try {


                                    JSONObject jsonObject = new JSONObject(resp);
                                    tmpfood.setName(jsonObject.getString("title"));
                                    tmpfood.setImage(jsonObject.getString("image"));
                                    foods.add(tmpfood);
                                    customAdaptor3.notifyDataSetChanged();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(MainActivity4.this, "ERROR", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                }
            });


        }




    }
}