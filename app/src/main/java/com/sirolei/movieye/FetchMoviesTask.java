package com.sirolei.movieye;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.sirolei.movieye.data.MovieContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

/**
 * Created by sansi on 2016/1/16.
 */
public class FetchMoviesTask extends AsyncTask<String, Void, Cursor> {

    private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();
    private String popUrl = "http://api.themoviedb.org/3/movie/popular?page=1&api_key=87941f3e4714ec06d5bf65f0a968a61f";
    private String rateUrl = "http://api.themoviedb.org/3/movie/top_rated?page=1&api_key=87941f3e4714ec06d5bf65f0a968a61f";
    private String movieUrl = "http://api.themoviedb.org/3/movie/top_rated?page=1&api_key=87941f3e4714ec06d5bf65f0a968a61f";
    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String BASE_IMG_URL = "http://image.tmdb.org/t/p/";
    private static final String EWM_PAGE = "page";
    private static final String EWM_APIKEY = "api_key";
    public static final String TYPE_POP = "popular";
    public static final String TYPE_RATE = "top_rated";
    private String type;
    private String page = "1";
    private String moviesStr;
    private OnPostExecuteListener listener;
    @Override
    protected Cursor doInBackground(String... params) {
        type = params[0];
        if (params.length > 1) {
            page = params[1];
        }
        String baseUrlStr = BASE_URL + type;
        Uri uri = Uri.parse(baseUrlStr).buildUpon()
                .appendQueryParameter(EWM_PAGE, page)
                .appendQueryParameter(EWM_APIKEY, BuildConfig.TMDB_API_KEY)
                .build();
        String buildUrlStr = uri.toString();
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            Log.d(LOG_TAG, buildUrlStr);
            URL url = new URL(buildUrlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream ins = connection.getInputStream();
            if (ins == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(ins));
            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            moviesStr = buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            return getMoviesFromJson(moviesStr, type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Cursor getMoviesFromJson(String moviesStr, String type) throws JSONException {
        // all results
        final String OWM_PAGE = "page";
        final String OWM_RESULTS = "results";
        final String OWM_TOTAL_RESULTS = "total_results";
        final String OWM_TOTAL_PAGES = "total_pages";
        // result item
        final String OWM_POSTER_PATH = "poster_path";
        final String OWM_ADULT = "adult";
        final String OWM_OVERVIEW = "overview";
        final String OWM_RELEASE_DATE = "release_date";
        final String OWM_ID = "id";
        final String OWM_ORIGIN_TITLE = "original_title";
        final String OWM_TITLE = "title";
        final String OWM_BACKDROP = "backdrop_path";
        final String OWM_POPULARITY = "popularity";
        final String OWM_VOTE_COUNT = "vote_count";
        final String OWM_VIDEO = "video";
        final String OWM_VOTE_AVERAGE = "vote_average";
        JSONObject moviesJson = new JSONObject(moviesStr);
        if (moviesJson == null){
            Log.d(LOG_TAG, "moviesJson null");
            return null;
        }

        if (!moviesJson.has(OWM_RESULTS)){
            Log.d(LOG_TAG, "moviesJson no results");
        }
        JSONArray moviesArray = moviesJson.getJSONArray(OWM_RESULTS);
        if (moviesArray == null || moviesArray.length() == 0){
            Log.d(LOG_TAG, "no movies in result list");
            return null;
        }

        int amount = moviesArray.length();
        Vector<ContentValues> cVVector = new Vector<ContentValues>(amount);

        for (int i = 0; i < amount; i++){
            JSONObject movieJsonObj = moviesArray.getJSONObject(i);
            ContentValues contentValues = new ContentValues();
            if (type == TYPE_POP){
                contentValues.put(MovieContract.PopMovieEntry.COLUNM_PAGE, moviesJson.getInt(OWM_PAGE));
                contentValues.put(MovieContract.PopMovieEntry.COLUNM_MOVIE_KEY, movieJsonObj.getInt(OWM_ID));
                contentValues.put(MovieContract.PopMovieEntry.COLUNM_POPULARITY, movieJsonObj.getDouble(OWM_POPULARITY));
                contentValues.put(MovieContract.PopMovieEntry.COLUNM_RELEASE_DATE, movieJsonObj.getString(OWM_RELEASE_DATE));
                contentValues.put(MovieContract.PopMovieEntry.COLUNM_POSTER, buildImageUrl(movieJsonObj.getString(OWM_POSTER_PATH)));
            } else if (type == TYPE_RATE){
                contentValues.put(MovieContract.RateMovieEntry.COLUNM_PAGE, moviesJson.getInt(OWM_PAGE));
                contentValues.put(MovieContract.RateMovieEntry.COLUNM_MOVIE_KEY, movieJsonObj.getInt(OWM_ID));
                contentValues.put(MovieContract.RateMovieEntry.COLUNM_AVERATE_VOTE, movieJsonObj.getDouble(OWM_VOTE_AVERAGE));
                contentValues.put(MovieContract.RateMovieEntry.COLUNM_RELEASE_DATE, movieJsonObj.getString(OWM_RELEASE_DATE));
                contentValues.put(MovieContract.RateMovieEntry.COLUNM_POSTER, buildImageUrl(movieJsonObj.getString(OWM_POSTER_PATH)));
            }

            cVVector.add(contentValues);
        }
        if (cVVector.size() > 0){
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            Uri uri = null;
            if (type == TYPE_POP){
                uri = MovieContract.PopMovieEntry.CONTENT_URI;
            }else if (type == TYPE_RATE){
                uri = MovieContract.RateMovieEntry.CONTENT_URI;
            }
            MovieApplicarion.getAppContext().getContentResolver().bulkInsert(uri, cvArray);
        }

        Uri retUri = type == TYPE_POP ? MovieContract.PopMovieEntry.buildMoviePopUri(1) : MovieContract.RateMovieEntry.buildMovieRateUri(1);

        Cursor cursor = MovieApplicarion.getAppContext().getContentResolver().query(retUri,
                null,
                null,
                null,
                null);

        return cursor;
    }

    private String buildImageUrl(String imgPath){
        String imgUrl = BASE_IMG_URL + "w185" + imgPath;
        return imgUrl;
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        if (listener != null ){
            listener.onPostExecute(cursor);
        }else {
            Log.d(LOG_TAG, "not post execute.");
        }
    }

    @Override
    protected void onPreExecute() {
        if (listener != null){
            listener.onPreExecute();
        }else {
            Log.d(LOG_TAG, "not post execute.");
        }
    }

    public void setOnPostExecuteListner(OnPostExecuteListener listner){
        this.listener = listner;
    }

    public void unregisterListner(){
        listener = null;
    }

    public interface OnPostExecuteListener{
        public void onPostExecute(Cursor cursor);
        public void onPreExecute();
    }
}
