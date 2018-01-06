package projects.nyinyihtunlwin.news;

import android.app.Application;

import dagger.android.support.DaggerAppCompatActivity;
import projects.nyinyihtunlwin.news.dagger.AppComponent;
import projects.nyinyihtunlwin.news.data.models.NewsModel;
import projects.nyinyihtunlwin.news.utils.ConfigUtils;

/**
 * Created by Dell on 11/4/2017.
 */

public class SFCNewsApp extends Application {

    public static final String LOG_TAG = "SFCNewsApp";

    @Override
    public void onCreate() {
        super.onCreate();
        ConfigUtils.initConfigUtils(getApplicationContext());
        NewsModel.getInstance().startLoadingMMNews(getApplicationContext());
    }

    private AppComponent initDagger(SFCNewsApp sfcNewsApp) {
        return null;
    }
}
