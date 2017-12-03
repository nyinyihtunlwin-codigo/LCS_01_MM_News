package projects.nyinyihtunlwin.news.events;

import java.util.List;

import projects.nyinyihtunlwin.news.data.vo.NewsVO;

/**
 * Created by Dell on 12/2/2017.
 */

public class RestApiEvents {
    public static class EmptyResponseEvent {

    }

    public static class ErrorInvokingAPIEvent {
        private String errorMsg;

        public ErrorInvokingAPIEvent(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getErrorMsg() {
            return errorMsg;
        }
    }

    public static class  NewsDataLoadedEvent{
        private int loadedPageIndex;
        private List<NewsVO> loadedNews;

        public NewsDataLoadedEvent(int loadedPageIndex, List<NewsVO> loadedNews) {
            this.loadedPageIndex = loadedPageIndex;
            this.loadedNews = loadedNews;
        }

        public int getLoadedPageIndex() {
            return loadedPageIndex;
        }

        public List<NewsVO> getLoadedNews() {
            return loadedNews;
        }
    }
}
