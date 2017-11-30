package kr.go.seoul.seoultrail.FAQInfo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.List;

import kr.go.seoul.seoultrail.R;

/**
 * Created by 지호 on 2017-10-26.
 */

public class FAQ_Depth2ExpandableAdapter extends BaseExpandableListAdapter {
    private static Context context;
    private List<String> depth2ExpandableListTitle;
    private LinkedHashMap<String, String> depth2ExpandableListDetail;

    private final static int CERTIFY_TITLE = 0;
    private final static int ADDINK_TITLE = 1;
    private final static int INFO_TITLE = 2;

    public FAQ_Depth2ExpandableAdapter(Context context, List<String> depth2ExpandableListTitle, LinkedHashMap<String, String> depth2ExpandableListDetail) {
        this.context = context;
        this.depth2ExpandableListTitle = depth2ExpandableListTitle;
        this.depth2ExpandableListDetail = depth2ExpandableListDetail;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.depth2ExpandableListTitle.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.depth2ExpandableListTitle.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.faq_depth2_group, null);
            convertView.setBackgroundColor(Color.rgb(240, 240, 240));
        }

        TextView depth2_title = (TextView) convertView.findViewById(R.id.depth2_title);
        depth2_title.setText(listTitle);
        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String depth2_certify_title = readTitleFromFile(CERTIFY_TITLE);
        String depth2_addInk_title = readTitleFromFile(ADDINK_TITLE);
        String depth2_info_title = readTitleFromFile(INFO_TITLE);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.faq_depth2_child, null);
        }
        TextView depth2_child_title = (TextView) convertView.findViewById(R.id.depth2_description_title);
        TextView depth2_child_answer = (TextView) convertView.findViewById(R.id.depth2_description_answer);

        // depth3 안에 제목 설정
        if(groupPosition == 0) { depth2_child_title.setText(depth2_certify_title); }
        else if(groupPosition == 1) { depth2_child_title.setText(depth2_addInk_title); }
        else { depth2_child_title.setText(depth2_info_title); }

        //depth3 안에 답변 설정
        String listTitle = (String)getGroup(groupPosition);
        depth2_child_answer.setText(depth2ExpandableListDetail.get(listTitle));

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    // 각자 다른 파일로부터 제목텍스트를 읽어옴
    public static String readTitleFromFile(int whatText) {
        InputStream inputStream = null;

        if(whatText == 0) {
            inputStream = context.getResources().openRawResource(R.raw.depth2_certify_title);
        } else if(whatText == 1) {
            inputStream = context.getResources().openRawResource(R.raw.depth2_addink_title);
        } else {
            inputStream = context.getResources().openRawResource(R.raw.depth2_info_title);
        }

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        StringBuilder stringBuilder = new StringBuilder();
        String strLine;

        try {
            while((strLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(strLine);
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}