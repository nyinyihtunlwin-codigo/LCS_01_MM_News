package projects.nyinyihtunlwin.news.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import projects.nyinyihtunlwin.news.R;
import projects.nyinyihtunlwin.news.viewitems.NewsDetailsImageViewItem;

/**
 * Created by Dell on 11/11/2017.
 */

public class NewsImagesPagerAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;
    private List<String> mImages;

    public NewsImagesPagerAdapter(Context context) {
        super();
        layoutInflater = LayoutInflater.from(context);
        mImages = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        NewsDetailsImageViewItem view = (NewsDetailsImageViewItem) layoutInflater.inflate(R.layout.view_item_news_details_image, container, false);

        view.setData(mImages.get(position));

        container.addView(view);  // attach created view to container

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void setImages(List<String> images) {
        this.mImages = images;
        notifyDataSetChanged();
    }
}
