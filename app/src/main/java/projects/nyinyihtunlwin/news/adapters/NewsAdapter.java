package projects.nyinyihtunlwin.news.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import projects.nyinyihtunlwin.news.R;
import projects.nyinyihtunlwin.news.data.vo.NewsVO;
import projects.nyinyihtunlwin.news.delegates.NewsItemDelegate;
import projects.nyinyihtunlwin.news.viewholders.NewsViewHolder;

/**
 * Created by Dell on 11/4/2017.
 */

public class NewsAdapter extends BaseRecyclerAdapter<NewsViewHolder, NewsVO> {

    private NewsItemDelegate newsItemDelegate;

    public NewsAdapter(Context context, NewsItemDelegate newsItemDelegate) {
        super(context);
        this.newsItemDelegate = newsItemDelegate;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // can use below style , no difference from above constructor
        //  LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = mLayoutInflater.inflate(R.layout.view_item_news, parent, false);
        return new NewsViewHolder(view, newsItemDelegate);
    }
}
