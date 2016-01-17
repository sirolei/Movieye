package com.sirolei.movieye.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.sirolei.movieye.DetailActivity;
import com.sirolei.movieye.FetchMoviesTask;
import com.sirolei.movieye.R;
import com.sirolei.movieye.adapter.MovieAdapter;
import com.sirolei.movieye.bean.Movie;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by sansi on 2016/1/16.
 */
public class MovieFragment extends Fragment implements AdapterView.OnItemClickListener,
        FetchMoviesTask.OnPostExecuteListener, AbsListView.OnScrollListener{

    private final String TAG = MovieFragment.class.getSimpleName();
    private String popUrl = "http://api.themoviedb.org/3/movie/popular?page=1&api_key=87941f3e4714ec06d5bf65f0a968a61f";
    private String rateUrl = "http://api.themoviedb.org/3/movie/top_rated?page=1&api_key=87941f3e4714ec06d5bf65f0a968a61f";
    private String movieUrl = "http://api.themoviedb.org/3/movie/top_rated?page=1&api_key=87941f3e4714ec06d5bf65f0a968a61f";
    private String videoUrl = "http://api.themoviedb.org/3/movie/244786/videos?api_key=87941f3e4714ec06d5bf65f0a968a61f";
    private String imageUrl = "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";
    private GridView mGridView;
    private ProgressBar mProgressBar;
    private MovieAdapter movieAdapter;
    private String currentType;
    public MovieFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.fragment_movie_progressbar);
        mGridView = (GridView) rootView.findViewById(R.id.fragment_movie_gridview);
//        Movie[] movies = {
//                new Movie(),
//                new Movie(),
//                new Movie(),
//                new Movie(),
//                new Movie(),
//                new Movie()
//        };
        movieAdapter = new MovieAdapter(new ArrayList<Movie>(), getActivity());
        mGridView.setAdapter(movieAdapter);
        mGridView.setOnItemClickListener(this);
        mGridView.setOnScrollListener(this);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        currentType = FetchMoviesTask.TYPE_POP;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movie, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_sort_pop:
                refresh(FetchMoviesTask.TYPE_POP);
                currentType = FetchMoviesTask.TYPE_POP;
                return true;
            case R.id.action_sort_rate:
                refresh(FetchMoviesTask.TYPE_RATE);
                currentType = FetchMoviesTask.TYPE_RATE;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        refresh(currentType);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Movie movie = (Movie) movieAdapter.getItem(position);
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, String.valueOf(movie.getId()));
        startActivity(intent);
    }

    public void refresh(String type){
        FetchMoviesTask task = new FetchMoviesTask();
        task.setOnPostExecuteListner(this);
        task.execute(type);
    }

    @Override
    public void onPostExecute(Movie[] movies) {
        Log.d(TAG, "get movies " + movies.length);
        movieAdapter.clear();
        movieAdapter.addAll(new ArrayList<Movie>(Arrays.asList(movies)));
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onPreExecute() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        final Picasso picasso = Picasso.with(getActivity());
        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_TOUCH_SCROLL){
            picasso.resumeTag(MovieAdapter.PICASSO_TAG);
        }else {
            picasso.pauseTag(MovieAdapter.PICASSO_TAG);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onStop() {
        super.onStop();
        Picasso.with(getActivity()).cancelTag(MovieAdapter.PICASSO_TAG);
    }
}
