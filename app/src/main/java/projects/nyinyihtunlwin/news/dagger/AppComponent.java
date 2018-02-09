package projects.nyinyihtunlwin.news.dagger;

import javax.inject.Singleton;

import dagger.Component;
import projects.nyinyihtunlwin.news.SFCNewsApp;
import projects.nyinyihtunlwin.news.activities.AddNewsActivity;
import projects.nyinyihtunlwin.news.activities.MainActivity;
import projects.nyinyihtunlwin.news.data.models.NewsModel;
import projects.nyinyihtunlwin.news.mvp.presenters.AddNewsPresenter;
import projects.nyinyihtunlwin.news.mvp.presenters.NewsListPresenter;

/**
 * Created by Dell on 1/6/2018.
 */
@Component(modules = {AppModule.class,UtilsModule.class,NetworkModule.class})
@Singleton
public interface AppComponent {

    void inject(SFCNewsApp app);

    void inject(NewsModel newsModel);

    void inject(NewsListPresenter newsListPresenter);

    void inject(MainActivity mainActivity);

    void inject(AddNewsActivity addNewsActivity);

    void inject(AddNewsPresenter addNewsPresenter);

}
