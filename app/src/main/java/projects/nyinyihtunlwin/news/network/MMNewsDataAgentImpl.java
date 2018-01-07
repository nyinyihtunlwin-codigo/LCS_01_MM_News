package projects.nyinyihtunlwin.news.network;

import android.content.Context;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import projects.nyinyihtunlwin.news.events.RestApiEvents;
import projects.nyinyihtunlwin.news.network.responses.GetNewsResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dell on 12/3/2017.
 */

public class MMNewsDataAgentImpl implements MMNewsDataAgent {

    private MMNewsAPI theAPI;

    public MMNewsDataAgentImpl() {
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        // time 60 sec is optimal.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://padcmyanmar.com/padc-3/mm-news/apis/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(okHttpClient)
                .build();

        theAPI = retrofit.create(MMNewsAPI.class);
    }

    @Override
    public void loadMMNews(String accessToken, int pageNo, final Context context) {
        Call<GetNewsResponse> loadMMNewsCall = theAPI.loadMMNews(pageNo, accessToken);
        loadMMNewsCall.enqueue(new MMNewsCallback<GetNewsResponse>() {
            @Override
            public void onResponse(Call<GetNewsResponse> call, Response<GetNewsResponse> response) {
                super.onResponse(call,response);
                GetNewsResponse getNewsResponse = response.body();
                if (getNewsResponse != null
                        && getNewsResponse.getNewsList().size() > 0) {
                    RestApiEvents.NewsDataLoadedEvent newsDataLoadedEvent = new RestApiEvents.NewsDataLoadedEvent(
                            getNewsResponse.getPageNo(), getNewsResponse.getNewsList(),context);
                    EventBus.getDefault().post(newsDataLoadedEvent);
                }
            }
        });
    }
}
