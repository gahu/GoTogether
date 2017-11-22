package kr.go.seoul.seoultrail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import kr.go.seoul.seoultrail.Common.PublicDefine;
import kr.go.seoul.seoultrail.CourseStamp.CourseStampFragment;

/**
 * Created by bgg89 on 2017-11-18.
 */

public class Stamp extends BaseActivity {

    private ViewPager viewPager;
    final int PAGE_COUNT = 2;
    private String tabTitles = "스탬프북";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.fragment_activity);
        PublicDefine.stamp = this;
        initView();
    }

    private void initView() {
        //viewPager = (ViewPager) findViewById(R.id.viewpager);
        //viewPager.setAdapter(new CourseStampListAdapter(getSupportFragmentManager()));
    }

    public void setPagerToStamp(final int courseNo) {
        viewPager.setCurrentItem(1);
        ((CoursePagerFragmentAdapter) viewPager.getAdapter()).setStampCourse(courseNo);
    }

    public Fragment getItem(int position) {
        return CourseStampFragment.newInstance(position + 1);
    }

    /*@Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        String title = tabTitles;
        return title;
    }*/

    public void setStampCourse(int course) {
        CourseStampFragment.newInstance(2).setCourseNo(course);
    }
}
