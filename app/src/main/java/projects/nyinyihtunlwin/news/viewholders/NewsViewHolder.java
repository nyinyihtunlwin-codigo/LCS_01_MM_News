package projects.nyinyihtunlwin.news.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nyinyihtunlwin.news.R;
import projects.nyinyihtunlwin.news.data.vo.NewsVO;
import projects.nyinyihtunlwin.news.data.vo.PublicationVO;
import projects.nyinyihtunlwin.news.delegates.NewsItemDelegate;
import projects.nyinyihtunlwin.news.events.TapNewsEvent;

/**
 * Created by Dell on 11/4/2017.
 */

public class NewsViewHolder extends BaseViewHolder<NewsVO> {

    private NewsItemDelegate mNewsItemDelegate;

    @BindView(R.id.tv_publication_name)
    TextView tvPublictionName;

    @BindView(R.id.iv_publication_logo)
    ImageView ivPublicationLogo;

    @BindView(R.id.tv_brief_news)
    TextView tvBriefNews;


    @BindView(R.id.tv_publish_date)
    TextView tvPublishDate;

    @BindView(R.id.iv_news_hero_image)
    ImageView ivNewsHeroImage;

    @BindView(R.id.tv_news_statistical_data)
    TextView tvNewsStatisticalData;


    public NewsViewHolder(final View itemView, final NewsItemDelegate newsItemDelegate) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mNewsItemDelegate = newsItemDelegate;
    }

    @Override
    public void setData(NewsVO data) {
        StringBuilder sb = new StringBuilder();
        PublicationVO publicationVO = data.getPublication();
        tvPublictionName.setText(publicationVO.getTitle());
        tvPublishDate.setText(data.getPostedDate());
        tvBriefNews.setText(data.getBrief());
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.img_publication_logo_placeholder)
                .centerCrop();
        Glide.with(itemView.getRootView().getContext()).load(publicationVO.getLogo()).apply(requestOptions).into(ivPublicationLogo);
        if (data.getImages() != null && data.getImages().size() >= 1) {
            Glide.with(itemView.getRootView().getContext()).load(data.getImages().get(0)).apply(requestOptions).into(ivNewsHeroImage);
        }else {
            ivNewsHeroImage.setVisibility(View.GONE);
        }
        if (data.getComments() != null && data.getComments().size() >= 1) {
            if (data.getSendTos() != null && data.getSendTos().size() >= 1) {
                if (data.getFavourites() != null && data.getFavourites().size() >= 1) {
                    sb.append(data.getFavourites().size() + " Likes " + data.getComments().size() + "- Comments - Sent to " + data.getSendTos().size() + " people");
                } else {
                    sb.append(data.getComments().size() + " Comments - Sent to " + data.getSendTos().size() + " people");
                }
            } else {
                if(data.getComments().size()>1){
                    sb.append(data.getComments().size() + " Comments");
                }else {
                    sb.append(data.getComments().size() + " Comment");
                }
            }
        } else if (data.getSendTos() != null && data.getSendTos().size() >= 1) {
            if (data.getSendTos().size() > 1) {
                sb.append("Sent to " + data.getSendTos().size() + " people");
            } else {
                sb.append("Sent to " + data.getSendTos().size() + " person");
            }
        } else if (data.getFavourites() != null && data.getFavourites().size() >= 1) {
            if (data.getFavourites().size() > 1) {
                sb.append(data.getFavourites().size() + " Likes ");
            } else {
                sb.append(data.getFavourites().size() + " Like ");
            }
        } else {
            sb.append("");
        }
        tvNewsStatisticalData.setText(sb.toString());
    }

    @Override
    public void onClick(View view) {
        //  mNewsItemDelegate.onTapNews(itemView);
        EventBus.getDefault().post(new TapNewsEvent("news_id"));
    }
}
