package com.example.alessandro.bakingapp.activity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.alessandro.bakingapp.R;
import com.example.alessandro.bakingapp.data.adapter.RecipeAdapter;
import com.example.alessandro.bakingapp.data.Recipe;
import com.example.alessandro.bakingapp.util.loader.RecipeLoader;
import com.example.alessandro.bakingapp.util.NetworkUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        RecipeAdapter.RecipeOnClickHandler {

    private static final int RECIPE_LOADER_ID = 0;

    @BindView(R.id.recycler_view_recipes)
    RecyclerView recyclerViewRecipes;

    @BindView(R.id.empty_view)
    TextView emptyView;

    @BindView(R.id.loading_indicator)
    ProgressBar loadingIndicator;

    private RecipeAdapter recipeAdapter;

    private final LoaderManager.LoaderCallbacks<List<Recipe>> recipeLoaderCallbacks =
            new LoaderManager.LoaderCallbacks<List<Recipe>>() {

                @Override
                public RecipeLoader onCreateLoader(int i, Bundle bundle) {
                    //set loadingIndicator Visible
                    loadingIndicator.setVisibility(View.VISIBLE);
                    return new RecipeLoader(MainActivity.this);
                }

                @Override
                public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> recipes) {
                    // set loadingIndicator Gone
                    loadingIndicator.setVisibility(View.GONE);
                    // clear recipeAdapter
                    recipeAdapter.clear();
                    // set recipeAdapter with recipes
                    recipeAdapter.setRecipes(recipes);
                    recipeAdapter.notifyDataSetChanged();
                }

                @Override
                public void onLoaderReset(Loader<List<Recipe>> loader) {
                    // clear recipeAdapter
                    recipeAdapter.clear();
                    recipeAdapter.notifyDataSetChanged();
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        recipeAdapter = new RecipeAdapter(this, this);
        recyclerViewRecipes.setAdapter(recipeAdapter);

        // check if the device is a Smartphone or a Tablet
        // set spanCount to 1 if device is a smartphone
        int spanCount = 1;
        // if the device is a tablet
        // set spanCount to 3
        if (getResources().getBoolean(R.bool.isTablet)) {
            spanCount = 3;
        }

        // Set the RecyclerView to the right layout according to device configuration
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        recyclerViewRecipes.setLayoutManager(layoutManager);

        // check if the network is available
        // Load the Recipes Cards
        if (NetworkUtils.isOnline(this)) {
            getLoaderManager().initLoader(RECIPE_LOADER_ID, null, recipeLoaderCallbacks);
        } else {
            // Load empty layout and set correct views visibility
            loadingIndicator.setVisibility(View.GONE);
            recyclerViewRecipes.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            emptyView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public void onClick(Recipe recipe) {
        // Set intent for RecipeDetailsActivity
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(getString(R.string.recipe), recipe);
        startActivity(intent);
    }
}
