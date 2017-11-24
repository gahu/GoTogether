package kr.go.seoul.seoultrail.WeatherInfo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;

import kr.go.seoul.seoultrail.Common.PublicDefine;
import kr.go.seoul.seoultrail.R;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by bgg89 on 2017-11-23.
 */

public class WeatherFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private static final String TAG = "WeatherParser";
    static String weatherURL = "http://www.kma.go.kr/weather/forecast/mid-term-rss3.jsp?stnId=109";
    static String course1URL = "";
    static String course2URL = "";
    static String course3URL = "";
    static String course4URL = "";
    static String course5URL = "";
    static String course6URL = "";
    static String course7URL = "";
    static String course8URL = "";

    private TextView[] courseText = new TextView[8];
    private ImageView[] courseImgView = new ImageView[8];

    ImageView iv_weather;

    public static WeatherFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, position);
        WeatherFragment fragment = new WeatherFragment();
        PublicDefine.weatherFragment = fragment;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_fragment, container, false); // layout 작성해서 넣어줄 곳

        courseText[0] = (TextView) view.findViewById(R.id.course01_weather);
        courseText[1] = (TextView) view.findViewById(R.id.course02_weather);
        courseText[2] = (TextView) view.findViewById(R.id.course03_weather);
        courseText[3] = (TextView) view.findViewById(R.id.course04_weather);
        courseText[4] = (TextView) view.findViewById(R.id.course05_weather);
        courseText[5] = (TextView) view.findViewById(R.id.course06_weather);
        courseText[6] = (TextView) view.findViewById(R.id.course07_weather);
        courseText[7] = (TextView) view.findViewById(R.id.course08_weather);

        iv_weather = (ImageView) view.findViewById(R.id.iv_weather);

        courseImgView[0] = (ImageView) view.findViewById(R.id.coure01_weather_image);
        courseImgView[1] = (ImageView) view.findViewById(R.id.coure02_weather_image);
        courseImgView[2] = (ImageView) view.findViewById(R.id.coure03_weather_image);
        courseImgView[3] = (ImageView) view.findViewById(R.id.coure04_weather_image);
        courseImgView[4] = (ImageView) view.findViewById(R.id.coure05_weather_image);
        courseImgView[5] = (ImageView) view.findViewById(R.id.coure06_weather_image);
        courseImgView[6] = (ImageView) view.findViewById(R.id.coure07_weather_image);
        courseImgView[7] = (ImageView) view.findViewById(R.id.coure08_weather_image);

        new ProcessNetworkWeatherBaseInfoThread().execute();

        return view;
    }

    // 기상청 rss 정보 파일을 다운로드 하는 부분
    public class ProcessNetworkWeatherBaseInfoThread extends AsyncTask<URL, Integer, Long> {
        ArrayList<WeatherInfo> longWeathers = new ArrayList<WeatherInfo>();

        protected Long doInBackground(URL... urls) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(weatherURL)
                    .build();

            Response response = null;

            try {
                response = client.newCall(request).execute();
                parseWeekXML(response.body().string());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Long result) {
            String data = "";

            for(int i=0; i<longWeathers.size(); i++) {
                data += "날짜 : " + longWeathers.get(i).getTmEf() + "\n" +
                        "현재 날씨 : " + longWeathers.get(i).getTmn() + "\n" +
                        "최저온도 : " + longWeathers.get(i).getTmx() + "\n" +
                        "최고온도 : " + longWeathers.get(i).getWf() + "\n" +
                        "-------------------------------------------------\n";
            }

            courseText[0].setText(data);
        }

        void parseWeekXML(String xml) {
            try {
                String tagName = "";
                boolean onCity = false;
                boolean onTmEf = false;
                boolean onWf = false;
                boolean onTmn = false;
                boolean onTmx = false;
                boolean onEnd = false;
                boolean isItemTag1 = false;
                int i = 0;

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();

                parser.setInput(new StringReader(xml));

                int eventType = parser.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        tagName = parser.getName();

                        if (tagName.equals("city")) {
                            eventType = parser.next();

                            if (parser.getText().equals("서울")) {    // 파싱하고 싶은 지역 이름을 쓴다
                                onCity = true;
                            } else {
                                if (onCity) { // 이미 parsing을 끝냈을 경우
                                    break;
                                } else {        // 아직 parsing을 못했을 경우
                                    onCity = false;
                                }
                            }
                        }

                        if (tagName.equals("data") && onCity) {
                            longWeathers.add(new WeatherInfo());
                            onEnd = false;
                            isItemTag1 = true;
                        }
                    } else if (eventType == XmlPullParser.TEXT && isItemTag1 && onCity) {
                        if (tagName.equals("tmEf") && !onTmEf) {
                            longWeathers.get(i).setTmEf(parser.getText());
                            onTmEf = true;
                        }
                        if (tagName.equals("wf") && !onWf) {
                            longWeathers.get(i).setWf(parser.getText());
                            onWf = true;
                        }
                        if (tagName.equals("tmn") && !onTmn) {
                            longWeathers.get(i).setTmn(parser.getText());
                            onTmn = true;
                        }
                        if (tagName.equals("tmx") && !onTmx) {
                            longWeathers.get(i).setTmx(parser.getText());
                            onTmx = true;
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        if (tagName.equals("reliability") && onEnd == false) {
                            i++;
                            onTmEf = false;
                            onWf = false;
                            onTmn = false;
                            onTmx = false;
                            isItemTag1 = false;
                            onEnd = true;
                        }
                    }

                    eventType = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
