package projects.nyinyihtunlwin.news.data.models;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import projects.nyinyihtunlwin.news.SFCNewsApp;
import projects.nyinyihtunlwin.news.activities.AddNewsActivity;
import projects.nyinyihtunlwin.news.dagger.AppComponent;
import projects.nyinyihtunlwin.news.data.vo.ActedUserVO;
import projects.nyinyihtunlwin.news.data.vo.CommentVO;
import projects.nyinyihtunlwin.news.data.vo.FavouriteActionVO;
import projects.nyinyihtunlwin.news.data.vo.NewsVO;
import projects.nyinyihtunlwin.news.data.vo.PublicationVO;
import projects.nyinyihtunlwin.news.data.vo.SendToVO;
import projects.nyinyihtunlwin.news.events.RestApiEvents;
import projects.nyinyihtunlwin.news.network.MMNewsDataAgent;
import projects.nyinyihtunlwin.news.network.MMNewsDataAgentImpl;
import projects.nyinyihtunlwin.news.persistence.MMNewsContract;
import projects.nyinyihtunlwin.news.utils.AppConstants;
import projects.nyinyihtunlwin.news.utils.ConfigUtils;

/**
 * Created by Dell on 12/3/2017.
 */

public class NewsModel {

    private List<NewsVO> mNews;
    // private int mmNewsPageIndex = 1;

    private static final String MM_NEWS = "mm_news";

    @Inject
    MMNewsDataAgent mDataAgent;

    @Inject
    ConfigUtils mConfigUtils;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    public NewsModel(Context context) {
        EventBus.getDefault().register(this);
        mNews = new ArrayList<>();
        SFCNewsApp sfcNewsApp = (SFCNewsApp) context.getApplicationContext();
        sfcNewsApp.getAppComponent().inject(this);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
    }

