package kr.go.seoul.seoultrail.WeatherInfo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
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
    static String midweatherURL = "http://www.kma.go.kr/weather/forecast/mid-term-rss3.jsp?stnId=109";
    static String shortweatherURL = "http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=1117066000"; // 서울 가운데 동네 rss 가져와서 파싱

    // 현재 날씨 변경할 텍스트랑 이미지뷰
    private static TextView currentwf;
    private static ImageView img_currentwf;
    private static TextView highlow;
    private static TextView currentday;
    private static TextView temperature;

    // 예보 날씨 변경할 텍스트랑 이미지뷰
    private static TextView[] plusDayText = new TextView[7];
    private static ImageView[] plusDayImage = new ImageView[7];

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

        currentwf = (TextView) view.findViewById(R.id.icon_wf);
        img_currentwf = (ImageView) view.findViewById(R.id.icon_background);
        highlow = (TextView) view.findViewById(R.id.icon_lowhigh);
        currentday = (TextView) view.findViewById(R.id.weather_time);
        temperature = (TextView) view.findViewById(R.id.weather_temp);

        plusDayText[0] = (TextView) view.findViewById(R.id.plus_oneday);
        plusDayText[1] = (TextView) view.findViewById(R.id.plus_twoday);
        plusDayText[2] = (TextView) view.findViewById(R.id.plus_threeday);
        plusDayText[3] = (TextView) view.findViewById(R.id.plus_fourday);
        plusDayText[4] = (TextView) view.findViewById(R.id.plus_fiveday);
        plusDayText[5] = (TextView) view.findViewById(R.id.plus_sixday);
        plusDayText[6] = (TextView) view.findViewById(R.id.plus_sevenday);

        plusDayImage[0] = (ImageView) view.findViewById(R.id.img_oneday);
        plusDayImage[1] = (ImageView) view.findViewById(R.id.img_twoday);
        plusDayImage[2] = (ImageView) view.findViewById(R.id.img_threeday);
        plusDayImage[3] = (ImageView) view.findViewById(R.id.img_fourday);
        plusDayImage[4] = (ImageView) view.findViewById(R.id.img_fiveday);
        plusDayImage[5] = (ImageView) view.findViewById(R.id.img_sixday);
        plusDayImage[6] = (ImageView) view.findViewById(R.id.img_sevenday);

        // UI Thread 부분, Handler를 이용해서 해봐야 하나?
        // AsyncTask를 사용하면 onPostExecute()에서 메인 UI Thread가 접근해서 하기 때문에 변경 가능한걸로 아는데..
        // AsyncTask는 메인Thread에서 한번만 사용 가능
        // 하나만 가능하다.
        new ProcessNetworkWeatherBaseInfoThread().execute();

        //new ProcessNetworkWeatherBaseInfoThread().execute();

        return view;
    }

    public static void setShortWeather(String weather) {
        switch(weather) {
            case "맑음" :
                img_currentwf.setImageResource(R.drawable.icon_weather_sunny_w);
                break;
            case "흐림" :
                img_currentwf.setImageResource(R.drawable.icon_weather_blur_w);
                break;
            case "눈" :
                img_currentwf.setImageResource(R.drawable.icon_weather_snow_w);
                break;
            case "비" :
                img_currentwf.setImageResource(R.drawable.icon_weather_rain_w);
                break;
            case "구름조금" :
                img_currentwf.setImageResource(R.drawable.icon_weather_cloudy_w);
                break;
            case "구름많음" :
                img_currentwf.setImageResource(R.drawable.icon_weather_cloudy_w);
                break;
            case "천둥번개" :
                img_currentwf.setImageResource(R.drawable.icon_weather_lightning_w);
                break;
            case "소나기" :
                img_currentwf.setImageResource(R.drawable.icon_weather_shower_w);
                break;
            default :
                img_currentwf.setImageResource(R.drawable.icon_weather_default_w);
                break;
        }
    }

    public static void setMidWeather(int w, String weather) {
        switch(weather) {
            case "맑음" :
                plusDayImage[w].setImageResource(R.drawable.icon_weather_sunny);
                break;
            case "흐림" :
                plusDayImage[w].setImageResource(R.drawable.icon_weather_blur);
                break;
            case "눈" :
                plusDayImage[w].setImageResource(R.drawable.icon_weather_snow);
                break;
            case "비" :
                plusDayImage[w].setImageResource(R.drawable.icon_weather_rain);
                break;
            case "구름조금" :
                plusDayImage[w].setImageResource(R.drawable.icon_weather_cloudy);
                break;
            case "구름많음" :
                plusDayImage[w].setImageResource(R.drawable.icon_weather_cloudy);
                break;
            case "천둥번개" :
                plusDayImage[w].setImageResource(R.drawable.icon_weather_lightning);
                break;
            case "소나기" :
                plusDayImage[w].setImageResource(R.drawable.icon_weather_shower);
                break;
            default :
                plusDayImage[w].setImageResource(R.drawable.icon_weather_default);
                break;
        }
    }

    // 기상청 rss 단기예보 정보 파일을 다운로드 하는 부분
    public class ProcessNetworkWeatherBaseInfoThread extends AsyncTask<URL, Integer, Long> {
        ArrayList<WeatherInfo> shortWeathers = new ArrayList<WeatherInfo>();
        ArrayList<WeatherInfo> longWeathers = new ArrayList<WeatherInfo>();

        protected Long doInBackground(URL... urls) {
            OkHttpClient client = new OkHttpClient();

            Request request1 = new Request.Builder().url(shortweatherURL).build();
            Request request2 = new Request.Builder().url(midweatherURL).build();

            Response response = null;

            try {
                response = client.newCall(request1).execute();
                parseShortXML(response.body().string());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                response = client.newCall(request2).execute();
                parseWeekXML(response.body().string());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        // 이 부분에서 비교해서 넣어줌
        protected void onPostExecute (Long result) {
            String data = "";
            long time = System.currentTimeMillis();
            SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            data = dayTime.format(new Date(time));

            for(int i=0; i<shortWeathers.size(); i++) {
                if(shortWeathers.get(i).getDay() == "1" && shortWeathers.get(i).getHour() == "3") { // 오늘 날씨
                    currentwf.setText(shortWeathers.get(i).getWfKor());
                    highlow.setText("▽" + shortWeathers.get(i).getTmn() + " △" + shortWeathers.get(i).getTmx());
                    currentday.setText(data);
                    // 파싱정보도 들어가 있고, 기본설정 데이터도 토스트 되는데 왜 정보가 안으로 안들어가지지?
                    temperature.setText(shortWeathers.get(i).getTemp());
                    setShortWeather(shortWeathers.get(i).getWfKor());
                } else if(shortWeathers.get(i).getDay() == "2" && shortWeathers.get(i).getHour() == "3") { // 내일 날씨
                    plusDayText[0].setText(shortWeathers.get(i).getWfKor());
                    setMidWeather(0, shortWeathers.get(i).getWfKor());
                }
            }
            String date = "";
            String wf = ""; // 날씨 이미지


            for(int i=1; i<longWeathers.size(); i++) {
                date = longWeathers.get(i).getTmEf();
                wf = longWeathers.get(i).getWf();

                // 비교해서 시간 12:00제외
                if(date.substring(11, 13) == "00") {
                    // 시간이 00이면 날짜만 추출해서 들어간다.
                    date = date.substring(5, 7) + "/" + date.substring(8, 10);
                    plusDayText[i].setText(date);
                    // 이미지 설정
                    setMidWeather(i, wf);
                } else continue;
            }

            Toast mToast = Toast.makeText(getActivity(), wf, Toast.LENGTH_LONG);
            mToast.show();
        }

        void parseShortXML(String xml) {
            try {
                String tagName = "";
                boolean onnextDay = false;
                boolean onThree = false;
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

                        /*if (tagName.equals("city")) {
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
                        }*/

                        // 태그명이 body면 그 하위 데이터 풀파싱
                        if (tagName.equals("body")) {
                            shortWeathers.add(new WeatherInfo());
                            onEnd = false;
                            isItemTag1 = true;
                        }
                    } else if (eventType == XmlPullParser.TEXT && isItemTag1) {
                        if (tagName.equals("hour") && !onThree) {
                            shortWeathers.get(i).setHour(parser.getText());
                            onThree = true;
                        }
                        if (tagName.equals("day") && !onnextDay) {
                            shortWeathers.get(i).setDay(parser.getText());
                            onnextDay = true;
                        }
                        if (tagName.equals("temp") && !onTmEf) {
                            shortWeathers.get(i).setTemp(parser.getText());
                            onTmEf = true;
                        }
                        if (tagName.equals("wfKor") && !onWf) {
                            shortWeathers.get(i).setWfKor(parser.getText());
                            onWf = true;
                        }
                        if (tagName.equals("tmn") && !onTmn) {
                            shortWeathers.get(i).setTmn(parser.getText());
                            onTmn = true;
                        }
                        if (tagName.equals("tmx") && !onTmx) {
                            shortWeathers.get(i).setTmx(parser.getText());
                            onTmx = true;
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        if (tagName.equals("reliability") && onEnd == false) {
                            i++;
                            onnextDay = false;
                            onThree = false;
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

        void parseWeekXML(String xml) {
            try {
                String tagName = "";
                boolean onCity = false;
                boolean onTmEf = false;
                boolean onWf = false;
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
                    } else if (eventType == XmlPullParser.END_TAG) {
                        if (tagName.equals("reliability") && onEnd == false) {
                            i++;
                            onTmEf = false;
                            onWf = false;
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
/*
    // 기상청 rss 중기예보 정보 파일을 다운로드 하는 부분
    public class ProcessNetworkWeatherBaseInfoThread extends AsyncTask<URL, Integer, Long> {
        ArrayList<WeatherInfo> longWeathers = new ArrayList<WeatherInfo>();

        protected Long doInBackground(URL... urls) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder().url(midweatherURL).build();

            Response response = null;

            try {
                response = client.newCall(request).execute();
                parseWeekXML(response.body().string());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        // 이 부분에서 비교해서 넣어줌
        protected void onPostExecute (Long result) {
            String data = ""; // 날짜 넣어줄 data
            String wf = ""; // 날씨 이미지


            for(int i=1; i<longWeathers.size(); i++) {
                data = longWeathers.get(i).getTmEf();
                wf = longWeathers.get(i).getWf();

                // 비교해서 시간 12:00제외
                if(data.substring(11, 13) == "00") {
                    // 시간이 00이면 날짜만 추출해서 들어간다.
                    data = data.substring(5, 7) + "/" + data.substring(8, 10);
                    plusDayText[i].setText(data);
                    // 이미지 설정
                    setMidWeather(i, wf);
                } else continue;
            }

            Toast mToast = Toast.makeText(getActivity(),data.substring(11, 13), Toast.LENGTH_SHORT);
            mToast.show();

        }

        void parseWeekXML(String xml) {
            try {
                String tagName = "";
                boolean onCity = false;
                boolean onTmEf = false;
                boolean onWf = false;
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
                    } else if (eventType == XmlPullParser.END_TAG) {
                        if (tagName.equals("reliability") && onEnd == false) {
                            i++;
                            onTmEf = false;
                            onWf = false;
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
    }*/
}
