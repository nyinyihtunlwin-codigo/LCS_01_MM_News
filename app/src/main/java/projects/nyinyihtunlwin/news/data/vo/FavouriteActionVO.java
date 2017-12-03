package projects.nyinyihtunlwin.news.data.vo;

import com.google.gson.annotations.SerializedName;

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
}
