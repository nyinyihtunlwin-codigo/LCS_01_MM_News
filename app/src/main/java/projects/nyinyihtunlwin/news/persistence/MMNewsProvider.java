package projects.nyinyihtunlwin.news.persistence;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Dell on 12/16/2017.
 */

public class MMNewsProvider extends ContentProvider {

    public static final int ACTED_USER = 100;
    public static final int PUBLICATION = 200;
    public static final int NEWS = 300;
    public static final int IMAGES_IN_NEWS = 400;
    public static final int FAVORITE_ACTION = 500;
    public static final int COMMENT_ACTION = 600;
    public static final int SENT_TO_ACTION = 700;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final SQLiteQueryBuilder sNewsWithPublication_IJ, sFavoriteActionsWithActedUser_IJ, sCommentActionsWithActedUser_IJ;

    static {
        sNewsWithPublication_IJ = new SQLiteQueryBuilder();
        sNewsWithPublication_IJ.setTables(
                MMNewsContract.NewsEntry.TABLE_NAME + " INNER JOIN " +
                        MMNewsContract.PublicationEntry.TABLE_NAME +
                        " ON " +
                        MMNewsContract.NewsEntry.TABLE_NAME + "." + MMNewsContract.NewsEntry.COLUMN_PUBLICATIOIN_ID + " = " +
                        MMNewsContract.PublicationEntry.TABLE_NAME + "." + MMNewsContract.PublicationEntry.COLUMN_PUBLICATION_ID
        );
        sFavoriteActionsWithActedUser_IJ = new SQLiteQueryBuilder();
        sFavoriteActionsWithActedUser_IJ.setTables(
                MMNewsContract.FavoriteActionEntry.TABLE_NAME + " INNER JOIN " +
                        MMNewsContract.UserEntry.TABLE_NAME +
                        " ON " +
                        MMNewsContract.FavoriteActionEntry.TABLE_NAME + "." + MMNewsContract.FavoriteActionEntry.COLUMN_USER_ID + " = " +
                        MMNewsContract.UserEntry.TABLE_NAME + "." + MMNewsContract.UserEntry.COLUMN_USER_ID
        );
        sCommentActionsWithActedUser_IJ = new SQLiteQueryBuilder();
        sCommentActionsWithActedUser_IJ.setTables(
                MMNewsContract.CommentEntry.TABLE_NAME + " INNER JOIN " +
                        MMNewsContract.UserEntry.TABLE_NAME +
                        " ON " +
                        MMNewsContract.CommentEntry.TABLE_NAME + "." + MMNewsContract.CommentEntry.COLUMN_USER_ID + " = " +
                        MMNewsContract.UserEntry.TABLE_NAME + "." + MMNewsContract.UserEntry.COLUMN_USER_ID
        );
    }

    private MMNewsDBHelper mDBHelper;

