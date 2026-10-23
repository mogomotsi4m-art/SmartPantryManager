package com.example.smartpantrymanager;

import android.content.*;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

public class RecipesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_recipes);
        boolean showAll = getIntent().getBooleanExtra("show_all_recipes", false);
        SafeArea.apply(this);
        TextView title = findViewById(R.id.recipesTitle);
        title.setText(showAll ? "All Saved Recipes" : "Suggested Recipes");
        RecyclerView list = findViewById(R.id.recipesRecycler);
        TextView msg = findViewById(R.id.recipeMessage);
        list.setLayoutManager(new LinearLayoutManager(this));
        DatabaseHelper db = new DatabaseHelper(this);

        java.util.List<Recipe> matches = showAll
                ? db.getRecipes()
                : db.getSuggestedRecipes();
        if (showAll) {
            msg.setText(matches.size() + " saved recipe(s).");
        } else {
            msg.setText(matches.isEmpty()
                    ? "No recipes match yet. Add all required ingredients and quantities."
                    : matches.size() + " recipe(s) match every required ingredient and quantity.");
        }
        list.setAdapter(new RecipeAdapter(matches, r -> {
            Intent i = new Intent(this, RecipeDetailActivity.class);
            i.putExtra("recipeId", r.id);
            startActivity(i);
        }));
        findViewById(R.id.backButton).setOnClickListener(v -> finish());
    }
}