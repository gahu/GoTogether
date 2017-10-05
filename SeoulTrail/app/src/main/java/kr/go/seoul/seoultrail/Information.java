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
        setContentView(R.layout.fragment_activity);
        PublicDefine.information = this;
        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new InformationPagerFragmentAdapter(getSupportFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager
        tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
        Typeface type = Typeface.createFromAsset(getAssets(), "NotoSansCJKkr-DemiLight.otf");
        LinearLayout view = (LinearLayout) tabsStrip.getChildAt(0);
        int tabCount = view.getChildCount();
        for (int i = 0; i < tabCount; i++) {
            TextView textView = (TextView) view.getChildAt(i);
            textView.setTypeface(type);
            textView.setIncludeFontPadding(false);
        }
    }

    public void setPager(int pagerNum) {
        viewPager.setCurrentItem(pagerNum);

        if (pagerNum == 1) {
            getPointInfoHandler.sendEmptyMessageDelayed(0, 300);
        }
    }

    public Handler getPointInfoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            PublicDefine.tourInfoFragment.setScroll();
        }
    };
}
