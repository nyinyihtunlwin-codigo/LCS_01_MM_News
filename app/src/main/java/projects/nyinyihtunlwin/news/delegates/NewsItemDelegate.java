package projects.nyinyihtunlwin.news.delegates;

import android.view.View;

import projects.nyinyihtunlwin.news.data.vo.NewsVO;

/**
 * Created by Dell on 11/11/2017.
 */

public interface NewsItemDelegate {
    void onTapComment();

    void onTapSentTo();

    void onTapFavourite();

    void onTapStatistics();

    void onTapNews(View view, NewsVO newsVO);
}
