package projects.nyinyihtunlwin.news.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import projects.nyinyihtunlwin.news.R;

/**
 * Created by Dell on 11/11/2017.
 */

public class NewsImagesPagerAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;

    public NewsImagesPagerAdapter(Context context) {
        super();
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.view_item_news_details_image, container, false);
        container.addView(view);  // attach created view to container
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
