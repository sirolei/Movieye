package com.sirolei.movieye.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.sirolei.movieye.util.MockData;

import java.util.HashSet;

/**
 * Created by siro on 2016/4/19.
 */
public class TestDb extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteTheDatabase();
    }

    void deleteTheDatabase(){
        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);
    }

    public void testCreateDb (){
        final HashSet<String> tableNameHashset = new HashSet<>();
        tableNameHashset.add(MovieContract.MovieEntry.TABLE_NAME);
        tableNameHashset.add(MovieContract.PopMovieEntry.TABLE_NAME);
        tableNameHashset.add(MovieContract.RatedMovieEntry.TABLE_NAME);
        tableNameHashset.add(MovieContract.VideoEntry.TABLE_NAME);
        tableNameHashset.add(MovieContract.ReviewEntry.TABLE_NAME);
        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);

        SQLiteDatabase db = new MovieDbHelper(mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type = 'table' ", null);
        assertTrue("This means the database has not create correctly", c.moveToFirst());

        do {
            tableNameHashset.remove(c.getString(0));
        }while(c.moveToNext());

        assertTrue("Error: you did not create all the required tables ", tableNameHashset.isEmpty());

        // check right columns of each table
        // check movie table
        c = db.rawQuery("PRAGMA table_info(" + MovieContract.MovieEntry.TABLE_NAME + ")", null);
        assertTrue("Error: his means that we were unable to query the database for table information", c.moveToFirst());
        final HashSet<String> movieColumnSet = new HashSet<>();
        movieColumnSet.add(MovieContract.MovieEntry._ID);
        movieColumnSet.add(MovieContract.MovieEntry.COLUNM_BACKDROP);
        movieColumnSet.add(MovieContract.MovieEntry.COLUNM_FAVORITE);
        movieColumnSet.add(MovieContract.MovieEntry.COLUNM_IMDB_ID);
        movieColumnSet.add(MovieContract.MovieEntry.COLUNM_MOVIE_ID);
        movieColumnSet.add(MovieContract.MovieEntry.COLUNM_ORIGINAL_LANGUAGE);
        movieColumnSet.add(MovieContract.MovieEntry.COLUNM_ORIGINAL_TITLE);
        movieColumnSet.add(MovieContract.MovieEntry.COLUNM_OVERVIEW);
        movieColumnSet.add(MovieContract.MovieEntry.COLUNM_POPULARITY);
        movieColumnSet.add(MovieContract.MovieEntry.COLUNM_POSTER);
        movieColumnSet.add(MovieContract.MovieEntry.COLUNM_PRODUCTION_COMPANIES);
        movieColumnSet.add(MovieContract.MovieEntry.COLUNM_PRODUCTION_COUNTRIES);
        movieColumnSet.add(MovieContract.MovieEntry.COLUNM_RELEASE_DATE);
        movieColumnSet.add(MovieContract.MovieEntry.COLUNM_REVENUE);
        movieColumnSet.add(MovieContract.MovieEntry.COLUNM_RUNTIME);
        movieColumnSet.add(MovieContract.MovieEntry.COLUNM_STATUS);
        movieColumnSet.add(MovieContract.MovieEntry.COLUNM_TAGLINE);
        movieColumnSet.add(MovieContract.MovieEntry.COLUNM_TITLE);
        movieColumnSet.add(MovieContract.MovieEntry.COLUNM_UPDATE_TIME);
        movieColumnSet.add(MovieContract.MovieEntry.COLUNM_VOTE_AVERAGE);
        movieColumnSet.add(MovieContract.MovieEntry.COLUNM_VOTE_COUNT);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            movieColumnSet.remove(c.getString(columnNameIndex));
        }while (c.moveToNext());

        assertTrue("Error: you did not create all the required colunm for movie table ", movieColumnSet.isEmpty());

        // check table pop movie
        c = db.rawQuery("PRAGMA table_info(" + MovieContract.PopMovieEntry.TABLE_NAME + ")", null);
        assertTrue("means we cannot query the database for the colunm information of table pop_movie", c.moveToFirst());
        final HashSet<String> popMovieColumnSet = new HashSet<>();
        popMovieColumnSet.add(MovieContract.PopMovieEntry.COLUNM_PAGE);
        popMovieColumnSet.add(MovieContract.PopMovieEntry.COLUNM_MOVIE_KEY);
        popMovieColumnSet.add(MovieContract.PopMovieEntry.COLUNM_POPULARITY);
        popMovieColumnSet.add(MovieContract.PopMovieEntry.COLUNM_POSTER);
        popMovieColumnSet.add(MovieContract.PopMovieEntry.COLUNM_RELEASE_DATE);
        popMovieColumnSet.add(MovieContract.PopMovieEntry._ID);

        columnNameIndex = c.getColumnIndex("name");
        do {
            popMovieColumnSet.remove(c.getString(columnNameIndex));
        }while (c.moveToNext());

        assertTrue("Error: you did not create all the required colunm for pop movie table ", popMovieColumnSet.isEmpty());

        // check table rate movie
        c = db.rawQuery("PRAGMA table_info(" + MovieContract.RatedMovieEntry.TABLE_NAME + ")", null);
        assertTrue("means we cannot query the database for the colunm information of table rate_movie", c.moveToFirst());
        final HashSet<String> rateMovieColumnSet = new HashSet<>();
        rateMovieColumnSet.add(MovieContract.RatedMovieEntry.COLUNM_PAGE);
        rateMovieColumnSet.add(MovieContract.RatedMovieEntry.COLUNM_MOVIE_KEY);
        rateMovieColumnSet.add(MovieContract.RatedMovieEntry.COLUNM_AVERATE_VOTE);
        rateMovieColumnSet.add(MovieContract.RatedMovieEntry.COLUNM_POSTER);
        rateMovieColumnSet.add(MovieContract.RatedMovieEntry.COLUNM_RELEASE_DATE);
        rateMovieColumnSet.add(MovieContract.RatedMovieEntry._ID);

        columnNameIndex = c.getColumnIndex("name");
        do {
            rateMovieColumnSet.remove(c.getString(columnNameIndex));
        }while (c.moveToNext());

        assertTrue("Error: you did not create all the required colunm for rate movie table ", rateMovieColumnSet.isEmpty());

        // check table video
        c = db.rawQuery("PRAGMA table_info(" + MovieContract.VideoEntry.TABLE_NAME + ")", null);
        assertTrue("means we cannot query the database for the colunm information of table video", c.moveToFirst());
        final HashSet<String> videoColumnSet = new HashSet<>();
        videoColumnSet.add(MovieContract.VideoEntry.COLUNM_KEY);
        videoColumnSet.add(MovieContract.VideoEntry.COLUNM_MOVIE_KEY);
        videoColumnSet.add(MovieContract.VideoEntry.COLUNM_NAME);
        videoColumnSet.add(MovieContract.VideoEntry.COLUNM_SITE);
        videoColumnSet.add(MovieContract.VideoEntry.COLUNM_SIZE);
        videoColumnSet.add(MovieContract.VideoEntry.COLUNM_TYPE);
        videoColumnSet.add(MovieContract.VideoEntry.COLUNM_VIDEO_ID);
        videoColumnSet.add(MovieContract.VideoEntry._ID);

        columnNameIndex = c.getColumnIndex("name");
        do {
            videoColumnSet.remove(c.getString(columnNameIndex));
        }while (c.moveToNext());

        assertTrue("Error: you did not create all the required colunm for video movie table ", videoColumnSet.isEmpty());

        //check review table
        c = db.rawQuery("PRAGMA table_info(" + MovieContract.ReviewEntry.TABLE_NAME + ")", null);
        assertTrue("means we cannot query the database for the colunm information of table review", c.moveToFirst());
        final HashSet<String> reviewColumnSet = new HashSet<>();
        reviewColumnSet.add(MovieContract.ReviewEntry.COLUNM_AUTHOR);
        reviewColumnSet.add(MovieContract.ReviewEntry.COLUNM_MOVIE_KEY);
        reviewColumnSet.add(MovieContract.ReviewEntry.COLUNM_PAGE);
        reviewColumnSet.add(MovieContract.ReviewEntry.COLUNM_REVIEW_ID);
        reviewColumnSet.add(MovieContract.ReviewEntry.COLUNM_TOTAL_PAGE);
        reviewColumnSet.add(MovieContract.ReviewEntry.COLUNM_URL);
        reviewColumnSet.add(MovieContract.ReviewEntry._ID);

        columnNameIndex = c.getColumnIndex("name");
        do {
            reviewColumnSet.remove(c.getString(columnNameIndex));
        }while (c.moveToNext());
        assertTrue("Error: you did not create all the required colunm for  review movie table ", reviewColumnSet.isEmpty());

        db.close();
    }

    public void testInsert(){
        SQLiteDatabase db = new MovieDbHelper(mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        long _id = 0;
        // check insert movie table
         _id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, MockData.getMovieContentValue(102));
        assertTrue("Error : insert column to movie table failed.", _id > 0);

        //check insert pop movie
        _id = db.insert(MovieContract.PopMovieEntry.TABLE_NAME, null, MockData.getPopMovieContentValue(102));
        assertTrue("Errot : insert colunm to pop movie table failed.", _id > 0);

        // check insert rate movie
        _id = db.insert(MovieContract.RatedMovieEntry.TABLE_NAME, null, MockData.getRateMovieContentValue(102));
        assertTrue("Errot : insert colunm to rate movie table failed.", _id > 0);

        // check insert video
        _id = db.insert(MovieContract.VideoEntry.TABLE_NAME, null, MockData.getVideoContentValue(102));
        assertTrue("Errot : insert colunm to video table failed.", _id > 0);

        // check insert review
        _id = db.insert(MovieContract.ReviewEntry.TABLE_NAME, null, MockData.getReviewContentValue(102));
        assertTrue("Errot : insert colunm to review movie table failed.", _id > 0);
        db.close();
    }

    public void testUpdate(){

        testInsert();

        SQLiteDatabase db = new MovieDbHelper(mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        String selection = "movie_id = ?";
        String[] selectionArgs = new String[]{"102"};
        long _id = 0;
        _id = db.update(MovieContract.MovieEntry.TABLE_NAME,  MockData.getMovieContentValue(103), selection, selectionArgs);
        assertTrue("Errot : update colunm to movie table failed.", _id > 0);

        _id = db.update(MovieContract.PopMovieEntry.TABLE_NAME, MockData.getPopMovieContentValue(103), selection, selectionArgs);
        assertTrue("Errot : update colunm to pop movie table failed.", _id > 0);

        _id = db.update(MovieContract.RatedMovieEntry.TABLE_NAME, MockData.getRateMovieContentValue(103), selection, selectionArgs);
        assertTrue("Errot : update colunm rate movie table failed.", _id > 0);

        _id = db.update(MovieContract.VideoEntry.TABLE_NAME, MockData.getVideoContentValue(103), selection, selectionArgs);
        assertTrue("Errot : update colunm to video movie table failed.", _id > 0);

        _id = db.update(MovieContract.ReviewEntry.TABLE_NAME, MockData.getReviewContentValue(103), selection, selectionArgs);
        assertTrue("Errot : update colunm to review movie table failed.", _id > 0);
        db.close();
    }

    public void testDelete(){
        testInsert();

        SQLiteDatabase db = new MovieDbHelper(mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());
        String selection = "movie_id = ?";
        String[] selectionArgs = new String[]{"102"};
        long _id = 0;
        _id = db.delete(MovieContract.MovieEntry.TABLE_NAME,selection,selectionArgs);
        assertTrue("Errot : delete colunm to movie table failed.", _id > 0);

        _id = db.delete(MovieContract.PopMovieEntry.TABLE_NAME,selection,selectionArgs);
        assertTrue("Errot : delete colunm to pop table failed.", _id > 0);

        _id = db.delete(MovieContract.RatedMovieEntry.TABLE_NAME,selection,selectionArgs);
        assertTrue("Errot : delete colunm to rate table failed.", _id > 0);

        _id = db.delete(MovieContract.VideoEntry.TABLE_NAME,selection,selectionArgs);
        assertTrue("Errot : delete colunm to video table failed.", _id > 0);

        _id = db.delete(MovieContract.ReviewEntry.TABLE_NAME,selection,selectionArgs);
        assertTrue("Errot : delete colunm to review table failed.", _id > 0);

        db.close();
    }
}
