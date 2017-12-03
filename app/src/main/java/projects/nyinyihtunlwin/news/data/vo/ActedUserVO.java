package projects.nyinyihtunlwin.news.data.vo;

import com.google.gson.annotations.SerializedName;

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
}
