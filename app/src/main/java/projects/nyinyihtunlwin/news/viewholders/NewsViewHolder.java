package projects.nyinyihtunlwin.news.viewholders;

import android.view.View;

import org.greenrobot.eventbus.EventBus;

import projects.nyinyihtunlwin.news.data.vo.NewsVO;
import projects.nyinyihtunlwin.news.delegates.NewsItemDelegate;
import projects.nyinyihtunlwin.news.events.TapNewsEvent;

/**
 * Created by Dell on 11/4/2017.
 */

public class NewsViewHolder extends BaseViewHolder<NewsVO> {

    private NewsItemDelegate mNewsItemDelegate;

    public NewsViewHolder(final View itemView, final NewsItemDelegate newsItemDelegate) {
        super(itemView);
        mNewsItemDelegate = newsItemDelegate;
    }

    @Override
    public void setData(NewsVO data) {

    }

    @Override
    public void onClick(View view) {
        //  mNewsItemDelegate.onTapNews(itemView);
        EventBus.getDefault().post(new TapNewsEvent("news_id"));
    }
}
