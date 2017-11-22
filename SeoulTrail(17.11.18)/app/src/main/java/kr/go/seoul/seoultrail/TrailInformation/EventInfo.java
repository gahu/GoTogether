package kr.go.seoul.seoultrail.TrailInformation;

/**
 * Created by ntsys on 2016-10-18.
 */
public class EventInfo {
    private String eventTitle;
    private String eventRegDate;
    private String imagURL;
    private String link;

    public EventInfo(String eventTitle, String eventRegDate, String imagURL, String link) {
        this.eventTitle = eventTitle;
        this.eventRegDate = eventRegDate;
        this.imagURL = imagURL;
        this.link = link;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventRegDate() {
        return eventRegDate;
    }

    public void setEventRegDate(String eventRegDate) {
        this.eventRegDate = eventRegDate;
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
