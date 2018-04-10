package com.example.alessandro.bakingapp.util;

import android.content.Context;

import com.example.alessandro.bakingapp.R;

// Image Utils is used to place the right Image in the Recipe Card if an image is not available
// It is used also in Recipe Steps when a video is not available
public final class ImageUtils {

    private ImageUtils() {

    }

    // The drawableId is returned according the Recipe Name
    public static int getImageResourceId(Context context, String recipeName) {
        int drawableId = -1;
        if (recipeName.equals(context.getString(R.string.nutella_pie))) {
            drawableId = R.drawable.nutella_pie;
        } else if (recipeName.equals(context.getString(R.string.brownies))) {
            drawableId = R.drawable.brownies;
        } else if (recipeName.equals(context.getString(R.string.yellow_cake))) {
            drawableId = R.drawable.yellowcake;
        } else if (recipeName.equals(context.getString(R.string.cheesecake))) {
            drawableId = R.drawable.cheesecake;
        }
        return drawableId;
    }
}
