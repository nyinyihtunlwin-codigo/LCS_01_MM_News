package projects.nyinyihtunlwin.news.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dell on 12/16/2017.
 */

public class MMNewsDBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "mmnews.db";

    private static final String SQL_CREATE_NEWS = "CREATE TABLE " + MMNewsContract.NewsEntry.TABLE_NAME + " (" +
            MMNewsContract.NewsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MMNewsContract.NewsEntry.COLUMN_NEWS_ID + " VARCHAR(256), " +
            MMNewsContract.NewsEntry.COLUMN_BRIEF + " TEXT, " +
            MMNewsContract.NewsEntry.COLUMN_DETAILS + " TEXT, " +
            MMNewsContract.NewsEntry.COLUMN_POSTED_DATE + " TEXT, " +
            MMNewsContract.NewsEntry.COLUMN_PUBLICATIOIN_ID + " TEXT, " +
            " UNIQUE (" + MMNewsContract.NewsEntry.COLUMN_NEWS_ID + ") ON CONFLICT REPLACE" +
            ");";
    private static final String SQL_CREATE_IMAGES_IN_NEWS = "CREATE TABLE " + MMNewsContract.ImageInNewsEntry.TABLE_NAME + " (" +
            MMNewsContract.ImageInNewsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MMNewsContract.ImageInNewsEntry.COLUMN_NEWS_ID + " VARCHAR(256), " +
            MMNewsContract.ImageInNewsEntry.COLUMN_IMAGE_URL + " TEXT, " +
            " UNIQUE (" + MMNewsContract.ImageInNewsEntry.COLUMN_IMAGE_URL + ") ON CONFLICT REPLACE" +
            ");";
    private static final String SQL_CREATE_PUBLICATION = "CREATE TABLE " + MMNewsContract.PublicationEntry.TABLE_NAME + " (" +
            MMNewsContract.PublicationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MMNewsContract.PublicationEntry.COLUMN_PUBLICATION_ID + " VARCHAR(256), " +
            MMNewsContract.PublicationEntry.COLUMN_TITLE + " TEXT, " +
            MMNewsContract.PublicationEntry.COLUMN_LOGO + " TEXT, " +
            " UNIQUE (" + MMNewsContract.PublicationEntry.COLUMN_PUBLICATION_ID + ") ON CONFLICT REPLACE" +
            ");";
    private static final String SQL_CREATE_FAVORITE_ACTION = "CREATE TABLE " + MMNewsContract.FavoriteActionEntry.TABLE_NAME + " (" +
            MMNewsContract.FavoriteActionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MMNewsContract.FavoriteActionEntry.COLUMN_FAVORITE_ID + " VARCHAR(256), " +
            MMNewsContract.FavoriteActionEntry.COLUMN_FAVORITE_DATE + " TEXT, " +
            MMNewsContract.FavoriteActionEntry.COLUMN_USER_ID + " VARCHAR(256), " +
            MMNewsContract.FavoriteActionEntry.COLUMN_NEWS_ID + " VARCHAR(256), " +
            " UNIQUE (" + MMNewsContract.FavoriteActionEntry.COLUMN_FAVORITE_ID + ") ON CONFLICT REPLACE" +
            ");";
    private static final String SQL_CREATE_USER = "CREATE TABLE " + MMNewsContract.UserEntry.TABLE_NAME + " (" +
            MMNewsContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MMNewsContract.UserEntry.COLUMN_USER_ID + " VARCHAR(256), " +
            MMNewsContract.UserEntry.COLUMN_USER_NAME + " TEXT, " +
            MMNewsContract.UserEntry.COLUMN_PROFILE_IMAGE + " TEXT, " +
            " UNIQUE (" + MMNewsContract.UserEntry.COLUMN_USER_ID + ") ON CONFLICT REPLACE" +
            ");";
    private static final String SQL_CREATE_COMMENT = "CREATE TABLE " + MMNewsContract.CommentEntry.TABLE_NAME + " (" +
            MMNewsContract.CommentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MMNewsContract.CommentEntry.COLUMN_COMMENT_ID + " VARCHAR(256), " +
            MMNewsContract.CommentEntry.COLUMN_COMMENT + " TEXT, " +
            MMNewsContract.CommentEntry.COLUMN_COMMENT_DATE + " TEXT, " +
            MMNewsContract.CommentEntry.COLUMN_USER_ID + " VARCHAR(256), " +
            MMNewsContract.CommentEntry.COLUMN_NEWS_ID + " VARCHAR(256), " +
            " UNIQUE (" + MMNewsContract.CommentEntry.COLUMN_COMMENT_ID + ") ON CONFLICT REPLACE" +
            ");";
    private static final String SQL_CREATE_SENT_TO = "CREATE TABLE " + MMNewsContract.SendToEntry.TABLE_NAME + " (" +
            MMNewsContract.SendToEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MMNewsContract.SendToEntry.COLUMN_SENT_TO_ID + " VARCHAR(256), " +
            MMNewsContract.SendToEntry.COLUMN_SENT_DATE + " TEXT, " +
            MMNewsContract.SendToEntry.COLUMN_SENDER_ID + " VARCHAR(256), " +
            MMNewsContract.SendToEntry.COLUMN_RECEIVER_ID + " VARCHAR(256), " +
            MMNewsContract.SendToEntry.COLUMN_NEWS_ID + " VARCHAR(256), " +
            " UNIQUE (" + MMNewsContract.SendToEntry.COLUMN_SENT_TO_ID + ") ON CONFLICT REPLACE" +
            ");";

    public MMNewsDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /**
         * NOTE : Start execute from leaf tables ( Don't start from root table)
         */
        sqLiteDatabase.execSQL(SQL_CREATE_USER);
        sqLiteDatabase.execSQL(SQL_CREATE_PUBLICATION);
        sqLiteDatabase.execSQL(SQL_CREATE_NEWS);
        sqLiteDatabase.execSQL(SQL_CREATE_IMAGES_IN_NEWS);

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_ACTION);
        sqLiteDatabase.execSQL(SQL_CREATE_COMMENT);
        sqLiteDatabase.execSQL(SQL_CREATE_SENT_TO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        /**
         * NOTE : Start drop table from root tables.
         */
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MMNewsContract.SendToEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MMNewsContract.CommentEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MMNewsContract.FavoriteActionEntry.TABLE_NAME);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MMNewsContract.ImageInNewsEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MMNewsContract.NewsEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MMNewsContract.PublicationEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MMNewsContract.UserEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
