package kr.go.seoul.seoultrail;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by 지호 on 2017-11-01.
 */

public class TabHost_FirstTab extends ActivityGroup {
    public static TabHost_FirstTab FirstTabHGroup;
    private ArrayList<View> history;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        history = new ArrayList<View>();
        FirstTabHGroup = this;

        Intent intent = new Intent(TabHost_FirstTab.this, Course.class);
        View view = getLocalActivityManager().startActivity("FirstTab_1", intent
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();
        replaceView(view);
    }

    // »õ·Î¿î LevelÀÇ Activity¸¦ Ãß°¡ÇÏ´Â °æ¿ì
    public void replaceView(View view) {
        history.add(view);
        setContentView(view);
    }

    // Back Key°¡ ´­·¯Á³À» °æ¿ì¿¡ ´ëÇÑ Ã³¸®
    public void back() {
        if(history.size() > 0) {
            history.remove(history.size()-1);
            if(history.size() ==  0)
                finish();
            else
                setContentView(history.get(history.size()-1));
        }
        else
        {
            finish();
        }
    }

    // Back Key¿¡ ´ëÇÑ Event Handler
    @Override
    public void onBackPressed() {
        FirstTabHGroup.back();
        return ;
    }
}
