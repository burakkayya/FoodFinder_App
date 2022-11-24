package com.example.foodfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    int buf;
    Button addIngredient;
    Button deleteIngredient;
    Button findFood;
    ListView listView;
    EditText textIngredient;
    List<Ingredient> ingredients=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addIngredient = findViewById(R.id.addButton);
        deleteIngredient=findViewById(R.id.deleteButton);
        findFood = findViewById(R.id.findButton);
        listView=findViewById(R.id.ingredientsList);
        textIngredient=findViewById(R.id.ingredientText);

        adaptor customAdaptor = new adaptor(this,ingredients);
        listView.setAdapter(customAdaptor);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                buf=i;
                textIngredient.setText(ingredients.get(buf).getName());
            }
        });

        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ingredient ingredient = new Ingredient(textIngredient.getText().toString());

                String webApiUrl="https://edamam-food-and-grocery-database.p.rapidapi.com/parser?ingr="+ingredient.getName();
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(webApiUrl).get().
                        addHeader("X-RapidAPI-Host", "edamam-food-and-grocery-database.p.rapidapi.com")
                        .addHeader("X-RapidAPI-Key", "5126b72c36msh1d01c73ff9dbbf7p1befb0jsna417675ce0ac")
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(MainActivity.this, "ERROR FAILURE", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        if(response.isSuccessful()){
                            String resp=response.body().string();
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    try {
                                        JSONObject jsonObject = new JSONObject(resp);
                                        JSONArray jsonArray =jsonObject.getJSONArray("parsed");
                                        JSONObject jsonObject2 = (JSONObject) jsonArray.get(0);
                                        JSONObject jsonObject3=jsonObject2.getJSONObject("food");
                                        ingredient.setImage(jsonObject3.getString("image"));
                                        ingredient.setName(jsonObject3.getString("label"));
                                        ingredients.add(ingredient);
                                        customAdaptor.notifyDataSetChanged();

                                        //JSONObject jsonObject2 = (JSONObject) jsonArray.get(i);
                                        // ingredient.setImage(jsonObject2.getString("image"));

                                    }
                                    catch (JSONException e){
                                        e.printStackTrace();
                                        Toast.makeText(MainActivity.this, "ERROR" , Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }
                    }
                });
                textIngredient.setText("");
                Toast.makeText(getApplicationContext(), "Ingredient added", Toast.LENGTH_SHORT).show();
            }
        });

        deleteIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredients.remove(buf);
                customAdaptor.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Ingredient deleted", Toast.LENGTH_SHORT).show();
            }
        });

        findFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity2.class);
                intent.putExtra("list",(Serializable) ingredients);
                startActivity(intent);
            }
        });


    }
}