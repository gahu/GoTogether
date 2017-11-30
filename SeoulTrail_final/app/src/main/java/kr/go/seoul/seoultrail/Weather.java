package kr.go.seoul.seoultrail;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import kr.go.seoul.seoultrail.Common.PublicDefine;
import kr.go.seoul.seoultrail.WeatherInfo.WeatherFragment;

/**
 * Created by bgg89 on 2017-11-23.
 */

public class Weather extends BaseActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.fragment_activity); // 기본 layout 수정
        PublicDefine.weather = this;
        initView();
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new WeatherPagerAdapter(getSupportFragmentManager()));
    }

    private class WeatherPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 1;
        // 생성자
        public WeatherPagerAdapter(FragmentManager fm) { super(fm); }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        /**
         * 실제 뷰페이지에서 보여질 fragment를 반환
         * 일반 어뎁터(갤러리, 리스트뷰 등)의 getView와 같은 역할
         * @param position - 뷰페이저에서 보여져야할 페이지 값(0부터)
         * @return 보여질 fragment
         */
        @Override
        public Fragment getItem(int position) {
            return WeatherFragment.newInstance(position);
        }
    }
}
