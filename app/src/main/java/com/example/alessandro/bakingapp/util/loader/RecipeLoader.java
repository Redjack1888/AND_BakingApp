package com.example.alessandro.bakingapp.util.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.alessandro.bakingapp.data.Recipe;
import com.example.alessandro.bakingapp.util.NetworkUtils;

import java.util.List;

public class RecipeLoader extends AsyncTaskLoader<List<Recipe>> {

    private List<Recipe> recipes;

    public RecipeLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        // deliver any previously loaded data immediately
        if (recipes != null) {
            deliverResult(recipes);
        } else {
            // if the data is not currently available, start a load
            forceLoad();
        }
    }

    @Override
    public List<Recipe> loadInBackground() {
        return NetworkUtils.fetchRecipes(getContext());
    }

    @Override
    public void deliverResult(List<Recipe> recipes) {
        this.recipes = recipes;
        super.deliverResult(recipes);
    }
}
