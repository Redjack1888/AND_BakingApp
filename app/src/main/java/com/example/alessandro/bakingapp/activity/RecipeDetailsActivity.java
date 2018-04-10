package com.example.alessandro.bakingapp.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.alessandro.bakingapp.R;
import com.example.alessandro.bakingapp.data.adapter.StepAdapter;
import com.example.alessandro.bakingapp.data.Recipe;
import com.example.alessandro.bakingapp.data.Step;
import com.example.alessandro.bakingapp.util.fragment.RecipeDetailsFragment;
import com.example.alessandro.bakingapp.util.fragment.StepDetailsFragment;
import com.example.alessandro.bakingapp.util.media.idlingresource.ExoPlayerIdlingResource;
import com.example.alessandro.bakingapp.util.FragmentUtils;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity implements
        RecipeDetailsFragment.RecipeDetailsOnClickListener,
        StepDetailsFragment.StepDetailsOnClickListener {

    @Nullable
    private ExoPlayerIdlingResource idlingResource;

    private Recipe recipe;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        // Get intent
        Intent intent = getIntent();
        if (intent != null) {
            // Get Recipe
            recipe = intent.getParcelableExtra(getString(R.string.recipe));
        }

        if (recipe != null) {
            // get ActionBar support
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                // Set recipe's Name as ActionBar Title
                actionBar.setTitle(recipe.getName());

            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager.findFragmentByTag(RecipeDetailsFragment.class.getSimpleName()) != null) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putParcelable(getString(R.string.recipe), recipe);

            fragmentManager.beginTransaction()
                    .add(
                            R.id.container_recipe_details,
                            RecipeDetailsFragment.newInstance(bundle),
                            RecipeDetailsFragment.class.getSimpleName()
                    )
                    .commit();

            // if device is a Tablet
            if (getResources().getBoolean(R.bool.isTablet)) {
                List<Step> steps = recipe.getSteps();
                if (steps != null) {
                    bundle = new Bundle();
                    bundle.putParcelableArrayList(getString(R.string.steps), (ArrayList<Step>) steps);
                    bundle.putInt(getString(R.string.step_position), position);
                    FragmentUtils.addDetailsFragment(this, bundle);
                }
            }
        }
    }

    @Override
    public void onStepSelected(int position) {
        // if device is a Tablet
        if (getResources().getBoolean(R.bool.isTablet)) {
            // get step list
            List<Step> steps = recipe.getSteps();
            if (steps != null) {
                this.position = position;

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(getString(R.string.steps), (ArrayList<Step>) steps);
                bundle.putInt(getString(R.string.step_position), position);
                FragmentUtils.replaceDetailsFragment(this, bundle);

                FragmentManager fragmentManager = getSupportFragmentManager();
                RecipeDetailsFragment recipeDetailsFragment = (RecipeDetailsFragment) fragmentManager
                        .findFragmentByTag(RecipeDetailsFragment.class.getSimpleName());
                if (recipeDetailsFragment != null) {
                    StepAdapter stepAdapter = recipeDetailsFragment.getStepAdapter();
                    stepAdapter.setSelectedRowIndex(position);
                    stepAdapter.notifyDataSetChanged();
                }
            }
        } else {
            // If the device is a Smartphone
            // get step list
            List<Step> steps = recipe.getSteps();

            // Set Intent for StepDetailsActivity
            Intent intent = new Intent(this, StepDetailsActivity.class);
            intent.putParcelableArrayListExtra(getString(R.string.steps), (ArrayList<Step>) steps);
            intent.putExtra(getString(R.string.step_position), position);

            // Put extra ActionBar Title. It will be used to set correct images
            // in the steps without videos
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                intent.putExtra(getString(R.string.name), actionBar.getTitle());
            }
            startActivity(intent);
        }
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new ExoPlayerIdlingResource();
        }
        return idlingResource;
    }

    public void setIdleState(boolean idle) {
        if (idlingResource == null) {
            return;
        }
        idlingResource.setIdleState(idle);
    }
}