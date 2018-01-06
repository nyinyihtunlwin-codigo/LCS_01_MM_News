package projects.nyinyihtunlwin.news.dagger;

import dagger.Component;
import projects.nyinyihtunlwin.news.SFCNewsApp;

/**
 * Created by Dell on 1/6/2018.
 */
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(SFCNewsApp app);

}
