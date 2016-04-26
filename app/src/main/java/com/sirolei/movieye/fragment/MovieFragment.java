package com.sirolei.movieye.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.sirolei.movieye.DetailActivity;
import com.sirolei.movieye.FetchMoviesTask;
import com.sirolei.movieye.R;
import com.sirolei.movieye.adapter.MovieAdapter;
import com.sirolei.movieye.data.MovieContract;
import com.sirolei.movieye.util.PreferenceUtility;
import com.squareup.picasso.Picasso;

/**
 * Created by sansi on 2016/1/16.
 */
public class MovieFragment extends Fragment implements AdapterView.OnItemClickListener,
        FetchMoviesTask.OnPostExecuteListener, AbsListView.OnScrollListener, LoaderManager.LoaderCallbacks<Cursor>{

    private final String TAG = MovieFragment.class.getSimpleName();
    private String popUrl = "http://api.themoviedb.org/3/movie/popular?page=1&api_key=87941f3e4714ec06d5bf65f0a968a61f";
    private String rateUrl = "http://api.themoviedb.org/3/movie/top_rated?page=1&api_key=87941f3e4714ec06d5bf65f0a968a61f";
    private String movieUrl = "http://api.themoviedb.org/3/movie/top_rated?page=1&api_key=87941f3e4714ec06d5bf65f0a968a61f";
    private String videoUrl = "http://api.themoviedb.org/3/movie/244786/videos?api_key=87941f3e4714ec06d5bf65f0a968a61f";
    private String imageUrl = "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";
    private GridView mGridView;
    private ProgressBar mProgressBar;
    private MovieAdapter movieAdapter;
    private String currentType = FetchMoviesTask.TYPE_POP;
    private FetchMoviesTask task;
    public static final String INTENT_MOVIE_ID = "movie_id";
    private static final int MOVIE_LOADER = 0;

    public MovieFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.fragment_movie_progressbar);
        mGridView = (GridView) rootView.findViewById(R.id.fragment_movie_gridview);

        movieAdapter = new MovieAdapter(getActivity(), null);
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
            case R.id.action_sort_pop:{
//                Cursor cursor = getActivity().getContentResolver().query(MovieContract.PopMovieEntry.buildMoviePopUri(1),
//                        null,
//                        null,
//                        null,
//                        null);
                currentType = FetchMoviesTask.TYPE_POP;
                if ((System.currentTimeMillis() - PreferenceUtility.getPopUpdatetime(getActivity())) > MovieAdapter.DAY_IN_MILLIS){
                    refresh(FetchMoviesTask.TYPE_POP);
                }else {
                    getLoaderManager().restartLoader(MOVIE_LOADER, null, this);
                }

                return true;
            }
            case R.id.action_sort_rate:{
//                Cursor cursor = getActivity().getContentResolver().query(MovieContract.RateMovieEntry.buildMovieRateUri(1),
//                        null,
//                        null,
//                        null,
//                        null);
                currentType = FetchMoviesTask.TYPE_RATE;
                if ((System.currentTimeMillis() - PreferenceUtility.getRateUpdatetime(getActivity())) > MovieAdapter.DAY_IN_MILLIS){
                    refresh(FetchMoviesTask.TYPE_RATE);
                }else {
                    getLoaderManager().restartLoader(MOVIE_LOADER, null, this);
                }
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if ((System.currentTimeMillis() - PreferenceUtility.getPopUpdatetime(getActivity())) > MovieAdapter.DAY_IN_MILLIS){
            refresh(FetchMoviesTask.TYPE_POP);
        }else {
            getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = movieAdapter.getCursor();
        cursor.moveToPosition(position);
        int columnMovieId = -1;
        if (currentType == FetchMoviesTask.TYPE_POP){
            columnMovieId = cursor.getColumnIndex(MovieContract.PopMovieEntry.COLUNM_MOVIE_KEY);
        }else {
            columnMovieId = cursor.getColumnIndex(MovieContract.RateMovieEntry.COLUNM_MOVIE_KEY);
        }
        long movieId = cursor.getLong(columnMovieId);
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(INTENT_MOVIE_ID, movieId);
        startActivity(intent);
    }

    public void refresh(String type){


        if (task != null){
            task.unregisterListner();
            task.cancel(true);
        }

        task = new FetchMoviesTask();
        task.setOnPostExecuteListner(this);
        task.execute(type);
    }

    @Override
    public void onPostExecute(Cursor cursor) {
        mProgressBar.setVisibility(View.GONE);
        if (cursor != null){
//            Log.d(TAG, "get movies " + cursor.getCount());
//            movieAdapter.swapCursor(cursor);
            getLoaderManager().restartLoader(MOVIE_LOADER, null, this);
            if (currentType == FetchMoviesTask.TYPE_POP){
                PreferenceUtility.setPopUpdateTime(getActivity(), System.currentTimeMillis());
            }else if (currentType == FetchMoviesTask.TYPE_RATE){
                PreferenceUtility.setRateUpdateTime(getActivity(), System.currentTimeMillis());
            }
        }
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

    @Override
    public void onDetach() {
        super.onDetach();
        if (task != null){
            task.unregisterListner();
            task.cancel(true);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = null;
        if (currentType == FetchMoviesTask.TYPE_POP){
            uri = MovieContract.PopMovieEntry.buildMoviePopUri(1);
        }else if (currentType == FetchMoviesTask.TYPE_RATE){
            uri = MovieContract.RateMovieEntry.buildMovieRateUri(1);
        }

        return new CursorLoader(getActivity(), uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        movieAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        movieAdapter.swapCursor(null);
    }
}
