package projects.nyinyihtunlwin.news.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import projects.nyinyihtunlwin.news.R;
import projects.nyinyihtunlwin.news.viewholders.RelatedNewsViewHolder;

/**
 * Created by Dell on 11/16/2017.
 */

public class RelatedNewsAdapter extends RecyclerView.Adapter<RelatedNewsViewHolder> {

    private LayoutInflater inflater;

    public RelatedNewsAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RelatedNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_item_related_news, parent, false);
        return new RelatedNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RelatedNewsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
