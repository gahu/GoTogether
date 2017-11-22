package kr.go.seoul.seoultrail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import kr.go.seoul.seoultrail.TrailInformation.EventFragment;
import kr.go.seoul.seoultrail.TrailInformation.IntroductionFragment;
import kr.go.seoul.seoultrail.TrailInformation.TourInfoFragment;

/**
 * Created by ntsys on 2016-08-09.
 */
public class InformationPagerFragmentAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"소개", "투어안내", "행사안내"};

    public InformationPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return IntroductionFragment.newInstance(position + 1);
        } else if (position == 1) {
            return TourInfoFragment.newInstance(position + 1);
        } else {
            return EventFragment.newInstance(position + 1);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        String title = tabTitles[position];
        return title;
    }
}