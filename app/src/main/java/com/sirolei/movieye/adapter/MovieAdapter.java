package com.sirolei.movieye.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.sirolei.movieye.R;
import com.sirolei.movieye.data.MovieContract;
import com.squareup.picasso.Picasso;

/**
 * Created by sansi on 2016/1/16.
 */
public class MovieAdapter extends CursorAdapter {

    private final String LOG_TAG = MovieAdapter.class.getSimpleName();
    public static final String PICASSO_TAG = "MovieItem Poster";
    public static final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;

    public MovieAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.imageView = (ImageView) view.findViewById(R.id.item_imageview);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        int columnPosterIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUNM_POSTER);
        String posterUrl = cursor.getString(columnPosterIndex);
        Picasso picasso = Picasso.with(context);
        picasso.setIndicatorsEnabled(true);
        picasso.load(posterUrl)
                .placeholder(R.mipmap.ic_movie_holder)
                .error(R.mipmap.ic_movie_error)
                .tag(PICASSO_TAG)
                .into(holder.imageView);
    }

//    private ArrayList<MovieItem> movieItemArrayList;
//    private Context mContext;

    //    public MovieAdapter(ArrayList<MovieItem> movieItemArrayList, Context context) {
//        this.movieItemArrayList = movieItemArrayList;
//        mContext = context;
//    }
//
//    @Override
//    public int getCount() {
//        return movieItemArrayList.size();
//    }
//0
//    @Override
//    public Object getItem(int position) {
//        return movieItemArrayList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder;
//        if (convertView == null){
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_movie, parent, false);
//            holder = new ViewHolder();
//            holder.imageView = (ImageView) convertView.findViewById(R.id.item_imageview);
//            convertView.setTag(holder);
//        }
//        holder = (ViewHolder) convertView.getTag();
//        Picasso picasso = Picasso.with(mContext);
//        picasso.setIndicatorsEnabled(true);
//        picasso.load(movieItemArrayList.get(position).getPosterUrl())
//                .placeholder(R.mipmap.ic_movie_holder)
//                .error(R.mipmap.ic_movie_error)
//                .tag(PICASSO_TAG)
//                .into(holder.imageView);
//        return convertView;
//    }
//
    class ViewHolder {
        ImageView imageView;
    }

//
//    public void addAll(Collection<? extends MovieItem> collection){
//        movieItemArrayList.addAll(collection);
//        notifyDataSetChanged();
//    }
}
