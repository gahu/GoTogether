package kr.go.seoul.seoultrail;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nethru.android.applogging.NethruAppLoggingException;
import com.nethru.android.applogging.WLAppTrackLogging;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.overlay.NMapPOIitem;

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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;

import kr.go.seoul.seoultrail.Common.CustomProgressDialog;
import kr.go.seoul.seoultrail.Common.DBHelper;
import kr.go.seoul.seoultrail.Common.FontUtils;
import kr.go.seoul.seoultrail.Common.PublicDefine;
import kr.go.seoul.seoultrail.Common.StampLocation;
import kr.go.seoul.seoultrail.GPS.GPSProvider;

public class MainActivity extends TabActivity {

    private TabHost tabHost;
    private ImageView header_image;

    final static String MAIN = "1";
    final static String COURSE = "2";
    final static String COMMUNITY = "3";
    final static String CAMERA = "4";

    private String lastTabTag = "1";     // 카페 커뮤니티 넘어 가는 부분 현재 참조 X

    private WebView notiWebView;
    private ArrayList<String> notiList = new ArrayList<>();
    private int notiIDX = -1;
    int cnt = 0;

    // 여기서부터는 17.10.24 수정 부분
//    private CheckBox btnMore;
    //    private LinearLayout gpsBtnLayout;
//    private TextView checkMyLocation;
//    private TextView checkNMapMyLocation;

    private GPSProvider gps;

    private Boolean stampOn = false;
    private Boolean completeAllCourse = false;
    private String lastStampInfo = "";

    private CustomProgressDialog dialogLoading;
    private AlertDialog alert = null;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PublicDefine.mainActivity = this;
        initView();
        /*modify 여기 부분을 initGPS() 주석 처리하니까 처음실행될때 위치 정보를 가져오지 않는다
          modify 지도에서 접근하였을 시에 GPS가 처리 되는것을 알 수 있다. 자기 정보를 눌렀을 경우
        */
        initGPS();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        FontUtils.getInstance(this).setGlobalFont(getWindow().getDecorView());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (gps != null) {
            gps.startGetMyLoation();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onDestroy() {
        if (gps != null) {
            gps.removeUpdate();
        }
        super.onDestroy();
    }

    private void initGPS() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        gps = new GPSProvider(MainActivity.this, mlocManager); //오브젝트 생성
        gps.setMlocListener(mlocListener);
    }

