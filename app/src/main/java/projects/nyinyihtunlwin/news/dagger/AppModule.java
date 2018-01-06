package projects.nyinyihtunlwin.news.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import projects.nyinyihtunlwin.news.SFCNewsApp;

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
    public Context provideContext() {
        return mApp.getApplicationContext();
    }

}
