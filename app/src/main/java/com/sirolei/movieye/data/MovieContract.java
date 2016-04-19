package com.sirolei.movieye.data;

import android.provider.BaseColumns;

/**
 * Created by siro on 2016/4/19.
 */
public class MovieContract {


    public static final class MovieEntry implements BaseColumns{
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
        public static final String COLUNM_PRODUCTION_COUNTRIES = "producation_countries";
        public static final String COLUNM_REVENUE = "revunue";
        public static final String COLUNM_RUNTIME = "runtime";
        public static final String COLUNM_STATUS = "status";
        public static final String COLUNM_TAGLINE = "tagline";
        public static final String COLUNM_VOTE_AVERAGE = "vote_average";
        public static final String COLUNM_VOTE_COUNT = "vote_count";
        public static final String COLUNM_POSTER = "poster";
        public static final String COLUNM_BACKDROP = "backdrop";

    }

    public static final class PopMovieEntry implements BaseColumns{
        public static final String TABLE_NAME = "pop_movie";
        public static final String COLUNM_MOVIE_KEY = "movie_id";
        public static final String COLUNM_POPULARITY = "popularity";
        public static final String COLUNM_RELEASE_DATE = "release_date";
    }

    public static final class RatedMovieEntry implements BaseColumns{
        public static final String TABLE_NAME = "rate_movie";
        public static final String COLUNM_MOVIE_KEY = "movie_id";
        public static final String COLUNM_AVERATE_VOTE = "vote_average";
        public static final String COLUNM_RELEASE_DATE = "release_date";
    }


}
