package kr.go.seoul.seoultrail.TrailVideo;

/**
 * Created by ntsys on 2016-10-18.
 */
public class VideoInfo {
    private String videoTitle;
    private String videoRegDate;
    private String imagURL;
    private String link;

    public VideoInfo(String eventTitle, String eventRegDate, String imagURL, String link) {
        this.videoTitle = eventTitle;
        this.videoRegDate = eventRegDate;
        this.imagURL = imagURL;
        this.link = link;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoRegDate() {
        return videoRegDate;
    }

    public void setVideoRegDate(String videoRegDate) {
        this.videoRegDate = videoRegDate;
    }

    public String getImagURL() {
        return imagURL;
    }

    public void setImagURL(String imagURL) {
        this.imagURL = imagURL;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
