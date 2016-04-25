package com.sirolei.movieye.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sirolei.movieye.R;

/**
 * Created by siro on 2016/4/25.
 */
public class PreferenceUtility {

    public static void setPopUpdateTime(Context context, long updatetime){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String prefkey = context.getString(R.string.last_pop_update_time);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(prefkey, updatetime);
        editor.commit();
    }

    public static long getPopUpdatetime(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String prefkey = context.getString(R.string.last_pop_update_time);
        return sp.getLong(prefkey, 0);

    }

    public static void setRateUpdateTime(Context context, long updatetime){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String prefkey = context.getString(R.string.last_rate_update_time);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(prefkey, updatetime);
        editor.commit();
    }

    public static long getRateUpdatetime(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String prefkey = context.getString(R.string.last_rate_update_time);
        return sp.getLong(prefkey, 0);

    }

}
