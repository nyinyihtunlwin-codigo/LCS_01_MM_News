package projects.nyinyihtunlwin.news.mvp.views;

import android.content.Context;

import java.util.List;

import projects.nyinyihtunlwin.news.data.vo.NewsVO;

/**
 * Created by Dell on 1/6/2018.
 */

public interface NewsListView {

    void displayNewsList(List<NewsVO> newsList);

    void showLoding();

    void navigateToNewsDetails(NewsVO newsVO);

    Context getContext();
}
