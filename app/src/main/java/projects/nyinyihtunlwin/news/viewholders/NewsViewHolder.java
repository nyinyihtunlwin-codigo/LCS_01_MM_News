package projects.nyinyihtunlwin.news.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import projects.nyinyihtunlwin.news.delegates.NewsItemDelegate;

/**
 * Created by Dell on 11/4/2017.
 */

public class NewsViewHolder extends RecyclerView.ViewHolder {
    private NewsItemDelegate mNewsItemDelegate;

    public NewsViewHolder(final View itemView, final NewsItemDelegate newsItemDelegate) {
        super(itemView);
        mNewsItemDelegate = newsItemDelegate;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNewsItemDelegate.onTapNews(itemView);
            }
        });
    }
}
