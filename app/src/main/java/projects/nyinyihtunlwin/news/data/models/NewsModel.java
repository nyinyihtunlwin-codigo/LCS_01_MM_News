package projects.nyinyihtunlwin.news.data.models;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import projects.nyinyihtunlwin.news.SFCNewsApp;
import projects.nyinyihtunlwin.news.data.vo.ActedUserVO;
import projects.nyinyihtunlwin.news.data.vo.CommentVO;
import projects.nyinyihtunlwin.news.data.vo.FavouriteActionVO;
import projects.nyinyihtunlwin.news.data.vo.NewsVO;
import projects.nyinyihtunlwin.news.data.vo.PublicationVO;
import projects.nyinyihtunlwin.news.data.vo.SendToVO;
import projects.nyinyihtunlwin.news.events.RestApiEvents;
import projects.nyinyihtunlwin.news.network.MMNewsDataAgentImpl;
import projects.nyinyihtunlwin.news.persistence.MMNewsContract;
import projects.nyinyihtunlwin.news.utils.AppConstants;
import projects.nyinyihtunlwin.news.utils.ConfigUtils;

/**
 * Created by Dell on 12/3/2017.
 */

public class NewsModel {

    private static NewsModel objInstance;

    private List<NewsVO> mNews;
    // private int mmNewsPageIndex = 1;

    private NewsModel() {
        EventBus.getDefault().register(this);
        mNews = new ArrayList<>();
    }

    public static NewsModel getInstance() {
        if (objInstance == null) {
            objInstance = new NewsModel();
        }
        return objInstance;
    }

    public void startLoadingMMNews(Context context) {
        MMNewsDataAgentImpl.getInstance().loadMMNews(AppConstants.ACCESS_TOKEN, ConfigUtils.getObjInstance().loadPageIndex(), context);
    }

    public List<NewsVO> getNews() {
        return mNews;
    }

    public void loadMoreNews(Context context) {
        int pageIndex = ConfigUtils.getObjInstance().loadPageIndex();
        MMNewsDataAgentImpl.getInstance().loadMMNews(AppConstants.ACCESS_TOKEN, pageIndex, context);
    }

    @Subscribe
    public void onNewsDataLoaded(RestApiEvents.NewsDataLoadedEvent event) {
        mNews.addAll(event.getLoadedNews());
        ConfigUtils.getObjInstance().savePageIndex(event.getLoadedPageIndex() + 1);

        //TODO Logic to save the data in Persistence Layer
        ContentValues[] newsCVs = new ContentValues[event.getLoadedNews().size()];
        List<ContentValues> publicationCVList = new ArrayList<>();
        List<ContentValues> imagesInNewsCVList = new ArrayList<>();
        List<ContentValues> favoriteActionsInNewsCVList = new ArrayList<>();
        List<ContentValues> userInActionCVList = new ArrayList<>();
        List<ContentValues> commentsInNewsCVList = new ArrayList<>();
        List<ContentValues> sentToInNewsCVList = new ArrayList<>();
        for (int index = 0; index < newsCVs.length; index++) {
            NewsVO newsVO = event.getLoadedNews().get(index);
            newsCVs[index] = newsVO.parseToContentValues();

            PublicationVO publication = newsVO.getPublication();
            publicationCVList.add(publication.parseToContentValues());

            for (String imageUrl : newsVO.getImages()) {
                ContentValues imagesInNewsCV = new ContentValues();
                imagesInNewsCV.put(MMNewsContract.ImageInNewsEntry.COLUMN_NEWS_ID, newsVO.getNewsId());
                imagesInNewsCV.put(MMNewsContract.ImageInNewsEntry.COLUMN_IMAGE_URL, imageUrl);
                imagesInNewsCVList.add(imagesInNewsCV);
            }
            for (FavouriteActionVO favouriteActionVO : newsVO.getFavourites()) {

                ContentValues favoriteActionCV = favouriteActionVO.parseToContentValues(newsVO.getNewsId());
                favoriteActionsInNewsCVList.add(favoriteActionCV);

                ContentValues actedUserCV = favouriteActionVO.getActedUser().parseToContentValues();
                userInActionCVList.add(actedUserCV);
            }
            for (CommentVO commentVO : newsVO.getComments()) {
                ContentValues commentActionCV = commentVO.parseToContentValues(newsVO.getNewsId());
                commentsInNewsCVList.add(commentActionCV);

                ContentValues actedUserCV = commentVO.getActedUser().parseToContentValues();
                userInActionCVList.add(actedUserCV);
            }
            for (SendToVO sendToVO : newsVO.getSendTos()) {
                ContentValues sentToActionCV = sendToVO.parseToContentValues(newsVO.getNewsId());
                sentToInNewsCVList.add(sentToActionCV);

                ContentValues senderCV = sendToVO.getActedUser().parseToContentValues();
                userInActionCVList.add(senderCV);

                ContentValues receiverCV = sendToVO.getReceivedUser().parseToContentValues();
                userInActionCVList.add(receiverCV);
            }
        }


        int insertedPublications = event.getContext().getContentResolver().bulkInsert(MMNewsContract.PublicationEntry.CONTENT_URI,
                publicationCVList.toArray(new ContentValues[0]));
        Log.d(SFCNewsApp.LOG_TAG, "inserted Publications :" + insertedPublications);

        int insertedImagesInNews = event.getContext().getContentResolver().bulkInsert(MMNewsContract.ImageInNewsEntry.CONTENT_URI,
                imagesInNewsCVList.toArray(new ContentValues[0]));
        Log.d(SFCNewsApp.LOG_TAG, "inserted Images in News" + insertedImagesInNews);

        int insertedFavoriteActionsInNews = event.getContext().getContentResolver().bulkInsert(MMNewsContract.FavoriteActionEntry.CONTENT_URI,
                favoriteActionsInNewsCVList.toArray(new ContentValues[0]));
        Log.d(SFCNewsApp.LOG_TAG, "inserted Favorite Actions :" + insertedFavoriteActionsInNews);

        int insertedActedUserActions = event.getContext().getContentResolver().bulkInsert(MMNewsContract.UserEntry.CONTENT_URI,
                userInActionCVList.toArray(new ContentValues[0]));
        Log.d(SFCNewsApp.LOG_TAG, "inserted acted users " + insertedActedUserActions);

        int insertedCommentActions = event.getContext().getContentResolver().bulkInsert(MMNewsContract.CommentEntry.CONTENT_URI,
                commentsInNewsCVList.toArray(new ContentValues[0]));
        Log.d(SFCNewsApp.LOG_TAG, "inserted comments " + insertedCommentActions);

        int insertedSentToActions = event.getContext().getContentResolver().bulkInsert(MMNewsContract.SendToEntry.CONTENT_URI,
                sentToInNewsCVList.toArray(new ContentValues[0]));
        Log.d(SFCNewsApp.LOG_TAG, "inserted sentTos " + insertedSentToActions);


        int insertedRowCount = event.getContext().getContentResolver().bulkInsert(MMNewsContract.NewsEntry.CONTENT_URI, newsCVs);
        Log.d(SFCNewsApp.LOG_TAG, "Inserted row : " + insertedRowCount);

    }

    public void forceRefreshNews(Context context) {
        mNews = new ArrayList<>();
        ConfigUtils.getObjInstance().savePageIndex(1);
        startLoadingMMNews(context);
    }
}
