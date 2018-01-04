package projects.nyinyihtunlwin.news.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.news.R;
import projects.nyinyihtunlwin.news.adapters.NewsImagesPagerAdapter;
import projects.nyinyihtunlwin.news.adapters.RelatedNewsAdapter;
import projects.nyinyihtunlwin.news.data.vo.NewsVO;
import projects.nyinyihtunlwin.news.persistence.MMNewsContract;

/**
 * Created by Dell on 11/11/2017.
 */

public class NewsDetailsActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.vp_news_details_images)
    ViewPager vpNewsDetailsImages;

    @BindView(R.id.iv_publication_logo)
    ImageView ivPublicationLogo;

    @BindView(R.id.tv_publication_name)
    TextView tvPublicationName;

    @BindView(R.id.tv_publish_date)
    TextView tvPublishDate;

    @BindView(R.id.tv_news_details)
    TextView tvNewsDetails;

    private static final String IE_NEWS_ID = "IE_NEWS_ID"; // IE = intent extra
    private static final int NEWS_DETAILS_LOADER_ID = 1002;

    private String mNewsId;

    private NewsImagesPagerAdapter mNewsImagesPagerAdapter;

    /**
     * Create intent object to start NewsDetailsActivity.
     *
     * @param context
     * @param newsId  : id for news object.
     * @return
     */
    public static Intent newIntent(Context context, String newsId) {
        // This is static factory method
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(IE_NEWS_ID, newsId);
        return intent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this, this);

        mNewsImagesPagerAdapter = new NewsImagesPagerAdapter(getApplicationContext());
        vpNewsDetailsImages.setAdapter(mNewsImagesPagerAdapter);


        mNewsId = getIntent().getStringExtra(IE_NEWS_ID);
        if (TextUtils.isEmpty(mNewsId)) {
            throw new RuntimeException("newsId required for NewsDetailsActivity");
        } else {
            getSupportLoaderManager().initLoader(NEWS_DETAILS_LOADER_ID, null, this);
        }


        RecyclerView rvRelatedNews = findViewById(R.id.rv_related_news);
        rvRelatedNews.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        RelatedNewsAdapter relatedNewsAdapter = new RelatedNewsAdapter(getApplicationContext());
        rvRelatedNews.setAdapter(relatedNewsAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getApplicationContext(),
                MMNewsContract.NewsEntry.CONTENT_URI,
                null,
                MMNewsContract.NewsEntry.COLUMN_NEWS_ID + " = ?",
                new String[]{mNewsId},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            NewsVO newsVO = NewsVO.parseFromCursor(getApplicationContext(), data);
            bindData(newsVO);
        }
    }

    private void bindData(NewsVO newsVO) {
        tvPublicationName.setText(newsVO.getPublication().getTitle());
        tvPublicationName.setText(newsVO.getPostedDate());
        tvNewsDetails.setText(newsVO.getDetails());

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.img_publication_logo_placeholder)
                .centerCrop();
        Glide.with(this).load(newsVO.getPublication().getLogo()).apply(requestOptions).into(ivPublicationLogo);

        if (newsVO.getImages().isEmpty()) {

        } else {
            mNewsImagesPagerAdapter.setImages(newsVO.getImages());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
