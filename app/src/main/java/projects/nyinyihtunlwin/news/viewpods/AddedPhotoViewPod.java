package projects.nyinyihtunlwin.news.viewpods;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.news.R;

/**
 * Created by aung on 8/19/17.
 */

public class AddedPhotoViewPod extends FrameLayout {

    @BindView(R.id.iv_added_photo)
    ImageView ivAddedPhoto;

    private String mPhotoUrl;

    public AddedPhotoViewPod(@NonNull Context context) {
        super(context);
    }

    public AddedPhotoViewPod(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AddedPhotoViewPod(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this, this);
    }

    public void setData(String photoUrl) {
        mPhotoUrl = photoUrl;
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.img_publication_logo_placeholder)
                .centerCrop();
        Glide.with(getContext()).load(photoUrl).apply(requestOptions).into(ivAddedPhoto);
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }
}
