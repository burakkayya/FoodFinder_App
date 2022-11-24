package com.example.foodfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

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

public class MainActivity2 extends AppCompatActivity {
    TextView lbl;
    ListView listView;
    String webApiUrl="";
    String ingredients_str="";
    List<Food> foods=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView=findViewById(R.id.listView);

        Bundle extras = getIntent().getExtras();
        List<Ingredient> ingredientList= (List<Ingredient>) extras.getSerializable("list");
        //Toast.makeText(this, String.valueOf(ingredientList.size()), Toast.LENGTH_SHORT).show();
        for(int i=0;i<ingredientList.size();i++ ) {
            //lbl.setText(lbl.getText()+ingredientList.get(i).getName()+", ");
            if (i == ingredientList.size() - 1) {
                ingredients_str = ingredients_str + ingredientList.get(i).getName();
            }
            else {
                ingredients_str = ingredients_str + ingredientList.get(i).getName() + "%2C";
            }
        }

        adaptor2 customAdaptor2 = new adaptor2(this,foods);
        listView.setAdapter(customAdaptor2);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),MainActivity3.class);
                intent.putExtra("Id", foods.get(i).getId());
                startActivity(intent);
            }
        });



        webApiUrl="https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/findByIngredients?ingredients="+ingredients_str+"&number=50&ignorePantry=true&ranking=1";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(webApiUrl).get().
                addHeader("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                .addHeader("X-RapidAPI-Key", "5126b72c36msh1d01c73ff9dbbf7p1befb0jsna417675ce0ac")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MainActivity2.this, "ERROR FAILURE", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if(response.isSuccessful()){
                    String resp=response.body().string();
                    MainActivity2.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {

                                JSONArray jsonArray = new JSONArray(resp);
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    Food tmpFood = new Food();
                                    JSONObject jsonObject2 = (JSONObject) jsonArray.get(i);
                                    tmpFood.setName(jsonObject2.getString("title"));
                                    tmpFood.setId(jsonObject2.getString("id"));
                                    //Toast.makeText(MainActivity2.this, tmpFood.getName().toString(), Toast.LENGTH_SHORT).show();
                                    tmpFood.setImage(jsonObject2.getString("image"));
                                    foods.add(tmpFood);
                                    customAdaptor2.notifyDataSetChanged();


                                }
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                                Toast.makeText(MainActivity2.this, "ERROR" , Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });
        /*JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, webApiUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    for(int i=0;i<response.getJSONArray("data").length();i++){
                        Food tmpFood=new Food();
                        JSONObject jsonObject = (JSONObject) response.getJSONArray("data").get(i);
                        tmpFood.setName(jsonObject.getString("name"));
                        Toast.makeText(MainActivity2.this, tmpFood.getName().toString(), Toast.LENGTH_SHORT).show();
                        tmpFood.setImage(response.getString("image"));

                    }

                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );*/

    }
}
