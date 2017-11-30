package kr.go.seoul.seoultrail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import kr.go.seoul.seoultrail.FAQInfo.FAQ_Depth1ExpandableAdapter;
import kr.go.seoul.seoultrail.FAQInfo.FAQ_ExpandableListDataPump;

/**
 * Created by 임지호 on 2017-10-10.
 */

public class FAQ extends BaseActivity {
    //    private ImageView btnClose;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    LinkedHashMap<String, List<String>> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_activity);
        expandableListView = (ExpandableListView) findViewById(R.id.elv_list);
        expandableListDetail = FAQ_ExpandableListDataPump.getDataAtDepth1();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new FAQ_Depth1ExpandableAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.expandGroup(0); // 0번째 줄 강제로 펼치기

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                if(i == 2) {
                    Uri uri = Uri.parse("https://gil.seoul.go.kr/walk/sub/guide/rss_list.jsp");

                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else if (i== 3) {
                    Uri uri = Uri.parse("https://gil.seoul.go.kr/walk/sub/guide/honor_list.jsp");

                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    public void callNumber(View view) {
        String number = ((TextView) view).getText().toString();
        if (number.contains("~")) {
            number = number.split("~")[0];
        }
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
        startActivity(intent);
    }

    public void sendEmail(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject of email");
        intent.putExtra(Intent.EXTRA_TEXT, "Body of email");
        intent.setData(Uri.parse("mailto:default@recipient.com")); // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        getApplicationContext().startActivity(intent);
    }
}