package kr.go.seoul.seoultrail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.astuetz.PagerSlidingTabStrip;

import kr.go.seoul.seoultrail.Common.PublicDefine;
import kr.go.seoul.seoultrail.CourseInfo.CoursePageBaseFragment;
import kr.go.seoul.seoultrail.CourseStamp.CourseStampFragment;

/**
 * Created by ntsys on 2016-08-09.
 */
public class MenuActivity extends BaseActivity {

    private PagerSlidingTabStrip tabsStrip;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.fragment_menu);
        PublicDefine.menuActivty = this;


        // 여기 부분은 메뉴 부분에서의 이벤트 발생 처리 부분입니다.


        // 1. 코스 정보 - Course

        final Button btn_course = (Button) findViewById(R.id.btn_course);
        btn_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MenuActivity.this, Course.class);

                view = Menu_Connection.FirstTabHGroup.getLocalActivityManager()
                        .startActivity("FirstTab_3", intent
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();

                Menu_Connection.FirstTabHGroup.replaceView(view);
            }
        });

        // 2. 행사 안내 Event
        final Button btn_event = (Button) findViewById(R.id.btn_event);
        btn_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MenuActivity.this, GuideActivity.class);

                view = Menu_Connection.FirstTabHGroup.getLocalActivityManager()
                        .startActivity("FirstTab_3", intent
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();

                Menu_Connection.FirstTabHGroup.replaceView(view);
            }
        });

        // 3. 소개 영상 Video
        final Button btn_video = (Button) findViewById(R.id.btn_video);
        btn_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MenuActivity.this,Video.class);
                view = Menu_Connection.FirstTabHGroup.getLocalActivityManager()
                        .startActivity("FirstTab_3", intent
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
                Menu_Connection.FirstTabHGroup.replaceView(view);
            }
        });

        // 4. 스탬프 Stamp

        final Button btn_stamp = (Button) findViewById(R.id.btn_stamp);
        btn_stamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MenuActivity.this, CourseStampFragment.class);
                view = Menu_Connection.FirstTabHGroup.getLocalActivityManager()
                        .startActivity("FirstTab_3", intent
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
                Menu_Connection.FirstTabHGroup.replaceView(view);
            }
        });

        // 5. 커뮤니티 Community

        final Button btn_community = (Button) findViewById(R.id.btn_community);
        btn_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MenuActivity.this, Community.class);
                view = Menu_Connection.FirstTabHGroup.getLocalActivityManager()
                        .startActivity("FirstTab_3", intent
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
                Menu_Connection.FirstTabHGroup.replaceView(view);
            }
        });

        // 6. 공식 카페 Cafe

        final Button btn_cafe = (Button) findViewById(R.id.btn_cafe);
        btn_cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MenuActivity.this, Cafe.class);
                view = Menu_Connection.FirstTabHGroup.getLocalActivityManager()
                        .startActivity("FirstTab_3", intent
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
                Menu_Connection.FirstTabHGroup.replaceView(view);
            }
        });

        // 7. 문의 안내 FAQ
        final Button btn_faq = (Button) findViewById(R.id.btn_faq);
        btn_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MenuActivity.this, FAQ.class);
                view = Menu_Connection.FirstTabHGroup.getLocalActivityManager()
                        .startActivity("FirstTab_3", intent
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
                Menu_Connection.FirstTabHGroup.replaceView(view);
            }
        });

        // 8. 카메라

        final Button btn_camera = (Button) findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, Camera.class);
                view = Menu_Connection.FirstTabHGroup.getLocalActivityManager()
                        .startActivity("FirstTab_3", intent
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
                Menu_Connection.FirstTabHGroup.replaceView(view);
            }
        });










    }
}