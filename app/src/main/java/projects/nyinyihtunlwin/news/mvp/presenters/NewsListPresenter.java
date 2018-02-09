package projects.nyinyihtunlwin.news.mvp.presenters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import projects.nyinyihtunlwin.news.SFCNewsApp;
import projects.nyinyihtunlwin.news.data.models.NewsModel;
import projects.nyinyihtunlwin.news.data.vo.NewsVO;
import projects.nyinyihtunlwin.news.delegates.NewsItemDelegate;
import projects.nyinyihtunlwin.news.mvp.views.NewsListView;

/**
 * Created by Dell on 1/6/2018.
 */

public class NewsListPresenter extends BasePresenter<NewsListView> implements NewsItemDelegate {

    @Inject
    NewsModel mNewsModel;

    public NewsListPresenter() {
    }

    @Override
    public void onCreate(NewsListView view) {
        super.onCreate(view);
        SFCNewsApp context = (SFCNewsApp) mView.getContext();
        context.getAppComponent().inject(this);
    }


    @Override
    public void onStart() {
        List<NewsVO> newsList = mNewsModel.getNews();
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

        mNewsModel.loadMoreNews(context);
    }

    public void onForceRefresh(Context context) {
        mNewsModel.forceRefreshNews(context);
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
     *
     * @param view
     * @param newsVO
     */
    @Override
    public void onTapNews(View view, NewsVO newsVO) {
        mView.navigateToNewsDetails(newsVO);
    }

    public void onSuccessGoogleSignIn(GoogleSignInAccount signInAccount) {
        mNewsModel.authenticateUserWithGoogleAccount(signInAccount, new NewsModel.UserAuthenticateDelegate() {
            @Override
            public void onSuccessAuthenticate(GoogleSignInAccount signInAccount) {
                Log.d(SFCNewsApp.LOG_TAG, "onSuccessAuthenticate");
            }

            @Override
            public void onFailureAuthenticate(String errrorMsg) {
                Log.d(SFCNewsApp.LOG_TAG, "onFailureAuthenticate");
            }
        });
    }

    public void onStartPublishingNews() {
        if(mNewsModel.isUserAuthenticate()){
            mView.navigateToAddNewsScreen();
        }else {
            mView.signInGoogle();
        }
    }
}
