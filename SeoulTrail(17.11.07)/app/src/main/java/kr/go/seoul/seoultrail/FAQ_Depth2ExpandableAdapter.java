package kr.go.seoul.seoultrail;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 지호 on 2017-10-26.
 */

public class FAQ_Depth2ExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> depth2ExpandableListTitle;
    private HashMap<String, String> depth2ExpandableListDetail;

    public FAQ_Depth2ExpandableAdapter(Context context, List<String> depth2ExpandableListTitle, HashMap<String, String> depth2ExpandableListDetail) {
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
        String depth2_child_title1 = "탬프북을 일부러 받으러 가기가 애매해서 자료실에서 스탬프북 자료를 받아 인쇄하여 사용할 예정입니다. " +
                "기존 안내에 보면 스탬프북이 아니더라도 여러가지 방법으로 인증가능하다는 안내를 읽었는데 아직까지 유효한지요.";
        String depth2_child_title2 = "날씨가 선선해지면서 스탬프 투어를 다시시작하였습니다. 안양천 2구간 스탬프가 2곳다 잉크가 없어서 찍히지 않더군요. " +
                "힘들게 걸어왔는데 스탬프가 찍히지 않아 허탈했습니다. 앞으로 계속 둘레길 투어를 할계획인데 전체구간 잉크 체크요청드립니다.";
        String depth2_child_title3 = "꽃피는 4월 중순에 시작하여 4월 30일에 둘레길을 무사히 완주했습니다. 서울 둘레길을 걸으며 주변에도 꼭 한번 걸어 보라고 권하고 싶더군요. " +
                "그런데 둘레길을 걸으며 안내표식이 제대로 되어 있지 않아 길을 잘못 들어 헤멨던 기억들이 많습니다. " +
                "나무팻말을 만나면 더없이 반갑지만 그것까지는 바라지 않더라도 주황색 리본을 좀더 촘촘히 매달아 주셨으면 합니다. " +
                "갈림길에서는 특히 가야할 방향을 정확히 알 수 있게 표시해 주셨으면 좋겠습니다. 그리고 오래되어 색깔이 바랜 리본들도 자주 교체해 주셨으면 좋겠습니다. " +
                "마지막으로 스템프도장에 잉크가 말라서 잘 안찍히는 구간들이 많았습니다. " +
                "어렵게 걸어서 찍은 스템프 수첩은 소중한 추억중 하나로 간직하고 싶은데 도장이 흐리면 아쉬움이 남더군요. " +
                "여러모로 신경써서 서울둘레길을 정비해 주시는 모든 분들께 감사드립니다. 서울둘레길을 더 많은 사람들이 걸을 수 있기를 바랍니다.";


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.faq_depth2_child, null);
        }
        TextView depth2_child_title = (TextView) convertView.findViewById(R.id.depth2_description_title);
        TextView depth2_child_answer = (TextView) convertView.findViewById(R.id.depth2_description_answer);

        // depth3 안에 제목 설정
        if(groupPosition == 0) { depth2_child_title.setText(depth2_child_title1); }
        else if(groupPosition == 1) { depth2_child_title.setText(depth2_child_title2); }
        else { depth2_child_title.setText(depth2_child_title3); }

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
}
