package kr.go.seoul.seoultrail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import kr.go.seoul.seoultrail.Common.PublicDefine;
import kr.go.seoul.seoultrail.TrailInformation.EventFragment;

/**
 * Created by 김관현 on 2017-10-10.
 */

public class Event extends BaseActivity {
    private ViewPager viewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);
        PublicDefine.event = this;
        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new EventFragmentAdapter(getSupportFragmentManager()));
    }

    // Adapter를 해당 page class에 작성
    private class EventFragmentAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 1;

        public EventFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        // 해당하는 page의 fragment를 생성
        @Override
        public Fragment getItem(int position) {
            return EventFragment.newInstance(position + 1);
        }
    }
}
