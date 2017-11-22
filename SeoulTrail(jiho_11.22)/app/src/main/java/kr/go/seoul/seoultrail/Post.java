package kr.go.seoul.seoultrail;

import java.util.Date;

/**
 * Created by 지호 on 2017-10-18.
 */

// 모델 클래스
public class Post {
    private String name;
    private long writeTime;
    private String bodyText;
    private String picture;

    // 기본 생성자를 필수로 만들어줘야지 데이터를 가져올 때 Error를 발생시키지 않습니다.
    public Post()  {}

    public Post(String name, String bodyText, String picture) {
        this.name = name;
        this.writeTime = new Date().getTime();
        this.bodyText = bodyText;
        this.picture = picture;
    }

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
        this.writeTime = writeTime;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
