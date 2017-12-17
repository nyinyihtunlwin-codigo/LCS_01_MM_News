package projects.nyinyihtunlwin.news.data.vo;

import android.content.ContentValues;

import com.google.gson.annotations.SerializedName;

import projects.nyinyihtunlwin.news.persistence.MMNewsContract;

/**
 * Created by Dell on 12/3/2017.
 */

class SendToVO {

    @SerializedName("send-to-id")
    private String sendToId;

    @SerializedName("sent-date")
    private String sentDate;

    @SerializedName("acted-user")
    private ActedUserVO actedUser;

    @SerializedName("received-user")
    private ActedUserVO receivedUser;

    public String getSendToId() {
        return sendToId;
    }

    public String getSentDate() {
        return sentDate;
    }

    public ActedUserVO getActedUser() {
        return actedUser;
    }

    public ActedUserVO getReceivedUser() {
        return receivedUser;
    }


    public ContentValues parseToContentValues(String newsId) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(MMNewsContract.SendToEntry.COLUMN_SENT_TO_ID, sendToId);
        contentValues.put(MMNewsContract.SendToEntry.COLUMN_SENT_DATE, sentDate);
        contentValues.put(MMNewsContract.SendToEntry.COLUMN_NEWS_ID, newsId);
        contentValues.put(MMNewsContract.SendToEntry.COLUMN_SENDER_ID, actedUser.getUserId());
        contentValues.put(MMNewsContract.SendToEntry.COLUMN_RECEIVER_ID, receivedUser.getUserId());

        return contentValues;
    }
}
