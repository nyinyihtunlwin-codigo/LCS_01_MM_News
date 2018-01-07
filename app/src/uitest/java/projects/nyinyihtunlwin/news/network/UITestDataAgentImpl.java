package projects.nyinyihtunlwin.news.network;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;

import projects.nyinyihtunlwin.news.SFCNewsApp;
import projects.nyinyihtunlwin.news.events.RestApiEvents;
import projects.nyinyihtunlwin.news.network.responses.GetNewsResponse;

/**
 * Created by Dell on 1/7/2018.
 */

public class UITestDataAgentImpl implements MMNewsDataAgent {

    private static final String PATH_OFFLINE_DATA = "pseudo_data";
    private static final String OFFLINE_MMNEWS = "offline-mmnews.json";

    @Override
    public void loadMMNews(String accessToken, int pageNo, Context context) {
        Log.d(SFCNewsApp.LOG_TAG, "loading news from offline json");
        try {
            String offlineData = loadOfflineData(context, OFFLINE_MMNEWS);
            GetNewsResponse getNewsResponse = new Gson().fromJson(offlineData, GetNewsResponse.class);

            if (getNewsResponse != null
                    && getNewsResponse.getNewsList().size() > 0) {
                RestApiEvents.NewsDataLoadedEvent newsDataLoadedEvent = new RestApiEvents.NewsDataLoadedEvent(
                        getNewsResponse.getPageNo(), getNewsResponse.getNewsList(), context);
                EventBus.getDefault().post(newsDataLoadedEvent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read text from assets folder.
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    private byte[] readJsonFile(Context context, String fileName) throws IOException {
        InputStream inStream = context.getAssets().open(fileName);
        int size = inStream.available();
        byte[] buffer = new byte[size];
        inStream.read(buffer);
        inStream.close();
        return buffer;
    }

    /**
     * @param fileName - name of Json File.
     * @return JSONObject from loaded file.
     * @throws IOException
     * @throws JSONException
     */
    public String loadOfflineData(Context context, String fileName) throws IOException, JSONException {
        byte[] buffer = readJsonFile(context, PATH_OFFLINE_DATA + "/" + fileName);
        return new String(buffer, "UTF-8").toString();
    }
}
