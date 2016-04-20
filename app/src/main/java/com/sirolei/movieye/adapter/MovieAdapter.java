package com.sirolei.movieye.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sirolei.movieye.R;
import com.sirolei.movieye.bean.MovieItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by sansi on 2016/1/16.
 */
public class MovieAdapter extends BaseAdapter {
    private final String LOG_TAG = MovieAdapter.class.getSimpleName();
    private ArrayList<MovieItem> movieItemArrayList;
    private Context mContext;
    public static final String PICASSO_TAG = "MovieItem Poster";
    public MovieAdapter(ArrayList<MovieItem> movieItemArrayList, Context context) {
        this.movieItemArrayList = movieItemArrayList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return movieItemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieItemArrayList.get(position);
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
        picasso.load(movieItemArrayList.get(position).getPosterUrl())
                .placeholder(R.mipmap.ic_movie_holder)
                .error(R.mipmap.ic_movie_error)
                .tag(PICASSO_TAG)
                .into(holder.imageView);
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
    }

    public void clear(){
        movieItemArrayList.clear();
    }

    public void addAll(Collection<? extends MovieItem> collection){
        movieItemArrayList.addAll(collection);
        notifyDataSetChanged();
    }
}
