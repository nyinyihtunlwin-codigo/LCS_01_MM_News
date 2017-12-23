package projects.nyinyihtunlwin.news.data.vo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import projects.nyinyihtunlwin.news.persistence.MMNewsContract;

/**
 * Created by Dell on 12/2/2017.
 */

public class NewsVO {

    @SerializedName("news-id")
    private String newsId;

    @SerializedName("brief")
    private String brief;

    @SerializedName("details")
    private String details;

    @SerializedName("images")
    private List<String> images;

    @SerializedName("posted-date")
    private String postedDate;

    @SerializedName("publication")
    private PublicationVO publication;

    @SerializedName("favorites")
    private List<FavouriteActionVO> favourites;

    @SerializedName("comments")
    private List<CommentVO> comments;

    @SerializedName("sent-tos")
    private List<SendToVO> sendTos;

    public String getNewsId() {
        return newsId;
    }

    public String getBrief() {
        return brief;
    }

    public String getDetails() {
        return details;
    }

    public List<String> getImages() {
        if (images == null) {
            images = new ArrayList<>();
        }
        return images;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public PublicationVO getPublication() {
        return publication;
    }

    public List<FavouriteActionVO> getFavourites() {
        if (favourites == null) {
            favourites = new ArrayList<>();
        }
        return favourites;
    }

    public List<CommentVO> getComments() {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        return comments;
    }

    public List<SendToVO> getSendTos() {
        return sendTos;
    }

    public ContentValues parseToContentValues() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(MMNewsContract.NewsEntry.COLUMN_NEWS_ID, newsId);
        contentValues.put(MMNewsContract.NewsEntry.COLUMN_BRIEF, brief);
        contentValues.put(MMNewsContract.NewsEntry.COLUMN_DETAILS, details);
        contentValues.put(MMNewsContract.NewsEntry.COLUMN_POSTED_DATE, postedDate);
        contentValues.put(MMNewsContract.NewsEntry.COLUMN_PUBLICATIOIN_ID, publication.getPublicationId());

        return contentValues;
    }

    public static NewsVO parseFromCursor(Context context, Cursor cursor) {
        NewsVO news = new NewsVO();
        news.newsId = cursor.getString(cursor.getColumnIndex(MMNewsContract.NewsEntry.COLUMN_NEWS_ID));
        news.brief = cursor.getString(cursor.getColumnIndex(MMNewsContract.NewsEntry.COLUMN_BRIEF));
        news.details = cursor.getString(cursor.getColumnIndex(MMNewsContract.NewsEntry.COLUMN_DETAILS));
        news.postedDate = cursor.getString(cursor.getColumnIndex(MMNewsContract.NewsEntry.COLUMN_POSTED_DATE));

        news.publication = PublicationVO.parseFromCursor(cursor);
        news.images = loadImagesInNews(context, news.newsId);
        news.favourites = loadFavoriteActionsInNews(context, news.newsId);
        news.comments = loadCommentActionsInNews(context, news.newsId);
        return news;
    }

    private static List<CommentVO> loadCommentActionsInNews(Context context, String newsId) {
        Cursor commentActionCursor = context.getContentResolver().query(MMNewsContract.CommentEntry.CONTENT_URI,
                null,
                MMNewsContract.CommentEntry.COLUMN_NEWS_ID + " = ?", new String[]{newsId},
                null);
        if (commentActionCursor != null && commentActionCursor.moveToFirst()) {
            List<CommentVO> commentVOList = new ArrayList<>();
            do {
                commentVOList.add(CommentVO.parseFromCursor(commentActionCursor));
            } while (commentActionCursor.moveToNext());
            commentActionCursor.close();
            return commentVOList;
        }
        return null;
    }

    private static List<FavouriteActionVO> loadFavoriteActionsInNews(Context context, String newsId) {
        Cursor favoriteActionCursor = context.getContentResolver().query(MMNewsContract.FavoriteActionEntry.CONTENT_URI,
                null,
                MMNewsContract.FavoriteActionEntry.COLUMN_NEWS_ID + " = ?", new String[]{newsId},
                null);
        if (favoriteActionCursor != null && favoriteActionCursor.moveToFirst()) {
            List<FavouriteActionVO> favouriteActionVOList = new ArrayList<>();
            do {
                favouriteActionVOList.add(FavouriteActionVO.parseFromCursor(favoriteActionCursor));
            } while (favoriteActionCursor.moveToNext());
            favoriteActionCursor.close();
            return favouriteActionVOList;
        }
        return null;
    }

    private static List<String> loadImagesInNews(Context context, String newsId) {
        Cursor imagesInNewsCursor = context.getContentResolver().query(MMNewsContract.ImageInNewsEntry.CONTENT_URI,
                null,
                MMNewsContract.ImageInNewsEntry.COLUMN_NEWS_ID + " = ?", new String[]{newsId},
                null);

        if (imagesInNewsCursor != null && imagesInNewsCursor.moveToFirst()) {
            List<String> imagesInNews = new ArrayList<>();
            do {
                imagesInNews.add(
                        imagesInNewsCursor.getString(
                                imagesInNewsCursor.getColumnIndex(MMNewsContract.ImageInNewsEntry.COLUMN_IMAGE_URL)
                        )
                );
            } while (imagesInNewsCursor.moveToNext());
            imagesInNewsCursor.close();
            return imagesInNews;
        }
        return null;
    }
}
