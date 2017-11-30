package kr.go.seoul.seoultrail.CourseStamp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

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

import kr.go.seoul.seoultrail.Common.DBHelper;
import kr.go.seoul.seoultrail.Common.FontUtils;
import kr.go.seoul.seoultrail.Common.PublicDefine;
import kr.go.seoul.seoultrail.Common.StampHistory;
import kr.go.seoul.seoultrail.Common.StampLocation;
import kr.go.seoul.seoultrail.ImageViewPopup;
import kr.go.seoul.seoultrail.R;

/**
 * Created by ntsys on 2016-08-09.
 */
public class CourseStampFragment extends Fragment implements View.OnClickListener {
    public static final String ARG_PAGE = "ARG_PAGE";

    private static CourseStampFragment fragment;
    private int mPage;
    private ScrollView stampScrollView;
    private ListView courseStampListview;
    private CourseStampListAdapter courseStampListAdapter;
    private ArrayList<CourseStampInfo> courseStampInfoArrayList;

    private View[] stampView = new View[8];
    private View[] stampImgView = new View[28];
    private int[] stampImgViewID = new int[]{R.id.stamp_01, R.id.stamp_02, R.id.stamp_03, R.id.stamp_04
            , R.id.stamp_05, R.id.stamp_06, R.id.stamp_07, R.id.stamp_08
            , R.id.stamp_09, R.id.stamp_10, R.id.stamp_11, R.id.stamp_12
            , R.id.stamp_13, R.id.stamp_14, R.id.stamp_15, R.id.stamp_16
            , R.id.stamp_17, R.id.stamp_18, R.id.stamp_19, R.id.stamp_20
            , R.id.stamp_21, R.id.stamp_22, R.id.stamp_23, R.id.stamp_24
            , R.id.stamp_25, R.id.stamp_26, R.id.stamp_27, R.id.stamp_28};
    private int[] stampImgOffID = new int[]{R.drawable.stamp01_off, R.drawable.stamp02_off, R.drawable.stamp03_off, R.drawable.stamp04_off
            , R.drawable.stamp05_off, R.drawable.stamp06_off, R.drawable.stamp07_off, R.drawable.stamp08_off
            , R.drawable.stamp09_off, R.drawable.stamp10_off, R.drawable.stamp11_off, R.drawable.stamp12_off
            , R.drawable.stamp13_off, R.drawable.stamp14_off, R.drawable.stamp15_off, R.drawable.stamp16_off
            , R.drawable.stamp17_off, R.drawable.stamp18_off, R.drawable.stamp19_off, R.drawable.stamp20_off
            , R.drawable.stamp21_off, R.drawable.stamp22_off, R.drawable.stamp23_off, R.drawable.stamp24_off
            , R.drawable.stamp25_off, R.drawable.stamp26_off, R.drawable.stamp27_off, R.drawable.stamp28_off};
    private int[] stampImgOnID = new int[]{R.drawable.stamp01_on, R.drawable.stamp02_on, R.drawable.stamp03_on, R.drawable.stamp04_on
            , R.drawable.stamp05_on, R.drawable.stamp06_on, R.drawable.stamp07_on, R.drawable.stamp08_on
            , R.drawable.stamp09_on, R.drawable.stamp10_on, R.drawable.stamp11_on, R.drawable.stamp12_on
            , R.drawable.stamp13_on, R.drawable.stamp14_on, R.drawable.stamp15_on, R.drawable.stamp16_on
            , R.drawable.stamp17_on, R.drawable.stamp18_on, R.drawable.stamp19_on, R.drawable.stamp20_on
            , R.drawable.stamp21_on, R.drawable.stamp22_on, R.drawable.stamp23_on, R.drawable.stamp24_on
            , R.drawable.stamp25_on, R.drawable.stamp26_on, R.drawable.stamp27_on, R.drawable.stamp28_on};
    //private View[] stampCountView = new View[28];
    /*private int[] stampCountViewID = new int[]{R.id.stamp_01_count, R.id.stamp_02_count, R.id.stamp_03_count, R.id.stamp_04_count
            , R.id.stamp_05_count, R.id.stamp_06_count, R.id.stamp_07_count, R.id.stamp_08_count
            , R.id.stamp_09_count, R.id.stamp_10_count, R.id.stamp_11_count, R.id.stamp_12_count
            , R.id.stamp_13_count, R.id.stamp_14_count, R.id.stamp_15_count, R.id.stamp_16_count
            , R.id.stamp_17_count, R.id.stamp_18_count, R.id.stamp_19_count, R.id.stamp_20_count
            , R.id.stamp_21_count, R.id.stamp_22_count, R.id.stamp_23_count, R.id.stamp_24_count
            , R.id.stamp_25_count, R.id.stamp_26_count, R.id.stamp_27_count, R.id.stamp_28_count};*/

