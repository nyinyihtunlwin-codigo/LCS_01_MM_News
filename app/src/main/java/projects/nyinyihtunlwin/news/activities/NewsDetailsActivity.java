package projects.nyinyihtunlwin.news.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.news.R;
import projects.nyinyihtunlwin.news.adapters.NewsImagesPagerAdapter;

/**
 * Created by Dell on 11/11/2017.
 */

public class NewsDetailsActivity extends AppCompatActivity {

    @BindView(R.id.vp_news_details_images)
    ViewPager vpNewsDetailsImages;


    public static Intent newIntent(Context context) {
        // This is static factory method
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        return intent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this, this);

        NewsImagesPagerAdapter newsImagesPagerAdapter = new NewsImagesPagerAdapter(getApplicationContext());
        vpNewsDetailsImages.setAdapter(newsImagesPagerAdapter);
    }
}