    LocationListener mlocListener = new LocationListener() { // 위치와 관련된 디바이스의 다양한 Event에 따른 리스너를 정의해주어야 한다.
        public void onLocationChanged(Location myLocation) {  // 사용자의 위치가 변할때마다 그를 감지해내는 메소드
            checkLocation(myLocation.getLatitude(), myLocation.getLongitude());
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    private void checkLocation(Double latitude, Double longitude) {
        float[] t;
        int completeCourseNo = 0;
        ArrayList<StampLocation> locationList = DBHelper.getInstance(MainActivity.this).getNoCompleteStampLocation();
        for (int i = 0; i < locationList.size(); i++) {
            t = new float[2];
            Location.distanceBetween(Double.parseDouble(locationList.get(i).getCOT_COORD_Y()), Double.parseDouble(locationList.get(i).getCOT_COORD_X())
                    , latitude, longitude, t);
            if (t[0] <= 100) {
                stampOn = true;
                DBHelper.getInstance(MainActivity.this).setCompleteStamp(locationList.get(i));
                completeAllCourse = DBHelper.getInstance(MainActivity.this).getCompleteAllCourse(locationList.get(i));
                completeCourseNo = locationList.get(i).getCourseNo();
            }
        }
        if (stampOn) {
            String msg = "";
            if (completeAllCourse) {
                setInformationTourInfo();
                completeAllCourse = false;
                msg = "모든코스를 완주하셨습니다.";
            } else {
                setPagerToCompleteStamp(completeCourseNo + -1);
                msg = "스탬프를 획득 하셨습니다.";
            }
            sendNotification(msg);
            stampOn = false;
        }
    }

    private void sendNotification(String msg) {
        String result = "";
        int id = (int) System.currentTimeMillis();
        try {
            result = URLDecoder.decode(msg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.e("NTsys", "메세지 오류 발생");
        }
        PendingIntent contentIntent = PendingIntent.getActivity(this, id,
                new Intent(this, IntroActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP),
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getResources().getString(R.string.app_name))
                .setContentText(result)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(contentIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(id, notificationBuilder.build());
    }

    public void setCheckNMapMyLocation(NGeoPoint myLocation) {
        checkLocation(myLocation.getLatitude(), myLocation.getLongitude());
    }

    private void initView() {
        header_image = (ImageView) findViewById(R.id.header_image);

        setupTabHost();

        tabHost.getTabWidget().setDividerDrawable(null);
        setupTab(MAIN);
        setupTab(COURSE);
        setupTab(COMMUNITY);
        setupTab(CAMERA);
        tabHost.setCurrentTab(0);           // MAIN 부분이 첫번째로 들어가기위함
        // 혹시나 setCurrentTab을 다른 탭으로 설정해버리면 제대로 된 값이 이벤트가 발생하지 않는다.


        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tag) {

                String loggingAction = "";

                if (tag.equals(MAIN)) {
                    //header_image.setImageResource(R.drawable.bg01_main);
                    lastTabTag = MAIN;
                    loggingAction = "MAIN";
                    increaseHeaderImage();
                } else if (tag.equals(COURSE)) {
                    //header_image.setImageResource(R.drawable.bg01_course);
                    lastTabTag = COURSE;
                    loggingAction = "COURSE";
                    increaseHeaderImage();
                } else if (tag.equals(COMMUNITY)) {
                    //header_image.setImageResource(R.drawable.bg01_community);
                    lastTabTag = COMMUNITY;
                    loggingAction = "COMMUNITY";
                    increaseHeaderImage();
                } else if (tag.equals(CAMERA)) {
                    lastTabTag = CAMERA;
                    loggingAction = "CAMERA";
                    increaseHeaderImage();
                }

                try {
                    WLAppTrackLogging.logging(MainActivity.this, PublicDefine.appLoggingActionKey + loggingAction);
                } catch (NethruAppLoggingException e) {
                    Log.e("NTsys", "AppTrackLogging 예외 발생");
                } catch (Exception e) {
                    Log.e("NTsys", "AppTrackLogging 예외 발생");
                }
            }
        });

        notiWebView = (WebView) findViewById(R.id.webview);
        if (notiList != null) {
            notiList.clear();
        } else {
            notiList = new ArrayList<>();
        }
        new ProcessNetworkImportantNoticeList().execute(null, null, null);
    }

    private void reduceHeaderImage() {
        int temp;
        int big_image_size = 450;
        int small_image_size = 300;

        header_image.getLayoutParams().height = small_image_size;
        header_image.requestLayout();
    }

    private void increaseHeaderImage() {
        int temp;
        int big_image_size = 500;
        int small_image_size = 300;

        temp = header_image.getLayoutParams().height;
        if(temp == small_image_size) {
            header_image.getLayoutParams().height = big_image_size;
            header_image.requestLayout();
        }
    }

    private void setupTabHost() {
        // xml resource에서 TabHost를 받아왔다면 setup()을 수행해주어야함.
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();
    }

    private void setupTab(final String tag) {
        View tabview = createTabView(tabHost.getContext(), tag);

        // TabSpec은 공개된 생성자가 없으므로 직접 생성할 수 없으며, TabHost의 newTabSpec메서드로 생성
        TabSpec setContent = tabHost.newTabSpec(tag).setIndicator(tabview);


        // 여기부분은탭 눌렀을경우 어떻게 동작할지 결정하는 부분이다. 그래서 MenuAcitivy로 넣어주었다.
        if (tag.equals(MAIN)) {
            setContent.setContent(new Intent(this, Menu_Connection.class));
        }
        else if (tag.equals(COURSE)) {
            setContent.setContent(new Intent(this, Course.class));
        }
        else if (tag.equals(COMMUNITY)) {
            setContent.setContent(new Intent(this, Community.class));
        }
        else if (tag.equals(CAMERA)) {
            setContent.setContent(new Intent(this, Camera.class));
        }
        tabHost.addTab(setContent);

    }


    private static View createTabView(final Context context, final String text) {
        // layoutinflater를 이용해 xml 리소스를 읽어와 tabview를 생성
        View view = LayoutInflater.from(context).inflate(R.layout.tab_widget_footer_custom, null);
        ImageView img;

        img = (ImageView) view.findViewById(R.id.tabs_image);
        if (text.equals(MAIN)) {
            img.setImageResource(R.drawable.btn01_main_on);
        } else if (text.equals(COURSE)) {
            img.setImageResource(R.drawable.btn02_course_on);
        } else if (text.equals(COMMUNITY)) {
            img.setImageResource(R.drawable.btn03_community_on);
        } else if (text.equals(CAMERA)) {
            img.setImageResource(R.drawable.btn04_camera_on);
        }
        return view;
    }

    public void setInformationTourInfo() {
        if (alert != null && alert.isShowing()) {
            alert.cancel();
            alert = null;
        }
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(this);
        alert_confirm.setMessage(FontUtils.getInstance(this).typeface("모든코스를 완주하셨습니다.\n완주 인증서 발급내용을 확인하시려면 확인 버튼을 선택해주세요.")).setCancelable(false).setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tabHost.setCurrentTab(1);
                        //PublicDefine.information.setPager(1);
                        // #2 인포메이션부분 띄우지 않기 위해서 주석처리함.


                    }
                }).setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alert = alert_confirm.create();
        final Typeface mTypeface = Typeface.createFromAsset(this.getAssets(), "arita_bold.ttf");
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alert.getButton(Dialog.BUTTON_POSITIVE).setTypeface(mTypeface);
                alert.getButton(Dialog.BUTTON_NEGATIVE).setTypeface(mTypeface);
            }
        });
        alert.show();

    }

    public void setPagerToCompleteStamp(final int courseNo) {
        if (alert != null && alert.isShowing()) {
            alert.cancel();
            alert = null;
        }
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(this);
        alert_confirm.setMessage(FontUtils.getInstance(this).typeface("스탬프를 획득 하셨습니다.\n스탬프 획득내용을 확인하시려면 확인 버튼을 선택해주세요.")).setCancelable(false).setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tabHost.setCurrentTab(0);
                        PublicDefine.course.setPagerToStamp(courseNo);
                    }
                }).setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alert = alert_confirm.create();
        final Typeface mTypeface = Typeface.createFromAsset(this.getAssets(), "arita_bold.ttf");
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alert.getButton(Dialog.BUTTON_POSITIVE).setTypeface(mTypeface);
                alert.getButton(Dialog.BUTTON_NEGATIVE).setTypeface(mTypeface);
            }
        });
        alert.show();
    }

    public void showProgressDialog() {
        if (dialogLoading == null) {
            dialogLoading = CustomProgressDialog.show(this, "", "");
            dialogLoading.setCancelable(false);
        }
        if (dialogLoading != null && dialogLoading.isShowing() == false) {
            dialogLoading.show();
        }
    }

    public void cancelProgressDialog() {
        if (dialogLoading != null && dialogLoading.isShowing() == true) {
            dialogLoading.cancel();
        }
    }

    public void showPointListItem(final ArrayList<String[]> cotCoordList, final View view) {
        if (alert != null && alert.isShowing()) {
            alert.cancel();
            alert = null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(new SpannableString[]{FontUtils.getInstance(this).typeface("지도에서 보기"), FontUtils.getInstance(this).typeface("리스트 보기")}, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 1) {
                    PublicDefine.courseMapFragment.showPointList(cotCoordList);
                }
                PublicDefine.courseMapFragment.setShowPointPin();
                dialog.dismiss();
            }
        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
                ((CheckBox) view).setChecked(false);
            }
        });
        builder.setCancelable(false);
        alert = builder.create();
        final Typeface mTypeface = Typeface.createFromAsset(this.getAssets(), "arita_bold.ttf");
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alert.getButton(Dialog.BUTTON_POSITIVE).setTypeface(mTypeface);
                alert.getButton(Dialog.BUTTON_NEGATIVE).setTypeface(mTypeface);
            }
        });
        alert.show();
    }

    public void notiConfirm(View view) {
        findViewById(R.id.noti_webview).setVisibility(View.GONE);
    }

    public void notiDetail(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("http://gil.seoul.go.kr/m/sub/introduce/notice_view.jsp?" + notiList.get(notiIDX));
        intent.setData(uri);
        startActivity(intent);
    }


    public void showDetailInfo(NMapPOIitem item) {
        new ProcessNetworkPointDetailThread().execute(item.getTag().toString(), null, null);
    }


    public class ProcessNetworkImportantNoticeList extends AsyncTask<Void, Void, String> {
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
                    notiList.add(jsonList.getJSONObject(i).getString("IDX"));
                }
            } catch (Exception e) {
            } finally {
                new ProcessNetworkNoticeList().execute(null, null, null);
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

            HttpGet httpGet = new HttpGet("https://mplatform.seoul.go.kr/api/dule/importantNoticeList.do");
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

    // TODO 여기 부분 네트워크 알림 리스트 가져오는 부분인데 어떻게 처리해볼까나

    public class ProcessNetworkNoticeList extends AsyncTask<Void, Void, String> {
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
                    notiList.add(jsonList.getJSONObject(i).getString("IDX"));
                }
                Collections.sort(notiList);
            } catch (Exception e) {
            } finally {
                notiIDX = 0;
                if (notiList.size() > 0) {
                    notiIDX = notiList.size() - 1;
                }
                new ProcessNetworkNotiThread().execute(notiList.get(notiIDX));
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

            HttpGet httpGet = new HttpGet("https://mplatform.seoul.go.kr/api/dule/importantNoticeList.do");
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

    //TODO 네트워크 알림 쓰레드 부분 구현
    public class ProcessNetworkNotiThread extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String content = executeClient(strings);
            return content;
        }

        protected void onPostExecute(String result) {
            String main = result.toString();
            String noti = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><title>Lorem Ipsum</title></head><bod>";
            try {
                JSONObject jsonMain = new JSONObject(main);
                JSONObject data = jsonMain.getJSONObject("data");
                noti = noti + data.getString("ORGN_CONTENTS");
            } catch (JSONException e) {
            }

            noti = noti + "</body></html>";

            notiWebView.loadData(noti, "text/html; charset=UTF-8", null);
        }

        // 실제 전송하는 부분
        public String executeClient(String[] strings) {
            HttpResponse response = null;
            ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();

            // 연결 HttpClient 객체 생성
            HttpClient client = new DefaultHttpClient();

            // 객체 연결 설정 부분, 연결 최대시간 등등
            HttpParams params = client.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 30000);
            HttpConnectionParams.setSoTimeout(params, 30000);

            HttpGet httpGet = new HttpGet("https://mplatform.seoul.go.kr/api/dule/noticeDetail.do?" + strings[0]);
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

    // TODO 네트워크 세부적인 쓰레드 과정 구현 부분

    public class ProcessNetworkPointDetailThread extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String content = executeClient(strings);
            return content;
        }

        protected void onPostExecute(String result) {
            resutData(result);
        }

        // 실제 전송하는 부분
        public String executeClient(String[] strings) {
            HttpResponse response = null;
            ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();

            // 연결 HttpClient 객체 생성
            HttpClient client = new DefaultHttpClient();

            // 객체 연결 설정 부분, 연결 최대시간 등등
            HttpParams params = client.getParams();
            HttpConnectionParams.setConnectionTimeout(params, 30000);
            HttpConnectionParams.setSoTimeout(params, 30000);

            HttpGet httpGet = new HttpGet("http://map.seoul.go.kr/smgis/apps/poi.do?cmd=getNewContentsDetail&key=" + PublicDefine.serviceSmgisKey + "&theme_id=100211&conts_id=" + strings[0]);
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

    private void resutData(String result) {
        String main = result.toString();
        JSONObject jsonMain = null;
        try {
            jsonMain = new JSONObject(main);
            JSONArray jsonBody = jsonMain.getJSONArray("body");

            if (jsonBody.length() > 0) {
                LayoutInflater layoutInflater = getLayoutInflater();
                View dilog = layoutInflater.inflate(R.layout.custom_point_detail_layout, null);
                ((TextView) dilog.findViewById(R.id.point_detail_name)).setText(Html.fromHtml(jsonBody.getJSONObject(0).getString("COT_CONTS_NAME")).toString());
                ((TextView) dilog.findViewById(R.id.point_detail_content)).setText(jsonBody.getJSONObject(0).getString("COT_VALUE_01"));
                if (!jsonBody.getJSONObject(0).getString("COT_IMG_MAIN_URL").equals("")) {
                    String imgUrl = "";
                    if (jsonBody.getJSONObject(0).getString("COT_IMG_MAIN_URL").startsWith("/")) {
                        imgUrl = PublicDefine.imageHostUrl + jsonBody.getJSONObject(0).getString("COT_IMG_MAIN_URL");
                    } else {
                        imgUrl = jsonBody.getJSONObject(0).getString("COT_IMG_MAIN_URL");
                    }
                    Glide.with(MainActivity.this).load(imgUrl).into(((ImageView) dilog.findViewById(R.id.point_detail_img)));
                } else {
                    (dilog.findViewById(R.id.point_detail_img_layout)).setVisibility(View.GONE);
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(dilog);
                builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setCancelable(false);
                alert = builder.create();
                final Typeface mTypeface = Typeface.createFromAsset(this.getAssets(), "arita_bold.ttf");
                alert.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        alert.getButton(Dialog.BUTTON_POSITIVE).setTypeface(mTypeface);
                        alert.getButton(Dialog.BUTTON_NEGATIVE).setTypeface(mTypeface);
                    }
                });
                alert.show();
            }
        } catch (JSONException e) {
        }

    }

    public void goMobileWeb(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("http://gil.seoul.go.kr/m/course/dulae_gil_list.jsp?course=2000");
        intent.setData(uri);
        startActivity(intent);
    }

    public void showMain(View view) {
        tabHost.setCurrentTab(1);
        //PublicDefine.information.setPager(0);
        // # 인포메이션 부분 없애기 위해서 주석 처리 하였음
    }


}
