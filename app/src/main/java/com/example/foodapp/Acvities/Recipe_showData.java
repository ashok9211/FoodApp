package com.example.foodapp.Acvities;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.foodapp.Models.Recipe;
import com.example.foodapp.R;
import com.example.foodapp.ViewModels.RecipeViewModel;

public class Recipe_showData extends AppCompatActivity {

    private static final String TAG = "soki";
    AppCompatImageView recipe_image;
    TextView recipe_title, recipe_ingredients, recipe_source, recipe_score;
    public RecipeViewModel mRecipeViewModel;
    public String recipeid ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_show);

        recipe_image =  findViewById(R.id.recipe_show_imageView);
        recipe_title = findViewById(R.id.recipe_show_title);
        recipe_ingredients = findViewById(R.id.recipe_show_instructions);
        recipe_source = findViewById(R.id.recipe_show_sourceName);
        recipe_score = findViewById(R.id.recipe_show_spoonacularScore);
        setDataForRecipe();

        mRecipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        mRecipeViewModel.querySingleRecipe(recipeid);
        subscribeViewModel();
    }

    private void subscribeViewModel() {
        mRecipeViewModel.getmRecipebyIdMutableLiveData().observe(this, recipe -> {
            if (recipe != null){
                Log.i(TAG, "onChanged: "+recipe.getSpoonacularScore()+recipe.getTitle()+recipe.getImage());

                Glide.with(getBaseContext())
                        .load(recipe.getImage())
                        .into(recipe_image);

                recipe_title.setText(recipe.getTitle());
                recipe_ingredients.setText(recipe.getInstructions());
                recipe_source.setText(recipe.getSourceName());
                recipe_score.setText(String.valueOf(Math.round(recipe.getSpoonacularScore())));
            }
        });
    }

    public void setDataForRecipe() {
        if (getIntent().hasExtra("recipe_data")) {
            Recipe recipe = getIntent().getParcelableExtra("recipe_data");
            Log.i(TAG, "setDataForRecipe: " + recipe.getTitle());

            if (recipe.getReceipe_id() != null) {
                recipeid = recipe.getReceipe_id();
                Log.d(TAG, "recipe id is "+recipeid);
            }
        }
    }
}