    public static CourseStampFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        if (fragment == null) {
            fragment = new CourseStampFragment();
            fragment.setArguments(args);
        }
        PublicDefine.courseStampFragment = fragment;
        return fragment;
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
        //View view = inflater.inflate(R.layout.course_stamp_fragment, container, false);
        View view = inflater.inflate(R.layout.course_list_group_stamp_list, container, false);
//        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
//        tvTitle.setText("CourseStampFragment #" + mPage);

        //변경하려는 View
        stampScrollView = (ScrollView) view.findViewById(R.id.stamp_scrollview);
        //setViewStampView();

        /*//scroll을 생성 단순 scroll
        stampScrollView = (ScrollView) view.findViewById(R.id.stamp_scrollview);
        //listview를 생성 단순 listview
        courseStampListview = (ListView) view.findViewById(R.id.course_stamp_listview);
        courseStampInfoArrayList = new ArrayList<CourseStampInfo>();

        courseStampListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setViewStampView(i);
                stampScrollView.setScrollY(0);
            }
        });*/
        stampView[0] = view.findViewById(R.id.course01_stamp);
        stampView[1] = view.findViewById(R.id.course02_stamp);
        stampView[2] = view.findViewById(R.id.course03_stamp);
        stampView[3] = view.findViewById(R.id.course04_stamp);
        stampView[4] = view.findViewById(R.id.course05_stamp);
        stampView[5] = view.findViewById(R.id.course06_stamp);
        stampView[6] = view.findViewById(R.id.course07_stamp);
        stampView[7] = view.findViewById(R.id.course08_stamp);

        stampImgView[0] = stampView[0].findViewById(stampImgViewID[0]);
        stampImgView[1] = stampView[0].findViewById(stampImgViewID[1]);
        stampImgView[2] = stampView[0].findViewById(stampImgViewID[2]);

        stampImgView[3] = stampView[1].findViewById(stampImgViewID[3]);
        stampImgView[4] = stampView[1].findViewById(stampImgViewID[4]);
        stampImgView[5] = stampView[1].findViewById(stampImgViewID[5]);

        stampImgView[6] = stampView[2].findViewById(stampImgViewID[6]);
        stampImgView[7] = stampView[2].findViewById(stampImgViewID[7]);
        stampImgView[8] = stampView[2].findViewById(stampImgViewID[8]);
        stampImgView[9] = stampView[2].findViewById(stampImgViewID[9]);

        stampImgView[10] = stampView[3].findViewById(stampImgViewID[10]);
        stampImgView[11] = stampView[3].findViewById(stampImgViewID[11]);
        stampImgView[12] = stampView[3].findViewById(stampImgViewID[12]);

        stampImgView[13] = stampView[4].findViewById(stampImgViewID[13]);
        stampImgView[14] = stampView[4].findViewById(stampImgViewID[14]);
        stampImgView[15] = stampView[4].findViewById(stampImgViewID[15]);

        stampImgView[16] = stampView[5].findViewById(stampImgViewID[16]);
        stampImgView[17] = stampView[5].findViewById(stampImgViewID[17]);
        stampImgView[18] = stampView[5].findViewById(stampImgViewID[18]);

        stampImgView[19] = stampView[6].findViewById(stampImgViewID[19]);
        stampImgView[20] = stampView[6].findViewById(stampImgViewID[20]);
        stampImgView[21] = stampView[6].findViewById(stampImgViewID[21]);

        stampImgView[22] = stampView[7].findViewById(stampImgViewID[22]);
        stampImgView[23] = stampView[7].findViewById(stampImgViewID[23]);
        stampImgView[24] = stampView[7].findViewById(stampImgViewID[24]);
        stampImgView[25] = stampView[7].findViewById(stampImgViewID[25]);
        stampImgView[26] = stampView[7].findViewById(stampImgViewID[26]);
        stampImgView[27] = stampView[7].findViewById(stampImgViewID[27]);


