package com.example.foodapp.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecipeTypeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public CircleImageView circleImageView ;
    public TextView recipeName ;
    RecipeClickListener recipeClickListener ;

    public RecipeTypeListViewHolder(@NonNull View itemView , RecipeClickListener recipeClickListener) {
        super(itemView);

        circleImageView = itemView.findViewById(R.id.profile_image);
        recipeName = itemView.findViewById(R.id.recipeListNames);
        this.recipeClickListener = recipeClickListener ;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        recipeClickListener.onCategorySelected(recipeName.getText().toString());
    }
}
