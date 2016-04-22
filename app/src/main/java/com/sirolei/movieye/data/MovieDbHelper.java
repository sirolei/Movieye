package com.sirolei.movieye.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by siro on 2016/4/19.
 */
public class MovieDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 8;
    static final String DATABASE_NAME = "movie.db";
    private final String TAG = MovieDbHelper.class.getSimpleName();

    final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
            MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieContract.MovieEntry.COLUNM_MOVIE_ID + " INTEGER NOT NULL, " +
            MovieContract.MovieEntry.COLUNM_IMDB_ID + " TEXT NOT NULL, " +
            MovieContract.MovieEntry.COLUNM_ORIGINAL_TITLE + " TEXT NOT NULL, " +
            MovieContract.MovieEntry.COLUNM_TITLE + " TEXT NOT NULL, "+
            MovieContract.MovieEntry.COLUNM_OVERVIEW + " TEXT, " +
            MovieContract.MovieEntry.COLUNM_ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +
            MovieContract.MovieEntry.COLUNM_POPULARITY + " REAL NOT NULL, " +
            MovieContract.MovieEntry.COLUNM_VOTE_AVERAGE + " REAL NOT NULL, " +
            MovieContract.MovieEntry.COLUNM_VOTE_COUNT + " INTEGER NOT NULL, " +
            MovieContract.MovieEntry.COLUNM_BACKDROP + " TEXT, " +
            MovieContract.MovieEntry.COLUNM_POSTER + " TEXT, " +
            MovieContract.MovieEntry.COLUNM_RELEASE_DATE + " TEXT NOT NULL, " +
            MovieContract.MovieEntry.COLUNM_REVENUE + " REAL, " +
            MovieContract.MovieEntry.COLUNM_RUNTIME + " INTEGER NOT NULL, " +
            MovieContract.MovieEntry.COLUNM_TAGLINE + " TEXT, " +
            MovieContract.MovieEntry.COLUNM_STATUS + " TEXT NOT NULL, " +
            MovieContract.MovieEntry.COLUNM_PRODUCTION_COMPANIES + " TEXT NOT NULL, " +
            MovieContract.MovieEntry.COLUNM_PRODUCTION_COUNTRIES + " TEXT NOT NULL, " +
            MovieContract.MovieEntry.COLUNM_FAVORITE + " TEXT NOT NULL DEFAULT false, " +
            MovieContract.MovieEntry.COLUNM_UPDATE_TIME + " REAL NOT NULL, " +
            " UNIQUE (" + MovieContract.MovieEntry.COLUNM_MOVIE_ID + ") ON CONFLICT REPLACE );";
    final String SQL_CREATE_POP_MOVIE_TABLE = "CREATE TABLE " + MovieContract.PopMovieEntry.TABLE_NAME + " (" +
            MovieContract.PopMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieContract.PopMovieEntry.COLUNM_POPULARITY + " REAL NOT NULL, " +
            MovieContract.PopMovieEntry.COLUNM_RELEASE_DATE + " TEXT NOT NULL, " +
            MovieContract.PopMovieEntry.COLUNM_MOVIE_KEY + " INTEGER NOT NULL, " +
            MovieContract.PopMovieEntry.COLUNM_POSTER + " TEXT NOT NULL, " +
            MovieContract.PopMovieEntry.COLUNM_PAGE + " REAL NOT NULL, " +
            " FOREIGN KEY(" + MovieContract.PopMovieEntry.COLUNM_MOVIE_KEY + ") REFERENCES " +
            MovieContract.MovieEntry.TABLE_NAME + " (" + MovieContract.MovieEntry._ID + "), " +
            " UNIQUE (" + MovieContract.PopMovieEntry.COLUNM_MOVIE_KEY + ") ON CONFLICT REPLACE );";

    final String SQL_CREATE_RATE_MOVIE_TABLE = "CREATE TABLE " + MovieContract.RatedMovieEntry.TABLE_NAME + " (" +
            MovieContract.RatedMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieContract.RatedMovieEntry.COLUNM_AVERATE_VOTE + " REAL NOT NULL, " +
            MovieContract.RatedMovieEntry.COLUNM_RELEASE_DATE + " TEXT NOT NULL, " +
            MovieContract.RatedMovieEntry.COLUNM_MOVIE_KEY + " INTEGER NOT NULL, " +
            MovieContract.RatedMovieEntry.COLUNM_POSTER + " TEXT NOT NULL, " +
            MovieContract.RatedMovieEntry.COLUNM_PAGE + " REAL NOT NULL, " +
            " FOREIGN KEY(" + MovieContract.RatedMovieEntry.COLUNM_MOVIE_KEY + ") REFERENCES " +
            MovieContract.MovieEntry.TABLE_NAME + " (" + MovieContract.MovieEntry._ID + "), " +
            " UNIQUE (" + MovieContract.RatedMovieEntry.COLUNM_MOVIE_KEY + ") ON CONFLICT REPLACE );";

    final String SQL_CREATE_VIDEO_TABLE = "CREATE TABLE " + MovieContract.VideoEntry.TABLE_NAME + " (" +
            MovieContract.VideoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieContract.VideoEntry.COLUNM_VIDEO_ID + " TEXT NOT NULL, " +
            MovieContract.VideoEntry.COLUNM_MOVIE_KEY + " INTEGER NOT NULL, " +
            MovieContract.VideoEntry.COLUNM_KEY + " TEXT NOT NULL, " +
            MovieContract.VideoEntry.COLUNM_NAME + " TEXT NOT NULL, " +
            MovieContract.VideoEntry.COLUNM_SITE + " TEXT NOT NULL, " +
            MovieContract.VideoEntry.COLUNM_TYPE + " TEXT NOT NULL," +
            MovieContract.VideoEntry.COLUNM_SIZE + " INTEGER NOT NULL, " +
            " FOREIGN KEY(" + MovieContract.VideoEntry.COLUNM_MOVIE_KEY + ") REFERENCES " +
            MovieContract.MovieEntry.TABLE_NAME + " (" + MovieContract.MovieEntry._ID + ")," +
            " UNIQUE (" + MovieContract.VideoEntry.COLUNM_VIDEO_ID + ") ON CONFLICT REPLACE );";

    final String SQL_CREATE_REVIEW_TABLE = "CREATE TABLE " + MovieContract.ReviewEntry.TABLE_NAME + " (" +
            MovieContract.ReviewEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MovieContract.ReviewEntry.COLUNM_MOVIE_KEY + " INTEGER NOT NULL, " +
            MovieContract.ReviewEntry.COLUNM_PAGE + " INTEGER NOT NULL, " +
            MovieContract.ReviewEntry.COLUNM_TOTAL_PAGE + " INTEGER NOT NULL, " +
            MovieContract.ReviewEntry.COLUNM_AUTHOR + " TEXT NOT NULL, " +
            MovieContract.ReviewEntry.COLUNM_REVIEW_ID + " TEXT NOT NULL," +
            MovieContract.ReviewEntry.COLUNM_URL + " TEXT NOT NULL, " +
            " FOREIGN KEY(" + MovieContract.VideoEntry.COLUNM_MOVIE_KEY + ") REFERENCES " +
            MovieContract.MovieEntry.TABLE_NAME + " (" + MovieContract.MovieEntry._ID + ")," +
            " UNIQUE (" + MovieContract.ReviewEntry.COLUNM_REVIEW_ID + ") ON CONFLICT REPLACE );";

    public MovieDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_MOVIE_TABLE);
        db.execSQL(SQL_CREATE_POP_MOVIE_TABLE);
        db.execSQL(SQL_CREATE_RATE_MOVIE_TABLE);
        db.execSQL(SQL_CREATE_VIDEO_TABLE);
        db.execSQL(SQL_CREATE_REVIEW_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade ");
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.PopMovieEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.RatedMovieEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.VideoEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.ReviewEntry.TABLE_NAME);

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
        db.execSQL(SQL_CREATE_POP_MOVIE_TABLE);
        db.execSQL(SQL_CREATE_RATE_MOVIE_TABLE);
        db.execSQL(SQL_CREATE_VIDEO_TABLE);
        db.execSQL(SQL_CREATE_REVIEW_TABLE);
    }
}
