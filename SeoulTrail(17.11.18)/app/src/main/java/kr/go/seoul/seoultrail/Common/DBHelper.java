package kr.go.seoul.seoultrail.Common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Html;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by ntsys on 2016-05-24.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper mDbHelper;
    // All Static variables
    private String DATABASE_PATH = null;
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "STAMP";


    // Contacts table name
    private static final String TABLE_LOCATION = "stamp_location";
    private static final String TABLE_HISTORY = "stamp_history";
    private SQLiteDatabase db;



    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_COURSE_ID = "COURSE_ID";
    private static final String KEY_COURSE_NO = "COURSE_NO";
    private static final String KEY_STAMP_NO = "STAMP_NO";
    private static final String KEY_LAT = "LAT";
    private static final String KEY_LNG = "LNG";
    private static final String KEY_STAMP_NAME = "STAMP_NAME";
    private static final String KEY_LAST_UPDATE_DATE = "LAST_UPDATE_DATE";
    private static final String KEY_COMPLETE_COUNT = "COMPLETE_COUNT";
    private static final String KEY_UPDATE_DATE = "UPDATE_DATE";
    private static final String ORG_STAMP = "{\"list\":[" +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"\",\"COT_COORD_X\":\"126.9821811\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.6129098\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/21/5095611149712603.jpg\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사54295717\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_08016\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[8코스] 명상길 시작점\",\"COT_TEL_NO\":\"\"}," +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"\",\"COT_COORD_X\":\"127.0101307\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.623674\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/28/5726670996599803.jpg\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사56775836\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_08017\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[8코스] 흰구름길 시작점\",\"COT_TEL_NO\":\"\"}," +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"04965\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"광진구 영화사로 145\",\"COT_COORD_X\":\"127.0998113\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.5520874\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/21/5083383583256876.jpg\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사64655038\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_02012\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[2코스] 아차산 관리사무소 앞\",\"COT_TEL_NO\":\"\"}," +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"\",\"COT_COORD_X\":\"127.1068478\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.5879911\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/20/5037033017317557.jpg\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사65285436\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_02011\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[2코스] 용마산 깔딱고개 쉼터\",\"COT_TEL_NO\":\"\"}," +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"04969\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"광진구 구천면로 29\",\"COT_COORD_X\":\"127.1090101\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.5459683\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/20/5037185396514522.JPG\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사65464969\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_03013\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[3코스] 광진교 초입 (코스 주변)\",\"COT_TEL_NO\":\"\"}," +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"01827\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"노원구 노원로1길 21\",\"COT_COORD_X\":\"127.0849971\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.6204756\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/21/5083190181681897.jpg\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사63375797\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_01018\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[1코스/2코스] 화랑대 4번 출구 앞 공원\",\"COT_TEL_NO\":\"\"}," +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"03361\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"은평구 진흥로19길 30\",\"COT_COORD_X\":\"126.9395916\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.6127044\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/21/5095561903951134.JPG\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사50535717\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_08015\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[8코스] 옛성길 시작점\",\"COT_TEL_NO\":\"\"},\n" +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"03309\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"은평구 연서로44길 55\",\"COT_COORD_X\":\"126.9370238\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.627476\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/21/5084249688977808.jpg\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사50325881\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_08014\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[8코스] 선림사 옆\",\"COT_TEL_NO\":\"\"}," +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"01365\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"도봉구 삼양로162가길 42-50\",\"COT_COORD_X\":\"127.0164408\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.6615697\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/28/5726695677712636.jpg\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사57356256\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_08018\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[8코스] 왕실묘역 시작점\",\"COT_TEL_NO\":\"\"}," +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"\",\"COT_COORD_X\":\"127.037224\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.4692376\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/21/5083721734412687.JPG\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사59074121\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_04014\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[4코스] 양재시민의숲 안내소 옆\",\"COT_TEL_NO\":\"\"}," +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"서초구\",\"COT_COORD_X\":\"126.986817\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.4734506\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/20/5037481465850053.jpg\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사54624170\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_04015\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[4코스] 우면산 끝부분\",\"COT_TEL_NO\":\"\"}," +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"강남구\",\"COT_COORD_X\":\"127.1019141\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.4866153\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/21/5083586431333736.jpg\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사64804311\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_04013\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[4코스] 대모산 초입\",\"COT_TEL_NO\":\"\"}," +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"\",\"COT_COORD_X\":\"126.9026618\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.5851514\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/20/5037805668776588.jpg\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사47265414\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_07015\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[7코스] 증산체육공원 화장실 앞\",\"COT_TEL_NO\":\"\"}," +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"강남구\",\"COT_COORD_X\":\"127.1067492\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.4882179\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/21/5081775288142086.JPG\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사65234329\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_03018\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[3코스] 탄천 끝부분\",\"COT_TEL_NO\":\"\"}," +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"\",\"COT_COORD_X\":\"126.9783526\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.4692835\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/21/5083933429868192.JPG\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사53874124\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_05011\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[5코스] 관음사 입구\",\"COT_TEL_NO\":\"\"}," +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"\",\"COT_COORD_X\":\"127.1401305\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.5123745\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/21/5083405443904726.jpg\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사68194596\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_03017\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[3코스] 방이동 생태경관보전지역 사무소 앞\",\"COT_TEL_NO\":\"\"}," +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"05225\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"강동구 동남로 918\",\"COT_COORD_X\":\"127.1569127\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.5552614\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/20/5037223693205185.jpg\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사69695071\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_03016\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[3코스] 일자산 초입 (고덕역 4번 출구 260m 앞)\",\"COT_TEL_NO\":\"\"}," +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"노원구\",\"COT_COORD_X\":\"127.0836533\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.668395\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/20/5036898724304879.jpg\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사63286329\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_01017\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[1코스] 불암산 우회코스 갈림길\",\"COT_TEL_NO\":\"\"}," +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"은평구\",\"COT_COORD_X\":\"126.9144327\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.6353518\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/20/5037849692316361.jpg\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사48335970\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_07016\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[7코스] 애봉산 끝부분\",\"COT_TEL_NO\":\"\"}," +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"관악구\",\"COT_COORD_X\":\"126.946582\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.4675814\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/20/5037589338840827.JPG\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사51064107\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_05012\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[5코스] 관악산 안내소 앞\",\"COT_TEL_NO\":\"\"}," +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"01300\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"도봉구 도봉산길 85\",\"COT_COORD_X\":\"127.0359596\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.6860381\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/28/5726727937741309.jpg\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사59086526\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_08019\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[8코스] 도봉탐방지원센터 옆\",\"COT_TEL_NO\":\"\"}," +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"01318\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"도봉구 마들로 916\",\"COT_COORD_X\":\"127.0469381\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.6892427\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/21/5085430067089947.jpg\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사60056561\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_01016\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[1코스] 창포원 관리사무소 앞\",\"COT_TEL_NO\":\"\"}," +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"07531\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"강서구 허준로 221-40\",\"COT_COORD_X\":\"126.8646443\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.5619642\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/20/5037729418666827.jpg\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사43885159\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_06013\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[6코스] 황금내 근린공원 화장실 앞\",\"COT_TEL_NO\":\"\"}," +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"07526\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"강서구 양천로57길 36\",\"COT_COORD_X\":\"126.8564305\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.5638441\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/20/5037780035241928.jpg\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사43165180\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_07014\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[7코스] 가양대교 시작점\",\"COT_TEL_NO\":\"\"}," +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"\",\"COT_COORD_X\":\"126.8709662\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.4958707\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/20/5037704463510614.jpg\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사44394425\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_06012\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[6코스] 구일역 앞\",\"COT_TEL_NO\":\"\"}," +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"08654\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"금천구 삼성산길 28\",\"COT_COORD_X\":\"126.9065426\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.4338925\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/21/5083989996410890.JPG\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사47493735\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_05013\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[5코스] 관악산 끝부분\",\"COT_TEL_NO\":\"\"}," +
            "{\"THM_THEME_NAME\":\"서울둘레길\",\"COT_THEME_SUB_ID\":\"17\",\"COT_COORD_DATA\":\"\",\"SUB_CATE_IMG_URI\":\"/smgis/ucimgs/theme/100211_S_20151029115415.PNG\",\"COT_NATION_BASE_AREA\":\"\",\"COT_THEME_ID\":\"100211\",\"COT_ADDR_NEW\":\"\",\"COT_COORD_X\":\"126.9020012\",\"COT_LINE_COLOR\":\"#0000FF\",\"COT_EXTRA_DATA_02\":\"http://gil.seoul.go.kr/\",\"COT_COORD_Y\":\"37.4342779\",\"COT_COORD_TYPE\":\"1\",\"COT_IMG_MAIN_URL\":\"http://gil.seoul.go.kr/view/point/2014/11/20/5037665361197156.jpg\",\"SUB_NAME\":\"스탬프시설\",\"COT_NATION_POINT_NUMBER\":\"다사47093740\",\"COT_LINE_PATTERN\":\"L\",\"COT_CONTS_ID\":\"gil_06011\",\"COT_LINE_WEIGHT\":\"4\",\"COT_CONTS_NAME\":\"[6코스] 석수역 앞\",\"COT_TEL_NO\":\"\"}" +
            "],\"msg\":\"성공하였습니다.\",\"result\":\"success\"}";

    private Context mContext;

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    public static DBHelper getInstance(Context context) {
        if (mDbHelper == null) {
            mDbHelper = new DBHelper(context); //fails here
        }
        return mDbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 위치 정보 테이블 생성
        String CREATE_LOCATION_TABLE = "CREATE TABLE " + TABLE_LOCATION + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_COURSE_ID + " TEXT," + KEY_COURSE_NO + " INTEGER,"
                + KEY_STAMP_NO + " INTEGER," + KEY_LAT + " TEXT," + KEY_LNG + " TEXT," + KEY_STAMP_NAME + " TEXT,"
                + KEY_LAST_UPDATE_DATE + " INTEGER," + KEY_COMPLETE_COUNT + " INTEGER" + ")";
        db.execSQL(CREATE_LOCATION_TABLE);

        // 히스토리 테이블 생성
        String CREATE_HISTORY_TABLE = "CREATE TABLE " + TABLE_HISTORY + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_COURSE_ID + " TEXT," + KEY_COURSE_NO + " INTEGER,"
                + KEY_STAMP_NO + " INTEGER," + KEY_UPDATE_DATE + " TEXT" + ")";

        db.execSQL(CREATE_HISTORY_TABLE);

        // 제이슨 형식으로 값을 가져오기 위함.
        try {
            JSONObject jsonMain = new JSONObject(ORG_STAMP.toString());
            JSONArray jsonlist = jsonMain.getJSONArray("list");
            String jsonCOT_CONTS_ID;
            String jsonCOT_COORD_X;
            String jsonCOT_COORD_Y;
            String jsonCOT_CONTS_NAME;

            for (int i = jsonlist.length() - 1; i >= 0; i--) {
                jsonCOT_CONTS_ID = jsonlist.getJSONObject(i).getString("COT_CONTS_ID");
                jsonCOT_COORD_Y = jsonlist.getJSONObject(i).getString("COT_COORD_Y");       //lat
                jsonCOT_COORD_X = jsonlist.getJSONObject(i).getString("COT_COORD_X");       //lng
                jsonCOT_CONTS_NAME = Html.fromHtml(jsonlist.getJSONObject(i).getString("COT_CONTS_NAME")).toString();
                setDefaultStamp(new StampLocation(jsonCOT_CONTS_ID, Integer.parseInt(jsonCOT_CONTS_ID.substring(4, 6)), 0
                        , jsonCOT_COORD_Y, jsonCOT_COORD_X, jsonCOT_CONTS_NAME), db);
                if (jsonCOT_CONTS_ID.equals("gil_01018")) {
                    setDefaultStamp(new StampLocation("gil_02010", 02, 0
                            , jsonCOT_COORD_Y, jsonCOT_COORD_X, jsonCOT_CONTS_NAME), db);
                }
            }
            setDefaultsortAndUpdateStampNo(db);
        } catch (Exception e) {
            Log.e("NTsys", "데이터 초기화 오류 발생");
        }
    }

    // db 존재 유무 확인

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);

        onCreate(db);
    }

    // ArrayList형태의 리스트 스탬프 위치 가져 오기 위함.
    public ArrayList<StampLocation> getStampLocationList(int course) {
        ArrayList<StampLocation> stampLocationList = new ArrayList<StampLocation>();
        StampLocation location;
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_LOCATION + " WHERE " + KEY_COURSE_NO + " = " + course;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                location = new StampLocation(cursor.getString(cursor.getColumnIndex(KEY_COURSE_ID))
                        , cursor.getInt(cursor.getColumnIndex(KEY_COURSE_NO))
                        , cursor.getInt(cursor.getColumnIndex(KEY_STAMP_NO))
                        , cursor.getString(cursor.getColumnIndex(KEY_LAT))
                        , cursor.getString(cursor.getColumnIndex(KEY_LNG))
                        , cursor.getString(cursor.getColumnIndex(KEY_STAMP_NAME)));
                stampLocationList.add(location);
            } while (cursor.moveToNext());
        } else {
            Log.e("NTsys", "stamplocation no data");
        }
        db.close();

        return stampLocationList;
    }
    // ArrayList형태의 스탬프 개수 확인하는 부분

    public ArrayList<StampLocation> getCompleteStampCount() {
        ArrayList<StampLocation> stampLocationList = new ArrayList<StampLocation>();
        StampLocation location;
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_LOCATION + " ORDER BY " + KEY_STAMP_NO;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                location = new StampLocation(cursor.getString(cursor.getColumnIndex(KEY_COURSE_ID))
                        , cursor.getInt(cursor.getColumnIndex(KEY_COURSE_NO))
                        , cursor.getInt(cursor.getColumnIndex(KEY_STAMP_NO))
                        , cursor.getString(cursor.getColumnIndex(KEY_LAT))
                        , cursor.getString(cursor.getColumnIndex(KEY_LNG))
                        , cursor.getString(cursor.getColumnIndex(KEY_LAST_UPDATE_DATE))
                        , cursor.getString(cursor.getColumnIndex(KEY_COMPLETE_COUNT)));
                stampLocationList.add(location);
            } while (cursor.moveToNext());
        } else {
            Log.e("NTsys", "location no data");
        }
        db.close();
        return stampLocationList;
    }

    // 스탬프 기록 확인 하는 부분

    public ArrayList<StampHistory> getCompleteStampHistory(int stampNum) {
        ArrayList<StampHistory> stampHistoryList = new ArrayList<StampHistory>();
        StampHistory history;
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_HISTORY + " WHERE " + KEY_STAMP_NO + " = " + stampNum + " ORDER BY " + KEY_UPDATE_DATE;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                history = new StampHistory(cursor.getInt(cursor.getColumnIndex(KEY_COURSE_NO))
                        , cursor.getInt(cursor.getColumnIndex(KEY_STAMP_NO)), cursor.getString(cursor.getColumnIndex(KEY_UPDATE_DATE)));
                stampHistoryList.add(history);
            } while (cursor.moveToNext());
        } else {
            Log.e("NTsys", "history no data");
        }
        db.close();
        return stampHistoryList;
    }

    // ArrayLsit형태의 스탬프 위치
    public ArrayList<StampLocation> getNoCompleteStampLocation() {
        ArrayList<StampLocation> stampLocationList = new ArrayList<StampLocation>();
        StampLocation location;
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat locationFormat = new SimpleDateFormat("yyyyMMdd");
        String selectQuery = "SELECT * FROM " + TABLE_LOCATION + " WHERE " + KEY_LAST_UPDATE_DATE + " < " + Integer.parseInt(locationFormat.format(new Date()));

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                location = new StampLocation(cursor.getString(cursor.getColumnIndex(KEY_COURSE_ID))
                        , cursor.getInt(cursor.getColumnIndex(KEY_COURSE_NO))
                        , cursor.getInt(cursor.getColumnIndex(KEY_STAMP_NO))
                        , cursor.getString(cursor.getColumnIndex(KEY_LAT))
                        , cursor.getString(cursor.getColumnIndex(KEY_LNG))
                        , cursor.getString(cursor.getColumnIndex(KEY_STAMP_NAME)));
                stampLocationList.add(location);
            } while (cursor.moveToNext());
        } else {
            Log.e("NTsys", "location no data");
        }
        db.close();
        return stampLocationList;
    }

    // 스탬프 세팅 부분
    public void setCompleteStamp(StampLocation location) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_LOCATION + " WHERE " + KEY_STAMP_NO + " = " + location.getStampNo();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            ContentValues values = new ContentValues();
            SimpleDateFormat locationFormat = new SimpleDateFormat("yyyyMMdd");
            values.put(KEY_LAST_UPDATE_DATE, Integer.parseInt(locationFormat.format(new Date())));
            values.put(KEY_COMPLETE_COUNT, cursor.getInt(cursor.getColumnIndex(KEY_COMPLETE_COUNT)) + 1);
            db.update(TABLE_LOCATION, values, KEY_COURSE_NO + " = ? AND " + KEY_STAMP_NO + " = ?", new String[]{String.valueOf(location.getCourseNo()), String.valueOf(location.getStampNo())});

            db.close();

            // 스탬프 추가 했을 경우?
            addHistory(location);
        } else {
            if (db.isOpen()) {
                db.close();
            }
            Log.e("NTsys", "no data setCompleteStamp location");
        }
    }
    // 모든 코스 정보 가져 오기 위한 메소드
    public boolean getCompleteAllCourse(StampLocation location) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT " + KEY_COMPLETE_COUNT + " FROM " + TABLE_LOCATION + " WHERE " + KEY_STAMP_NO + " = " + location.getStampNo();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            if (cursor.getInt(cursor.getColumnIndex(KEY_COMPLETE_COUNT)) == 1) {
                selectQuery = "SELECT COUNT(" + KEY_STAMP_NO + ") FROM " + TABLE_LOCATION + " WHERE " + KEY_COMPLETE_COUNT + " = 0";
                cursor = db.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    if (cursor.getInt(0) == 0) {
                        if (db.isOpen()) {
                            db.close();
                        }
                        return true;
                    } else {
                        if (db.isOpen()) {
                            db.close();
                        }
                        return false;
                    }
                } else {
                    if (db.isOpen()) {
                        db.close();
                    }
                    return false;
                }
            } else {
                if (db.isOpen()) {
                    db.close();
                }
                return false;
            }
        } else {
            Log.e("NTsys", "getCompleteAllCourse noData");
            if (db.isOpen()) {
                db.close();
            }
            return false;
        }
    }

    // 위치 업데이트 하기 위함.

    public void updateLocation(StampLocation location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_COURSE_NO, location.getCourseNo());
        values.put(KEY_STAMP_NO, location.getStampNo());
        values.put(KEY_LAT, location.getCOT_COORD_Y());
        values.put(KEY_LNG, location.getCOT_COORD_X());
        values.put(KEY_STAMP_NAME, location.getCOT_CONTS_NAME());

        int row = db.update(TABLE_LOCATION, values, KEY_COURSE_ID + " = ?", new String[]{location.getCourseID()});
        if (row == 0) {
            addLocation(location);
        }
        db.close();
    }

    // 위치 추가
    public void addLocation(StampLocation location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_COURSE_ID, location.getCourseID());
        values.put(KEY_COURSE_NO, location.getCourseNo());
        values.put(KEY_STAMP_NO, location.getStampNo());
        values.put(KEY_LAT, location.getCOT_COORD_Y());
        values.put(KEY_LNG, location.getCOT_COORD_X());
        values.put(KEY_STAMP_NAME, location.getCOT_CONTS_NAME());
        values.put(KEY_LAST_UPDATE_DATE, 0);
        values.put(KEY_COMPLETE_COUNT, 0);

        db.insert(TABLE_LOCATION, null, values);
        db.close();

    }

    // 위치 기록 부분
    public void addHistory(StampLocation location) {
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat historyFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ContentValues values = new ContentValues();
        values.put(KEY_COURSE_NO, location.getCourseNo());
        values.put(KEY_STAMP_NO, location.getStampNo());
        values.put(KEY_UPDATE_DATE, historyFormat.format(new Date()));

        db.insert(TABLE_HISTORY, null, values);
        db.close();

        if (PublicDefine.courseStampFragment != null) {
            PublicDefine.courseStampFragment.setVaidateCompleteStampCount();
        }
    }

    // 스탬프 Sort부분

    public void sortAndUpdateStampNo() {
        int i = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_LOCATION + " ORDER BY " + KEY_COURSE_ID;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ContentValues values = new ContentValues();
                values.put(KEY_STAMP_NO, i++);
                db.update(TABLE_LOCATION, values, KEY_COURSE_ID + " = ?", new String[]{cursor.getString(cursor.getColumnIndex(KEY_COURSE_ID))});
            } while (cursor.moveToNext());
        } else {
            Log.e("NTsys", "location no data");
        }
        db.close();
    }

    // 스탬프 없을 경우

    private void setDefaultStamp(StampLocation location, SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put(KEY_COURSE_ID, location.getCourseID());
        values.put(KEY_COURSE_NO, location.getCourseNo());
        values.put(KEY_STAMP_NO, location.getStampNo());
        values.put(KEY_LAT, location.getCOT_COORD_Y());
        values.put(KEY_LNG, location.getCOT_COORD_X());
        values.put(KEY_STAMP_NAME, location.getCOT_CONTS_NAME());
        values.put(KEY_LAST_UPDATE_DATE, 0);
        values.put(KEY_COMPLETE_COUNT, 0);

        db.insert(TABLE_LOCATION, null, values);
    }

    // 스탬프 번호 정렬

    public void setDefaultsortAndUpdateStampNo(SQLiteDatabase db) {
        int i = 0;
        String selectQuery = "SELECT * FROM " + TABLE_LOCATION + " ORDER BY " + KEY_COURSE_ID;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                ContentValues values = new ContentValues();
                values.put(KEY_STAMP_NO, i++);

                int row = db.update(TABLE_LOCATION, values, KEY_COURSE_ID + " = ?", new String[]{cursor.getString(cursor.getColumnIndex(KEY_COURSE_ID))});
            } while (cursor.moveToNext());
        } else {
            Log.e("NTsys", "location no data");
        }
    }
}
