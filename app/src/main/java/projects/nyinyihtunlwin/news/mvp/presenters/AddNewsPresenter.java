package projects.nyinyihtunlwin.news.mvp.presenters;

import javax.inject.Inject;

import projects.nyinyihtunlwin.news.SFCNewsApp;
import projects.nyinyihtunlwin.news.data.models.NewsModel;
import projects.nyinyihtunlwin.news.mvp.views.AddNewsView;

/**
 * Created by Dell on 1/28/2018.
 */

public class AddNewsPresenter extends BasePresenter<AddNewsView> {

    @Inject
    NewsModel mNewsModel;

    public AddNewsPresenter() {
    }

    @Override
    public void onCreate(AddNewsView view) {
        super.onCreate(view);
        SFCNewsApp context = (SFCNewsApp) mView.getContext();
        context.getAppComponent().inject(this);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    public void onTapPublish(String photoPath, String newsContent) {
        mNewsModel.publishNews(photoPath,newsContent);
 /*       mNewsModel.uploadFile(photoPath, new NewsModel.UploadFileCallback() {
            @Override
            public void onUploadSucceeded(String uploadedPaths) {
                mView.showUploadedNewsPhoto(uploadedPaths);

            }

            @Override
            public void onUploadFailed(String msg) {

            }
        });*/
    }
}
