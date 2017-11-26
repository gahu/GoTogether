package kr.go.seoul.seoultrail.CourseStamp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kr.go.seoul.seoultrail.Common.FontUtils;
import kr.go.seoul.seoultrail.R;

/**
 * Created by ntsys on 2016-08-16.
 */
public class CourseStampListAdapter extends ArrayAdapter<CourseStampInfo> {

    private ViewHolder viewHolder = null;
    private LayoutInflater inflater = null;
    private ArrayList<CourseStampInfo> infoList = null;
    private int[] courseNum = {R.drawable.num1, R.drawable.num2, R.drawable.num3, R.drawable.num4,
            R.drawable.num5, R.drawable.num6, R.drawable.num7, R.drawable.num8};
    private Context mContext = null;

    public CourseStampListAdapter(Context c, int ResourceId,
                                  ArrayList<CourseStampInfo> arrays) {
        super(c, ResourceId, arrays);
        this.inflater = LayoutInflater.from(c);
        this.mContext = c;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public CourseStampInfo getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {

        View v = convertview;

        if (v == null) {
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.course_stamp_list_title, null);
            viewHolder.courseNumImg = (ImageView) v.findViewById(R.id.course_num_img);
            viewHolder.courseTitle = (TextView) v.findViewById(R.id.course_title);
            v.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        viewHolder.courseNumImg.setImageResource(courseNum[position]);
        viewHolder.courseTitle.setText(getItem(position).courseTitle.replace("코스", "\n코스"));
        FontUtils.getInstance(this.mContext).setGlobalFont(parent);
        return v;
    }

    class ViewHolder {
        public ImageView courseNumImg;
        public TextView courseTitle;
    }
}
