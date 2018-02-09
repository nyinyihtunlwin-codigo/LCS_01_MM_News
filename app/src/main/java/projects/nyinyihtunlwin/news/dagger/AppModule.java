package projects.nyinyihtunlwin.news.dagger;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import projects.nyinyihtunlwin.news.SFCNewsApp;
import projects.nyinyihtunlwin.news.data.models.NewsModel;
import projects.nyinyihtunlwin.news.mvp.presenters.AddNewsPresenter;
import projects.nyinyihtunlwin.news.mvp.presenters.NewsListPresenter;
import projects.nyinyihtunlwin.news.network.MMNewsDataAgent;
import projects.nyinyihtunlwin.news.network.MMNewsDataAgentImpl;
import projects.nyinyihtunlwin.news.utils.ConfigUtils;

/**
 * Created by Dell on 1/6/2018.
 */

@Module
public class AppModule {

    private SFCNewsApp mApp;

    public AppModule(SFCNewsApp app) {
        mApp = app;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mApp.getApplicationContext();
    }

    @Provides
    @Singleton
    public NewsModel provideNewsModel(Context context) {
        return new NewsModel(context);
    }

    @Provides
    public NewsListPresenter provideNewsListPresenter() {
        return new NewsListPresenter();
    }

    @Provides
    public AddNewsPresenter provideAddNewsPresenter() {
        return new AddNewsPresenter();
    }

}
