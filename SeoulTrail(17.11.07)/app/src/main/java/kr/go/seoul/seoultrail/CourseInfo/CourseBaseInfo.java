package kr.go.seoul.seoultrail.CourseInfo;

/**
 * Created by ntsys on 2016-10-17.
 */
public class CourseBaseInfo {
    private int courseNo;
    private int courseLevel;
    private String courseName;
    private String location;
    private String distance;
    private String walkTime;

    public CourseBaseInfo(int courseNo, int courseLevel, String courseName, String location, String distance, String walkTime) {
        this.courseNo = courseNo;
        this.courseLevel = courseLevel;
        this.courseName = courseName;
        this.location = location;
        this.distance = distance;
        this.walkTime = walkTime;
    }

    public int getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(int courseNo) {
        this.courseNo = courseNo;
    }

    public int getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(int courseLevel) {
        this.courseLevel = courseLevel;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getWalkTime() {
        return walkTime;
    }

    public void setWalkTime(String walkTime) {
        this.walkTime = walkTime;
    }
}
