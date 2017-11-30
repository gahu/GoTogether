package kr.go.seoul.seoultrail;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import kr.go.seoul.seoultrail.Common.PublicDefine;

public class Menu_Connection extends ActivityGroup {
    public static Menu_Connection FirstTabHGroup;
    private ArrayList<View> history;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        history = new ArrayList<View>();
        FirstTabHGroup = this;

        Intent intent = new Intent(Menu_Connection.this, MenuActivity.class);
        View view = getLocalActivityManager().startActivity("FirstTab_1", intent
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)).getDecorView();

        replaceView(view);

    }
    // ���ο� Level�� Activity�� �߰��ϴ� ���

    public void replaceView(View view) {
        history.add(view);
        setContentView(view);
    }

    // Back Key�� �������� ��쿡 ���� ó��
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
    @Override
    public void onBackPressed() {
        PublicDefine.mainActivity.settingTab(1);
        FirstTabHGroup.back();
        return ;
    }
    public void test(){
        FirstTabHGroup.back();
    }
}
