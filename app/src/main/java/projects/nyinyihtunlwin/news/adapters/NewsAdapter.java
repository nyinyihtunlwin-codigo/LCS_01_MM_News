package projects.nyinyihtunlwin.news.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import projects.nyinyihtunlwin.news.R;
import projects.nyinyihtunlwin.news.delegates.NewsItemDelegate;
import projects.nyinyihtunlwin.news.viewholders.NewsViewHolder;

/**
 * Created by Dell on 11/4/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private LayoutInflater layoutInflater;
    private NewsItemDelegate newsItemDelegate;

    public NewsAdapter(Context context, NewsItemDelegate newsItemDelegate) {
        layoutInflater = LayoutInflater.from(context);
        this.newsItemDelegate = newsItemDelegate;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // can use below style , no difference from above constructor
        //  LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.view_item_news, parent, false);
        return new NewsViewHolder(view, newsItemDelegate);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 16;
    }
}
