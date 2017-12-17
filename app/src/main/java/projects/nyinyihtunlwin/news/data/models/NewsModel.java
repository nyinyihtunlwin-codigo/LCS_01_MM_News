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
import projects.nyinyihtunlwin.news.data.vo.NewsVO;
import projects.nyinyihtunlwin.news.events.RestApiEvents;
import projects.nyinyihtunlwin.news.network.MMNewsDataAgentImpl;
import projects.nyinyihtunlwin.news.persistence.MMNewsContract;
import projects.nyinyihtunlwin.news.utils.AppConstants;

/**
 * Created by Dell on 12/3/2017.
 */

public class NewsModel {

    private static NewsModel objInstance;

    private List<NewsVO> mNews;
    private int mmNewsPageIndex = 1;

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
        MMNewsDataAgentImpl.getInstance().loadMMNews(AppConstants.ACCESS_TOKEN, mmNewsPageIndex, context);
    }

    public List<NewsVO> getNews() {
        return mNews;
    }

    public void loadMoreNews(Context context) {
        MMNewsDataAgentImpl.getInstance().loadMMNews(AppConstants.ACCESS_TOKEN, mmNewsPageIndex, context);
    }

    @Subscribe
    public void onNewsDataLoaded(RestApiEvents.NewsDataLoadedEvent event) {
        mNews.addAll(event.getLoadedNews());
        mmNewsPageIndex = event.getLoadedPageIndex() + 1;

        //TODO Logic to save the data in Persistence Layer
        ContentValues[] newsCVs = new ContentValues[event.getLoadedNews().size()];
        for (int index = 0; index < newsCVs.length; index++) {
            newsCVs[index] = event.getLoadedNews().get(index).parseToContentValues();
        }

        int insertedRowCount = event.getContext().getContentResolver().bulkInsert(MMNewsContract.NewsEntry.CONTENT_URI, newsCVs);
        Log.d(SFCNewsApp.LOG_TAG, "Inserted row : " + insertedRowCount);
    }

    public void forceRefreshNews(Context context) {
        mNews = new ArrayList<>();
        mmNewsPageIndex = 1;
        startLoadingMMNews(context);
    }
}
