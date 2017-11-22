package kr.go.seoul.seoultrail.TrailInformation;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

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

import kr.go.seoul.seoultrail.Common.EndlessScrollListener;
import kr.go.seoul.seoultrail.Common.PublicDefine;
import kr.go.seoul.seoultrail.R;

/**
 * Created by ntsys on 2016-08-09.
 */
public class EventFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private static EventFragment fragment;
    private int mPage;
    private ListView informationEventListView;
    private ArrayList<EventInfo> informationEventInfoArrayList;
    private int pageNum = 1;

    public static EventFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        if (fragment == null) {
            fragment = new EventFragment();
            fragment.setArguments(args);
        }
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
        View view = inflater.inflate(R.layout.information_event, container, false);
        informationEventListView = (ListView) view.findViewById(R.id.information_event_listview);
        informationEventInfoArrayList = new ArrayList<EventInfo>();

        informationEventListView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                if (pageNum != -1) {
                    PublicDefine.mainActivity.showProgressDialog();
                    new ProcessNetworkEventInfoThread().execute(pageNum);
                }
                return true;
            }
        });

        informationEventListView.setAdapter(new EventListAdapter(getContext(), R.layout.information_event_list_item, informationEventInfoArrayList));
        informationEventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(((EventInfo) parent.getAdapter().getItem(position)).getLink());
                intent.setData(uri);
                startActivity(intent);
            }
        });
        PublicDefine.mainActivity.showProgressDialog();
        new ProcessNetworkEventInfoThread().execute(pageNum);
        return view;
    }

    public class ProcessNetworkEventInfoThread extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... integers) {
            String content = executeClient(integers);
            return content;
        }

        protected void onPostExecute(String result) {
            String main = result.toString();

            try {
                JSONObject jsonMain = new JSONObject(main);
                JSONArray jsonList = jsonMain.getJSONArray("list");
                String imgTag;
                if (jsonList != null && jsonList.length() != 0) {
                    for (int i = 0; i < jsonList.length(); i++) {
                        imgTag = jsonList.getJSONObject(i).getString("IMG");
                        informationEventInfoArrayList.add(new EventInfo(jsonList.getJSONObject(i).getString("TITLE")
                                , jsonList.getJSONObject(i).getString("REG_DATE")
                                , imgTag.substring(imgTag.indexOf("'") + 1, imgTag.indexOf("'", imgTag.indexOf("'") + 1))
                                , jsonList.getJSONObject(i).getString("LINK")));
                    }
                    pageNum = pageNum + 1;
                } else {
                    pageNum = -1;
                }

            } catch (Exception e) {
            } finally {
                PublicDefine.mainActivity.cancelProgressDialog();
                ((BaseAdapter) informationEventListView.getAdapter()).notifyDataSetChanged();
            }
        }

        // 실제 전송하는 부분
        public String executeClient(Integer[] integers) {
            PublicDefine.mainActivity.showProgressDialog();
            HttpResponse response = null;
            ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();

            // 연결 HttpClient 객체 생성
            HttpClient client = new DefaultHttpClient();

            // 객체 연결 설정 부분, 연결 최대시간 등등
            HttpParams params = client.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 30000);
            HttpConnectionParams.setSoTimeout(params, 30000);

            HttpGet httpGet = new HttpGet("https://mplatform.seoul.go.kr/api/dule/eventList.do?pagenum=" + integers[0]);
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

    public void setInitList() {
        if (informationEventListView != null) {
            informationEventListView.setScrollY(0);
        }
    }
}