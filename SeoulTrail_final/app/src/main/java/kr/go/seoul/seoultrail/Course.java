package kr.go.seoul.seoultrail;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import kr.go.seoul.seoultrail.Common.PublicDefine;
import kr.go.seoul.seoultrail.CourseInfo.CoursePageBaseFragment;
import kr.go.seoul.seoultrail.CourseStamp.CourseStampFragment;

/**
 * Created by ntsys on 2016-08-09.
 * Modified by JO on 2017-10-29
 */

public class Course extends BaseActivity {
    private ViewPager viewPager;

    public boolean isMap = false;
    public boolean isPOINTLIST = false;
    @Override
    public void onBackPressed() {

        //Intent intent = new Intent(Course.this, MainActivity.class);
        //startActivity(intent);
        PublicDefine.mainActivity.Main_Move();



       // PublicDefine.mainActivity.settingTab(1);

    }
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.fragment_activity);
        PublicDefine.course = this;
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

        // Course.class에서 탭으로 분류하던 부분을 제거
    }

    // Adapter를 해당 page class에 작성
    private class CoursePagerFragmentAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 1;

        public CoursePagerFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        // 해당하는 page의 fragment를 생성
        @Override
        public Fragment getItem(int position) {
            return CoursePageBaseFragment.newInstance(position + 1);
        }

        public void setStampCourse(int course) {
            CourseStampFragment.newInstance(2).setCourseNo(course);
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
