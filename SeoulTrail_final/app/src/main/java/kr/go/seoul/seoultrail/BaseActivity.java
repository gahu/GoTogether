package kr.go.seoul.seoultrail;

import android.support.v7.app.AppCompatActivity;

import kr.go.seoul.seoultrail.Common.PublicDefine;

import static kr.go.seoul.seoultrail.Menu_Connection.FirstTabHGroup;

/**
 * Created by ntsys on 2016-08-17.
 */
public class BaseActivity extends AppCompatActivity {
    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;

    @Override
    public void onBackPressed() {
        //onBackPressed();
        FirstTabHGroup.back();
        PublicDefine.mainActivity.settingTab(1);

        return ;
    }

}
