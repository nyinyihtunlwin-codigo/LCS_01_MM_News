package projects.nyinyihtunlwin.news.data.vo;

import android.content.ContentValues;

import com.google.gson.annotations.SerializedName;

import projects.nyinyihtunlwin.news.persistence.MMNewsContract;

/**
 * Created by Dell on 12/3/2017.
 */

public class CommentVO {

    @SerializedName("comment-id")
    private String commentId;

    @SerializedName("comment")
    private String comment;

    @SerializedName("comment-date")
    private String commentDate;

    @SerializedName("acted-user")
    private ActedUserVO actedUser;

    public String getCommentId() {
        return commentId;
    }

    public String getComment() {
        return comment;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public ActedUserVO getActedUser() {
        return actedUser;
    }

    public ContentValues parseToContentValues(String newsId) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(MMNewsContract.CommentEntry.COLUMN_COMMENT_ID, commentId);
        contentValues.put(MMNewsContract.CommentEntry.COLUMN_COMMENT, comment);
        contentValues.put(MMNewsContract.CommentEntry.COLUMN_COMMENT_DATE, commentDate);
        contentValues.put(MMNewsContract.CommentEntry.COLUMN_NEWS_ID, newsId);
        contentValues.put(MMNewsContract.CommentEntry.COLUMN_USER_ID, actedUser.getUserId());

        return contentValues;
    }
}
