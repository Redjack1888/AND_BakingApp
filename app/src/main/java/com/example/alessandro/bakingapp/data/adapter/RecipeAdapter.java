package com.example.alessandro.bakingapp.data.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alessandro.bakingapp.R;
import com.example.alessandro.bakingapp.data.Recipe;
import com.example.alessandro.bakingapp.util.ImageUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

// Class that manages the list of recipes (RecyclerView)
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final Context context;
    private final RecipeOnClickHandler clickHandler;
    private List<Recipe> recipes;

    public RecipeAdapter(Context context, RecipeOnClickHandler clickHandler) {
        this.context = context;
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.layout_recipe_list_item,
                parent,
                false
        );
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        if (recipes == null) {
            return;
        }
        Recipe recipe = recipes.get(position);
        holder.textViewRecipeName.setText(recipe.getName());
        holder.textViewServes.setText(context.getString(
                R.string.serves,
                String.valueOf(recipe.getServings())
        ));

        // Check the images to use
        if (!TextUtils.isEmpty(recipe.getImageUrl())) {
            Uri uri = Uri.parse(recipe.getImageUrl());
            Picasso.with(context).load(uri).into(holder.imageViewRecipeItem);
        } else {
            // Use ImageUtils to fill images according Recipe Name
            int drawableId = ImageUtils.getImageResourceId(context, recipe.getName());
            Picasso.with(context).load(drawableId).into(holder.imageViewRecipeItem);
        }
    }

    @Override
    public int getItemCount() {
        if (recipes == null) {
            return 0;
        }
        return recipes.size();
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void clear() {
        if (recipes == null) {
            return;
        }
        recipes.clear();
    }

    public interface RecipeOnClickHandler {
        void onClick(Recipe recipe);
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.text_view_recipe_name)
        TextView textViewRecipeName;

        @BindView(R.id.text_view_serves)
        TextView textViewServes;

        @BindView(R.id.image_view_recipe_item)
        ImageView imageViewRecipeItem;

        RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int index = getAdapterPosition();
            clickHandler.onClick(recipes.get(index));
        }
    }
}
