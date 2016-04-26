package com.sirolei.movieye.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sirolei.movieye.FetchMovieDetailTask;
import com.sirolei.movieye.R;
import com.sirolei.movieye.bean.Movie;
import com.sirolei.movieye.data.MovieContract;
import com.sirolei.movieye.util.TimeUtility;
import com.squareup.picasso.Picasso;

/**
 * Created by sansi on 2016/1/16.
 */
public class DetailFragment extends Fragment implements FetchMovieDetailTask.DetailResultListener, View.OnClickListener{

    public DetailFragment() {
    }

    FetchMovieDetailTask task;
    TextView title ;
    ImageView poster;
    TextView synopsis;
    TextView average_vote;
    Button markAsFavorite;
    TextView runtime;
    TextView releaseDate;

    final String TAG = DetailFragment.class.getSimpleName();
    final String[] movieProjection = new String[]{MovieContract.MovieEntry.COLUNM_TITLE,
            MovieContract.MovieEntry.COLUNM_VOTE_AVERAGE,
            MovieContract.MovieEntry.COLUNM_RELEASE_DATE,
            MovieContract.MovieEntry.COLUNM_OVERVIEW,
            MovieContract.MovieEntry.COLUNM_FAVORITE,
            MovieContract.MovieEntry.COLUNM_RUNTIME,
            MovieContract.MovieEntry.COLUNM_POSTER,
            MovieContract.MovieEntry.COLUNM_UPDATE_TIME};

    final int COLUMN_TITLE_INDEX = 0;
    final int VOLUMN_VOTE_AVERAGE_INDEX = 1;
    final int COLUMN_RELEASE_DATE_INDEX = 2;
    final int COLUMN_OVERVIEW_INDEX = 3;
    final int COLUMN_FAVORITE_INDEX = 4;
    final int COLUMN_RUNTIME_INDEX = 5;
    final int COLUMN_POSTER_INDEX = 6;
    final int COLUMN_UPDATE_TIME_INDEX = 7;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        title = (TextView) rootView.findViewById(R.id.fragment_detail_title);
        poster = (ImageView) rootView.findViewById(R.id.fragment_detail_poster);
        synopsis = (TextView) rootView.findViewById(R.id.fragment_detail_synopsis);
        average_vote = (TextView) rootView.findViewById(R.id.fragment_detail_vote);
        runtime = (TextView)rootView.findViewById(R.id.fragment_detail_runtime);
        releaseDate = (TextView) rootView.findViewById(R.id.fragment_detail_release);
        markAsFavorite = (Button) rootView.findViewById(R.id.fragment_detail_markfavorite);
        markAsFavorite.setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        long id = getActivity().getIntent().getLongExtra(MovieFragment.INTENT_MOVIE_ID, -1);
        if (id == -1){
            return;
        }


        Cursor cursor = getActivity().getContentResolver().query(MovieContract.MovieEntry.buildMovieUrl(id), movieProjection,null, null, null);
        if (!cursor.moveToFirst()){
            updateMovieDetail(String.valueOf(id));
        }else {
            cursor.moveToFirst();
            int lastUpdateDay = TimeUtility.getJulientDay(cursor.getLong(COLUMN_UPDATE_TIME_INDEX));
            int curDay = TimeUtility.getJulientDay(System.currentTimeMillis());
            if (curDay - lastUpdateDay >= 1){
                Log.d(TAG, "need updateMovieDetail from " + lastUpdateDay + " to " + curDay);
                updateMovieDetail(String.valueOf(id));
            }else {
                average_vote.setText(String.format(getString(R.string.average_vote), cursor.getDouble(VOLUMN_VOTE_AVERAGE_INDEX)));
                title.setText(cursor.getString(COLUMN_TITLE_INDEX));
                synopsis.setText(cursor.getString(COLUMN_OVERVIEW_INDEX));
                runtime.setText(getResources().getString(R.string.runtime, cursor.getString(COLUMN_RUNTIME_INDEX)));
                releaseDate.setText(cursor.getString(COLUMN_RELEASE_DATE_INDEX));
                Picasso.with(getActivity())
                        .load(cursor.getString(COLUMN_POSTER_INDEX))
                        .placeholder(R.mipmap.ic_movie_holder)
                        .error(R.mipmap.ic_movie_error)
                        .into(poster);
            }
            cursor.close();
        }
    }

    private void updateMovieDetail(String id){
        if (task != null){
            task.unregisterListener();
            task.cancel(true);
        }
        task = new FetchMovieDetailTask();
        task.setListener(this);
        task.execute(String.valueOf(id));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail, menu);
    }

    @Override
    public void onPostExecute(Movie movie) {
        average_vote.setText(String.format(getString(R.string.average_vote), movie.getVoteAverage()));
        title.setText(movie.getTitle());
        synopsis.setText(movie.getOverview());
        runtime.setText(getResources().getString(R.string.runtime, movie.getRuntime()));
        releaseDate.setText(movie.getReleaseDate());
        Picasso.with(getActivity())
                .load(movie.getPoster())
                .placeholder(R.mipmap.ic_movie_holder)
                .error(R.mipmap.ic_movie_error)
                .into(poster);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (task != null){
            task.unregisterListener();
            task.cancel(true);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
