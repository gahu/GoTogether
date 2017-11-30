package kr.go.seoul.seoultrail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import kr.go.seoul.seoultrail.Common.PublicDefine;
import kr.go.seoul.seoultrail.CourseStamp.CourseStampFragment;

/**
 * Created by JO on 2017-10-20.
 */

public class Stamp extends BaseActivity {
    final int PAGE_COUNT = 1;

    private ViewPager viewPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle Bundle) {
        super.onCreate(Bundle);
        setContentView(R.layout.fragment_activity);
        PublicDefine.stamp = this;
        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        mPagerAdapter = new StampPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mPagerAdapter);
    }

    private class StampPagerAdapter extends FragmentPagerAdapter {

        public StampPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
                // CourseStamp page의 Fragment를 생성합니다.
            return CourseStampFragment.newInstance(position + 1);
        }
    }
}
