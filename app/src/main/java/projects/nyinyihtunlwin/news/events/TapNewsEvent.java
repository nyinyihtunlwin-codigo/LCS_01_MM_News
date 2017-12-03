package projects.nyinyihtunlwin.news.events;

/**
 * Created by Dell on 12/2/2017.
 */

public class TapNewsEvent {
    String postID;

    public TapNewsEvent(String postID) {
        this.postID = postID;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }
}
