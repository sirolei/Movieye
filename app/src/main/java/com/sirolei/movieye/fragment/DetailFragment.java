package com.sirolei.movieye.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sirolei.movieye.R;
import com.sirolei.movieye.bean.Movie;
import com.squareup.picasso.Picasso;

/**
 * Created by sansi on 2016/1/16.
 */
public class DetailFragment extends Fragment {

    public DetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        TextView title = (TextView) rootView.findViewById(R.id.fragment_detail_title);
        ImageView poster = (ImageView) rootView.findViewById(R.id.fragment_detail_poster);
        TextView synopsis = (TextView) rootView.findViewById(R.id.fragment_detail_synopsis);
        TextView average_vote = (TextView) rootView.findViewById(R.id.fragment_detail_vote);
        Movie movie = (Movie) getActivity().getIntent().getSerializableExtra("Movie");
        title.setText(movie.getTitle());
        synopsis.setText(movie.getSynopsis());
        average_vote.setText(String.format(getString(R.string.average_vote), movie.getVoteAverage()));
        Picasso.with(getActivity())
                .load(movie.getPosterUrl())
                .placeholder(R.mipmap.ic_movie_holder)
                .error(R.mipmap.ic_movie_error)
                .into(poster);
        return rootView;
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
}
