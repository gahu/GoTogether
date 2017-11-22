package kr.go.seoul.seoultrail.Common;

/**
 * Created by ntsys on 2016-10-05.
 */
public class StampHistory {
    private int courseNo;
    private int stampNo;
    private String updateDate;

    public StampHistory(int courseNo, int stampNo) {
        this.courseNo = courseNo;
        this.stampNo = stampNo;
    }

    public StampHistory(int courseNo, int stampNo, String updateDate) {
        this.courseNo = courseNo;
        this.stampNo = stampNo;
        this.updateDate = updateDate;
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

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
