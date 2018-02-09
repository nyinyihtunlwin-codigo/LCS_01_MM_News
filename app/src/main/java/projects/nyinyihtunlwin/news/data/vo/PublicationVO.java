package projects.nyinyihtunlwin.news.data.vo;

import android.content.ContentValues;
import android.database.Cursor;

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

    public static PublicationVO parseFromCursor(Cursor cursor) {

        PublicationVO publication = new PublicationVO();

        publication.publicationId = cursor.getString(cursor.getColumnIndex(MMNewsContract.PublicationEntry.COLUMN_PUBLICATION_ID));
        publication.title = cursor.getString(cursor.getColumnIndex(MMNewsContract.PublicationEntry.COLUMN_TITLE));
        publication.logo = cursor.getString(cursor.getColumnIndex(MMNewsContract.PublicationEntry.COLUMN_LOGO));

        return publication;
    }

    public static PublicationVO dummyPublication() {
        PublicationVO publicationVO = new PublicationVO();
        publicationVO.publicationId = "pub09";
        publicationVO.title = "Dummy Title";
        publicationVO.logo = "\"https://www.mmtimes.com/sites/mmtimes.com/files/styles/mmtimes_ratio_c_three_col/public/default_images/sample-page-02_2.jpg?itok=ox55HGAr\"";
        return publicationVO;
    }
}
