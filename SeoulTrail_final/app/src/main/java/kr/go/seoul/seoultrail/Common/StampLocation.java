package kr.go.seoul.seoultrail.Common;

/**
 * Created by ntsys on 2016-10-05.
 */
public class StampLocation {
    private String courseID;
    private int courseNo;
    private int stampNo;
    private String COT_COORD_X;     //lng
    private String COT_COORD_Y;     //lat
    private String COT_CONTS_NAME;
    private String last_update_date;
    private String complete_count;

    public StampLocation(String courseID, int courseNo, int stampNo, String COT_COORD_Y, String COT_COORD_X, String COT_CONTS_NAME) {
        this.courseID = courseID;
        this.courseNo = courseNo;
        this.stampNo = stampNo;
        this.COT_COORD_Y = COT_COORD_Y;
        this.COT_COORD_X = COT_COORD_X;
        this.COT_CONTS_NAME = COT_CONTS_NAME;
    }

    public StampLocation(String courseID, int courseNo, int stampNo, String COT_COORD_X, String COT_COORD_Y, String last_update_date, String complete_count) {
        this.courseID = courseID;
        this.courseNo = courseNo;
        this.stampNo = stampNo;
        this.COT_COORD_X = COT_COORD_X;
        this.COT_COORD_Y = COT_COORD_Y;
        this.last_update_date = last_update_date;
        this.complete_count = complete_count;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public int getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(int courseNo) {
        this.courseNo = courseNo;
    }

    public int getStampNo() {
        return stampNo;
    }

    public void setStampNo(int stampNo) {
        this.stampNo = stampNo;
    }

    public String getCOT_COORD_X() {
        return COT_COORD_X;
    }

    public void setCOT_COORD_X(String COT_COORD_X) {
        this.COT_COORD_X = COT_COORD_X;
    }

    public String getCOT_COORD_Y() {
        return COT_COORD_Y;
    }

    public void setCOT_COORD_Y(String COT_COORD_Y) {
        this.COT_COORD_Y = COT_COORD_Y;
    }

    public String getCOT_CONTS_NAME() {
        return COT_CONTS_NAME;
    }

    public void setCOT_CONTS_NAME(String COT_CONTS_NAME) {
        this.COT_CONTS_NAME = COT_CONTS_NAME;
    }

    public String getLast_update_date() {
        return last_update_date;
    }

    public void setLast_update_date(String last_update_date) {
        this.last_update_date = last_update_date;
    }

    public String getComplete_count() {
        return complete_count;
    }

    public void setComplete_count(String complete_count) {
        this.complete_count = complete_count;
    }
}