    public void startLoadingMMNews(final Context context) {
        //  mDataAgent.loadMMNews(AppConstants.ACCESS_TOKEN, mConfigUtils.loadPageIndex(), context);
        DatabaseReference mmNewsDBR = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mmNewsNodeDBR = mmNewsDBR.child(MM_NEWS);
        mmNewsNodeDBR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<NewsVO> newsList = new ArrayList<>();
                for (DataSnapshot newsDSS : dataSnapshot.getChildren()) {
                    NewsVO news = newsDSS.getValue(NewsVO.class);
                    newsList.add(news);
                }
                saveNewsData(context, newsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public List<NewsVO> getNews() {
        return mNews;
    }

    public void loadMoreNews(Context context) {
       /* int pageIndex = mConfigUtils.loadPageIndex();
        mDataAgent.loadMMNews(AppConstants.ACCESS_TOKEN, pageIndex, context);*/
    }

    @Subscribe
    public void onNewsDataLoaded(RestApiEvents.NewsDataLoadedEvent event) {
        mNews.addAll(event.getLoadedNews());
        mConfigUtils.savePageIndex(event.getLoadedPageIndex() + 1);

        saveNewsData(event.getContext(), event.getLoadedNews());

    }

    private void saveNewsData(Context context, List<NewsVO> newsList) {
        //TODO Logic to save the data in Persistence Layer
        ContentValues[] newsCVs = new ContentValues[newsList.size()];
        List<ContentValues> publicationCVList = new ArrayList<>();
        List<ContentValues> imagesInNewsCVList = new ArrayList<>();
        List<ContentValues> favoriteActionsInNewsCVList = new ArrayList<>();
        List<ContentValues> userInActionCVList = new ArrayList<>();
        List<ContentValues> commentsInNewsCVList = new ArrayList<>();
        List<ContentValues> sentToInNewsCVList = new ArrayList<>();
        for (int index = 0; index < newsCVs.length; index++) {
            NewsVO newsVO = newsList.get(index);
            newsCVs[index] = newsVO.parseToContentValues();

            PublicationVO publication = newsVO.getPublication();
            if (publication != null) {
                publicationCVList.add(publication.parseToContentValues());
            }

            for (String imageUrl : newsVO.getImages()) {
                ContentValues imagesInNewsCV = new ContentValues();
                imagesInNewsCV.put(MMNewsContract.ImageInNewsEntry.COLUMN_NEWS_ID, newsVO.getNewsId());
                imagesInNewsCV.put(MMNewsContract.ImageInNewsEntry.COLUMN_IMAGE_URL, imageUrl);
                imagesInNewsCVList.add(imagesInNewsCV);
            }
            for (FavouriteActionVO favouriteActionVO : newsVO.getFavourites()) {

                ContentValues favoriteActionCV = favouriteActionVO.parseToContentValues(newsVO.getNewsId());
                favoriteActionsInNewsCVList.add(favoriteActionCV);

                ContentValues actedUserCV = favouriteActionVO.getActedUser().parseToContentValues();
                userInActionCVList.add(actedUserCV);
            }
            for (CommentVO commentVO : newsVO.getComments()) {
                ContentValues commentActionCV = commentVO.parseToContentValues(newsVO.getNewsId());
                commentsInNewsCVList.add(commentActionCV);

                ContentValues actedUserCV = commentVO.getActedUser().parseToContentValues();
                userInActionCVList.add(actedUserCV);
            }
            for (SendToVO sendToVO : newsVO.getSendTos()) {
                ContentValues sentToActionCV = sendToVO.parseToContentValues(newsVO.getNewsId());
                sentToInNewsCVList.add(sentToActionCV);

                ContentValues senderCV = sendToVO.getActedUser().parseToContentValues();
                userInActionCVList.add(senderCV);

                ContentValues receiverCV = sendToVO.getReceivedUser().parseToContentValues();
                userInActionCVList.add(receiverCV);
            }
        }


        int insertedPublications = context.getContentResolver().bulkInsert(MMNewsContract.PublicationEntry.CONTENT_URI,
                publicationCVList.toArray(new ContentValues[0]));
        Log.d(SFCNewsApp.LOG_TAG, "inserted Publications :" + insertedPublications);

        int insertedImagesInNews = context.getContentResolver().bulkInsert(MMNewsContract.ImageInNewsEntry.CONTENT_URI,
                imagesInNewsCVList.toArray(new ContentValues[0]));
        Log.d(SFCNewsApp.LOG_TAG, "inserted Images in News" + insertedImagesInNews);

        int insertedFavoriteActionsInNews = context.getContentResolver().bulkInsert(MMNewsContract.FavoriteActionEntry.CONTENT_URI,
                favoriteActionsInNewsCVList.toArray(new ContentValues[0]));
        Log.d(SFCNewsApp.LOG_TAG, "inserted Favorite Actions :" + insertedFavoriteActionsInNews);

        int insertedActedUserActions = context.getContentResolver().bulkInsert(MMNewsContract.UserEntry.CONTENT_URI,
                userInActionCVList.toArray(new ContentValues[0]));
        Log.d(SFCNewsApp.LOG_TAG, "inserted acted users " + insertedActedUserActions);

        int insertedCommentActions = context.getContentResolver().bulkInsert(MMNewsContract.CommentEntry.CONTENT_URI,
                commentsInNewsCVList.toArray(new ContentValues[0]));
        Log.d(SFCNewsApp.LOG_TAG, "inserted comments " + insertedCommentActions);

        int insertedSentToActions = context.getContentResolver().bulkInsert(MMNewsContract.SendToEntry.CONTENT_URI,
                sentToInNewsCVList.toArray(new ContentValues[0]));
        Log.d(SFCNewsApp.LOG_TAG, "inserted sentTos " + insertedSentToActions);


        int insertedRowCount = context.getContentResolver().bulkInsert(MMNewsContract.NewsEntry.CONTENT_URI, newsCVs);
        Log.d(SFCNewsApp.LOG_TAG, "Inserted row : " + insertedRowCount);
    }

    public void forceRefreshNews(Context context) {
        mNews = new ArrayList<>();
        mConfigUtils.savePageIndex(1);
        startLoadingMMNews(context);
    }

    public boolean isUserAuthenticate() {
        return mFirebaseUser != null;
    }

    public void authenticateUserWithGoogleAccount(final GoogleSignInAccount signInAccount, final UserAuthenticateDelegate delegate) {
        Log.d(SFCNewsApp.LOG_TAG, "signInAccount Id :" + signInAccount.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(SFCNewsApp.LOG_TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.d(SFCNewsApp.LOG_TAG, "signInWithCredential", task.getException());
                            delegate.onFailureAuthenticate(task.getException().getMessage());
                        } else {
                            mFirebaseUser = mFirebaseAuth.getCurrentUser();
                            Log.d(SFCNewsApp.LOG_TAG, "signInWithCredential - successful");
                            delegate.onSuccessAuthenticate(signInAccount);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(SFCNewsApp.LOG_TAG, "OnFailureListener : " + e.getMessage());
                        delegate.onFailureAuthenticate(e.getMessage());
                    }
                });
    }

    public void publishNews(String photoPath, final String newsContent) {
        uploadFile(photoPath, new UploadFileCallback() {
            @Override
            public void onUploadSucceeded(String uploadedPaths) {
                List<String> images = new ArrayList<>();
                images.add(uploadedPaths);
                NewsVO newsToPublish = new NewsVO(newsContent, newsContent, images, new Date().toString());
                newsToPublish.setDummyPublication(PublicationVO.dummyPublication());

                DatabaseReference mmNewsDBR = FirebaseDatabase.getInstance().getReference();
                DatabaseReference mmNewsNodeDBR = mmNewsDBR.child(MM_NEWS);
                mmNewsNodeDBR.child(newsToPublish.getPostedDate()).setValue(newsToPublish);

            }

            @Override
            public void onUploadFailed(String msg) {

            }
        });
    }

    public void uploadFile(String fileToUpload, final UploadFileCallback uploadFileCallback) {
        Uri file = Uri.parse(fileToUpload);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference pathToUpload = storage.getReferenceFromUrl(AppConstants.FIRE_STOPRAGE_BUCKET);

        StorageReference uploadingFile = pathToUpload.child(file.getLastPathSegment()); // get file name
        UploadTask uploadTask = uploadingFile.putFile(file);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                uploadFileCallback.onUploadFailed(e.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri uploadedImageUri = taskSnapshot.getDownloadUrl();
                uploadFileCallback.onUploadSucceeded(uploadedImageUri.toString());
            }
        });
    }

    public interface UserAuthenticateDelegate {
        void onSuccessAuthenticate(GoogleSignInAccount signInAccount);

        void onFailureAuthenticate(String errrorMsg);
    }

    public interface UploadFileCallback {
        void onUploadSucceeded(String uploadedPaths);

        void onUploadFailed(String msg);
    }
}
