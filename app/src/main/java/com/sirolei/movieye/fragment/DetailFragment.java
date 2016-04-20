package com.sirolei.movieye.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.sirolei.movieye.bean.MovieItem;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        title = (TextView) rootView.findViewById(R.id.fragment_detail_title);
        poster = (ImageView) rootView.findViewById(R.id.fragment_detail_poster);
        synopsis = (TextView) rootView.findViewById(R.id.fragment_detail_synopsis);
        average_vote = (TextView) rootView.findViewById(R.id.fragment_detail_vote);
        runtime = (TextView)rootView.findViewById(R.id.fragment_detail_runtime);
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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (task != null){
            task.unregisterListener();
            task.cancel(true);
        }
        MovieItem movieItem = (MovieItem) getActivity().getIntent().getSerializableExtra("MovieItem");
        int id = movieItem.getId();
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
