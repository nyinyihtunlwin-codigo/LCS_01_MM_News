package projects.nyinyihtunlwin.news.dagger;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import projects.nyinyihtunlwin.news.utils.ConfigUtils;

/**
 * Created by Dell on 1/7/2018.
 */

@Module
public class UtilsModule {

    @Provides
    @Singleton
    public ConfigUtils provideConfigUtils(Context context){
      return new ConfigUtils(context);
    }
}
