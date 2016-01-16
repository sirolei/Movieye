package com.sirolei.movieye;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sirolei.movieye.fragment.DetailFragment;

/**
 * Created by sansi on 2016/1/16.
 */
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }
}
