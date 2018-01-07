package projects.nyinyihtunlwin.news.dagger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import projects.nyinyihtunlwin.news.network.MMNewsDataAgent;
import projects.nyinyihtunlwin.news.network.MMNewsDataAgentImpl;

/**
 * Created by Dell on 1/7/2018.
 */
@Module
public class NetworkModule {
    @Provides
    @Singleton
    public MMNewsDataAgent provideMMNewsDataAgent() {
        return new MMNewsDataAgentImpl();
    }
}
