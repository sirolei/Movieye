package com.sirolei.movieye.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by siro on 2016/4/19.
 */
public class MovieProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDbHelper dbHelper;

    static final int MOVIE = 100;
    static final int MOVIE_WITH_MOVIE_ID = 101;
    static final int MOVIE_POP = 102;
    static final int MOVIE_POP_WITH_PAGE = 103;
    static final int MOVIE_RATE = 104;
    static final int MOVIE_RATE_WITH_PAGE = 105;
    static final int MOVIE_VIDEO = 106;
    static final int MOVIE_VIDEO_WITH_MOVIE_ID = 107;
    static final int MOVIE_REVIEW = 108;
    static final int MOVIE_REVIEW_WITH_MOVIE_ID = 109;
    static final int MOVIE_REVIEW_WITH_MOVIE_ID_AND_PAGE = 110;

    private static final SQLiteQueryBuilder sMovieQueryBuilder;
    private static final SQLiteQueryBuilder sPopMovieQueryBuilder;
    private static final SQLiteQueryBuilder sRateMovieQueryBuilder;
    private static final SQLiteQueryBuilder sVideoQueryBuilder;
    private static final SQLiteQueryBuilder sReviewQueryBuilder;

    static {
        sMovieQueryBuilder = new SQLiteQueryBuilder();
        sPopMovieQueryBuilder = new SQLiteQueryBuilder();
        sRateMovieQueryBuilder = new SQLiteQueryBuilder();
        sVideoQueryBuilder = new SQLiteQueryBuilder();
        sReviewQueryBuilder = new SQLiteQueryBuilder();

        sMovieQueryBuilder.setTables(MovieContract.MovieEntry.TABLE_NAME);
        sPopMovieQueryBuilder.setTables(MovieContract.PopMovieEntry.TABLE_NAME);
        sRateMovieQueryBuilder.setTables(MovieContract.RateMovieEntry.TABLE_NAME);
        sVideoQueryBuilder.setTables(MovieContract.VideoEntry.TABLE_NAME);
        sReviewQueryBuilder.setTables(MovieContract.ReviewEntry.TABLE_NAME);
    }

    static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, MovieContract.PATH_MOVIE, MOVIE);
        matcher.addURI(authority, MovieContract.PATH_MOVIE + "/#", MOVIE_WITH_MOVIE_ID);
        matcher.addURI(authority, MovieContract.PATH_POP_MOVIE, MOVIE_POP);
        matcher.addURI(authority, MovieContract.PATH_POP_MOVIE + "/#", MOVIE_POP_WITH_PAGE);
        matcher.addURI(authority, MovieContract.PATH_RATE_MOVIE, MOVIE_RATE);
        matcher.addURI(authority, MovieContract.PATH_RATE_MOVIE + "/#", MOVIE_RATE_WITH_PAGE);
        matcher.addURI(authority, MovieContract.PATH_VIDEO, MOVIE_VIDEO);
        matcher.addURI(authority, MovieContract.PATH_VIDEO + "/#", MOVIE_VIDEO_WITH_MOVIE_ID);
        matcher.addURI(authority, MovieContract.PATH_REVIEW, MOVIE_REVIEW);
        matcher.addURI(authority, MovieContract.PATH_REVIEW + "/#", MOVIE_REVIEW_WITH_MOVIE_ID);
        matcher.addURI(authority, MovieContract.PATH_REVIEW + "/#/#", MOVIE_REVIEW_WITH_MOVIE_ID_AND_PAGE);
        return matcher;
    }

    final String sMovieWithIdSelection = MovieContract.MovieEntry.TABLE_NAME + "." + MovieContract.MovieEntry.COLUNM_MOVIE_ID + " = ? ";
    final String sPopMovieWithPageSelection = MovieContract.PopMovieEntry.TABLE_NAME + "." + MovieContract.PopMovieEntry.COLUNM_PAGE + " <= ? ";
    final String sRateMovieWithPageSelection = MovieContract.RateMovieEntry.TABLE_NAME  + "." + MovieContract.RateMovieEntry.COLUNM_PAGE + " <= ?";
    final String sVideoWithMovieIdSelection = MovieContract.VideoEntry.TABLE_NAME + "." + MovieContract.VideoEntry.COLUNM_MOVIE_KEY + " = ? ";
    final String sReviewWithMovieIdSelection = MovieContract.ReviewEntry.TABLE_NAME + "." + MovieContract.ReviewEntry.COLUNM_MOVIE_KEY + " = ? ";
    final String sReviewWithMovieIdAndPage = MovieContract.ReviewEntry.TABLE_NAME + "." + MovieContract.ReviewEntry.COLUNM_MOVIE_KEY + " = ? AND "
            + MovieContract.ReviewEntry.TABLE_NAME + "." + MovieContract.ReviewEntry.COLUNM_PAGE + " = ? ";


    private Cursor getMovieByMovieId(Uri uri, String[] projection, String order){
        long movieId = MovieContract.MovieEntry.getMovieIdFromMovieUri(uri);
        return sMovieQueryBuilder.query(dbHelper.getReadableDatabase(),
                projection,
                sMovieWithIdSelection,
                new String[]{Long.toString(movieId)},
                null,
                null,
                order);
    }

    private Cursor getPopMovieByPage(Uri uri, String[] projection, String order){
        int page = MovieContract.PopMovieEntry.getPageIdFromPopMovieUri(uri);
        return sPopMovieQueryBuilder.query(dbHelper.getReadableDatabase(),
                projection,
                sPopMovieWithPageSelection,
                new String[]{Integer.toString(page)},
                null,
                null,
                order);
    }

    private Cursor getRateMovieByPage(Uri uri, String[] projection, String order){
        int page = MovieContract.RateMovieEntry.getPageIdFromRateMovieUri(uri);
        return sRateMovieQueryBuilder.query(dbHelper.getReadableDatabase(),
                projection,
                sRateMovieWithPageSelection,
                new String[]{Integer.toString(page)},
                null,
                null,
                order);
    }

    private Cursor getVideoByMovieId(Uri uri, String[] projection, String order){
        long movieId = MovieContract.VideoEntry.getMovieIdFormVideoUri(uri);
        return sVideoQueryBuilder.query(dbHelper.getReadableDatabase(),
                projection,
                sVideoWithMovieIdSelection,
                new String[]{Long.toString(movieId)},
                null,
                null,
                order);
    }

    private Cursor getReviewByMovieId(Uri uri, String[] projection, String order){
        long movieId = MovieContract.ReviewEntry.getMovieIdFromReviewUri(uri);
        return sReviewQueryBuilder.query(dbHelper.getReadableDatabase(),
                projection,
                sReviewWithMovieIdSelection,
                new String[]{Long.toString(movieId)},
                null,
                null,
                order);
    }

    private Cursor getReviewByMovieIdAndPage(Uri uri, String[] projection, String order){
        long movieId = MovieContract.ReviewEntry.getMovieIdFromReviewUri(uri);
        int page = MovieContract.ReviewEntry.getPageIdFromReviewUri(uri);
        return sReviewQueryBuilder.query(dbHelper.getReadableDatabase(),
                projection,
                sReviewWithMovieIdAndPage,
                new String[]{Long.toString(movieId), Integer.toString(page)},
                null,
                null,
                order);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;

        switch (sUriMatcher.match(uri)){
            case MOVIE:
                retCursor = dbHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case MOVIE_WITH_MOVIE_ID:
                retCursor = getMovieByMovieId(uri, projection, sortOrder);
                break;
            case MOVIE_POP:
                retCursor = dbHelper.getReadableDatabase().query(
                        MovieContract.PopMovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case MOVIE_POP_WITH_PAGE:
                retCursor = getPopMovieByPage(uri, projection, sortOrder);
                break;
            case MOVIE_RATE:
                retCursor = dbHelper.getReadableDatabase().query(
                        MovieContract.RateMovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case MOVIE_RATE_WITH_PAGE:
                retCursor = getRateMovieByPage(uri, projection, sortOrder);
                break;
            case MOVIE_VIDEO:
                retCursor = dbHelper.getReadableDatabase().query(
                        MovieContract.VideoEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case MOVIE_VIDEO_WITH_MOVIE_ID:
                retCursor = getVideoByMovieId(uri, projection, sortOrder);
                break;
            case MOVIE_REVIEW:
                retCursor = dbHelper.getReadableDatabase().query(
                        MovieContract.ReviewEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case MOVIE_REVIEW_WITH_MOVIE_ID:
                retCursor = getReviewByMovieId(uri, projection, sortOrder);
                break;
            case MOVIE_REVIEW_WITH_MOVIE_ID_AND_PAGE:
                retCursor = getReviewByMovieIdAndPage(uri, projection, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int code = sUriMatcher.match(uri);
        switch (code){
            case MOVIE:
                return MovieContract.MovieEntry.CONTENT_TYPE;
            case MOVIE_WITH_MOVIE_ID:
                return MovieContract.MovieEntry.CONTENT_ITEM_TYPE;
            case MOVIE_POP:
            case MOVIE_POP_WITH_PAGE:
                return MovieContract.PopMovieEntry.CONTENT_TYPE;
            case MOVIE_RATE:
            case MOVIE_RATE_WITH_PAGE:
                return MovieContract.RateMovieEntry.CONTENT_TYPE;
            case MOVIE_REVIEW:
            case MOVIE_REVIEW_WITH_MOVIE_ID:
            case MOVIE_REVIEW_WITH_MOVIE_ID_AND_PAGE:
                return MovieContract.ReviewEntry.CONTENT_TYPE;
            case MOVIE_VIDEO:
            case MOVIE_VIDEO_WITH_MOVIE_ID:
                return MovieContract.VideoEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int match = sUriMatcher.match(uri);
        Uri retUri = null;
        switch (match){
            case MOVIE: {
                long id = dbHelper.getWritableDatabase().insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    long movie_id = values.getAsLong(MovieContract.MovieEntry.COLUNM_MOVIE_ID);
                    retUri = MovieContract.MovieEntry.buildMovieUrl(movie_id);
                }else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case MOVIE_POP: {
                long id = dbHelper.getWritableDatabase().insert(MovieContract.PopMovieEntry.TABLE_NAME, null, values);
                if (id > 0){
                    int page = values.getAsInteger(MovieContract.PopMovieEntry.COLUNM_PAGE);
                    retUri = MovieContract.PopMovieEntry.buildMoviePopUri(page);
                }else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case MOVIE_RATE: {
                long id = dbHelper.getWritableDatabase().insert(MovieContract.RateMovieEntry.TABLE_NAME, null, values);
                if (id > 0){
                    int page = values.getAsInteger(MovieContract.RateMovieEntry.COLUNM_PAGE);
                    retUri = MovieContract.RateMovieEntry.buildMovieRateUri(page);
                }else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case MOVIE_VIDEO: {
                long id = dbHelper.getWritableDatabase().insert(MovieContract.VideoEntry.TABLE_NAME, null, values);
                if (id > 0){
                    long movie_id = values.getAsInteger(MovieContract.VideoEntry.COLUNM_MOVIE_KEY);
                    retUri = MovieContract.VideoEntry.buildMovieVideoUrl(movie_id);
                }else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case MOVIE_REVIEW: {
                long id = dbHelper.getWritableDatabase().insert(MovieContract.ReviewEntry.TABLE_NAME, null, values);
                if (id > 0){
                    long movie_id =  values.getAsLong(MovieContract.ReviewEntry.COLUNM_MOVIE_KEY);
                    retUri = MovieContract.ReviewEntry.buildMovieReviewUrl(movie_id);
                }else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return retUri;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int insertCount = 0;
        switch (match){
            case MOVIE_POP:
               insertCount = excuteBulkInsert(db, MovieContract.PopMovieEntry.TABLE_NAME, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return insertCount;
            case MOVIE_RATE:
                insertCount = excuteBulkInsert(db, MovieContract.RateMovieEntry.TABLE_NAME, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return insertCount;
            case MOVIE_VIDEO:
                insertCount = excuteBulkInsert(db, MovieContract.VideoEntry.TABLE_NAME, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return insertCount;
            case MOVIE_REVIEW:
                insertCount = excuteBulkInsert(db, MovieContract.ReviewEntry.TABLE_NAME, values);
                getContext().getContentResolver().notifyChange(uri, null);
                return insertCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    private int excuteBulkInsert(SQLiteDatabase db, String table, ContentValues[] values){
        int insertCount = 0;
        if (db.isReadOnly()){
            return insertCount;
        }

        db.beginTransaction();
        try {
            for(ContentValues value : values){
                long _id = db.insert(table, null, value);
                if (_id > 0){
                    insertCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return insertCount;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowDeleted;
        // if selection is null then means to delete all rows,we need pass "1" as the whereClause
        if (null == selection){
            selection = "1";
        }
        switch (match){
            case MOVIE:
                rowDeleted = db.delete(MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MOVIE_POP:
                rowDeleted = db.delete(MovieContract.PopMovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MOVIE_RATE:
                rowDeleted = db.delete(MovieContract.RateMovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MOVIE_VIDEO:
                rowDeleted = db.delete(MovieContract.VideoEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MOVIE_REVIEW:
                rowDeleted = db.delete(MovieContract.ReviewEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowUpdate;
        switch (match){
            case MOVIE:
                rowUpdate = db.update(MovieContract.MovieEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case MOVIE_POP:
                rowUpdate = db.update(MovieContract.PopMovieEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case MOVIE_RATE:
                rowUpdate = db.update(MovieContract.RateMovieEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case MOVIE_VIDEO:
                rowUpdate = db.update(MovieContract.VideoEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case MOVIE_REVIEW:
                rowUpdate = db.update(MovieContract.ReviewEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowUpdate != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowUpdate;
    }

    @Override
    public void shutdown() {
        dbHelper.close();
        super.shutdown();
    }
}
