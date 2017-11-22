package kr.go.seoul.seoultrail.CourseInfo;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by ntsys on 2016-10-21.
 */
public class CoursePointListFragment extends ActivityHostFragment {

    @Override
    protected Class<? extends Activity> getActivityClass() {
        SharedPreferences pref = this.getActivity().getSharedPreferences("SYSTEM", 0);
        SharedPreferences.Editor edit = pref.edit();
        edit.putBoolean("ISPOINTLIST", true);
        edit.commit();
        return CoursePointListActivity.class;
    }

    public void setCoursePointList(ArrayList<String[]> cotCoordList) {
        CoursePointListActivity.setData(cotCoordList);
    }

    public static CoursePointListFragment newInstance() {
        CoursePointListFragment fragment = new CoursePointListFragment();
        return fragment;
    }
}
