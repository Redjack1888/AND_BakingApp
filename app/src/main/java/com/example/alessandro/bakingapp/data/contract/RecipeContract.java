package com.example.alessandro.bakingapp.data.contract;

import android.net.Uri;

// Contract for recipes
public class RecipeContract {

    private static final String AUTHORITY = "com.example.alessandro.bakingapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
}