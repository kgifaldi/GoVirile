package com.example.kylegifaldi.atry;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kylegifaldi on 4/23/18.
 */

public class SavedIngredients extends AppCompatActivity {

    private List<Ingredient> ingredientList = new ArrayList<>();
    private RecyclerView recyclerView;
    private IngredientAdapter iAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_ingredients);
        Intent i = getIntent();
        String captured = i.getStringExtra("captured");
        String title = i.getStringExtra("title");

        System.out.println("nonono: ");
        System.out.println(title);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        iAdapter = new IngredientAdapter(ingredientList);
        recyclerView.setHasFixedSize(true);

        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);

        // adding inbuilt divider line
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(iAdapter);

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Ingredient ing = ingredientList.get(position);
                Toast.makeText(getApplicationContext(), ing.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SavedIngredients.this, CaptureDisplay.class);
                i.putExtra("captured", getIntent().getStringExtra("captured"));
                i.putExtra("show_check", "0");
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareMovieData(title, captured);

    }
    private void prepareMovieData(String title, String captured) {

        if(title != null) {
            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            PublicData.captured_array.put(title, today.getTime().toString());
        }
        for(HashMap.Entry<String, String> entry : PublicData.captured_array.entrySet()){
            Ingredient ing = new Ingredient(entry.getKey(), entry.getValue());
            ingredientList.add(ing);

        }
        //ing = new Ingredient("Inside Out", "Animation, Kids & Family", "2015");
        //ingredientList.add(ing);

        //ing =
        //ingredientList.add(ing);
        /*
        ing = new Ingredient("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
        ingredientList.add(ing);

        ing = new Ingredient("Shaun the Sheep", "Animation", "2015");
        ingredientList.add(ing);

        ing = new Ingredient("The Martian", "Science Fiction & Fantasy", "2015");
        ingredientList.add(ing);

        ing = new Ingredient("Mission: Impossible Rogue Nation", "Action", "2015");
        ingredientList.add(ing);

        */
        iAdapter.notifyDataSetChanged();
    }
}
