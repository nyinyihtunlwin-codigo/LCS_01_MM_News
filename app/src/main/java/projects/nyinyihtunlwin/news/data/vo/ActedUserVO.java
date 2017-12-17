package projects.nyinyihtunlwin.news.data.vo;

import android.content.ContentValues;

import com.google.gson.annotations.SerializedName;

import projects.nyinyihtunlwin.news.persistence.MMNewsContract;

/**
 * Created by Dell on 12/3/2017.
 */

class ActedUserVO {

    @SerializedName("user-id")
    private String userId;

    @SerializedName("user-name")
    private String userName;

    @SerializedName("profile-image")
    private String profileImage;

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public ContentValues parseToContentValues() {

        ContentValues contentValues = new ContentValues();

        contentValues.put(MMNewsContract.UserEntry.COLUMN_USER_ID, userId);
        contentValues.put(MMNewsContract.UserEntry.COLUMN_USER_NAME, userName);
        contentValues.put(MMNewsContract.UserEntry.COLUMN_PROFILE_IMAGE, profileImage);

        return contentValues;
    }
}
