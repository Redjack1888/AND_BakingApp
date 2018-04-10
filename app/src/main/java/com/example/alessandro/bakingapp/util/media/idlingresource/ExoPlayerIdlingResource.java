package com.example.alessandro.bakingapp.util.media.idlingresource;

import android.os.Build;
import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class ExoPlayerIdlingResource implements IdlingResource {

    private final AtomicBoolean idleNow = new AtomicBoolean(true);
    @Nullable
    private volatile ResourceCallback callback;

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return idleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }

    public void setIdleState(boolean idleNow) {
        this.idleNow.set(idleNow);
        if (idleNow && callback != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Objects.requireNonNull(callback).onTransitionToIdle();
            }
        }
    }
}