    private static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MMNewsContract.CONTENT_AUTHORITY, MMNewsContract.PATH_USER, ACTED_USER);
        uriMatcher.addURI(MMNewsContract.CONTENT_AUTHORITY, MMNewsContract.PATH_PUBLICATION, PUBLICATION);
        uriMatcher.addURI(MMNewsContract.CONTENT_AUTHORITY, MMNewsContract.PATH_NEWS, NEWS);
        uriMatcher.addURI(MMNewsContract.CONTENT_AUTHORITY, MMNewsContract.PATH_IMAGES_IN_NEWS, IMAGES_IN_NEWS);
        uriMatcher.addURI(MMNewsContract.CONTENT_AUTHORITY, MMNewsContract.PATH_FAVORITE, FAVORITE_ACTION);
        uriMatcher.addURI(MMNewsContract.CONTENT_AUTHORITY, MMNewsContract.PATH_COMMENT, COMMENT_ACTION);
        uriMatcher.addURI(MMNewsContract.CONTENT_AUTHORITY, MMNewsContract.PATH_SENT_TO, SENT_TO_ACTION);


        return uriMatcher;
    }

    private String getTableName(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case ACTED_USER:
                return MMNewsContract.UserEntry.TABLE_NAME;
            case PUBLICATION:
                return MMNewsContract.PublicationEntry.TABLE_NAME;
            case NEWS:
                return MMNewsContract.NewsEntry.TABLE_NAME;
            case IMAGES_IN_NEWS:
                return MMNewsContract.ImageInNewsEntry.TABLE_NAME;
            case FAVORITE_ACTION:
                return MMNewsContract.FavoriteActionEntry.TABLE_NAME;
            case COMMENT_ACTION:
                return MMNewsContract.CommentEntry.TABLE_NAME;
            case SENT_TO_ACTION:
                return MMNewsContract.SendToEntry.TABLE_NAME;
        }
        return null;
    }

    private Uri getContentUri(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case ACTED_USER:
                return MMNewsContract.UserEntry.CONTENT_URI;
            case PUBLICATION:
                return MMNewsContract.PublicationEntry.CONTENT_URI;
            case NEWS:
                return MMNewsContract.NewsEntry.CONTENT_URI;
            case IMAGES_IN_NEWS:
                return MMNewsContract.ImageInNewsEntry.CONTENT_URI;
            case FAVORITE_ACTION:
                return MMNewsContract.FavoriteActionEntry.CONTENT_URI;
            case COMMENT_ACTION:
                return MMNewsContract.CommentEntry.CONTENT_URI;
            case SENT_TO_ACTION:
                return MMNewsContract.SendToEntry.CONTENT_URI;
        }
        return null;
    }

    @Override
    public boolean onCreate() {
        mDBHelper = new MMNewsDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor queryCursor;
        switch (sUriMatcher.match(uri)) {
            case NEWS:
                queryCursor = sNewsWithPublication_IJ.query(mDBHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case FAVORITE_ACTION:
                queryCursor = sFavoriteActionsWithActedUser_IJ.query(mDBHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case COMMENT_ACTION:
                queryCursor = sCommentActionsWithActedUser_IJ.query(mDBHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                queryCursor = mDBHelper.getReadableDatabase().query(getTableName(uri),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
        }
        if (getContext() != null) {
            queryCursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return queryCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int matchUri = sUriMatcher.match(uri);
        switch (matchUri) {
            case ACTED_USER:
                return MMNewsContract.UserEntry.DIR_TYPE;
            case PUBLICATION:
                return MMNewsContract.PublicationEntry.DIR_TYPE;
            case NEWS:
                return MMNewsContract.NewsEntry.DIR_TYPE;
            case IMAGES_IN_NEWS:
                return MMNewsContract.ImageInNewsEntry.DIR_TYPE;
            case FAVORITE_ACTION:
                return MMNewsContract.FavoriteActionEntry.DIR_TYPE;
            case COMMENT_ACTION:
                return MMNewsContract.CommentEntry.DIR_TYPE;
            case SENT_TO_ACTION:
                return MMNewsContract.SendToEntry.DIR_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        long _id = db.insert(tableName, null, contentValues);
        if (_id > 0) {
            Uri tableContentUri = getContentUri(uri);
            Uri insertedUri = ContentUris.withAppendedId(tableContentUri, _id);
            if (getContext() != null) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
            return insertedUri;
        }
        return null;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        int insertedCount = 0;

        try {
            db.beginTransaction();
            for (ContentValues cv : values) {
                long _id = db.insert(tableName, null, cv);
                if (_id > 0) {
                    insertedCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }

        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return insertedCount;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int rowDeleted;
        String tableName = getTableName(uri);
        rowDeleted = db.delete(tableName, selection, selectionArgs);
        Context context = getContext();
        if (context != null && rowDeleted > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return rowDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int rowUpdated;
        String tableName = getTableName(uri);

        rowUpdated = db.update(tableName, contentValues, selection, selectionArgs);
        Context context = getContext();
        if (context != null && rowUpdated > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return rowUpdated;
    }
}
