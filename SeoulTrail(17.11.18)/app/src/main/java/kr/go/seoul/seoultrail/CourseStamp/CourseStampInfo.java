package kr.go.seoul.seoultrail.CourseStamp;

/**
 * Created by ntsys on 2016-08-16.
 */
public class CourseStampInfo {
    public CourseStampFragment courseStampFragment;
    public int courseNo;
    public String courseTitle;
    public int completeStamp;

    public CourseStampInfo(CourseStampFragment courseStampFragment, int courseNo, String courseTitle) {
        this.courseStampFragment = courseStampFragment;
        this.courseNo = courseNo;
        this.courseTitle = courseTitle;
        this.completeStamp = 0;
    }

    public void setCompleteStamp(int completeStamp) {
        this.completeStamp += completeStamp;
    }

    public int getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(int courseNo) {
        this.courseNo = courseNo;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public int getCompleteStamp() {
        return completeStamp;
    }
}
