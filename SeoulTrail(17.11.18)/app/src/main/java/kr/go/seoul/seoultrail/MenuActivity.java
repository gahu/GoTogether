package kr.go.seoul.seoultrail;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;

import java.util.List;

import kr.go.seoul.seoultrail.Common.PublicDefine;

/**
 * Created by ntsys on 2016-08-09.
 * */
public class MenuActivity extends BaseActivity {
<<<<<<< HEAD

    private PagerSlidingTabStrip tabsStrip;
    private ViewPager viewPager;



=======
    private PagerSlidingTabStrip tabsStrip;
    private ViewPager viewPager;

>>>>>>> 9719a4e066a5af9d015b30b2ba06ee80ad9d034b
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.fragment_menu);
        PublicDefine.menuActivty = this;

        // 여기 부분은 메뉴 부분에서의 이벤트 발생 처리 부분입니다.

        // 1. 코스 정보 - Course
        final View view1 = (View) findViewById(R.id.header_layout); // View를 얻오옴
        final Button btn_course = (Button) findViewById(R.id.btn_course);
        btn_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
<<<<<<< HEAD
                // modify 여기 메인에서 넘어 갈때 이미지 없애려고 추가했는데 잘안넘어오는데 왜 그럴까
                //ViewGroup.LayoutParams mParams = view.getLayoutParams(); // View의 Parent 설정 속성인 (LayoutParams) 을 얻어옴
                //view1.setLayoutParams(mParams); // View에 새로운 속성을 적용


                //view1.setVisibility(View.GONE);
                //view1.invalidate();
=======
>>>>>>> 9719a4e066a5af9d015b30b2ba06ee80ad9d034b
                Intent intent = new Intent(MenuActivity.this, Course.class);
                view = Menu_Connection.FirstTabHGroup.getLocalActivityManager()
                        .startActivity("FirstTab_3", intent
                                .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)).getDecorView();
                Menu_Connection.FirstTabHGroup.replaceView(view);

<<<<<<< HEAD


=======
>>>>>>> 9719a4e066a5af9d015b30b2ba06ee80ad9d034b
                if(view1 != null){
                    view1.setVisibility(view.GONE);

                }else{
                    final ImageView view2 = (ImageView) findViewById(R.id.header_image); // View를 얻오옴
                    //view2.setVisibility(view.GONE);

                }
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

                Intent intent = new Intent(MenuActivity.this, Stamp.class);
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
<<<<<<< HEAD
           Init();
=======
                Intent intent = new Intent(MenuActivity.this, Cafe.class);
                view = Menu_Connection.FirstTabHGroup.getLocalActivityManager()
                        .startActivity("FirstTab_3", intent
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
                Menu_Connection.FirstTabHGroup.replaceView(view);
                //Init();
>>>>>>> 9719a4e066a5af9d015b30b2ba06ee80ad9d034b
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


/*
    @Override
    private void OnClick(View view) {

        view1.setVisibility(View.GONE);
    }
*/

<<<<<<< HEAD


=======
>>>>>>> 9719a4e066a5af9d015b30b2ba06ee80ad9d034b
    public void Init() {

        CustomSchemeURL csurl = new CustomSchemeURL(MenuActivity.this);
        if (csurl.canOpenCafeAppURL()) {
            startActivity(csurl.getIntent());
        } else {
            csurl.openCafeAppDownloadPage(MenuActivity.this);
        }

    }
    public class CustomSchemeURL {
        public static final String DAUMCAFEAPP_PACKAGE_NAME = "net.daum.android.cafe";
        public static final String DAUMCAFEAPP_DOWNLOAD_PAGE = "market://details?id=net.daum.android.cafe";
        public Intent cafe;
        public Context mContext;

        public CustomSchemeURL(MenuActivity menuActivity) {
            this.mContext = menuActivity;
        }

        /**
         * myp scheme을 처리할 수 있는 어플리케이션이 존재하는지 검사
         *
         * @return 사용가능할 경우 true
         */
        public boolean canOpenCafeAppURL() {
            PackageManager pm = mContext.getPackageManager();
            List infos = pm.queryIntentActivities(getIntent(), PackageManager.MATCH_DEFAULT_ONLY);

            return infos != null && infos.size() > 0;
        }

        public boolean existCafeApp() {
            PackageManager pm = mContext.getPackageManager();

            try {
                return (pm.getPackageInfo(DAUMCAFEAPP_PACKAGE_NAME, PackageManager.GET_SIGNATURES) != null);
            } catch (PackageManager.NameNotFoundException e) {
                return false;
            }
        }

        public void openCafeAppDownloadPage(Context context) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri iurl = Uri.parse(DAUMCAFEAPP_DOWNLOAD_PAGE);
            intent.setData(iurl);
            context.startActivity(intent);
        }

        public Intent getIntent() {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("daumcafe://cafehome?grpcode=seoultrail157"));
            return intent;
        }
    }






}