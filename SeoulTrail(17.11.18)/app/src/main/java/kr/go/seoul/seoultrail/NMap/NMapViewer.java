/*
 * NMapViewer.java $version 2010. 1. 1
 *
 * Copyright 2010 NHN Corp. All rights Reserved.
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package kr.go.seoul.seoultrail.NMap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.Toast;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.nmapmodel.NMapPlacemark;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.maps.overlay.NMapPathData;
import com.nhn.android.maps.overlay.NMapPathLineStyle;
import com.nhn.android.mapviewer.overlay.NMapCalloutCustomOverlay;
import com.nhn.android.mapviewer.overlay.NMapCalloutOverlay;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.nhn.android.mapviewer.overlay.NMapPathDataOverlay;

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

import kr.go.seoul.seoultrail.Common.DBHelper;
import kr.go.seoul.seoultrail.Common.FontUtils;
import kr.go.seoul.seoultrail.Common.PublicDefine;
import kr.go.seoul.seoultrail.Common.StampLocation;
import kr.go.seoul.seoultrail.GPS.GPSProvider;
import kr.go.seoul.seoultrail.R;

/**
 * Sample class for map viewer library.
 *
 * @author kyjkim
 */
public class NMapViewer extends NMapActivity {
    private static final String LOG_TAG = "NMapViewer";
    private static final boolean DEBUG = false;

    // set your Client ID which is registered for NMapViewer library.
    private static final String CLIENT_ID = PublicDefine.nMapClientId;

    private MapContainerView mMapContainerView;

    private NMapView mMapView;
    private NMapController mMapController;

    private static final NGeoPoint NMAP_LOCATION_DEFAULT = new NGeoPoint(126.978371, 37.5666091);
    private static final int NMAP_ZOOMLEVEL_DEFAULT = 11;
    private static final int NMAP_VIEW_MODE_DEFAULT = NMapView.VIEW_MODE_VECTOR;
    private static final boolean NMAP_TRAFFIC_MODE_DEFAULT = false;
    private static final boolean NMAP_BICYCLE_MODE_DEFAULT = false;

    private static final String KEY_ZOOM_LEVEL = "NMapViewer.zoomLevel";
    private static final String KEY_CENTER_LONGITUDE = "NMapViewer.centerLongitudeE6";
    private static final String KEY_CENTER_LATITUDE = "NMapViewer.centerLatitudeE6";
    private static final String KEY_VIEW_MODE = "NMapViewer.viewMode";
    private static final String KEY_TRAFFIC_MODE = "NMapViewer.trafficMode";
    private static final String KEY_BICYCLE_MODE = "NMapViewer.bicycleMode";

    private SharedPreferences mPreferences;

    private NMapOverlayManager mOverlayManager;
    private NMapPathDataOverlay pathDataOverlay;
    private NMapPOIdataOverlay poiDataStampOverlay;
    private static NMapPOIdataOverlay poiDataPointOverlay;
    private NMapPOIdataOverlay poiDataSafePointOverlay;
    private NMapPathDataOverlay pathDataRouteOverlay;
    private NMapPOIdataOverlay poiDataRouteOverlay;

    private ArrayList<String[]> cotCoordList = new ArrayList<String[]>();

    private boolean isCurrentLocation = false;
    private boolean checkStamp = false;
    private boolean checkPoint = false;
    private boolean checkSafePoint = false;
    private boolean checkRoute = false;

    private NMapMyLocationOverlay mMyLocationOverlay;
    private NMapLocationManager mMapLocationManager;
    private NMapCompassManager mMapCompassManager;

    private NMapViewerResourceProvider mMapViewerResourceProvider;

    private NMapPOIdataOverlay mFloatingPOIdataOverlay;
    private NMapPOIitem mFloatingPOIitem;

    private static boolean USE_XML_LAYOUT = true;
    private static int course = -1;

    public static void setCourse(int course) {
        NMapViewer.course = course;
    }

