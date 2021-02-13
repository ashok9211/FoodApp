package com.example.foodapp.Adapters;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.Models.Recipe;
import com.example.foodapp.R;
import com.example.foodapp.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int Recipe_List = 1;
    private static final int Loading_List = 2;
    private static final int Recipe_Type_List = 3 ;
    private static final String TAG = "soki";

    private List<Recipe> mRecipeList;
    private final RecipeClickListener mRecipeClickListener;

    public RecipeRecyclerAdapter(RecipeClickListener mRecipeClickListener) {
        this.mRecipeClickListener = mRecipeClickListener;
    }

    public void setmRecipeList(List<Recipe> mRecipeList) {
        this.mRecipeList = mRecipeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case Recipe_List: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_single_item_view, null);
                return new RecipeViewHolder(view, mRecipeClickListener);
            }
            case Loading_List: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_horizontal_dotted_progress, parent,false);
                return new LoadingViewHolder(view);
            }
            case Recipe_Type_List: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recipenamelist, parent,false);
                return new RecipeTypeListViewHolder(view,mRecipeClickListener);
            }

            default: {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_single_item_view,null);
                return new RecipeViewHolder(view, mRecipeClickListener);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int recipe_ViewTypes = getItemViewType(position);
        if (mRecipeList != null) {
            if (recipe_ViewTypes == Recipe_List) {
//                Picasso.get().load(mRecipeList.get(position).getImage()).into(((RecipeViewHolder) holder).recipe_image);

                Glide.with(holder.itemView.getContext())
                        .load(mRecipeList.get(position).getImage())
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .fitCenter()
                        .into(((RecipeViewHolder) holder).recipe_image);


                ((RecipeViewHolder) holder).recipe_title.setText(mRecipeList.get(position).getTitle());
                ((RecipeViewHolder) holder).recipe_publisher.setText(mRecipeList.get(position).getSourceName());
                ((RecipeViewHolder) holder).recipe_score.setText(String.valueOf(Math.round(mRecipeList.get(position).getSpoonacularScore())));
            }
            else if (recipe_ViewTypes == Recipe_Type_List){
                Uri path = Uri.parse("android.resources://com.example.foodapp/drawable/"+ mRecipeList.get(position).getImage());
                Log.i(TAG, "Hola bro "+path);

                Glide.with(holder.itemView.getContext())
                        .load(path)
                        .fitCenter()
                        .into(((RecipeTypeListViewHolder)holder).circleImageView);

                ((RecipeTypeListViewHolder) holder).recipeName.setText(mRecipeList.get(position).getTitle());
            }
        }
    }

    //getting itemView type in BindViewHolder method
    @Override
    public int getItemViewType(int position) {
        if (mRecipeList.get(position).getReadyInMinutes() == "-1"){
            return Recipe_Type_List ;
        }
        else if (mRecipeList.get(position).getTitle().equals("LOADING...")) {
            return Loading_List;
        }
        else if (position == mRecipeList.size()-1 && position != 0 && !mRecipeList.get(position-1).getTitle().equals("EXHAUSTED...")){
            return Loading_List ;
        }
        else
            return Recipe_List;
    }

    // Displaying Loading
    public void LoadingDisplay() {
        if (!isLoading()) {
            Recipe recipe = new Recipe();
            recipe.setTitle("LOADING...");
            List<Recipe> listofrecipe = new ArrayList<>();
            listofrecipe.add(recipe);
            mRecipeList = listofrecipe;
            Log.e(TAG, "LoadingDisplay: " +mRecipeList );
            notifyDataSetChanged();
        }
    }

    //Display Loading Contents
    public void displayCategories(){
        List<Recipe> contentsList = new ArrayList<>();
        for (int i = 0 ; i < Constants.DEFAULT_SEARCH_CATEGORIES.length;i++){
            Recipe mRecipe = new Recipe();
            mRecipe.setImage(Constants.DEFAULT_SEARCH_CATEGORY_IMAGES[i]);
            mRecipe.setTitle(Constants.DEFAULT_SEARCH_CATEGORIES[i]);
            mRecipe.setReadyInMinutes("-1");
            contentsList.add(mRecipe);
        }
        mRecipeList = contentsList ;
        notifyDataSetChanged();
    }

    // Is List Loading
    public Boolean isLoading() {
        if (mRecipeList != null) {
            if (mRecipeList.size() > 0) {
                if (mRecipeList.get(mRecipeList.size() - 1).getTitle().equals("LOADING...")) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        if (mRecipeList != null) {
            return mRecipeList.size();
        }
        return 0;
    }

    public Recipe getSelectedRecipe(int position){
        if(mRecipeList != null){
            if (mRecipeList.size() > 0){
                return mRecipeList.get(position);
            }
        }
        return null;
    }
}
