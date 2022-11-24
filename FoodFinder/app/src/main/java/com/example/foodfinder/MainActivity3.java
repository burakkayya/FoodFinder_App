package com.example.foodfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity3 extends AppCompatActivity {
    String webApiUrl;
    ImageView imageViewFood;
    TextView textFoodName;
    TextView textFoodDescription;
    ImageButton buttonFav;
    ImageButton buttonFav2;
    Button buttonGoFav;
    Food tempFood= new Food();
    public static List<String> favList=new ArrayList<>();
    public static SharedPreferences sharedPref;
    public static SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        imageViewFood=findViewById(R.id.imageviewFood);
        textFoodName=findViewById(R.id.textviewFoodName);
        textFoodDescription=(TextView) findViewById(R.id.textviewFoodDescription);
        buttonFav=(ImageButton) findViewById(R.id.buttonFav);
        buttonFav2=(ImageButton) findViewById(R.id.buttonFav2);
        textFoodDescription.setMovementMethod(new ScrollingMovementMethod());
        buttonGoFav=findViewById(R.id.buttonGoFavList);

        sharedPref=this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        favList=new ArrayList<String>(sharedPref.getStringSet("favList", null));
        if(!favList.isEmpty()) {
            //favList=(List<String>) sharedPref.getStringSet("favList", null);
            Toast.makeText(this, "Fav Dolu", Toast.LENGTH_SHORT).show();
        }
        if(favList.isEmpty()){
            Toast.makeText(this, "Fav Bos", Toast.LENGTH_SHORT).show();
        }

        Bundle extras = getIntent().getExtras();
        tempFood.setId(extras.getString("Id").toString());
        Toast.makeText(MainActivity3.this, "ID "+tempFood.getId(), Toast.LENGTH_SHORT).show();

        for(int i=0;i<favList.size();i++){
            Toast.makeText(this, favList.get(i).toString(), Toast.LENGTH_SHORT).show();
            if(favList.get(i).equals(tempFood.getId())){
                buttonFav.setImageResource(R.drawable.fav);
            }
        }

        webApiUrl="https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/"+tempFood.getId()+"/information";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(webApiUrl).get().
                addHeader("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                .addHeader("X-RapidAPI-Key", "5126b72c36msh1d01c73ff9dbbf7p1befb0jsna417675ce0ac")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MainActivity3.this, "ERROR FAILURE", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if(response.isSuccessful()){
                    String resp=response.body().string();
                    MainActivity3.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {

                                    JSONObject jsonObject = new JSONObject(resp);
                                    textFoodName.setText(jsonObject.getString("title"));
                                    textFoodDescription.setText(jsonObject.getString("instructions"));
                                    Glide.with(MainActivity3.this).load(jsonObject.getString("image")).into(imageViewFood);


                            }
                            catch (JSONException e){
                                e.printStackTrace();
                                Toast.makeText(MainActivity3.this, "ERROR" , Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });

        buttonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = ((BitmapDrawable)buttonFav.getDrawable()).getBitmap();
                Bitmap bitmap2 = ((BitmapDrawable)buttonFav2.getDrawable()).getBitmap();
                if(bitmap == bitmap2) {
                    tempFood.setFavStatus("1");
                    favList.add(tempFood.getId().toString().trim());
                    Toast.makeText(MainActivity3.this, favList.get(0).toString(), Toast.LENGTH_SHORT).show();
                    buttonFav.setImageResource(R.drawable.fav);
                }
                else if(bitmap != bitmap2){
                    for(int j =0;j<favList.size();j++){
                        if (favList.get(j).toString().equals(tempFood.getId())){
                            favList.remove(j);
                            tempFood.setFavStatus("0");
                            buttonFav.setImageResource(R.drawable.notfav);

                        }
                    }

                }
                editor.putStringSet("favList", (Set<String>) new HashSet<String>(favList));
                editor.commit();
            }
        });
        buttonGoFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity4.class);
                //intent.putExtra("list",(Serializable) favList);
                intent.putStringArrayListExtra("list",(ArrayList<String>) favList);
                startActivity(intent);
            }
        });


    }
}