    public static int getCourse() {
        return course;
    }
    private GPSProvider gps;
    LocationListener mlocListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        } // 위치와 관련된 디바이스의 다양한 Event에 따른 리스너를 정의해주어야 한다.
    };
    private void initGPS() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        gps = new GPSProvider(NMapViewer.this, mlocManager); //오브젝트 생성
        gps.setMlocListener(mlocListener);
    }

    private void turnGPSOn(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(!provider.contains("gps")){ //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
            Toast.makeText(this, "GPS TEST 호출 됨",Toast.LENGTH_LONG).show();

        }
    }

    private void turnGPSOff(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(provider.contains("gps")){ //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
            Toast.makeText(this, "GPS off 호출 됨",Toast.LENGTH_LONG).show();
        }
    }
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Handler appStartHandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(NMapViewer.this);
                    alertDialogBuilder.setMessage(FontUtils.getInstance(NMapViewer.this).typeface("GPS가 중단된 상태 입니다.\n환경설정에서 활성화 하시겠습니까?"));
                    alertDialogBuilder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(NMapViewer.this, NMapViewer.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    });
                    alertDialogBuilder.setNegativeButton("설정", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                            dialog.dismiss();
                            finish();
                        }
                    });
                    final AlertDialog alertDialog = alertDialogBuilder.create();
                    final Typeface mTypeface = Typeface.createFromAsset(NMapViewer.this.getAssets(), "arita_bold.ttf");
                    alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            alertDialog.getButton(Dialog.BUTTON_POSITIVE).setTypeface(mTypeface);
                            alertDialog.getButton(Dialog.BUTTON_NEGATIVE).setTypeface(mTypeface);
                        }
                    });
                    alertDialog.show();
                } else {
                    Intent intent = new Intent(NMapViewer.this, NMapViewer.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        };













        //initGPS();
        setContentView(R.layout.course_map_fragment);

        mMapView = (NMapView) findViewById(R.id.naver_map);

        // set a registered Client Id for Open MapViewer Library
        mMapView.setClientId(CLIENT_ID);

        // initialize map view
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.requestFocus();

        // register listener for map state changes
        mMapView.setOnMapStateChangeListener(onMapViewStateChangeListener);
        mMapView.setOnMapViewTouchEventListener(onMapViewTouchEventListener);
        mMapView.setOnMapViewDelegate(onMapViewTouchDelegate);

        mMapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        // use map controller to zoom in/out, pan and set map center, zoom level etc.
        mMapController = mMapView.getMapController();

        // use built in zoom controls
        NMapView.LayoutParams lp = new NMapView.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, NMapView.LayoutParams.BOTTOM_RIGHT);
        mMapView.setBuiltInZoomControls(false, lp);

        // create resource provider
        mMapViewerResourceProvider = new NMapViewerResourceProvider(this);

        // set data provider listener
        super.setMapDataProviderListener(onDataProviderListener);

        // create overlay manager
        mOverlayManager = new NMapOverlayManager(this, mMapView, mMapViewerResourceProvider);
        // register callout overlay listener to customize it.
        mOverlayManager.setOnCalloutOverlayListener(onCalloutOverlayListener);
        // register callout overlay view listener to customize it.
        mOverlayManager.setOnCalloutOverlayViewListener(onCalloutOverlayViewListener);

        // location manager
        mMapLocationManager = new NMapLocationManager(this);
        mMapLocationManager.setOnLocationChangeListener(onMyLocationChangeListener);

        // compass manager
        mMapCompassManager = new NMapCompassManager(this);

        // create my location overlay
        mMyLocationOverlay = mOverlayManager.createMyLocationOverlay(mMapLocationManager, mMapCompassManager);
    }

    @Override
    protected void onStart() {
        //turnGPSOn();
        super.onStart();

    }

    @Override
    protected void onResume() {
//		startMyLocation();
//		if(isCurrentLocation){
//			startMyLocation();
//		}
        getCourseInfo();
        super.onResume();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {

        stopMyLocation();
        // save map view state such as map center position and zoom level.
        saveInstanceState();
        //turnGPSOff();
        super.onDestroy();
    }

    private void startMyLocation() {

        if (mMyLocationOverlay != null) {
            if (!mOverlayManager.hasOverlay(mMyLocationOverlay)) {
                mOverlayManager.addOverlay(mMyLocationOverlay);
            }

            if (mMapLocationManager.isMyLocationEnabled()) {

                mMyLocationOverlay.setCompassHeadingVisible(true);

                mMapCompassManager.enableCompass();

                mMapView.setAutoRotateEnabled(false, false);

                mMapView.postInvalidate();
            } else {
                boolean isMyLocationEnabled = mMapLocationManager.enableMyLocation(true);
                if (!isMyLocationEnabled) {
                    // modify 여기 부분 현재위치 사용할때 설정으로 이동하기 위함

                    Toast.makeText(NMapViewer.this, "위치 정보를 사용하시려면 GPS 허용을 해주십시오.",
                            Toast.LENGTH_LONG).show();

                    Intent goToSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(goToSettings);

                    return;
                } else {
                    mMyLocationOverlay.setCompassHeadingVisible(true);

                    mMapCompassManager.enableCompass();

                    mMapView.setAutoRotateEnabled(false, false);
                }
            }
        }
    }

    private void stopMyLocation() {
        if (mMyLocationOverlay != null) {
            mMapLocationManager.disableMyLocation();

            if (mMyLocationOverlay.isCompassHeadingVisible()) {
                if (mOverlayManager.hasOverlay(mMyLocationOverlay)) {
                    mOverlayManager.removeMyLocationOverlay();
                }
                mMyLocationOverlay.setCompassHeadingVisible(false);

                mMapCompassManager.disableCompass();

                mMapView.setAutoRotateEnabled(false, false);
            }
        }
    }

    /* NMapDataProvider Listener */
    private final OnDataProviderListener onDataProviderListener = new OnDataProviderListener() {

        @Override
        public void onReverseGeocoderResponse(NMapPlacemark placeMark, NMapError errInfo) {

            if (errInfo != null) {
                Toast.makeText(NMapViewer.this, errInfo.toString(), Toast.LENGTH_LONG).show();
                return;
            }

            if (mFloatingPOIitem != null && mFloatingPOIdataOverlay != null) {
                mFloatingPOIdataOverlay.deselectFocusedPOIitem();

                if (placeMark != null) {
                    mFloatingPOIitem.setTitle(placeMark.toString());
                }
                mFloatingPOIdataOverlay.selectPOIitemBy(mFloatingPOIitem.getId(), false);
            }
        }

    };

    /* MyLocation Listener */
    private final NMapLocationManager.OnLocationChangeListener onMyLocationChangeListener = new NMapLocationManager.OnLocationChangeListener() {

        @Override
        public boolean onLocationChanged(NMapLocationManager locationManager, NGeoPoint myLocation) {

            PublicDefine.mainActivity.setCheckNMapMyLocation(myLocation);
            if (mMapController != null) {
                mMapController.animateTo(myLocation);
            }

            return true;
        }

        @Override
        public void onLocationUpdateTimeout(NMapLocationManager locationManager) {

            Toast.makeText(NMapViewer.this, "Your current location is temporarily unavailable.", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onLocationUnavailableArea(NMapLocationManager locationManager, NGeoPoint myLocation) {

            Toast.makeText(NMapViewer.this, "Your current location is unavailable area.", Toast.LENGTH_LONG).show();
        }

    };

    /* MapView State Change Listener*/
    private final NMapView.OnMapStateChangeListener onMapViewStateChangeListener = new NMapView.OnMapStateChangeListener() {

        @Override
        public void onMapInitHandler(NMapView mapView, NMapError errorInfo) {

            if (errorInfo == null) { // success
                // restore map view state such as map center position and zoom level.
                restoreInstanceState();

            } else { // fail
                Toast.makeText(NMapViewer.this, errorInfo.toString(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onAnimationStateChange(NMapView mapView, int animType, int animState) {
        }

        @Override
        public void onMapCenterChange(NMapView mapView, NGeoPoint center) {
        }

        @Override
        public void onZoomLevelChange(NMapView mapView, int level) {
        }

        @Override
        public void onMapCenterChangeFine(NMapView mapView) {

        }
    };

    private final NMapView.OnMapViewTouchEventListener onMapViewTouchEventListener = new NMapView.OnMapViewTouchEventListener() {

        @Override
        public void onLongPress(NMapView mapView, MotionEvent ev) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onLongPressCanceled(NMapView mapView) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSingleTapUp(NMapView mapView, MotionEvent ev) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTouchDown(NMapView mapView, MotionEvent ev) {

        }

        @Override
        public void onScroll(NMapView mapView, MotionEvent e1, MotionEvent e2) {
        }

        @Override
        public void onTouchUp(NMapView mapView, MotionEvent ev) {
            // TODO Auto-generated method stub

        }

    };

    private final NMapView.OnMapViewDelegate onMapViewTouchDelegate = new NMapView.OnMapViewDelegate() {

        @Override
        public boolean isLocationTracking() {
            if (mMapLocationManager != null) {
                if (mMapLocationManager.isMyLocationEnabled()) {
                    return mMapLocationManager.isMyLocationFixed();
                }
            }
            return false;
        }

    };

    /* POI data State Change Listener*/
    private final NMapPOIdataOverlay.OnStateChangeListener onPOIdataStateChangeListener = new NMapPOIdataOverlay.OnStateChangeListener() {

        @Override
        public void onCalloutClick(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
            poiDataOverlay.deselectFocusedPOIitem();
            PublicDefine.mainActivity.showDetailInfo(item);
        }

        @Override
        public void onFocusChanged(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
            if (item != null) {
                Log.e("NTsys", "onFocusChanged: " + item.toString());
            } else {
                Log.e("NTsys", "onFocusChanged: ");
            }
        }
    };

    private final NMapPOIdataOverlay.OnFloatingItemChangeListener onPOIdataFloatingItemChangeListener = new NMapPOIdataOverlay.OnFloatingItemChangeListener() {

        @Override
        public void onPointChanged(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
            NGeoPoint point = item.getPoint();

            findPlacemarkAtLocation(point.longitude, point.latitude);

            item.setTitle(null);

        }
    };

    private final NMapOverlayManager.OnCalloutOverlayListener onCalloutOverlayListener = new NMapOverlayManager.OnCalloutOverlayListener() {

        @Override
        public NMapCalloutOverlay onCreateCalloutOverlay(NMapOverlay itemOverlay, NMapOverlayItem overlayItem,
                                                         Rect itemBounds) {

            // handle overlapped items
            if (itemOverlay instanceof NMapPOIdataOverlay) {
                NMapPOIdataOverlay poiDataOverlay = (NMapPOIdataOverlay) itemOverlay;

                // check if it is selected by touch event
                if (!poiDataOverlay.isFocusedBySelectItem()) {
                    int countOfOverlappedItems = 1;

                    NMapPOIdata poiData = poiDataOverlay.getPOIdata();
                    for (int i = 0; i < poiData.count(); i++) {
                        NMapPOIitem poiItem = poiData.getPOIitem(i);

                        // skip selected item
                        if (poiItem == overlayItem) {
                            continue;
                        }

                        // check if overlapped or not
                        if (Rect.intersects(poiItem.getBoundsInScreen(), overlayItem.getBoundsInScreen())) {
                            countOfOverlappedItems++;
                        }
                    }

                    if (countOfOverlappedItems > 1) {
                        String text = countOfOverlappedItems + " overlapped items for " + overlayItem.getTitle();
                        Toast.makeText(NMapViewer.this, text, Toast.LENGTH_LONG).show();
                        return null;
                    }
                }
            }

            // use custom old callout overlay
            if (overlayItem instanceof NMapPOIitem) {
                NMapPOIitem poiItem = (NMapPOIitem) overlayItem;

                if (poiItem.showRightButton()) {
                    return new NMapCalloutCustomOldOverlay(itemOverlay, overlayItem, itemBounds,
                            mMapViewerResourceProvider);
                }
            }

            // use custom callout overlay
            return new NMapCalloutCustomOverlay(itemOverlay, overlayItem, itemBounds, mMapViewerResourceProvider);

        }

    };

    private final NMapOverlayManager.OnCalloutOverlayViewListener onCalloutOverlayViewListener = new NMapOverlayManager.OnCalloutOverlayViewListener() {

        @Override
        public View onCreateCalloutOverlayView(NMapOverlay itemOverlay, NMapOverlayItem overlayItem, Rect itemBounds) {

            if (overlayItem != null) {
                // [TEST] 말풍선 오버레이를 뷰로 설정함
                String title = overlayItem.getTitle();
                if (title != null && title.length() > 5) {
                    return new NMapCalloutCustomOverlayView(NMapViewer.this, itemOverlay, overlayItem, itemBounds);
                }
            }

            // null을 반환하면 말풍선 오버레이를 표시하지 않음
            return null;
        }

    };

    /* Local Functions */
    private static boolean mIsMapEnlared = false;

    private void restoreInstanceState() {
        mPreferences = getPreferences(MODE_PRIVATE);

        int longitudeE6 = mPreferences.getInt(KEY_CENTER_LONGITUDE, NMAP_LOCATION_DEFAULT.getLongitudeE6());
        int latitudeE6 = mPreferences.getInt(KEY_CENTER_LATITUDE, NMAP_LOCATION_DEFAULT.getLatitudeE6());
        int level = mPreferences.getInt(KEY_ZOOM_LEVEL, NMAP_ZOOMLEVEL_DEFAULT);
        int viewMode = mPreferences.getInt(KEY_VIEW_MODE, NMAP_VIEW_MODE_DEFAULT);
        boolean trafficMode = mPreferences.getBoolean(KEY_TRAFFIC_MODE, NMAP_TRAFFIC_MODE_DEFAULT);
        boolean bicycleMode = mPreferences.getBoolean(KEY_BICYCLE_MODE, NMAP_BICYCLE_MODE_DEFAULT);

        mMapController.setMapViewMode(viewMode);
        mMapController.setMapViewTrafficMode(trafficMode);
        mMapController.setMapViewBicycleMode(bicycleMode);

        if (mMapView.getMapProjection().isProjectionScaled()) {
            if (mMapView.getMapProjection().isMapHD()) {
                mMapView.setScalingFactor(2.0F, false);
            } else {
                mMapView.setScalingFactor(1.0F, false);
            }
        } else {
            mMapView.setScalingFactor(2.0F, true);
        }
    }

    private void saveInstanceState() {
        if (mPreferences == null) {
            return;
        }

        NGeoPoint center = mMapController.getMapCenter();
        int level = mMapController.getZoomLevel();
        int viewMode = mMapController.getMapViewMode();
        boolean trafficMode = mMapController.getMapViewTrafficMode();
        boolean bicycleMode = mMapController.getMapViewBicycleMode();

        SharedPreferences.Editor edit = mPreferences.edit();

        edit.putInt(KEY_CENTER_LONGITUDE, center.getLongitudeE6());
        edit.putInt(KEY_CENTER_LATITUDE, center.getLatitudeE6());
        edit.putInt(KEY_ZOOM_LEVEL, level);
        edit.putInt(KEY_VIEW_MODE, viewMode);
        edit.putBoolean(KEY_TRAFFIC_MODE, trafficMode);
        edit.putBoolean(KEY_BICYCLE_MODE, bicycleMode);

        edit.commit();

    }

    /**
     * Container view class to rotate map view.
     */
    private class MapContainerView extends ViewGroup {

        public MapContainerView(Context context) {
            super(context);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            final int width = getWidth();
            final int height = getHeight();
            final int count = getChildCount();
            for (int i = 0; i < count; i++) {
                final View view = getChildAt(i);
                final int childWidth = view.getMeasuredWidth();
                final int childHeight = view.getMeasuredHeight();
                final int childLeft = (width - childWidth) / 2;
                final int childTop = (height - childHeight) / 2;
                view.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
            }

            if (changed) {
                mOverlayManager.onSizeChanged(width, height);
            }
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int w = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
            int h = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
            int sizeSpecWidth = widthMeasureSpec;
            int sizeSpecHeight = heightMeasureSpec;

            final int count = getChildCount();
            for (int i = 0; i < count; i++) {
                final View view = getChildAt(i);

                if (view instanceof NMapView) {
                    if (mMapView.isAutoRotateEnabled()) {
                        int diag = (((int) (Math.sqrt(w * w + h * h)) + 1) / 2 * 2);
                        sizeSpecWidth = MeasureSpec.makeMeasureSpec(diag, MeasureSpec.EXACTLY);
                        sizeSpecHeight = sizeSpecWidth;
                    }
                }

                view.measure(sizeSpecWidth, sizeSpecHeight);
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void getCourseInfo() {
        PublicDefine.mainActivity.showProgressDialog();
        mOverlayManager.clearOverlays();
        getStampLocation(getCourse());
        new ProcessNetworkCourseInfoThread().execute(null, null, null);
    }

    private void getStampLocation(int course) {
        setCourseStamp(DBHelper.getInstance(mMapView.getContext()).getStampLocationList(course));
    }

    public class ProcessNetworkCourseInfoThread extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            String content = executeClient();
            return content;
        }

        protected void onPostExecute(String result) {
            String main = result.toString();

            try {
                JSONObject jsonMain = new JSONObject(main);
                JSONArray jsonBody = jsonMain.getJSONArray("body");
                JSONArray jsonCotCoordData;
                JSONArray jsonCotCoordPointData;
                ArrayList<String[]> cotCoordRouteList = new ArrayList<String[]>();
                String[] cotCoordData;
                ArrayList<String[]> cotCoordList = new ArrayList<String[]>();
                for (int i = jsonBody.length() - 1; i >= 0; i--) {
                    jsonCotCoordData = jsonBody.getJSONObject(i).getJSONArray("COT_COORD_DATA");
                    for (int j = 0; j < jsonCotCoordData.length(); j++) {
                        jsonCotCoordPointData = jsonCotCoordData.getJSONArray(j);
                        cotCoordData = new String[]{jsonCotCoordPointData.getString(0), jsonCotCoordPointData.getString(1)};
                        cotCoordList.add(cotCoordData);
                    }
                    setCourseLine(cotCoordList, jsonBody.getJSONObject(i).getString("COT_LINE_COLOR"));
                    cotCoordList.clear();

                }
                getRouteInfoHandler.sendEmptyMessageDelayed(0, 300);
//				setCourseRoute(cotCoordRouteList);

            } catch (Exception e) {
                PublicDefine.mainActivity.cancelProgressDialog();
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

            HttpGet httpGet = new HttpGet("http://map.seoul.go.kr/smgis/apps/theme.do?cmd=getContentsList&page_no=1&page_size=999&key=" + PublicDefine.serviceSmgisKey + "&coord_x=127.0245909&coord_y=37.5669845&distance=200000&search_type=0&search_name=&theme_id=100211&subcate_id=100211," + getCourse());
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

    public class ProcessNetworkRouteInfoThread extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            String content = executeClient();
            return content;
        }

        protected void onPostExecute(String result) {
            String main = result.toString();

            try {
                JSONObject jsonMain = new JSONObject(main);
                JSONArray jsonBody = jsonMain.getJSONArray("body");
                JSONArray jsonCotCoordData;
                JSONArray jsonCotCoordPointData;
                String[] cotCoordData;
                ArrayList<String[]> cotCoordList = new ArrayList<String[]>();
                ArrayList<String[]> cotCoordRouteList = new ArrayList<String[]>();
                for (int i = jsonBody.length() - 1; i >= 0; i--) {
                    jsonCotCoordData = jsonBody.getJSONObject(i).getJSONArray("COT_COORD_DATA");
                    for (int j = 0; j < jsonCotCoordData.length(); j++) {
                        jsonCotCoordPointData = jsonCotCoordData.getJSONArray(j);
                        cotCoordData = new String[]{jsonCotCoordPointData.getString(0), jsonCotCoordPointData.getString(1)};
                        cotCoordList.add(cotCoordData);
                    }
                    setCourseRoute(cotCoordList, jsonBody.getJSONObject(i).getString("COT_LINE_COLOR"));
                    cotCoordList.clear();
                    cotCoordRouteList.add(new String[]{jsonBody.getJSONObject(i).getString("COT_COORD_X")
                            , jsonBody.getJSONObject(i).getString("COT_COORD_Y")
                            , Html.fromHtml(jsonBody.getJSONObject(i).getString("COT_CONTS_NAME")).toString()
                            , jsonBody.getJSONObject(i).getString("COT_CONTS_ID")});

                }

                setCourseRoutePoint(cotCoordRouteList);
                cotCoordRouteList.clear();
            } catch (Exception e) {
                PublicDefine.mainActivity.cancelProgressDialog();
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

            HttpGet httpGet = new HttpGet("http://map.seoul.go.kr/smgis/apps/theme.do?cmd=getContentsList&page_no=1&page_size=999&key=" + PublicDefine.serviceSmgisKey + "&coord_x=127.0245909&coord_y=37.5669845&distance=200000&search_type=0&search_name=&theme_id=100211&subcate_id=100211," + (getCourse() + 17));
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

    public class ProcessNetworkPointInfoThread extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            String content = executeClient();
            return content;
        }

        protected void onPostExecute(String result) {
            String main = result.toString();

            try {
                JSONObject jsonMain = new JSONObject(main);
                JSONArray jsonBody = jsonMain.getJSONArray("body");
                String jsonCOT_CONTS_ID;
                String jsonCOT_COORD_X;
                String jsonCOT_COORD_Y;
                String jsonCOT_CONTS_NAME;
                String[] cotCoordData;
                ArrayList<String[]> cotCoordList = new ArrayList<String[]>();
                for (int i = 1; i < jsonBody.length(); i++) {
                    jsonCOT_CONTS_ID = jsonBody.getJSONObject(i).getString("COT_CONTS_ID");
                    jsonCOT_COORD_Y = jsonBody.getJSONObject(i).getString("COT_COORD_Y");       //lat
                    jsonCOT_COORD_X = jsonBody.getJSONObject(i).getString("COT_COORD_X");       //lng
                    jsonCOT_CONTS_NAME = jsonBody.getJSONObject(i).getString("COT_CONTS_NAME");
                    cotCoordData = new String[]{jsonCOT_CONTS_ID, Html.fromHtml(jsonCOT_CONTS_NAME).toString(), jsonCOT_COORD_X, jsonCOT_COORD_Y};
                    cotCoordList.add(cotCoordData);
                }
                setCoursePoint(cotCoordList);
                cotCoordList.clear();

            } catch (Exception e) {
                PublicDefine.mainActivity.cancelProgressDialog();
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

            HttpGet httpGet = new HttpGet("http://map.seoul.go.kr/smgis/apps/theme.do?cmd=getContentsList&page_no=1&page_size=999&key=" + PublicDefine.serviceSmgisKey + "&coord_x=127.0245909&coord_y=37.5669845&distance=200000&search_type=0&search_name=&theme_id=100211&subcate_id=100211," + (getCourse() + 8));
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

    public class ProcessNetworkSafePointInfoThread extends AsyncTask<Void, Void, String> {

        protected String doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            String content = executeClient();
            return content;
        }

        protected void onPostExecute(String result) {
            String main = result.toString();

            try {
                JSONObject jsonMain = new JSONObject(main);
                JSONArray jsonBody = jsonMain.getJSONArray("body");
                String jsonCOT_CONTS_ID;
                String jsonCOT_COORD_X;
                String jsonCOT_COORD_Y;
                String jsonCOT_CONTS_NAME;
                String[] cotCoordData;
                ArrayList<String[]> cotCoordList = new ArrayList<String[]>();
                for (int i = 1; i < jsonBody.length(); i++) {
                    jsonCOT_CONTS_ID = jsonBody.getJSONObject(i).getString("COT_CONTS_ID");
                    jsonCOT_COORD_Y = jsonBody.getJSONObject(i).getString("COT_COORD_Y");       //lat
                    jsonCOT_COORD_X = jsonBody.getJSONObject(i).getString("COT_COORD_X");       //lng
                    jsonCOT_CONTS_NAME = jsonBody.getJSONObject(i).getString("COT_CONTS_NAME");
                    cotCoordData = new String[]{jsonCOT_CONTS_ID, Html.fromHtml(jsonCOT_CONTS_NAME).toString(), jsonCOT_COORD_X, jsonCOT_COORD_Y};
                    cotCoordList.add(cotCoordData);
                }
                setSafePoint(cotCoordList);
                cotCoordList.clear();

            } catch (Exception e) {
                PublicDefine.mainActivity.cancelProgressDialog();
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

            HttpGet httpGet = new HttpGet("http://map.seoul.go.kr/smgis/apps/theme.do?cmd=getContentsList&page_no=1&page_size=999&key=" + PublicDefine.serviceSmgisKey + "&coord_x=127.0245909&coord_y=37.5669845&distance=200000&search_type=0&search_name=&theme_id=100211&subcate_id=100211," + (getCourse() + 25));
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

    private void setCourseLine(ArrayList<String[]> cotCoordList, String cotLineColor) {
        if (cotCoordList.size() > 0) {
            NMapPathData pathData = new NMapPathData(cotCoordList.size());

            pathData.initPathData();
            for (int i = 0; i < cotCoordList.size(); i++) {
                pathData.addPathPoint(Double.parseDouble(cotCoordList.get(i)[0]), Double.parseDouble(cotCoordList.get(i)[1]), NMapPathLineStyle.TYPE_SOLID);
            }
            pathData.endPathData();

            NMapPathLineStyle p = new NMapPathLineStyle(mMapView.getContext());
            p.setLineColor(Color.parseColor(cotLineColor), 0xff);
            pathData.setPathLineStyle(p);

            pathDataOverlay = mOverlayManager.createPathDataOverlay(pathData);
            pathDataOverlay.showAllPathData(0);

            pathDataRouteOverlay = mOverlayManager.createPathDataOverlay();
        }
    }

    public Handler getRouteInfoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            new ProcessNetworkRouteInfoThread().execute(null, null, null);
        }
    };

    private void setCourseRoute(ArrayList<String[]> cotCoordRouteList, String cotLineColor) {
        if (cotCoordRouteList.size() > 0) {
            NMapPathData pathData = new NMapPathData(cotCoordRouteList.size());

            pathData.initPathData();
            for (int i = 0; i < cotCoordRouteList.size(); i++) {
                pathData.addPathPoint(Double.parseDouble(cotCoordRouteList.get(i)[0]), Double.parseDouble(cotCoordRouteList.get(i)[1]), NMapPathLineStyle.TYPE_DASH);
            }
            pathData.endPathData();

            NMapPathLineStyle p = new NMapPathLineStyle(mMapView.getContext());
            p.setLineColor(Color.parseColor(cotLineColor), 0xff);
            pathData.setPathLineStyle(p);

            pathDataRouteOverlay.addPathData(pathData);
            pathDataRouteOverlay.showAllPathData(0);
            pathDataRouteOverlay.setHidden(true);
        }
    }

    private void setCourseRoutePoint(ArrayList<String[]> cotCoordRouteList) {
        if (cotCoordRouteList.size() > 0) {
            int markerId = NMapPOIflagType.SAFESPOT + getCourse();

            // set POI data
            NMapPOIdata poiData = new NMapPOIdata(cotCoordRouteList.size(), mMapViewerResourceProvider);
            poiData.beginPOIdata(cotCoordRouteList.size());
            for (int i = 0; i < cotCoordRouteList.size(); i++) {
                poiData.addPOIitem(Double.parseDouble(cotCoordRouteList.get(i)[0]), Double.parseDouble(cotCoordRouteList.get(i)[1])
                        , cotCoordRouteList.get(i)[2], markerId, cotCoordRouteList.get(i)[3], 0).setRightAccessory(true, NMapPOIflagType.CLICKABLE_ARROW);
            }
            poiData.endPOIdata();

            // create POI data overlay
            poiDataRouteOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);

            // set event listener to the overlay
            poiDataRouteOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);

            if (!checkRoute) {
                poiDataRouteOverlay.setHidden(true);
            }

            poiDataRouteOverlay.showAllPOIdata(0);
        }

        getPointInfoHandler.sendEmptyMessageDelayed(0, 300);
    }

    public Handler getPointInfoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            new ProcessNetworkPointInfoThread().execute(null, null, null);
        }
    };

    private void setCourseStamp(ArrayList<StampLocation> stampLocationList) {
        if (stampLocationList.size() > 0) {
            int markerId = NMapPOIflagType.PIN;
            String courseID = "";

            // set POI data
            NMapPOIdata poiData = new NMapPOIdata(stampLocationList.size(), mMapViewerResourceProvider);
            poiData.beginPOIdata(stampLocationList.size());
            for (int i = 0; i < stampLocationList.size(); i++) {
                if (stampLocationList.get(i).getCourseID().equals("gil_02010")) {
                    courseID = "gil_01018";
                } else {
                    courseID = stampLocationList.get(i).getCourseID();
                }
                poiData.addPOIitem(Double.parseDouble(stampLocationList.get(i).getCOT_COORD_X()), Double.parseDouble(stampLocationList.get(i).getCOT_COORD_Y())
                        , stampLocationList.get(i).getCOT_CONTS_NAME(), markerId, courseID, 0).setRightAccessory(true, NMapPOIflagType.CLICKABLE_ARROW);
            }
            poiData.endPOIdata();

            // create POI data overlay
            poiDataStampOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);

            // set event listener to the overlay
            poiDataStampOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);

            if (!checkStamp) {
                poiDataStampOverlay.setHidden(true);
            }

            poiDataStampOverlay.showAllPOIdata(0);
        }
    }

    private void setCoursePoint(ArrayList<String[]> cotCoordList) {
        if (cotCoordList.size() > 0) {
            int markerId = NMapPOIflagType.SPOT;

            // set POI data
            NMapPOIdata poiData = new NMapPOIdata(cotCoordList.size(), mMapViewerResourceProvider);
            poiData.beginPOIdata(cotCoordList.size());
            for (int i = 0; i < cotCoordList.size(); i++) {
                poiData.addPOIitem(Double.parseDouble(cotCoordList.get(i)[2]), Double.parseDouble(cotCoordList.get(i)[3]), cotCoordList.get(i)[1], markerId, cotCoordList.get(i)[0], 0).setRightAccessory(true, NMapPOIflagType.CLICKABLE_ARROW);
                this.cotCoordList.add(cotCoordList.get(i));
            }
            poiData.endPOIdata();

            // create POI data overlay
            poiDataPointOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);

            // set event listener to the overlay
            poiDataPointOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);

            if (!checkPoint) {
                poiDataPointOverlay.setHidden(true);
            }

            poiDataPointOverlay.showAllPOIdata(0);
        }

        getSafePointInfoHandler.sendEmptyMessageDelayed(0, 300);
    }

    private void setSafePoint(ArrayList<String[]> cotCoordList) {
        if (cotCoordList.size() > 0) {
            int markerId = NMapPOIflagType.SAFESPOT;

            // set POI data
            NMapPOIdata poiData = new NMapPOIdata(cotCoordList.size(), mMapViewerResourceProvider);
            poiData.beginPOIdata(cotCoordList.size());
            for (int i = 0; i < cotCoordList.size(); i++) {
                poiData.addPOIitem(Double.parseDouble(cotCoordList.get(i)[2]), Double.parseDouble(cotCoordList.get(i)[3]), cotCoordList.get(i)[1], markerId, cotCoordList.get(i)[0], 0);
            }
            poiData.endPOIdata();

            // create POI data overlay
            poiDataSafePointOverlay = mOverlayManager.createPOIdataOverlay(poiData, null);

            if (!checkSafePoint) {
                poiDataSafePointOverlay.setHidden(true);
            }

            poiDataSafePointOverlay.showAllPOIdata(0);
        }

        PublicDefine.mainActivity.cancelProgressDialog();
    }

    public Handler getSafePointInfoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            new ProcessNetworkSafePointInfoThread().execute(null, null, null);
        }
    };

    @Override
    public void onBackPressed() {
        Log.e("NTsys", "NMapViewer onBackPressed");
        return;
//		super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("NTsys", "NMapViewer onKeyDown : " + keyCode);
        return false;
//		return super.onKeyDown(keyCode, event);
    }

    public void setCurrentLocation(View view) {
        if (((CheckBox) view).isChecked()) {
            isCurrentLocation = true;
            startMyLocation();
        } else {
            isCurrentLocation = false;
            stopMyLocation();
        }
    }

    public void showStamp(View view) {
        checkStamp = ((CheckBox) view).isChecked();
        if (poiDataStampOverlay != null) {
            if (((CheckBox) view).isChecked()) {
                poiDataStampOverlay.setHidden(false);
                poiDataStampOverlay.showAllPOIdata(0);
            } else {
                poiDataStampOverlay.setHidden(true);
                if (checkPoint) {
                    if (poiDataPointOverlay != null)
                        poiDataPointOverlay.showAllPOIdata(0);
                } else if (checkSafePoint) {
                    if (poiDataSafePointOverlay != null)
                        poiDataSafePointOverlay.showAllPOIdata(0);
                } else if (checkRoute) {
                    if (poiDataRouteOverlay != null)
                        poiDataRouteOverlay.showAllPOIdata(0);
                } else {
                    if (poiDataStampOverlay != null)
                        poiDataStampOverlay.showAllPOIdata(0);
                }
            }
        } else {
            ((CheckBox) view).setChecked(false);
            checkStamp = false;
        }
    }

    public void showPoint(View view) {
        checkPoint = ((CheckBox) view).isChecked();
        if (poiDataPointOverlay != null) {
            if (((CheckBox) view).isChecked()) {
                PublicDefine.mainActivity.showPointListItem(this.cotCoordList, view);
            } else {
                poiDataPointOverlay.setHidden(true);
                if (checkStamp) {
                    if (poiDataStampOverlay != null)
                        poiDataStampOverlay.showAllPOIdata(0);
                } else if (checkSafePoint) {
                    if (poiDataSafePointOverlay != null)
                        poiDataSafePointOverlay.showAllPOIdata(0);
                } else if (checkRoute) {
                    if (poiDataRouteOverlay != null)
                        poiDataRouteOverlay.showAllPOIdata(0);
                } else {
                    if (poiDataPointOverlay != null)
                        poiDataPointOverlay.showAllPOIdata(0);
                }
            }
        } else {
            ((CheckBox) view).setChecked(false);
            checkPoint = false;
        }
    }

    public void showSafePoint(View view) {
        checkSafePoint = ((CheckBox) view).isChecked();
        if (poiDataSafePointOverlay != null) {
            if (((CheckBox) view).isChecked()) {
                poiDataSafePointOverlay.setHidden(false);
                poiDataSafePointOverlay.showAllPOIdata(0);
            } else {
                poiDataSafePointOverlay.setHidden(true);
                if (checkPoint) {
                    if (poiDataPointOverlay != null)
                        poiDataPointOverlay.showAllPOIdata(0);
                } else if (checkStamp) {
                    if (poiDataStampOverlay != null)
                        poiDataStampOverlay.showAllPOIdata(0);
                } else if (checkRoute) {
                    if (poiDataRouteOverlay != null)
                        poiDataRouteOverlay.showAllPOIdata(0);
                } else {
                    if (poiDataSafePointOverlay != null)
                        poiDataSafePointOverlay.showAllPOIdata(0);
                }
            }
        } else {
            ((CheckBox) view).setChecked(false);
            checkSafePoint = false;
        }
    }

    public static void showPointPin() {
        poiDataPointOverlay.setHidden(false);
        poiDataPointOverlay.showAllPOIdata(0);
    }

    public static void selectionPointPin(int position) {
        poiDataPointOverlay.selectPOIitem(position, true);
    }

    public void showRoute(View view) {
        checkRoute = ((CheckBox) view).isChecked();
        if (poiDataRouteOverlay != null && pathDataRouteOverlay != null) {
            if (((CheckBox) view).isChecked()) {
                pathDataRouteOverlay.setHidden(false);
                pathDataRouteOverlay.showAllPathData(0);
                poiDataRouteOverlay.setHidden(false);
                poiDataRouteOverlay.showAllPOIdata(0);
            } else {
                pathDataRouteOverlay.setHidden(true);
                poiDataRouteOverlay.setHidden(true);
                if (checkPoint) {
                    if (poiDataPointOverlay != null)
                        poiDataPointOverlay.showAllPOIdata(0);
                } else if (checkStamp) {
                    if (poiDataStampOverlay != null)
                        poiDataStampOverlay.showAllPOIdata(0);
                } else if (checkSafePoint) {
                    if (poiDataSafePointOverlay != null)
                        poiDataSafePointOverlay.showAllPOIdata(0);
                } else {
                    if (poiDataRouteOverlay != null)
                        poiDataRouteOverlay.showAllPOIdata(0);
                }
            }
        } else {
            ((CheckBox) view).setChecked(false);
            checkRoute = false;
        }
    }
}
