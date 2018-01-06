package projects.nyinyihtunlwin.news.mvp.presenters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import projects.nyinyihtunlwin.news.data.models.NewsModel;
import projects.nyinyihtunlwin.news.data.vo.NewsVO;
import projects.nyinyihtunlwin.news.delegates.NewsItemDelegate;
import projects.nyinyihtunlwin.news.mvp.views.NewsListView;

/**
 * Created by Dell on 1/6/2018.
 */

public class NewsListPresenter extends BasePresenter implements NewsItemDelegate {

    private NewsListView mView;

    public NewsListPresenter(NewsListView view) {
        mView = view;
    }

    @Override
    public void onStart() {
        List<NewsVO> newsList = NewsModel.getInstance().getNews();
        if (!newsList.isEmpty()) {
            mView.displayNewsList(newsList);
        } else {
            mView.showLoding();
        }
    }

    @Override
    public void onStop() {

    }

    public void onNewsListEndReach(Context context) {

        NewsModel.getInstance().loadMoreNews(context);
    }

    public void onForceRefresh(Context context) {
        NewsModel.getInstance().forceRefreshNews(context);
    }

    public void onDataLoaded(Context context, Cursor data) {
        if (data != null && data.moveToFirst()) {
            List<NewsVO> newsList = new ArrayList<>();
            do {
                NewsVO newsVO = NewsVO.parseFromCursor(context, data);
                newsList.add(newsVO);
            } while (data.moveToNext());
            mView.displayNewsList(newsList);
        }
    }


    @Override
    public void onTapComment() {

    }

    @Override
    public void onTapSentTo() {

    }

    @Override
    public void onTapFavourite() {

    }

    @Override
    public void onTapStatistics() {

    }

    /**
     * Wrong way , don't pass view object
     * @param view
     * @param newsVO
     */
    @Override
    public void onTapNews(View view, NewsVO newsVO) {
        mView.navigateToNewsDetails(newsVO);
    }
}
