package projects.nyinyihtunlwin.news.data.vo;

import android.content.ContentValues;

import com.google.gson.annotations.SerializedName;

import projects.nyinyihtunlwin.news.persistence.MMNewsContract;

/**
 * Created by Dell on 12/3/2017.
 */

public class PublicationVO {

    @SerializedName("publication-id")
    private String publicationId;

    @SerializedName("title")
    private String title;

    @SerializedName("logo")
    private String logo;

    public String getPublicationId() {
        return publicationId;
    }

    public String getTitle() {
        return title;
    }

    public String getLogo() {
        return logo;
    }

    public ContentValues parseToContentValues() {

        ContentValues contentValues = new ContentValues();

        contentValues.put(MMNewsContract.PublicationEntry.COLUMN_PUBLICATION_ID, publicationId);
        contentValues.put(MMNewsContract.PublicationEntry.COLUMN_TITLE, title);
        contentValues.put(MMNewsContract.PublicationEntry.COLUMN_LOGO, logo);

        return contentValues;
    }
}
