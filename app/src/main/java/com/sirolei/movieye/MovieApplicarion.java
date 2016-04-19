package com.sirolei.movieye;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * Created by siro on 2016/4/19.
 */
public class MovieApplicarion extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        context = this;
    }

    public static Context getAppContext(){
        return context;
    }
}
