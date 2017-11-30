package kr.go.seoul.seoultrail;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import kr.go.seoul.seoultrail.Common.PublicDefine;

/**
 * Created by ntsys on 2016-08-09.
 */
public class Information extends BaseActivity {

    private PagerSlidingTabStrip tabsStrip;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.fragment_activity_info);
        PublicDefine.information = this;
        initView();         // 초기화 시킨다. information 클래스 아래 탭 두번째 기능
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);   //ViewPager는 소개부분의 화면을 나타낸다.
        viewPager.setAdapter(new InformationPagerFragmentAdapter(getSupportFragmentManager()));
        // 소개부분의 화면을 어댑터로 붙여버리기위한 설정.

        // Give the PagerSlidingTabStrip the ViewPager
        tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);     // 탭슬라이딩바 소개,투어,행사안내부분
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);      // 탭 슬라이더에 viewPager를 붙여버린다.

        //탭 슬라이더 마다 LinearLayout의 형태로 붙여주기 위한것.
        Typeface type = Typeface.createFromAsset(getAssets(), "arita_bold.ttf");
        LinearLayout view = (LinearLayout) tabsStrip.getChildAt(0);
        int tabCount = view.getChildCount();
        for (int i = 0; i < tabCount; i++) {
            TextView textView = (TextView) view.getChildAt(i);      //
            textView.setTypeface(type);         // 글자 설정
            textView.setIncludeFontPadding(false);      // 폰트 패딩 설정
        }
    }

    public void setPager(int pagerNum) {
        viewPager.setCurrentItem(pagerNum);
        if (pagerNum == 1) {
            getPointInfoHandler.sendEmptyMessageDelayed(0, 300);        // 소개,투어,행사 넘길때딜레이 제어
        }
    }

    public Handler getPointInfoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            PublicDefine.tourInfoFragment.setScroll();
        }
    };
}
