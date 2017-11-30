package kr.go.seoul.seoultrail.CourseInfo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import kr.go.seoul.seoultrail.Common.PublicDefine;
import kr.go.seoul.seoultrail.R;

/**
 * Created by ntsys on 2016-08-09.
 */
public class CourseListFragment extends BaseFragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private static CourseListFragment courseListFragment;
    private int mPage;
    private ExpandableListView courseListView = null;
    private CourseListAdapter courseListAdapter;
    private ArrayList<CourseBaseInfo> mGroupList = null;

    public static CourseListFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        courseListFragment = new CourseListFragment();
        PublicDefine.courseListFragment = courseListFragment;
        courseListFragment.setArguments(args);
        return courseListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_list_fragment, container, false);
        courseListView = (ExpandableListView) view.findViewById(R.id.course_list_view);
        courseListView.setSmoothScrollbarEnabled(true);
        courseListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            int lastClickedPosition = -1;

            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                // 선택 한 groupPosition의 펼침/닫힘 상태 체크
                Boolean isExpand = (!courseListView.isGroupExpanded(groupPosition));

                // 이 전에 열려있던 group 닫기
                courseListView.collapseGroup(lastClickedPosition);

                if (isExpand) {
                    courseListView.expandGroup(groupPosition);
                }
                lastClickedPosition = groupPosition;
                courseListView.setSelection(groupPosition);
                return true;
            }
        });

        mGroupList = new ArrayList<CourseBaseInfo>();

        courseListAdapter = new CourseListAdapter(getContext(), CourseListFragment.this, mGroupList);
        courseListView.setAdapter(courseListAdapter);

        //PublicDefine.mainActivity.showProgressDialog();
        new ProcessNetworkCourseBaseInfoThread().execute(null, null, null);
        return view;
    }

    public class ProcessNetworkCourseBaseInfoThread extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            String content = executeClient();
            return content;
        }

        protected void onPostExecute(String result) {
            String main = result.toString();

            try {
                JSONObject jsonMain = new JSONObject(main);
                JSONArray jsonList = jsonMain.getJSONArray("list");
                for (int i = 0; i < jsonList.length(); i++) {
                    mGroupList.add(new CourseBaseInfo(Integer.parseInt(jsonList.getJSONObject(i).getString("COURSE_NO")),
                            Integer.parseInt(jsonList.getJSONObject(i).getString("COURSE_LEVEL")),
                            jsonList.getJSONObject(i).getString("COURSE_NM"),
                            jsonList.getJSONObject(i).getString("LOCATION"),
                            jsonList.getJSONObject(i).getString("DISTANCE"),
                            jsonList.getJSONObject(i).getString("WALK_TIME")));
                }

                Collections.sort(mGroupList, myComparator);
                courseListAdapter.notifyDataSetChanged();
                PublicDefine.mainActivity.cancelProgressDialog();
            } catch (Exception e) {
            }
        }

        // 실제 전송하는 부분
        public String executeClient() {
            HttpResponse response = null;
            ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();

            // 연결 HttpClient 객체 생성
            HttpClient client = new DefaultHttpClient();

            // 객체 연결 설정 부분, 연결 최대시간 등등
            HttpParams params = client.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 30000);
            HttpConnectionParams.setSoTimeout(params, 30000);

            HttpGet httpGet = new HttpGet("https://mplatform.seoul.go.kr/api/dule/courseBaseInfo.do");
            try {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(post, "UTF-8");

                response = client.execute(httpGet);
                String resultJson = EntityUtils.toString(response.getEntity(), "UTF-8");
                return resultJson;
            } catch (ClientProtocolException e) {
                Log.e("NTsys", "연결 예외 상황 발생");
                return "";
            } catch (IOException e) {
                Log.e("NTsys", "연결 예외 상황 발생");
                return "";
            }
        }

    }

    //Comparator 를 만든다.
    private final static Comparator<CourseBaseInfo> myComparator = new Comparator<CourseBaseInfo>() {

        public int compare(CourseBaseInfo object1, CourseBaseInfo object2) {
            return (object1.getCourseNo() < object2.getCourseNo()) ? -1 : (object1.getCourseNo() > object2.getCourseNo()) ? 1 : 0;

        }
    };

}