package com.sirolei.movieye.data;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

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
        mContext.deleteDatabase(MovieDbHelper.DATABASE_NAME);

        SQLiteDatabase db = new MovieDbHelper(mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

    }
}
