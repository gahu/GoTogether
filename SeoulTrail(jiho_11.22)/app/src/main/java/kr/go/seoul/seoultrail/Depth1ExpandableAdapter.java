package kr.go.seoul.seoultrail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 지호 on 2017-10-24.
 */

public class Depth1ExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;

    private List<String> depth2ExpandableListTitle;
    private HashMap<String, String> depth2ExpandableListDetail;

    public Depth1ExpandableAdapter(Context context, List<String> expandableListTitle,
                                   HashMap<String, List<String>> expandableListDetail) {
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
            depth2ExpandableListDetail = ExpandableListDataPump.getDataAtDepth2();
            depth2ExpandableListTitle = new ArrayList<String>(depth2ExpandableListDetail.keySet());
            Depth2ExpandableListView depth2ELV = new Depth2ExpandableListView(context);
            depth2ELV.setAdapter(new Depth2ExpandableAdapter(context, depth2ExpandableListTitle, depth2ExpandableListDetail));
            depth2ELV.setGroupIndicator(null);
            return depth2ELV;
        } else if(listPosition == 1) { /* 가이드 액티비티 */
            if(convertView != null) {
                convertView = null;
            }
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.activity_guide, null);
            return convertView;
        } else if (listPosition == 2) {
            if(convertView != null) {
                convertView = null;
            }
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.depth2_email, null);
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
            convertView = layoutInflater.inflate(R.layout.depth1_group, null);
        }
        ImageView depth1_icon = (ImageView) convertView.findViewById(R.id.depth1_icon);
        switch (listPosition) {
            case 0 : {
                depth1_icon.setImageResource(R.drawable.icon_faq);
                break;
            }
            case 1 : {
                depth1_icon.setImageResource(R.drawable.icon_questgif);
                break;
            }
            case 2 : {
                depth1_icon.setImageResource(R.drawable.icon_mail);
                break;
            }
        }

        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
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



