package projects.nyinyihtunlwin.news.network;

import android.content.Context;

/**
 * Created by Dell on 12/3/2017.
 */

public interface MMNewsDataAgent {
    void loadMMNews(String accessToken, int pageNo, Context context);
}
