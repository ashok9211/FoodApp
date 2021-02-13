package com.example.foodapp.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView recipe_title,recipe_publisher ,recipe_score ;
    ImageView recipe_image ;
    RecipeClickListener mRecipeClickListener ;

    public RecipeViewHolder(@NonNull View itemView, RecipeClickListener mRecipeClickListener) {
        super(itemView);
        this.mRecipeClickListener = mRecipeClickListener ;
        recipe_title = itemView.findViewById(R.id.recipe_title_text);
        recipe_publisher = itemView.findViewById(R.id.recipe_publisher_text);
        recipe_score = itemView.findViewById(R.id.recipe_SpooncaluerScore_text);
        recipe_image = itemView.findViewById(R.id.recipe_image_search);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mRecipeClickListener.onRecipeSelected(getAdapterPosition());
    }
}
