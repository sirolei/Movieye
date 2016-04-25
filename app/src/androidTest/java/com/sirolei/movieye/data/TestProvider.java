package com.sirolei.movieye.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.sirolei.movieye.util.MockData;

/**
 * Created by siro on 2016/4/22.
 */
public class TestProvider extends AndroidTestCase {

    MovieDbHelper dbHelper;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecords();
        dbHelper = new MovieDbHelper(mContext);
        assertNotNull(dbHelper);
    }

    public void deleteAllRecords() {
        deleteAllRecordsFromProvider();
    }

    public void deleteAllRecordsFromProvider() {
        mContext.getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI, null, null);
        mContext.getContentResolver().delete(MovieContract.PopMovieEntry.CONTENT_URI, null, null);
        mContext.getContentResolver().delete(MovieContract.RateMovieEntry.CONTENT_URI, null, null);
        mContext.getContentResolver().delete(MovieContract.VideoEntry.CONTENT_URI, null, null);
        mContext.getContentResolver().delete(MovieContract.ReviewEntry.CONTENT_URI, null, null);

        Cursor cursor = mContext.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);
        assertEquals("delete movie data failed ", 0, cursor.getCount());
        cursor.close();

        cursor = mContext.getContentResolver().query(MovieContract.PopMovieEntry.CONTENT_URI, null, null, null, null);
        assertEquals("delete pop movie data failed", 0, cursor.getCount());
        cursor.close();

        cursor = mContext.getContentResolver().query(MovieContract.RateMovieEntry.CONTENT_URI, null, null, null, null);
        assertEquals("delete rate movie data failed", 0, cursor.getCount());
        cursor.close();

        cursor = mContext.getContentResolver().query(MovieContract.VideoEntry.CONTENT_URI, null, null, null, null);
        assertEquals("delete video data failed", 0, cursor.getCount());
        cursor.close();

        cursor = mContext.getContentResolver().query(MovieContract.ReviewEntry.CONTENT_URI, null, null, null, null);
        assertEquals("delete pop review failed",0, cursor.getCount());
        cursor.close();

    }

    public void testInsertMovie() {
        ContentValues contentValues = MockData.getMovieContentValue(103);

        Uri uri = mContext.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
        assertNotNull(uri);
        long movieid = MovieContract.MovieEntry.getMovieIdFromMovieUri(uri);
        assertEquals(103, movieid);
    }

    public void testInsertPopMovie() {
        ContentValues contentValues = MockData.getPopMovieContentValue(102);
        Uri uri = mContext.getContentResolver().insert(MovieContract.PopMovieEntry.CONTENT_URI, contentValues);
        assertNotNull(uri);
        int page = MovieContract.PopMovieEntry.getPageIdFromPopMovieUri(uri);
        assertEquals(2, page);
    }

    public void testInsertRateMovie() {
        ContentValues contentValues = MockData.getRateMovieContentValue(102);
        Uri uri = mContext.getContentResolver().insert(MovieContract.RateMovieEntry.CONTENT_URI, contentValues);
        assertNotNull(uri);
        int page = MovieContract.RateMovieEntry.getPageIdFromRateMovieUri(uri);
        assertEquals(1, page);
    }

    public void testInsertVideo() {
        ContentValues contentValues = MockData.getVideoContentValue(103);
        Uri uri = mContext.getContentResolver().insert(MovieContract.VideoEntry.CONTENT_URI, contentValues);
        assertNotNull(uri);
        long movieId = MovieContract.VideoEntry.getMovieIdFormVideoUri(uri);
        assertEquals(103, movieId);
    }

    public void testInsertReview() {
        ContentValues contentValues = MockData.getReviewContentValue(103);
        Uri uri = mContext.getContentResolver().insert(MovieContract.ReviewEntry.CONTENT_URI, contentValues);
        assertNotNull(uri);
        long movieId = MovieContract.ReviewEntry.getMovieIdFromReviewUri(uri);
        assertEquals(103, movieId);
    }

    public void testBulkInsertPopMovie() {
        ContentValues[] contentValuesArr = MockData.getPopMovieContentValueArray();
        int count = mContext.getContentResolver().bulkInsert(MovieContract.PopMovieEntry.CONTENT_URI, contentValuesArr);
        assertTrue(count > 0);
    }

    public void testBulkInsertRateMovie() {
        ContentValues[] contentValuesArr = MockData.getRateMovieContentValueArray();
        int count = mContext.getContentResolver().bulkInsert(MovieContract.RateMovieEntry.CONTENT_URI, contentValuesArr);
        assertTrue(count > 0);
    }

    public void testBulkInsertVideo() {
        ContentValues[] contentValuesArr = MockData.getVideoContentValueArray();
        int count = mContext.getContentResolver().bulkInsert(MovieContract.VideoEntry.CONTENT_URI, contentValuesArr);
        assertTrue(count > 0);
    }

    public void testBulkInsertReview() {
        ContentValues[] contentValuesArr = MockData.getReviewContentValueArray();
        int count = mContext.getContentResolver().bulkInsert(MovieContract.ReviewEntry.CONTENT_URI, contentValuesArr);
        assertTrue(count > 0);
    }

    public void testUpdateMovie(){
        testInsertMovie();
        ContentValues contentValues = MockData.getMovieContentValue(103);
        contentValues.put(MovieContract.MovieEntry.COLUNM_TITLE, "update the title");
        String selection = "movie_id = ?";
        String[] selectionArg = new String[]{"103"};
        int rowUpdate = mContext.getContentResolver().update(MovieContract.MovieEntry.CONTENT_URI, contentValues, selection, selectionArg);
        assertTrue("update failed", rowUpdate > 0);
        Cursor cursor = mContext.getContentResolver().query(MovieContract.MovieEntry.buildMovieUrl(103),null, null, null, null);
        assertNotNull("cursor is null", cursor);
        assertTrue("cursor is empty", cursor.moveToFirst());
        int columnTitleIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUNM_TITLE);
        assertEquals("update the title", cursor.getString(columnTitleIndex));
        cursor.close();
    }

    public void testUpdatePopMovie(){
        testInsertPopMovie();
        ContentValues contentValues = MockData.getPopMovieContentValue(102);
        contentValues.put(MovieContract.PopMovieEntry.COLUNM_RELEASE_DATE, "2016-09-09");
        String selection = "movie_id = ?";
        String[] selectionArg = new String[]{"102"};
        int rowUpdate = mContext.getContentResolver().update(MovieContract.PopMovieEntry.CONTENT_URI, contentValues, selection, selectionArg);
        assertTrue("update failed ",  rowUpdate > 0);
        Cursor cursor = mContext.getContentResolver().query(MovieContract.PopMovieEntry.CONTENT_URI, null, selection, selectionArg, null);
        assertNotNull("cursor is null", cursor);
        assertTrue("cursor is empty", cursor.moveToFirst());
        int columnReleaseIndex = cursor.getColumnIndex(MovieContract.PopMovieEntry.COLUNM_RELEASE_DATE);
        assertEquals("2016-09-09", cursor.getString(columnReleaseIndex));
        cursor.close();
    }

    public void testUpdateRateMovie(){
        testInsertRateMovie();
        ContentValues contentValues = MockData.getRateMovieContentValue(102);
        contentValues.put(MovieContract.RateMovieEntry.COLUNM_RELEASE_DATE, "2016-09-09");
        String selection = "movie_id = ?";
        String[] selectionArg = new String[]{"102"};
        int rowUpdate = mContext.getContentResolver().update(MovieContract.RateMovieEntry.CONTENT_URI, contentValues, selection, selectionArg);
        assertTrue("update failed ",  rowUpdate > 0);
        Cursor cursor = mContext.getContentResolver().query(MovieContract.RateMovieEntry.CONTENT_URI, null, selection, selectionArg, null);
        assertNotNull("cursor is null", cursor);
        assertTrue("cursor is empty", cursor.moveToFirst());
        int columnReleaseIndex = cursor.getColumnIndex(MovieContract.RateMovieEntry.COLUNM_RELEASE_DATE);
        assertEquals("2016-09-09", cursor.getString(columnReleaseIndex));
        cursor.close();
    }

    public void testUpdateVideo(){
        testInsertVideo();
        ContentValues contentValues = MockData.getVideoContentValue(103);
        contentValues.put(MovieContract.VideoEntry.COLUNM_NAME, "update name");
        String selection = "movie_id = ?";
        String[] selectionArg = new String[]{"103"};
        int rowUpdate = mContext.getContentResolver().update(MovieContract.VideoEntry.CONTENT_URI, contentValues, selection, selectionArg);
        assertTrue("update failed ",  rowUpdate > 0);
        Cursor cursor = mContext.getContentResolver().query(MovieContract.VideoEntry.CONTENT_URI, null, selection, selectionArg, null);
        assertNotNull("cursor is null", cursor);
        assertTrue("cursor is empty", cursor.moveToFirst());
        int columnIndex = cursor.getColumnIndex(MovieContract.VideoEntry.COLUNM_NAME);
        assertEquals("update name", cursor.getString(columnIndex));
        cursor.close();
    }

    public void testUpdateReview(){
        testInsertReview();
        ContentValues contentValues = MockData.getReviewContentValue(103);
        contentValues.put(MovieContract.ReviewEntry.COLUNM_AUTHOR, "update author");
        String selection = "movie_id = ?";
        String[] selectionArg = new String[]{"103"};
        int rowUpdate = mContext.getContentResolver().update(MovieContract.ReviewEntry.CONTENT_URI, contentValues, selection, selectionArg);
        assertTrue("update failed ",  rowUpdate > 0);
        Cursor cursor = mContext.getContentResolver().query(MovieContract.ReviewEntry.CONTENT_URI, null, selection, selectionArg, null);
        assertNotNull("cursor is null", cursor);
        assertTrue("cursor is empty", cursor.moveToFirst());
        int columnIndex = cursor.getColumnIndex(MovieContract.ReviewEntry.COLUNM_AUTHOR);
        assertEquals("update author", cursor.getString(columnIndex));
        cursor.close();
    }

    public void testQueryMovie(){
        testInsertMovie();
        Cursor cursor = mContext.getContentResolver().query(MovieContract.MovieEntry.buildMovieUrl(103), null, null, null, null);
        assertNotNull("failed : cursor is null", cursor);
        assertTrue("failed: query result is empty.", cursor.moveToFirst());
        int colunmIndex = cursor.getColumnIndex(MovieContract.MovieEntry.COLUNM_MOVIE_ID);
        assertEquals("failed : do not have 103 movie_id", 103, cursor.getLong(colunmIndex));
        cursor.close();
    }

    public void testQueryPop(){
        testBulkInsertPopMovie();
        Cursor cursor = mContext.getContentResolver().query(MovieContract.PopMovieEntry.buildMoviePopUri(2), null, null, null, null);
        assertNotNull("failed: cursor is null in pop table", cursor);
        assertTrue("failed : query result is empty in pop table",  cursor.moveToFirst());
        assertEquals("failed : do not exist 20 rows in page2 in poptable ", 20, cursor.getCount());
        cursor.close();
    }

    public void testQueryRate(){
        testBulkInsertRateMovie();
        Cursor cursor = mContext.getContentResolver().query(MovieContract.RateMovieEntry.buildMovieRateUri(2), null, null, null, null);
        assertNotNull("failed: cursor is null in rate table", cursor);
        assertTrue("failed : query result is empty in rate table",  cursor.moveToFirst());
        assertEquals("failed : do not exist 20 rows in page2 in rate table", 20, cursor.getCount());
        cursor.close();
    }

    public void testQueryVideo(){
        testBulkInsertVideo();
        Cursor cursor = mContext.getContentResolver().query(MovieContract.VideoEntry.buildMovieVideoUrl(103), null, null, null, null);
        assertNotNull("failed: cursor is null ", cursor);
        assertTrue("failed : query result is empty",  cursor.moveToFirst());
        assertEquals("failed : do not exist 3 rows video ", 3, cursor.getCount());
        cursor.close();
    }

    public void testQueryReview(){
        testBulkInsertReview();
        Cursor cursor = mContext.getContentResolver().query(MovieContract.ReviewEntry.buildMovieReviewUrl(103), null, null, null, null);
        assertNotNull("failed: cursor is null", cursor);
        assertTrue("failed : query result is empty", cursor.moveToFirst());
        assertEquals("failed: do not exist 3 rows review", 3,  cursor.getCount());
        cursor.close();
        cursor = mContext.getContentResolver().query(MovieContract.ReviewEntry.buildMovieReviewWithPageUrl(103, 2), null, null, null, null);
        assertNotNull("failed: cursor is null", cursor);
        assertFalse("failed : cursor need to be empty", cursor.moveToFirst());
        cursor.close();
    }

}
