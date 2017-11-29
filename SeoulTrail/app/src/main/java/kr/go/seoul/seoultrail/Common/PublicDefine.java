package kr.go.seoul.seoultrail.Common;

import kr.go.seoul.seoultrail.Course;
import kr.go.seoul.seoultrail.CourseInfo.CourseListFragment;
import kr.go.seoul.seoultrail.CourseInfo.CourseMapFragment;
import kr.go.seoul.seoultrail.CourseStamp.CourseStampFragment;
import kr.go.seoul.seoultrail.Cafe;
import kr.go.seoul.seoultrail.Event;
import kr.go.seoul.seoultrail.GuideActivity;
import kr.go.seoul.seoultrail.Information;
import kr.go.seoul.seoultrail.MainActivity;
import kr.go.seoul.seoultrail.MenuActivity;
import kr.go.seoul.seoultrail.Menu_Connection;
import kr.go.seoul.seoultrail.Stamp;
import kr.go.seoul.seoultrail.TrailInformation.TourInfoFragment;
import kr.go.seoul.seoultrail.Video;
import kr.go.seoul.seoultrail.Weather;
import kr.go.seoul.seoultrail.WeatherInfo.WeatherFragment;

/**
 * Created by ntsys on 2016-09-05.
 * Modified by 김관현 on 2017-10-30
 */
public class PublicDefine {
    public static Video video;
    public static MainActivity mainActivity;
    public static Course course;
    public static CourseListFragment courseListFragment;
    public static CourseMapFragment courseMapFragment;
    public static Information information;
    public static MenuActivity menuActivty;        // 메뉴 엑티비티 추가함
    public static CourseStampFragment courseStampFragment;
    public static Event event;
    public static Stamp stamp;
    public static Cafe cafe;
    public static Weather weather;
    public static WeatherFragment weatherFragment;
    public static GuideActivity guideActivity;
    public static TourInfoFragment tourInfoFragment;
    public static Menu_Connection menu_connection;
    public static String serviceSmgisKey = "a2e67e834d624f09aa68f1a1bdfa5fa5";
    public static String nMapClientId = "lusQzicuKpnMyPi_pLbv";

    public static String imageHostUrl = "http://map.seoul.go.kr";

    public static String appKey = "app.A030.gil.seoul.go.kr";
    public static String appLoggingActionKey = "http://app.A030.gil.seoul.go.kr/";
    public static String appLoggingUrl = "http://weblog.eseoul.go.kr/wlo/Logging";
}
