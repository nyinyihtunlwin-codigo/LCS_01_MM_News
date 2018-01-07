package projects.nyinyihtunlwin.news;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import javax.inject.Inject;

import projects.nyinyihtunlwin.news.dagger.AppComponent;
import projects.nyinyihtunlwin.news.dagger.AppModule;
import projects.nyinyihtunlwin.news.dagger.DaggerAppComponent;
import projects.nyinyihtunlwin.news.data.models.NewsModel;
import projects.nyinyihtunlwin.news.utils.ConfigUtils;

/**
 * Created by Dell on 11/4/2017.
 */

public class SFCNewsApp extends Application {

    public static final String LOG_TAG = "SFCNewsApp";

    private AppComponent mAppComponent;

    @Inject
    Context mContext;

    @Inject
    NewsModel mNewsModel;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = initDagger(); //daggar init
        mAppComponent.inject(this);//register consumer

        mNewsModel.startLoadingMMNews(getApplicationContext());

        Log.d(LOG_TAG, "Context : " + mContext);
    }

    private AppComponent initDagger() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