        /*stampCountView[0] = stampView[0].findViewById(stampCountViewID[0]);
        stampCountView[1] = stampView[0].findViewById(stampCountViewID[1]);
        stampCountView[2] = stampView[0].findViewById(stampCountViewID[2]);

        stampCountView[3] = stampView[1].findViewById(stampCountViewID[3]);
        stampCountView[4] = stampView[1].findViewById(stampCountViewID[4]);
        stampCountView[5] = stampView[1].findViewById(stampCountViewID[5]);

        stampCountView[6] = stampView[2].findViewById(stampCountViewID[6]);
        stampCountView[7] = stampView[2].findViewById(stampCountViewID[7]);
        stampCountView[8] = stampView[2].findViewById(stampCountViewID[8]);
        stampCountView[9] = stampView[2].findViewById(stampCountViewID[9]);

        stampCountView[10] = stampView[3].findViewById(stampCountViewID[10]);
        stampCountView[11] = stampView[3].findViewById(stampCountViewID[11]);
        stampCountView[12] = stampView[3].findViewById(stampCountViewID[12]);

        stampCountView[13] = stampView[4].findViewById(stampCountViewID[13]);
        stampCountView[14] = stampView[4].findViewById(stampCountViewID[14]);
        stampCountView[15] = stampView[4].findViewById(stampCountViewID[15]);

        stampCountView[16] = stampView[5].findViewById(stampCountViewID[16]);
        stampCountView[17] = stampView[5].findViewById(stampCountViewID[17]);
        stampCountView[18] = stampView[5].findViewById(stampCountViewID[18]);

        stampCountView[19] = stampView[6].findViewById(stampCountViewID[19]);
        stampCountView[20] = stampView[6].findViewById(stampCountViewID[20]);
        stampCountView[21] = stampView[6].findViewById(stampCountViewID[21]);

        stampCountView[22] = stampView[7].findViewById(stampCountViewID[22]);
        stampCountView[23] = stampView[7].findViewById(stampCountViewID[23]);
        stampCountView[24] = stampView[7].findViewById(stampCountViewID[24]);
        stampCountView[25] = stampView[7].findViewById(stampCountViewID[25]);
        stampCountView[26] = stampView[7].findViewById(stampCountViewID[26]);
        stampCountView[27] = stampView[7].findViewById(stampCountViewID[27]);*/

        //클릭시 보여주기.
        for (int i = 0; i < stampImgView.length; i++) {
            stampImgView[i].setOnClickListener(this);
        }

        new ProcessNetworkCourseBaseInfoThread().execute(null, null, null);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setVaidateCompleteStampCount() {
        ArrayList<StampLocation> stampLocationList = DBHelper.getInstance(this.getContext()).getCompleteStampCount();
        for (int i = 0; i < stampLocationList.size(); i++) {
            //텍스트를 뷰에 1로 바꿔준다. 이부분을 완료로 수정
            //((TextView) stampCountView[i]).setText(stampLocationList.get(i).getComplete_count());
            if (stampLocationList.get(i).getComplete_count().equals("0")) {
                ((ImageView) stampImgView[i]).setImageResource(stampImgOffID[i]);
            } else {
                ((ImageView) stampImgView[i]).setImageResource(stampImgOnID[i]);
            }
            stampImgView[i].invalidate();
            //카운트 없애주기
            //stampCountView[i].invalidate();
        }

    }

    private void setViewStampView(/*int courseNum*/) {
        for (int i = 0; i < stampView.length; i++) {
            //stampView[i].setVisibility(View.GONE);
            stampView[i].setVisibility(View.VISIBLE);
        }
        //stampView[courseNum].setVisibility(View.VISIBLE);
    }

    //클릭시에 그냥 토스트 메세지 띄우고 해당 사진을 여기서 보여주면 되지 않나?
    @Override
    public void onClick(View view) {
        //courseStampListAdapter.notifyDataSetChanged();
        for (int i = 0; i < stampImgView.length; i++) {
            if (view.getId() == stampImgView[i].getId()) {
                ArrayList<StampHistory> arraylistStampHistoyList = DBHelper.getInstance(getActivity()).getCompleteStampHistory(i);
<<<<<<< HEAD:SeoulTrail_final/app/src/main/java/kr/go/seoul/seoultrail/CourseStamp/CourseStampFragment.java
                //showHistoryList(arraylistStampHistoyList);
                SpannableString[] histoyList;
                if (arraylistStampHistoyList.size() > 0) {
                    histoyList = new SpannableString[arraylistStampHistoyList.size()];
                    for (int j = 0; j < arraylistStampHistoyList.size(); j++) {
                        histoyList[j] = FontUtils.getInstance(getActivity()).typeface(arraylistStampHistoyList.get(j).getUpdateDate());
                    }
                    final int e = i;
                    stampImgView[e].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getActivity(), ImageViewPopup.class);
                            intent.putExtra("RESID", stampImgOnID[e]);
                            getActivity().startActivity(intent);
                        }
                    });
                } else {
                    histoyList = new SpannableString[]{FontUtils.getInstance(getActivity()).typeface("스탬프 이력이 없습니다.")};
                    Toast.makeText(getActivity(), "스탬프 이력이 없습니다", Toast.LENGTH_SHORT).show();
                }
