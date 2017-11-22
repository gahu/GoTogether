package kr.go.seoul.seoultrail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import kr.go.seoul.seoultrail.CourseInfo.CoursePageBaseFragment;
import kr.go.seoul.seoultrail.CourseStamp.CourseStampFragment;

/**
 * Created by ntsys on 2016-08-09.
 */
public class CoursePagerFragmentAdapter extends FragmentStatePagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"코스별 정보", "스탬프북"};

    public CoursePagerFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return CoursePageBaseFragment.newInstance(position + 1);
        } else {
            return CourseStampFragment.newInstance(position + 1);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        String title = tabTitles[position];
        return title;
    }

    public void setStampCourse(int course) {
        CourseStampFragment.newInstance(2).setCourseNo(course);
    }
}