package kr.go.seoul.seoultrail;

import android.content.Intent;
<<<<<<< HEAD:SeoulTrail_final/app/src/main/java/kr/go/seoul/seoultrail/MenuActivity.java
=======
import android.graphics.Typeface;
>>>>>>> ca4c9829395aed517e854978b33530427e00b35b:SeoulTrail/app/src/main/java/kr/go/seoul/seoultrail/MenuActivity.java
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.astuetz.PagerSlidingTabStrip;

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

<<<<<<< HEAD:SeoulTrail_final/app/src/main/java/kr/go/seoul/seoultrail/MenuActivity.java
=======

>>>>>>> ca4c9829395aed517e854978b33530427e00b35b:SeoulTrail/app/src/main/java/kr/go/seoul/seoultrail/MenuActivity.java
                PublicDefine.mainActivity.settingText(1);
                Intent intent = new Intent(MenuActivity.this, GuideActivity.class);
                view = Menu_Connection.FirstTabHGroup.getLocalActivityManager()
                        .startActivity("FirstTab_3", intent
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();

                Menu_Connection.FirstTabHGroup.replaceView(view);
                PublicDefine.mainActivity.tf = true;

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
                PublicDefine.mainActivity.tf = true;

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
                PublicDefine.mainActivity.tf = true;

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
                PublicDefine.mainActivity.tf = true;

            }
        });

        // modify 5. 공식 카페 부분
<<<<<<< HEAD:SeoulTrail_final/app/src/main/java/kr/go/seoul/seoultrail/MenuActivity.java
=======

>>>>>>> ca4c9829395aed517e854978b33530427e00b35b:SeoulTrail/app/src/main/java/kr/go/seoul/seoultrail/MenuActivity.java
        final Button btn_cafe = (Button) findViewById(R.id.btn_cafe);
        btn_cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PublicDefine.mainActivity.settingText(5);
                Intent intent = new Intent(MenuActivity.this, Cafe.class);
                view = Menu_Connection.FirstTabHGroup.getLocalActivityManager()
                        .startActivity("FirstTab_3", intent
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
                Menu_Connection.FirstTabHGroup.replaceView(view);
<<<<<<< HEAD:SeoulTrail_final/app/src/main/java/kr/go/seoul/seoultrail/MenuActivity.java
                PublicDefine.mainActivity.tf = true;
                PublicDefine.mainActivity.Main_Move();
=======

>>>>>>> ca4c9829395aed517e854978b33530427e00b35b:SeoulTrail/app/src/main/java/kr/go/seoul/seoultrail/MenuActivity.java
            }

        });

        // modify 6.날씨 정보 부분

        final Button btn_weather = (Button) findViewById(R.id.btn_weather);
        btn_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PublicDefine.mainActivity.settingText(6);
                Intent intent = new Intent(MenuActivity.this, Weather.class);
                view = Menu_Connection.FirstTabHGroup.getLocalActivityManager()
                        .startActivity("FirstTab_3", intent
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
                Menu_Connection.FirstTabHGroup.replaceView(view);
<<<<<<< HEAD:SeoulTrail_final/app/src/main/java/kr/go/seoul/seoultrail/MenuActivity.java
                PublicDefine.mainActivity.tf = true;
=======
>>>>>>> ca4c9829395aed517e854978b33530427e00b35b:SeoulTrail/app/src/main/java/kr/go/seoul/seoultrail/MenuActivity.java

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
                PublicDefine.mainActivity.tf = true;

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
                PublicDefine.mainActivity.tf = true;

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
                PublicDefine.mainActivity.tf = true;

            }
        });
<<<<<<< HEAD:SeoulTrail_final/app/src/main/java/kr/go/seoul/seoultrail/MenuActivity.java
=======
            // 메뉴 부분 폰트 적용하기
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "arita_bold.ttf");  //asset > fonts 폴더 내 폰트파일 적용
        btn_guide.setTypeface(typeFace);
        btn_cafe.setTypeface(typeFace);
        btn_etc.setTypeface(typeFace);
        btn_history.setTypeface(typeFace);
        btn_video.setTypeface(typeFace);
        btn_stamp.setTypeface(typeFace);
        btn_faq.setTypeface(typeFace);
        btn_event.setTypeface(typeFace);
        btn_weather.setTypeface(typeFace);


    }
>>>>>>> ca4c9829395aed517e854978b33530427e00b35b:SeoulTrail/app/src/main/java/kr/go/seoul/seoultrail/MenuActivity.java

    }
    public void check(){
        Menu_Connection.FirstTabHGroup.back();
    }

/*
    @Override
    private void OnClick(View view) {

        view1.setVisibility(View.GONE);
    }
*/
<<<<<<< HEAD:SeoulTrail_final/app/src/main/java/kr/go/seoul/seoultrail/MenuActivity.java
=======








>>>>>>> ca4c9829395aed517e854978b33530427e00b35b:SeoulTrail/app/src/main/java/kr/go/seoul/seoultrail/MenuActivity.java
}