package kr.go.seoul.seoultrail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import kr.go.seoul.seoultrail.Common.PublicDefine;

/**
 * Created by 김관현 on 2017-10-10.
 */

public class Event extends BaseActivity {
    private ViewPager viewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event);
        PublicDefine.event = this;
        //initView();
    }

   /* private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new EventListAdapter(getSupportFragmentManager()));
    }*/

}
