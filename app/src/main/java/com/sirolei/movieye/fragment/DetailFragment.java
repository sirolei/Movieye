package com.sirolei.movieye.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.sirolei.movieye.data.MovieDbHelper;
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
//        average_vote.setText(String.format(getString(R.string.average_vote), movieItem.getVoteAverage()));
//        Picasso.with(getActivity())
//                .load(movieItem.getPosterUrl())
//                .placeholder(R.mipmap.ic_movie_holder)
//                .error(R.mipmap.ic_movie_error)
//                .into(poster);
        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        long id = getActivity().getIntent().getLongExtra(MovieFragment.INTENT_MOVIE_ID, -1);
        if (id == -1){
            return;
        }

        MovieDbHelper dbHelper = new MovieDbHelper(getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = new String[]{MovieContract.MovieEntry.COLUNM_TITLE,
                MovieContract.MovieEntry.COLUNM_VOTE_AVERAGE,
                MovieContract.MovieEntry.COLUNM_RELEASE_DATE,
                MovieContract.MovieEntry.COLUNM_OVERVIEW,
                MovieContract.MovieEntry.COLUNM_FAVORITE,
                MovieContract.MovieEntry.COLUNM_RUNTIME,
                MovieContract.MovieEntry.COLUNM_POSTER,
                MovieContract.MovieEntry.COLUNM_UPDATE_TIME};
        String selection = "movie_id=?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        Cursor cursor = db.query(MovieContract.MovieEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (!cursor.moveToFirst()){
            updateMovieDetail(String.valueOf(id));
        }else {
            cursor.moveToFirst();
            int lastUpdateDay = TimeUtility.getJulientDay(cursor.getLong(7));
            int curDay = TimeUtility.getJulientDay(System.currentTimeMillis());
            if (curDay - lastUpdateDay >= 1){
                Log.d(TAG, "need updateMovieDetail from " + lastUpdateDay + " to " + curDay);
                updateMovieDetail(String.valueOf(id));
            }else {
                average_vote.setText(String.format(getString(R.string.average_vote), cursor.getDouble(1)));
                title.setText(cursor.getString(0));
                synopsis.setText(cursor.getString(3));
                runtime.setText(getResources().getString(R.string.runtime, cursor.getString(5)));
                releaseDate.setText(cursor.getString(2));
                Picasso.with(getActivity())
                        .load(cursor.getString(6))
                        .placeholder(R.mipmap.ic_movie_holder)
                        .error(R.mipmap.ic_movie_error)
                        .into(poster);
            }
        }
        db.close();
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
