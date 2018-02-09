package projects.nyinyihtunlwin.news.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.news.R;
import projects.nyinyihtunlwin.news.SFCNewsApp;
import projects.nyinyihtunlwin.news.adapters.NewsAdapter;
import projects.nyinyihtunlwin.news.components.EmptyViewPod;
import projects.nyinyihtunlwin.news.components.SmartRecyclerView;
import projects.nyinyihtunlwin.news.components.SmartScrollListener;
import projects.nyinyihtunlwin.news.data.models.NewsModel;
import projects.nyinyihtunlwin.news.data.vo.NewsVO;
import projects.nyinyihtunlwin.news.delegates.NewsItemDelegate;
import projects.nyinyihtunlwin.news.events.RestApiEvents;
import projects.nyinyihtunlwin.news.events.TapNewsEvent;
import projects.nyinyihtunlwin.news.mvp.presenters.NewsListPresenter;
import projects.nyinyihtunlwin.news.mvp.views.NewsListView;
import projects.nyinyihtunlwin.news.persistence.MMNewsContract;

public class MainActivity
        extends BaseActivity
        implements LoaderManager.LoaderCallbacks<Cursor>, NewsListView, GoogleApiClient.OnConnectionFailedListener {

    private static final int NEWS_LOADER_ID = 1001;
    protected static final int RC_GOOGLE_SIGN_IN = 1236;


    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.rv_news)
    SmartRecyclerView rvNews;

    @BindView(R.id.vp_empty_news)
    EmptyViewPod vpEmptyNews;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    NewsListPresenter mPresenter;

    private NewsAdapter newsAdapter;

    protected GoogleApiClient mGoogleApiClient;

    private SmartScrollListener mSmartScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this, this);


        SFCNewsApp applicationContext = (SFCNewsApp) getApplicationContext();
        applicationContext.getAppComponent().inject(this);

        mPresenter.onCreate(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
       /*         Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                //        drawerLayout.openDrawer(GravityCompat.END); // where to start open (drawer / navigation view)
         /*       Intent intent = LoginRegisterActivity.newIntent(getApplicationContext());
                startActivity(intent);*/
                Snackbar.make(rvNews, "You need to sign with Google to publish in MM-News.", Snackbar.LENGTH_INDEFINITE).setAction("Sign-In", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.onStartPublishingNews();
                    }
                }).show();
            }
        });
        rvNews.setEmptyView(vpEmptyNews);
        rvNews.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        newsAdapter = new NewsAdapter(getApplicationContext(), mPresenter);
        rvNews.setAdapter(newsAdapter);

        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.OnSmartScrollListener() {
            @Override
            public void onListEndReached() {
                Snackbar.make(rvNews, "Loading new data.", Snackbar.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(true);

                mPresenter.onNewsListEndReach(getApplicationContext());
            }
        });
        rvNews.addOnScrollListener(mSmartScrollListener);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.onForceRefresh(getApplicationContext());
            }
        });

        getSupportLoaderManager().initLoader(NEWS_LOADER_ID, null, this);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("949797302459-m2ep90333cqljjsiub39to7d9j2549di.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this /*FragmentActivity*/, this /*OnConnectionFailedListener*/)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            processGoogleSignInResult(result);
        }
    }

    private void processGoogleSignInResult(GoogleSignInResult signInResult) {
        if (signInResult.isSuccess()) {
            // Google Sign-In was successful, authenticate with Firebase
            GoogleSignInAccount account = signInResult.getSignInAccount();
            mPresenter.onSuccessGoogleSignIn(account);
        } else {
            // Google Sign-In failed
            Log.e(SFCNewsApp.LOG_TAG, "Google Sign-In failed.");
            Snackbar.make(rvNews, "Your Google sign-in failed.", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        mPresenter.onStart();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTapNewsEvent(TapNewsEvent event) {
/*        Intent intent = NewsDetailsActivity.newIntent(getApplicationContext());
        startActivity(intent);*/
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewsDataLoaded(RestApiEvents.NewsDataLoadedEvent event) {
   /*     newsAdapter.appendNewData(event.getLoadedNews());
        swipeRefreshLayout.setRefreshing(false);*/
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorInvokingAPI(RestApiEvents.ErrorInvokingAPIEvent event) {
        Snackbar.make(rvNews, event.getErrorMsg(), Snackbar.LENGTH_INDEFINITE).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        mPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getApplicationContext(),
                MMNewsContract.NewsEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mPresenter.onDataLoaded(getApplicationContext(), data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void displayNewsList(List<NewsVO> newsList) {
        newsAdapter.setNewData(newsList);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoding() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void navigateToNewsDetails(NewsVO newsVO) {
        Intent intent = NewsDetailsActivity.newIntent(getApplicationContext(), newsVO.getNewsId());
        startActivity(intent);
 /*       ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                // the context of the activity
                MainActivity.this,

                // For each shared element, add to this method a new Pair item,
                // which contains the reference of the view we are transitioning *from*,
                // and the value of the transitionName attribute
                new Pair<View, String>(view.findViewById(R.id.iv_publication_logo),
                        getString(R.string.transition_name_publication_logo)),
                new Pair<View, String>(view.findViewById(R.id.tv_publication_name),
                        getString(R.string.transition_name_publication_name)),
                new Pair<View, String>(view.findViewById(R.id.tv_publish_date),
                        getString(R.string.transition_name_publish_date))
        );
        ActivityCompat.startActivity(MainActivity.this, intent, options.toBundle());*/
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void navigateToAddNewsScreen() {
        Intent intent = AddNewsActivity.newIntent(getApplicationContext());
        startActivity(intent);
    }

    @Override
    public void signInGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "onConnectionFailed", Toast.LENGTH_SHORT).show();
    }
}
