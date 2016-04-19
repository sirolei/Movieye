package com.sirolei.movieye;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by siro on 2016/4/19.
 */
public class MovieApplicarion extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
