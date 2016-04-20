package com.sirolei.movieye;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.sirolei.movieye.bean.Movie;
import com.sirolei.movieye.data.MovieContract;
import com.sirolei.movieye.data.MovieDbHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by siro on 2016/4/20.
 */
public class FetchMovieDetailTask extends AsyncTask<String, Void, Movie> {

    private final String TAG = FetchMovieDetailTask.class.getSimpleName();

    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String BASE_IMG_URL = "http://image.tmdb.org/t/p/";
    private static final String EWM_APIKEY = "api_key";

    final String OWM_MOVIE_ID = "id";
    final String OWM_BACKDROP = "backdrop_path";
    final String OWM_IMDB_ID = "imdb_id";
    final String OWM_ORIGINAL_LANGUAGE = "original_language";
    final String OWM_ORIGINAL_TITLE = "original_title";
    final String OWM_OVERVIEW = "overview";
    final String OWM_POPULARITY = "popularity";
    final String OWM_POSTER = "poster_path";
    final String OWM_PRODUCTION_COMPANY = "production_companies";
    final String OWM_PRODUCTION_COUNTRY = "production_countries";
    final String OWM_RELEASE_DATE = "release_date";
    final String OWM_REVENUE = "revenue";
    final String OWM_RUNTIME = "runtime";
    final String OWM_STATUS = "status";
    final String OWM_TAGLINE = "tagline";
    final String OWM_TITLE = "title";
    final String OWM_VOTE_AVERAGE = "vote_average";
    final String OWM_VOTE_COUNT = "vote_count";

    DetailResultListener listener;
    @Override
    protected Movie doInBackground(String... params) {

        String id = params[0];
        String detailUrl = BASE_URL + id;
        Uri uri = Uri.parse(detailUrl).buildUpon().appendQueryParameter(EWM_APIKEY, BuildConfig.TMDB_API_KEY).build();
        Log.d(TAG, "detail Uri is " + uri.toString());
        URL url;
        HttpURLConnection connection;
        BufferedReader reader;
        String detailStr;
        try {
            url = new URL(uri.toString());
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
            while( (line = reader.readLine()) != null){
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0){
                return null;
            }
            detailStr = buffer.toString();
            Log.d(TAG, "detail : " + detailStr);
            Movie movie = getDetailJson(detailStr);
            return movie;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    public Movie getDetailJson(String jsonStr){
        Movie movie = new Movie();
        try {
            JSONObject object = new JSONObject(jsonStr);

            movie.setId(object.getInt(OWM_MOVIE_ID));
            movie.setImdbId(object.getString(OWM_IMDB_ID));
            movie.setPopularity(object.getDouble(OWM_POPULARITY));
            movie.setPoster(buidlImageUrl(object.getString(OWM_POSTER)));
            movie.setBackdrop(buidlImageUrl(object.getString(OWM_BACKDROP)));
            movie.setOriginalLanguage(object.getString(OWM_ORIGINAL_LANGUAGE));
            movie.setTitle(object.getString(OWM_TITLE));
            movie.setOriginalTitle(object.getString(OWM_ORIGINAL_TITLE));
            movie.setOverview(object.getString(OWM_OVERVIEW));
            movie.setProducationCountries(object.getString(OWM_PRODUCTION_COUNTRY));
            movie.setProductionCompanies(object.getString(OWM_PRODUCTION_COMPANY));
            movie.setRevunue(object.getDouble(OWM_REVENUE));
            movie.setReleaseDate(object.getString(OWM_RELEASE_DATE));
            movie.setRuntime(object.getInt(OWM_RUNTIME));
            movie.setStatus(object.getString(OWM_STATUS));
            movie.setTagline(object.getString(OWM_TAGLINE));
            movie.setVoteAverage(object.getDouble(OWM_VOTE_AVERAGE));
            movie.setVoteCount(object.getDouble(OWM_VOTE_COUNT));

            ContentValues contentValues = new ContentValues();
            contentValues.put(MovieContract.MovieEntry.COLUNM_BACKDROP, movie.getBackdrop());
            contentValues.put(MovieContract.MovieEntry.COLUNM_POSTER, movie.getPoster());
            contentValues.put(MovieContract.MovieEntry.COLUNM_IMDB_ID, movie.getImdbId());
            contentValues.put(MovieContract.MovieEntry.COLUNM_MOVIE_ID, movie.getId());
            contentValues.put(MovieContract.MovieEntry.COLUNM_ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
            contentValues.put(MovieContract.MovieEntry.COLUNM_ORIGINAL_TITLE, movie.getOriginalTitle());
            contentValues.put(MovieContract.MovieEntry.COLUNM_OVERVIEW, movie.getOverview());
            contentValues.put(MovieContract.MovieEntry.COLUNM_PRODUCTION_COMPANIES, movie.getProductionCompanies());
            contentValues.put(MovieContract.MovieEntry.COLUNM_PRODUCTION_COUNTRIES, movie.getProducationCountries());
            contentValues.put(MovieContract.MovieEntry.COLUNM_RELEASE_DATE, movie.getReleaseDate());
            contentValues.put(MovieContract.MovieEntry.COLUNM_POPULARITY, movie.getPopularity());
            contentValues.put(MovieContract.MovieEntry.COLUNM_REVENUE, movie.getRevunue());
            contentValues.put(MovieContract.MovieEntry.COLUNM_STATUS, movie.getStatus());
            contentValues.put(MovieContract.MovieEntry.COLUNM_RUNTIME, movie.getRuntime());
            contentValues.put(MovieContract.MovieEntry.COLUNM_TAGLINE, movie.getTagline());
            contentValues.put(MovieContract.MovieEntry.COLUNM_TITLE, movie.getTitle());
            contentValues.put(MovieContract.MovieEntry.COLUNM_VOTE_AVERAGE, movie.getVoteAverage());
            contentValues.put(MovieContract.MovieEntry.COLUNM_VOTE_COUNT, movie.getVoteCount());

            MovieDbHelper dbHelper = new MovieDbHelper(MovieApplicarion.getAppContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.insert(MovieContract.MovieEntry.TABLE_NAME, null, contentValues);
            db.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movie;
    }

    @Override
    protected void onPostExecute(Movie movie) {
        super.onPostExecute(movie);
        if (listener != null){
            listener.onPostExecute(movie);
        }
    }

    public void setListener(DetailResultListener listener){
        this.listener = listener;
    }

    public void unregisterListener(){
        listener = null;
    }

    public interface DetailResultListener {
        public void onPostExecute(Movie movie);
    }

    private String buidlImageUrl(String path){
        return  BASE_IMG_URL + "w185" + path;
    }
}
