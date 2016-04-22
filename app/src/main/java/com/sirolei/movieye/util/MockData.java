package com.sirolei.movieye.util;

import android.content.ContentValues;

import com.sirolei.movieye.data.MovieContract;

/**
 * Created by siro on 2016/4/22.
 */
public class MockData {

    public static ContentValues getMovieContentValue(long movieId){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry.COLUNM_POSTER, "test poster url");
        contentValues.put(MovieContract.MovieEntry.COLUNM_PRODUCTION_COUNTRIES, "test production countries");
        contentValues.put(MovieContract.MovieEntry.COLUNM_BACKDROP, "test backdrop url");
        contentValues.put(MovieContract.MovieEntry.COLUNM_VOTE_COUNT, "231");
        contentValues.put(MovieContract.MovieEntry.COLUNM_IMDB_ID, "test imdb");
        contentValues.put(MovieContract.MovieEntry.COLUNM_UPDATE_TIME, System.currentTimeMillis());
        contentValues.put(MovieContract.MovieEntry.COLUNM_MOVIE_ID, movieId);
        contentValues.put(MovieContract.MovieEntry.COLUNM_ORIGINAL_LANGUAGE, "en");
        contentValues.put(MovieContract.MovieEntry.COLUNM_OVERVIEW, "testReview");
        contentValues.put(MovieContract.MovieEntry.COLUNM_ORIGINAL_TITLE, "test origin title");
        contentValues.put(MovieContract.MovieEntry.COLUNM_POPULARITY, 1.222);
        contentValues.put(MovieContract.MovieEntry.COLUNM_PRODUCTION_COMPANIES, "text production companies");
        contentValues.put(MovieContract.MovieEntry.COLUNM_RELEASE_DATE, "2016-04-22");
        contentValues.put(MovieContract.MovieEntry.COLUNM_REVENUE, 1234455);
        contentValues.put(MovieContract.MovieEntry.COLUNM_RUNTIME, 123);
        contentValues.put(MovieContract.MovieEntry.COLUNM_TITLE, "test title");
        contentValues.put(MovieContract.MovieEntry.COLUNM_STATUS, "release");
        contentValues.put(MovieContract.MovieEntry.COLUNM_TAGLINE, "test tagline");
        contentValues.put(MovieContract.MovieEntry.COLUNM_VOTE_AVERAGE, 85.2);
        return contentValues;
    }

    public static ContentValues getPopMovieContentValue(int movieId){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.PopMovieEntry.COLUNM_POSTER, "testPoster");
        contentValues.put(MovieContract.PopMovieEntry.COLUNM_POPULARITY, 86.88);
        contentValues.put(MovieContract.PopMovieEntry.COLUNM_MOVIE_KEY, movieId);
        contentValues.put(MovieContract.PopMovieEntry.COLUNM_PAGE, 1);
        contentValues.put(MovieContract.PopMovieEntry.COLUNM_RELEASE_DATE, "2015-05-14");
        return contentValues;
    }

    public static ContentValues[] getPopMovieContentValueArray(){
        ContentValues[] contentValueArray = new ContentValues[20];
        for (int i = 0; i < contentValueArray.length; i++){
            contentValueArray[i] =  getPopMovieContentValue((int)(Math.random() * 1000));
        }
        return contentValueArray;
    }

    public static ContentValues getRateMovieContentValue(int movieId){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.RatedMovieEntry.COLUNM_POSTER, "testPoster");
        contentValues.put(MovieContract.RatedMovieEntry.COLUNM_AVERATE_VOTE, 86.88);
        contentValues.put(MovieContract.RatedMovieEntry.COLUNM_MOVIE_KEY, movieId);
        contentValues.put(MovieContract.RatedMovieEntry.COLUNM_PAGE, 1);
        contentValues.put(MovieContract.RatedMovieEntry.COLUNM_RELEASE_DATE, "2015-05-14");
        return contentValues;
    }

    public static ContentValues[] getRateMovieContentValueArray(){
        ContentValues[] contentValueArray = new ContentValues[3];
        for (int i = 0; i < contentValueArray.length; i++){
            contentValueArray[i] =  getRateMovieContentValue((int)(Math.random() * 1000));
        }
        return contentValueArray;
    }


    public static ContentValues getVideoContentValue(int movieId){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.VideoEntry.COLUNM_KEY, "shiahsioad");
        contentValues.put(MovieContract.VideoEntry.COLUNM_MOVIE_KEY, movieId);
        contentValues.put(MovieContract.VideoEntry.COLUNM_NAME, "trailer 1");
        contentValues.put(MovieContract.VideoEntry.COLUNM_SIZE, 720);
        contentValues.put(MovieContract.VideoEntry.COLUNM_SITE, "youtube");
        contentValues.put(MovieContract.VideoEntry.COLUNM_TYPE, "trailer");
        contentValues.put(MovieContract.VideoEntry.COLUNM_VIDEO_ID, "shdaioahoidsa");
        return contentValues;
    }

    public static ContentValues[] getVideoContentValueArray(){
        ContentValues[] contentValueArray = new ContentValues[3];
        for (int i = 0; i < contentValueArray.length; i++){
            contentValueArray[i] =  getVideoContentValue((int)(Math.random() * 1000));
        }
        return contentValueArray;
    }

    public static ContentValues getReviewContentValue(int movieId){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.ReviewEntry.COLUNM_MOVIE_KEY, movieId);
        contentValues.put(MovieContract.ReviewEntry.COLUNM_AUTHOR, "siro");
        contentValues.put(MovieContract.ReviewEntry.COLUNM_PAGE, 1);
        contentValues.put(MovieContract.ReviewEntry.COLUNM_REVIEW_ID, "dsahidahoi");
        contentValues.put(MovieContract.ReviewEntry.COLUNM_TOTAL_PAGE, 2);
        contentValues.put(MovieContract.ReviewEntry.COLUNM_URL, "www.movieye.com/review/dsahidahoi");
        return contentValues;
    }

    public static ContentValues[] getReviewContentValueArray(){
        ContentValues[] contentValueArray = new ContentValues[3];
        for (int i = 0; i < contentValueArray.length; i++){
            contentValueArray[i] =  getReviewContentValue((int)(Math.random() * 1000));
        }
        return contentValueArray;
    }
}
