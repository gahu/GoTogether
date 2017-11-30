package kr.go.seoul.seoultrail.CourseInfo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import kr.go.seoul.seoultrail.Common.FontUtils;
import kr.go.seoul.seoultrail.Common.PublicDefine;
import kr.go.seoul.seoultrail.ImageViewPopup;
import kr.go.seoul.seoultrail.R;

/**
 * Created by ntsys on 2016-08-10.
 */
public class CourseListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private CourseListFragment courseListFragment;
    ArrayList<CourseBaseInfo> groupList = null;
    private LayoutInflater inflater = null;
    private ViewHolder viewHolder = null;
    private int[] courseNum = {R.drawable.num1, R.drawable.num2, R.drawable.num3, R.drawable.num4,
            R.drawable.num5, R.drawable.num6, R.drawable.num7, R.drawable.num8};

    private int[] courseLevel = {R.drawable.img_ha, R.drawable.img_jung, R.drawable.img_sang};

    private int[] course01 = {R.drawable.course01_1, R.drawable.course02_1, R.drawable.course03_1, R.drawable.course04_1,
            R.drawable.course05_1, R.drawable.course06_1, R.drawable.course07_1, R.drawable.course08_1};
    private int[] course02 = {R.drawable.course01_2, R.drawable.course02_2, R.drawable.course03_2, R.drawable.course04_2,
            R.drawable.course05_2, R.drawable.course06_2, R.drawable.course07_2, R.drawable.course08_2};
    private int[] course03 = {R.drawable.course01_3, R.drawable.course02_3, R.drawable.course03_3, R.drawable.course04_3,
            R.drawable.course05_3, R.drawable.course06_3, R.drawable.course07_3, R.drawable.course08_3};

    public CourseListAdapter(Context context, CourseListFragment courseListFragment, ArrayList<CourseBaseInfo> groupList) {
        super();
        this.context = context;
        this.courseListFragment = courseListFragment;
        this.inflater = LayoutInflater.from(context);
        this.groupList = groupList;
    }

    // 그룹 포지션을 반환한다.
    @Override
    public String getGroup(int groupPosition) {
        return groupList.get(groupPosition).getCourseName();
    }

    // 그룹 사이즈를 반환한다.
    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    // 그룹 ID를 반환한다.
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // 그룹뷰 각각의 ROW
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        View v = convertView;
        final int position;
        if (groupPosition >= courseNum.length) {
            position = courseNum.length - 1;
        } else {
            position = groupPosition;
        }

        if (v == null) {
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.course_list_group_title, parent, false);
            viewHolder.courseNumImg = (ImageView) v.findViewById(R.id.course_num_img);
            viewHolder.courseTitle = (TextView) v.findViewById(R.id.course_title);
            viewHolder.courseLevelImg = (ImageView) v.findViewById(R.id.course_level_img);
            viewHolder.courseLocation = (TextView) v.findViewById(R.id.course_location);
            viewHolder.courseWalkTime = (TextView) v.findViewById(R.id.course_walk_time);
            viewHolder.viewMap = (RelativeLayout) v.findViewById(R.id.view_map);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.courseNumImg.setImageResource(courseNum[position]);
        viewHolder.courseTitle.setText(getGroup(position));
        viewHolder.courseLevelImg.setImageResource(courseLevel[groupList.get(position).getCourseLevel() - 1]);

        viewHolder.viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CourseMapFragment map = CourseMapFragment.newInstance();
                PublicDefine.courseMapFragment = map;
                PublicDefine.courseMapFragment.setCourse(position);
                courseListFragment.startFragment(courseListFragment.getFragmentManager(), PublicDefine.courseMapFragment.getClass());
            }
        });

        viewHolder.courseLocation.setText(groupList.get(position).getLocation() + "(" + groupList.get(position).getDistance() + ")");
        viewHolder.courseWalkTime.setText(groupList.get(position).getWalkTime());

        FontUtils.getInstance(this.context).setGlobalFont(parent);

        return v;
    }

    // 차일드뷰를 반환한다.
    @Override
    public String getChild(int groupPosition, int childPosition) {
        return null;
    }

    // 차일드뷰 사이즈를 반환한다.
    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    // 차일드뷰 ID를 반환한다.
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    // 차일드뷰 각각의 ROW
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        View v = convertView;
        final int position;
        if (groupPosition >= course01.length) {
            position = course01.length - 1;
        } else {
            position = groupPosition;
        }
        if (v == null) {
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.course_list_content_item, null);
            viewHolder.courseInfo01 = (ImageView) v.findViewById(R.id.course_info_01);
            viewHolder.courseInfo02 = (ImageView) v.findViewById(R.id.course_info_02);
            viewHolder.courseInfo03 = (ImageView) v.findViewById(R.id.course_info_03);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        viewHolder.courseInfo01.setImageResource(course01[position]);
        viewHolder.courseInfo02.setImageResource(course02[position]);
        viewHolder.courseInfo03.setImageResource(course03[position]);
        viewHolder.courseInfo02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ImageViewPopup.class);
                intent.putExtra("RESID", course02[position]);
                context.startActivity(intent);
            }
        });
        return v;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ViewHolder {
        public ImageView courseNumImg;
        public TextView courseTitle;
        public ImageView courseLevelImg;
        public TextView courseLocation;
        public TextView courseWalkTime;
        public RelativeLayout viewMap;

        public ImageView courseInfo01;
        public ImageView courseInfo02;
        public ImageView courseInfo03;
    }
}
