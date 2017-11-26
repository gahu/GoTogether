package kr.go.seoul.seoultrail;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.astuetz.PagerSlidingTabStrip;

import java.util.List;

import kr.go.seoul.seoultrail.Common.PublicDefine;

/**
 * Created by 김관현 on 2017-11-10.
 * */
public class MenuActivity extends BaseActivity {
    private PagerSlidingTabStrip tabsStrip;
    private ViewPager viewPager;

    private int value;

    private void setValue(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
    final MainActivity mainActivity = new MainActivity();
    MainActivity activity_;
    // modify 여기 부분이 메뉴부분에서 상태 확인해서 글자 바꾸는부분

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.fragment_menu);
        PublicDefine.menuActivty = this;

        // 여기 부분은 메뉴 부분에서의 이벤트 발생 처리 부분입니다.

        // modify 1.앱 가이드 부분
        final View view1 = (View) findViewById(R.id.header_layout); // View를 얻오옴
        final Button btn_guide = (Button) findViewById(R.id.btn_guide);

        btn_guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PublicDefine.mainActivity.settingText(1);
                Intent intent = new Intent(MenuActivity.this, GuideActivity.class);
                view = Menu_Connection.FirstTabHGroup.getLocalActivityManager()
                         .startActivity("FirstTab_3", intent
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
                Menu_Connection.FirstTabHGroup.replaceView(view);

            }
        });

        // modify 2. 서울둘레길이란? 소개 부분
        final Button btn_history = (Button) findViewById(R.id.btn_history);
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PublicDefine.mainActivity.settingText(2);
                Intent intent = new Intent(MenuActivity.this, Information.class);
                view = Menu_Connection.FirstTabHGroup.getLocalActivityManager()
                        .startActivity("FirstTab_3", intent
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();

                Menu_Connection.FirstTabHGroup.replaceView(view);
            }
        });

        // modify 3. 소개 영상 부분
        final Button btn_video = (Button) findViewById(R.id.btn_video);
        btn_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PublicDefine.mainActivity.settingText(3);
                Intent intent = new Intent(MenuActivity.this,Video.class);
                view = Menu_Connection.FirstTabHGroup.getLocalActivityManager()
                        .startActivity("FirstTab_3", intent
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
                Menu_Connection.FirstTabHGroup.replaceView(view);
            }
        });

        // modify 4. 스탬프 부분

        final Button btn_stamp = (Button) findViewById(R.id.btn_stamp);
        btn_stamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PublicDefine.mainActivity.settingText(4);
                Intent intent = new Intent(MenuActivity.this, Stamp.class);
                view = Menu_Connection.FirstTabHGroup.getLocalActivityManager()
                        .startActivity("FirstTab_3", intent
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
                Menu_Connection.FirstTabHGroup.replaceView(view);
            }
        });

        // modify 5. 공식 카페 부분
        PublicDefine.mainActivity.settingText(5);
        final Button btn_cafe = (Button) findViewById(R.id.btn_cafe);
        btn_cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MenuActivity.this, Stamp.class);
                view = Menu_Connection.FirstTabHGroup.getLocalActivityManager()
                        .startActivity("FirstTab_3", intent
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
                Menu_Connection.FirstTabHGroup.replaceView(view);
                Init();
            }

        });

        // modify 6.날씨 정보 부분

        final Button btu_weather = (Button) findViewById(R.id.btn_weather);
        btu_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PublicDefine.mainActivity.settingText(6);
                Intent intent = new Intent(MenuActivity.this, Weather.class);
                view = Menu_Connection.FirstTabHGroup.getLocalActivityManager()
                        .startActivity("FirstTab_3", intent
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
                Menu_Connection.FirstTabHGroup.replaceView(view);

            }
        });

        // modify 7. 행사 안내 부분

        final Button btn_event = (Button) findViewById(R.id.btn_event);
        btn_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PublicDefine.mainActivity.settingText(7);

                Intent intent = new Intent(MenuActivity.this, Event.class);
                view = Menu_Connection.FirstTabHGroup.getLocalActivityManager()
                        .startActivity("FirstTab_3", intent
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
                Menu_Connection.FirstTabHGroup.replaceView(view);
            }
        });

        // modify 8번 문의 안내 부분

        final Button btn_faq = (Button) findViewById(R.id.btn_faq);
        btn_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PublicDefine.mainActivity.settingText(8);
                Intent intent = new Intent(MenuActivity.this, FAQ.class);
                view = Menu_Connection.FirstTabHGroup.getLocalActivityManager()
                        .startActivity("FirstTab_3", intent
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
                Menu_Connection.FirstTabHGroup.replaceView(view);
            }
        });

        // modify 9번 기타 부분

        final Button btn_etc = (Button) findViewById(R.id.btn_etc);
        btn_etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PublicDefine.mainActivity.settingText(9);
                Intent intent = new Intent(MenuActivity.this, SeoulTrail.class);
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