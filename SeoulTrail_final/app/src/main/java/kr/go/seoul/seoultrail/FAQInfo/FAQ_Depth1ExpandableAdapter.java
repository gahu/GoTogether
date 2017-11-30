package kr.go.seoul.seoultrail.FAQInfo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import kr.go.seoul.seoultrail.R;

/**
 * Created by 지호 on 2017-10-24.
 */

public class FAQ_Depth1ExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> expandableListTitle;
    private LinkedHashMap<String, List<String>> expandableListDetail;

    private List<String> depth2ExpandableListTitle;
    private LinkedHashMap<String, String> depth2ExpandableListDetail;

    public FAQ_Depth1ExpandableAdapter(Context context, List<String> expandableListTitle,
                                       LinkedHashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        if(listPosition == 0) {
            if(convertView != null) {
                convertView = null;
            }

            depth2ExpandableListDetail = FAQ_ExpandableListDataPump.getDataAtDepth2(context);
            depth2ExpandableListTitle = new ArrayList<String>(depth2ExpandableListDetail.keySet());
            FAQ_Depth2ExpandableListView depth2ELV = new FAQ_Depth2ExpandableListView(context);
            depth2ELV.setAdapter(new FAQ_Depth2ExpandableAdapter(context, depth2ExpandableListTitle, depth2ExpandableListDetail));
            depth2ELV.setGroupIndicator(null);
            return depth2ELV;
        } else if(listPosition == 1) {
            // EMAIL LAYOUT

            if(convertView != null) {
                convertView = null;
            }
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.faq_depth2_email, null);

            // email layout 뷰 초기화
            Button sendBtn = (Button) convertView.findViewById(R.id.email_send_btn);
            final EditText titleWrite = (EditText) convertView.findViewById(R.id.email_title_write);
            final EditText bodyWrite = (EditText) convertView.findViewById(R.id.email_body_write);

            sendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, titleWrite.getText().toString());
                    intent.putExtra(Intent.EXTRA_TEXT, bodyWrite.getText().toString());
                    intent.setData(Uri.parse("mailto:seoultrail.gotogether@gmail.com")); // or just "mailto:" for blank
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                    context.startActivity(intent);
                }
            });
            System.out.println("convertview ~~~~~~~~~~~~~~~~~~~~~" + convertView.toString());
            return convertView;
        } else if (listPosition == 2) {
            // 시민 블로그

            if(convertView != null) {
                convertView = null;
            }

            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.blank_activity, null);

            return convertView;
        } else if (listPosition == 3) {
            // 명예의 전당

            if(convertView != null) {
                convertView = null;
            }

            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.blank_activity, null);

            return convertView;
        } else {
            return null;
        }

    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        String listTitle = (String) getGroup(listPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.faq_depth1_group, null);

        }
        // 뷰 초기화
        ImageView depth1_icon = (ImageView) convertView.findViewById(R.id.depth1_icon);
        TextView listTitleDescription = (TextView) convertView.findViewById(R.id.list_title_description);

        switch (listPosition) {
            case 0 : {
                depth1_icon.setImageResource(R.drawable.icon_faq);
                listTitleDescription.setText("둘레길 이용자들이 자주 묻는 질문들입니다. 둘레길에 관한 궁금한 점을 확인해보세요.");
                break;
            }
            case 1 : {
                depth1_icon.setImageResource(R.drawable.icon_mail);
                listTitleDescription.setText("seoultrail.gotogether@gmail.com");
                break;
            }
            case 2 : {
                depth1_icon.setImageResource(R.drawable.icon_blog);
                listTitleDescription.setText("서울 둘레길 관련 블로그 페이지를 공유할 수 있는 공간입니다. 서울 둘레길과 관련한 나만의 블로그를 올려보세요.");
                break;
            }
            case 3 : {
                depth1_icon.setImageResource(R.drawable.icon_halloffame);
                listTitleDescription.setText("서울 둘레길을 완주하신 분들의 명예의 전당입니다. 완주인증서와 함께 찍은 본인의 사진을 올려주세요.");
            }
        }

        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.list_title);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }


}



