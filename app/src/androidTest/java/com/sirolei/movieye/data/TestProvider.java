package com.sirolei.movieye.data;

import android.content.ContentValues;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.sirolei.movieye.util.MockData;

/**
 * Created by siro on 2016/4/22.
 */
public class TestProvider extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testInsertMovie(){
        ContentValues contentValues = MockData.getMovieContentValue(103);

        Uri uri = mContext.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
        assertNotNull(uri);
        long movieid = MovieContract.MovieEntry.getMovieIdFromMovieUri(uri);
        assertEquals(102, movieid);
    }
}
