package com.sirolei.movieye.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by siro on 2016/4/19.
 */
public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.sirolei.movieye.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "movie";
    public static final String PATH_POP_MOVIE = "movie_pop";
    public static final String PATH_RATE_MOVIE = "movie_rate";
    public static final String PATH_VIDEO = "movie_video";
    public static final String PATH_REVIEW = "movie_review";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;


        public static final String TABLE_NAME = "movie";
        public static final String COLUNM_MOVIE_ID = "movie_id";
        public static final String COLUNM_IMDB_ID = "imdb_id";
        public static final String COLUNM_TITLE = "title";
        public static final String COLUNM_ORIGINAL_TITLE = "original_title";
        public static final String COLUNM_ORIGINAL_LANGUAGE = "original_language";
        public static final String COLUNM_RELEASE_DATE = "release_date";
        public static final String COLUNM_POPULARITY = "popularity";
        public static final String COLUNM_OVERVIEW = "overview";
        public static final String COLUNM_PRODUCTION_COMPANIES = "production_companies";
        public static final String COLUNM_PRODUCTION_COUNTRIES = "production_countries";
        public static final String COLUNM_REVENUE = "revenue";
        public static final String COLUNM_RUNTIME = "runtime";
        public static final String COLUNM_STATUS = "status";
        public static final String COLUNM_TAGLINE = "tagline";
        public static final String COLUNM_VOTE_AVERAGE = "vote_average";
        public static final String COLUNM_VOTE_COUNT = "vote_count";
        public static final String COLUNM_POSTER = "poster";
        public static final String COLUNM_BACKDROP = "backdrop";
        public static final String COLUNM_FAVORITE = "favorite";
        public static final String COLUNM_UPDATE_TIME = "update_time";

        public static Uri buildMovieUrl(long movieId) {
            return ContentUris.withAppendedId(CONTENT_URI, movieId);
        }

        public static long getMovieIdFromMovieUri(Uri uri){
            return Long.parseLong(uri.getPathSegments().get(1));
        }
    }

    public static final class PopMovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_POP_MOVIE).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POP_MOVIE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POP_MOVIE;

        public static final String TABLE_NAME = "movie_pop";
        public static final String COLUNM_PAGE = "page";
        public static final String COLUNM_MOVIE_KEY = "movie_id";
        public static final String COLUNM_POPULARITY = "popularity";
        public static final String COLUNM_RELEASE_DATE = "release_date";
        public static final String COLUNM_POSTER = "poster";

        public static Uri buildMoviePopUri(int page) {
            return ContentUris.withAppendedId(CONTENT_URI, page);
        }

        public static int getPageIdFromPopMovieUri(Uri uri){
            return Integer.parseInt(uri.getPathSegments().get(1));
        }
    }

    public static final class RateMovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_RATE_MOVIE).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RATE_MOVIE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RATE_MOVIE;

        public static final String TABLE_NAME = "movie_rate";
        public static final String COLUNM_PAGE = "page";
        public static final String COLUNM_MOVIE_KEY = "movie_id";
        public static final String COLUNM_AVERATE_VOTE = "vote_average";
        public static final String COLUNM_RELEASE_DATE = "release_date";
        public static final String COLUNM_POSTER = "poster";

        public static Uri buildMovieRateUri(int page) {
            return ContentUris.withAppendedId(CONTENT_URI, page);
        }

        public static int getPageIdFromRateMovieUri(Uri uri){
            return Integer.parseInt(uri.getPathSegments().get(1));
        }
    }

    public static final class VideoEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_VIDEO).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VIDEO;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_VIDEO;

        public static final String TABLE_NAME = "movie_video";
        public static final String COLUNM_VIDEO_ID = "id";
        public static final String COLUNM_KEY = "key";
        public static final String COLUNM_NAME = "name";
        public static final String COLUNM_SITE = "site";
        public static final String COLUNM_SIZE = "size";
        public static final String COLUNM_TYPE = "type";
        public static final String COLUNM_MOVIE_KEY = "movie_id";

        public static Uri buildMovieVideoUrl(long movieId) {
            return ContentUris.withAppendedId(CONTENT_URI, movieId);
        }

        public static long getMovieIdFormVideoUri(Uri uri){
            return Long.parseLong(uri.getPathSegments().get(1));
        }

    }

    public static final class ReviewEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEW).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW;

        public static final String TABLE_NAME = "movie_review";
        public static final String COLUNM_PAGE = "page";
        public static final String COLUNM_TOTAL_PAGE = "total_page";
        public static final String COLUNM_REVIEW_ID = "id";
        public static final String COLUNM_AUTHOR = "author";
        public static final String COLUNM_URL = "url";
        public static final String COLUNM_MOVIE_KEY = "movie_id";

        public static Uri buildMovieReviewUrl(long movieId) {
            return ContentUris.withAppendedId(CONTENT_URI, movieId);
        }

        public static Uri buildMovieReviewWithPageUrl(long movieId, int page) {
            return ContentUris.withAppendedId(ContentUris.withAppendedId(CONTENT_URI, movieId), page);
        }

        public static long getMovieIdFromReviewUri(Uri uri){
            return Long.parseLong(uri.getPathSegments().get(1));
        }

        public static int getPageIdFromReviewUri(Uri uri){
            return Integer.parseInt(uri.getPathSegments().get(2));
        }

    }


}
