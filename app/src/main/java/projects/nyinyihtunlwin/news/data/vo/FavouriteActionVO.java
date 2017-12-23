package projects.nyinyihtunlwin.news.data.vo;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import projects.nyinyihtunlwin.news.persistence.MMNewsContract;

/**
 * Created by Dell on 12/3/2017.
 */

public class FavouriteActionVO {

    @SerializedName("favorite-id")
    private String favouriteId;

    @SerializedName("favorite-date")
    private String favouriteDate;

    @SerializedName("acted-user")
    private ActedUserVO actedUser;

    public String getFavouriteId() {
        return favouriteId;
    }

    public String getFavouriteDate() {
        return favouriteDate;
    }

    public ActedUserVO getActedUser() {
        return actedUser;
    }

    public ContentValues parseToContentValues(String newsId) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(MMNewsContract.FavoriteActionEntry.COLUMN_FAVORITE_ID, favouriteId);
        contentValues.put(MMNewsContract.FavoriteActionEntry.COLUMN_FAVORITE_DATE, favouriteDate);
        contentValues.put(MMNewsContract.FavoriteActionEntry.COLUMN_USER_ID, actedUser.getUserId());
        contentValues.put(MMNewsContract.FavoriteActionEntry.COLUMN_NEWS_ID, newsId);

        return contentValues;
    }

    public static FavouriteActionVO parseFromCursor(Cursor cursor) {
        FavouriteActionVO favourite = new FavouriteActionVO();
        favourite.favouriteId = cursor.getString(cursor.getColumnIndex(MMNewsContract.FavoriteActionEntry.COLUMN_FAVORITE_ID));
        favourite.favouriteDate = cursor.getString(cursor.getColumnIndex(MMNewsContract.FavoriteActionEntry.COLUMN_FAVORITE_DATE));
        favourite.actedUser = ActedUserVO.parseFromCursor(cursor);

        return favourite;
    }
}
