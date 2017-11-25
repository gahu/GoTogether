package kr.go.seoul.seoultrail.WeatherInfo;

import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.ArrayList;

import kr.go.seoul.seoultrail.Common.PublicDefine;
import kr.go.seoul.seoultrail.R;

/**
 * Created by bgg89 on 2017-11-23.
 */

public class WeatherFragment extends Fragment {

    TextView tv[] = new TextView[36];

    public static WeatherFragment newInstance(int position) {
        Bundle args = new Bundle();
        WeatherFragment fragment = new WeatherFragment();
        PublicDefine.weatherFragment = fragment;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.course_list_base, container, false); // layout 작성해서 넣어줄 곳
        if (savedInstanceState == null) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content, CourseListFragment.newInstance(mPage)).commit();
        }
        return view;

        tv[0] = (TextView) findViewById(R.id.TextView01);
        tv[1] = (TextView) findViewById(R.id.TextView02);

        new ProcessNetworkWeatherBaseInfoThread().execute(null, null, null);
        return view;
    }

    public class ProcessNetworkWeatherBaseInfoThread extends AsyncTask<Void, Void, String> {

        final static String weatherURL = "http://www.kma.go.kr/weather/forecast/mid-term-rss3.jsp?stnId=109";

        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            String content = executeClient();
            return content;
        }

        protected void onPostExecute(String result) {
            try {
                XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = parserFactory.newPullParser();
                parser.setInput(weatherURL.openStream(), "utf-8");

                while(parser.next() != XmlPullParser.END_DOCUMENT) {
                    String name = parser.getName();
                    if((name != null) && name.equals("data")) {
                        namet = parser.getAttributeValue("", "seq"); // < > 사이의 seq가 있는 것을 추출
                    }
                    // 현재날씨 발표된 시간
                    if((name != null) && name.equals("pubData")) {
                        attrnewswrites = parser.nextText();
                    }
                    // 날씨 지역
                    if((name != null) && name.equals("category")) {
                        category = parser.nextText();
                    }
                    tv[1].setTextColor(Color.BLACK);
                    tv[1].setText("날씨발표 : " + attrnewswrites + "\n지역 : " + category);

                    String parts = "0"; // 동네에서 가져올 부분 선택
                    if((namet != null) && namet.equals(parts)) {
                        //현재 시간
                        if((name != null) && name.equals("hour")) {
                            now_hour = parser.nextText();
                        }
                        //현재 온도
                        if((name != null) && name.equals("temp")) {
                            now_degree = parser.nextText();

                            tv[3].setTextSize(30);
                            tv[3].setText(Html.fromHtml("<font color=\"black\">"
                                    + "온도 : " + "</font>"
                                    + "<font color=\"black\">"
                                    + String.format("%.0f", Double.valueOf(now_degree)) + "&nbsp; 도" + "</font>"));
                        }
                        // 현재 날씨
                        if((name != null) && name.equals("wfKor")) {
                            wfKor = parser.nextText();
                            // tv[2].setText(wfKor);
                            // 현재날씨 상태 이미지
                            if(wfKor.equals("맑음")) {
                                iv[0].setBackgroundResource(R.drawable.sun);
                            } else if(wfKor.equals("구름조금")) {
                                iv[0].setBackgroundResource(R.drawable.low_cloud);
                            } else if(wfKor.equals("구름많음")) {
                                iv[0].setBackgroundResource(R.drawable.many_cloud);
                            } else if(wfKor.equals("흐림")) {
                                iv[0].setBackgroundResource(R.drawable.cloud);
                            } else if(wfKor.equals("비")) {
                                iv[0].setBackgroundResource(R.drawable.rain);
                            } else if(wfKor.equals("눈/비")) {
                                iv[0].setBackgroundResource(R.drawable.snowrain);
                            } else if(wfKor.equals("눈")) {
                                iv[0].setBackgroundResource(R.drawable.rain);
                            } else {
                                iv[0].setBackgroundResource(R.drawable.kisang);
                            }
                        }
                        // 현재 바람
                        if((name != null) && name.equals("wdKor")) {
                            wdKor = parser.nextText();
                            tv[4].setText(Html.fromHtml("<font color=\"black\">"
                                    + "바람 : " + wdKor + "풍"
                                    + String.format("%.ㅣf", Double.valueOf(ws)) + " m/s" + "</font>"));
                        }
                        // 풍속
                        if((name != null) && name.equals("ws")) {
                            ws = parser.nextText();
                        }
                        // 현재습도
                        if((name != null) && name.equals("reh")) {
                            reh = parser.nextText();
                        }
                        // 현재 강수확률
                        if((name != null) && name.equals("pop")) {
                            pop = parser.nextText();
                        }
                        tv[5].setTextColor(Color.BLACK);
                        tv[5].setText("강수확룰 : " + pop + "%" + "\n습도 : " + reh);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("second try", "error");
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

            HttpGet httpGet = new HttpGet(weatherURL);
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
}
