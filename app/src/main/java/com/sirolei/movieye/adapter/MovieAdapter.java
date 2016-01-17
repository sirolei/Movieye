package com.sirolei.movieye.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sirolei.movieye.R;
import com.sirolei.movieye.bean.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by sansi on 2016/1/16.
 */
public class MovieAdapter extends BaseAdapter {
    private final String LOG_TAG = MovieAdapter.class.getSimpleName();
    private ArrayList<Movie> movieArrayList;
    private Context mContext;

    public MovieAdapter(ArrayList<Movie> movieArrayList, Context context) {
        this.movieArrayList = movieArrayList;
        mContext = context;
    }

    @Override
    public int getCount() {
        Log.v(LOG_TAG, "get count " + movieArrayList.size());
        return movieArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_movie, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.item_imageview);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        Picasso picasso = Picasso.with(mContext);
        picasso.setIndicatorsEnabled(true);
        picasso.load(movieArrayList.get(position).getImgUrl())
                .placeholder(R.mipmap.ic_movie_holder)
                .error(R.mipmap.ic_movie_error)
                .into(holder.imageView);
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
    }

    public void clear(){
        movieArrayList.clear();
    }

    public void addAll(Collection<? extends Movie> collection){
        movieArrayList.addAll(collection);
        notifyDataSetChanged();
    }
}
