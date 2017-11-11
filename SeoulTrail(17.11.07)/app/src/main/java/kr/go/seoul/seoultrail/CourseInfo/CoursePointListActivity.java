package kr.go.seoul.seoultrail.CourseInfo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import kr.go.seoul.seoultrail.Common.FontUtils;
import kr.go.seoul.seoultrail.Common.PublicDefine;
import kr.go.seoul.seoultrail.R;

/**
 * Created by ntsys on 2016-10-21.
 */
public class CoursePointListActivity extends Activity {

    private ListView coursePointListView;
    private static ArrayAdapter<String> testAdapter;
    private static ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_point_list);

        initView();
    }

    private void initView() {
        coursePointListView = (ListView) findViewById(R.id.course_point_list_view);
        testAdapter = new ArrayAdapter<String>(CoursePointListActivity.this, android.R.layout.simple_list_item_1, data) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                FontUtils.getInstance(CoursePointListActivity.this).setGlobalFont(parent);
                return super.getView(position, convertView, parent);
            }
        };
        coursePointListView.setAdapter(testAdapter);
        coursePointListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                PublicDefine.course.dismissPointListActivity(position);
            }
        });
    }

    public static void setData(ArrayList<String[]> cotCoordList) {
        data.clear();
        for (int i = 0; i < cotCoordList.size(); i++) {
            data.add(cotCoordList.get(i)[1]);
        }
        testAdapter.notifyDataSetChanged();
    }
}