=======
                showHistoryList(arraylistStampHistoyList);
>>>>>>> ca4c9829395aed517e854978b33530427e00b35b:SeoulTrail/app/src/main/java/kr/go/seoul/seoultrail/CourseStamp/CourseStampFragment.java
            }
        }
    }

    // 이력이 있는지 없는지 보여준다. 메인 액티비티에서 불러와야 하는데 메뉴 액티비티가 주로 활동하는데 불러오기 때문에 에러가 난다.
    // 부분 삭제
    /*private void showHistoryList(ArrayList<StampHistory> arraylistStampHistoyList) {
        SpannableString[] histoyList;
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(getActivity());
        if (arraylistStampHistoyList.size() > 0) {
            histoyList = new SpannableString[arraylistStampHistoyList.size()];
            for (int i = 0; i < arraylistStampHistoyList.size(); i++) {
                histoyList[i] = FontUtils.getInstance(getActivity()).typeface(arraylistStampHistoyList.get(i).getUpdateDate());
            }
        } else {
            histoyList = new SpannableString[]{FontUtils.getInstance(getActivity()).typeface("스탬프 이력이 없습니다.")};
        }
        alert_confirm.setItems(histoyList, null);
        alert_confirm.setNegativeButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        // 이미지를 저장해서 alert_confirm.create()로 생성하고 이것을 메세지 형식으로 Show()해준다.
        // 오류를 해결하기 위해선, 상위액티비티.this를 적용시키거나 / isFinishing() 검사 / 최상위 액티비티의 context를 static으로 선언해서 사용 허가를 줘야한다.
        final AlertDialog alert = alert_confirm.create();
        final Typeface mTypeface = Typeface.createFromAsset(getActivity().getAssets(), "arita_bold.ttf");
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alert.getButton(Dialog.BUTTON_POSITIVE).setTypeface(mTypeface);
                alert.getButton(Dialog.BUTTON_NEGATIVE).setTypeface(mTypeface);
            }
        });

        alert.show();

<<<<<<< HEAD:SeoulTrail_final/app/src/main/java/kr/go/seoul/seoultrail/CourseStamp/CourseStampFragment.java
    }*/
=======
    }
>>>>>>> ca4c9829395aed517e854978b33530427e00b35b:SeoulTrail/app/src/main/java/kr/go/seoul/seoultrail/CourseStamp/CourseStampFragment.java

    public void setCourseNo(final int courseNo) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                courseStampListview.performItemClick(courseStampListAdapter.getView(courseNo, null, null), courseNo, courseStampListAdapter.getItemId(courseNo));
                courseStampListview.setSelection(courseNo);
            }
        });
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
                    courseStampInfoArrayList.add(new CourseStampInfo(CourseStampFragment.this,
                            Integer.parseInt(jsonList.getJSONObject(i).getString("COURSE_NO")),
                            jsonList.getJSONObject(i).getString("COURSE_NM")));
                }
                Collections.sort(courseStampInfoArrayList, myComparator);
                ///courseStampListAdapter = new CourseStampListAdapter(CourseStampFragment.this.getContext(), R.layout.course_stamp_list_title, courseStampInfoArrayList);
                courseStampListview.setAdapter(courseStampListAdapter);
            } catch (Exception e) {
            }

            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    //courseStampListview.performItemClick(courseStampListAdapter.getView(0, null, null), 0, courseStampListAdapter.getItemId(0));
                    setVaidateCompleteStampCount();
                }
            });
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
    private final static Comparator<CourseStampInfo> myComparator = new Comparator<CourseStampInfo>() {

        public int compare(CourseStampInfo object1, CourseStampInfo object2) {
            return (object1.getCourseNo() < object2.getCourseNo()) ? -1 : (object1.getCourseNo() > object2.getCourseNo()) ? 1 : 0;

        }
    };
}