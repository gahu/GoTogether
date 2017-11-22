package kr.go.seoul.seoultrail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import kr.go.seoul.seoultrail.Common.PublicDefine;

/**
 * Created by ntsys on 2016-08-09.
 */
public class Course extends BaseActivity {

    private PagerSlidingTabStrip tabsStrip;
    private ViewPager viewPager;
    public boolean isMap = false;
    public boolean isPOINTLIST = false;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.fragment_activity);
        PublicDefine.course = this;
        final Button btn_test = (Button)findViewById(R.id.test_btn);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Course.this, TabHost_test.class);

                view = TabHost_FirstTab.FirstTabHGroup.getLocalActivityManager()
                        .startActivity("FirstTab_2", intent
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();

                TabHost_FirstTab.FirstTabHGroup.replaceView(view);
            }
        });
        initView();
    }

    private void initView() {
        SharedPreferences pref = getSharedPreferences("SYSTEM", 0);
        SharedPreferences.Editor edit = pref.edit();
        edit.putBoolean("ISMAP", false);
        edit.commit();

        edit.putBoolean("ISPOINTLIST", false);
        edit.commit();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CoursePagerFragmentAdapter(getSupportFragmentManager()));
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        SharedPreferences pref = getSharedPreferences("SYSTEM", 0);
        isMap = pref.getBoolean("ISMAP", false);
        isPOINTLIST = pref.getBoolean("ISPOINTLIST", false);
        if (viewPager.getCurrentItem() == 0) {
            if (isMap) {
                if (isPOINTLIST) {
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putBoolean("ISPOINTLIST", false);
                    edit.commit();
                    getSupportFragmentManager().popBackStack();
                } else {
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putBoolean("ISMAP", false);
                    edit.commit();
                    getSupportFragmentManager().popBackStack();
                }
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    public void setPagerToStamp(final int courseNo) {
        viewPager.setCurrentItem(1);
        ((CoursePagerFragmentAdapter) viewPager.getAdapter()).setStampCourse(courseNo);
    }

    public void dismissPointListActivity(int potions) {
        SharedPreferences pref = getSharedPreferences("SYSTEM", 0);
        SharedPreferences.Editor edit = pref.edit();
        edit.putBoolean("ISPOINTLIST", false);
        edit.commit();
        getSupportFragmentManager().popBackStack();

        PublicDefine.courseMapFragment.setSelectionPointPin(potions);
    }
}
