package projects.nyinyihtunlwin.news.network;

import org.greenrobot.eventbus.EventBus;

import projects.nyinyihtunlwin.news.events.RestApiEvents;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dell on 12/9/2017.
 */

public abstract class MMNewsCallback<T extends MMNewsResponse> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        MMNewsResponse mmNewsResponse = response.body();
        if(mmNewsResponse==null){
            RestApiEvents.ErrorInvokingAPIEvent errorEvent
                    = new RestApiEvents.ErrorInvokingAPIEvent("No data could be load for now. Please try again later.");
            EventBus.getDefault().post(errorEvent);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        RestApiEvents.ErrorInvokingAPIEvent errorEvent
                = new RestApiEvents.ErrorInvokingAPIEvent(t.getMessage());
        EventBus.getDefault().post(errorEvent);
    }
}
