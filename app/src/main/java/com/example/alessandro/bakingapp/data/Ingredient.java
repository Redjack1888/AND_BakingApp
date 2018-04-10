package com.example.alessandro.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Stores the data of the ingredient.
 */

public class Ingredient implements Parcelable {

    static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
    private final String name;
    private final String measure;
    private final String quantity;

    public Ingredient(String name, String measure, String quantity) {
        this.name = name;
        this.measure = measure;
        this.quantity = quantity;
    }

    private Ingredient(Parcel in) {
        name = in.readString();
        measure = in.readString();
        quantity = in.readString();
    }

    public String getName() {
        return name;
    }

    public String getMeasure() {
        return measure;
    }

    public String getQuantity() {
        return quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(measure);
        parcel.writeString(quantity);
    }
}
