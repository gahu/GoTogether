package kr.go.seoul.seoultrail.CommunityManager;

/**
 * Created by 지호 on 2017-10-18.
 */

// 모델 클래스
public class Post {
    private boolean iconYesOrNo;
    private String name;
    private long writeTime;
    private String bodyText;
    private boolean pictureYesOrNo;

    // 기본 생성자를 필수로 만들어줘야지 데이터를 가져올 때 Error를 발생시키지 않습니다.
    public Post()  {}

    public Post(boolean iconYesOrNo, String name, long writeTime, String bodyText, boolean pictureYesOrNo) {
        this.iconYesOrNo = iconYesOrNo;
        this.name = name;
        this.writeTime = writeTime;
        this.bodyText = bodyText;
        this.pictureYesOrNo = pictureYesOrNo;
    }

    public boolean getIconYesOrNo() { return iconYesOrNo; }

    public void setIconYesOrNo(boolean iconYesOrNo) { this.iconYesOrNo = iconYesOrNo; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(long writeTime) {
        this.writeTime = writeTime*-1;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public boolean getPictureYesOrNo() {
        return pictureYesOrNo;
    }

    public void setPictureYesOrNo(boolean pictureYesOrNo) {
        this.pictureYesOrNo = pictureYesOrNo;
    }
}
