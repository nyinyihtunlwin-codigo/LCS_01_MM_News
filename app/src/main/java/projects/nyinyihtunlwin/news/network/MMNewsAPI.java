package projects.nyinyihtunlwin.news.network;

import projects.nyinyihtunlwin.news.network.responses.GetNewsResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Dell on 12/3/2017.
 */

public interface MMNewsAPI {

    /**
     * if when invokes api, request data will be form data , so use @Field,@FormUrlEncoded
     * @param pageIndex
     * @param accessToken
     * @return
     */
    @FormUrlEncoded
    @POST("v1/getMMNews.php")
    Call<GetNewsResponse> loadMMNews(
            @Field("page") int pageIndex,
            @Field("access_token") String accessToken);
}
