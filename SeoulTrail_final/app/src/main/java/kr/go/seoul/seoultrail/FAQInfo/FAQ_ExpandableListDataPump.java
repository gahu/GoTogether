package kr.go.seoul.seoultrail.FAQInfo;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import kr.go.seoul.seoultrail.FAQ;
import kr.go.seoul.seoultrail.R;

/**
 * Created by 지호 on 2017-10-25.
 */

public class FAQ_ExpandableListDataPump {
    private final static int CERTIFY_REPLY = 3;
    private final static int ADDINT_REPLY = 4;
    private final static int INFO_REPLY = 5;

    public static LinkedHashMap<String, List<String>> getDataAtDepth1() {
        LinkedHashMap<String, List<String>> depth1ExpandableListDetail = new LinkedHashMap<>();

        List<String> faq = new ArrayList<String>();
        faq.add("faq");

        List<String> email = new ArrayList<String>();
        email.add("email");

        List<String> blog = new ArrayList<String>();
        blog.add("blog");

        List<String> hallOfFame = new ArrayList<String>();
        hallOfFame.add("hallOfFame");

        depth1ExpandableListDetail.put("자주 찾는 질문", faq);
        depth1ExpandableListDetail.put("담당자 문의하기", email);
        depth1ExpandableListDetail.put("시민 블로그", blog);
        depth1ExpandableListDetail.put("명예의 전당", hallOfFame);


        return depth1ExpandableListDetail;
    }

    public static LinkedHashMap<String, String> getDataAtDepth2(Context context) {
        LinkedHashMap<String, String> depth2ExpandableListDetail = new LinkedHashMap<>();

//        String certify_reply = FAQ_Depth2ExpandableAdapter.readTitleFromFile(CERTIFY_REPLY);
//        String addInk_reply = FAQ_Depth2ExpandableAdapter.readTitleFromFile(ADDINT_REPLY);
//        String info_reply = FAQ_Depth2ExpandableAdapter.readTitleFromFile(INFO_REPLY);

        String certify_reply = readReplyFromFile(context, CERTIFY_REPLY);
        String addInk_reply = readReplyFromFile(context, ADDINT_REPLY);
        String info_reply = readReplyFromFile(context, INFO_REPLY);

        depth2ExpandableListDetail.put("인증 안내 문의", certify_reply);
        depth2ExpandableListDetail.put("스탬프 잉크 보충 요청", addInk_reply);
        depth2ExpandableListDetail.put("둘레길 안내 표식 관련", info_reply);

        return depth2ExpandableListDetail;
    }

    // 각자 다른 파일로부터 제목텍스트를 읽어옴
    public static String readReplyFromFile(Context context, int whatText) {
        InputStream inputStream = null;

        if(whatText == 3) {
            inputStream = context.getResources().openRawResource(R.raw.depth2_certify_reply);
        } else if(whatText == 4) {
            inputStream = context.getResources().openRawResource(R.raw.depth2_addink_reply);
        } else {
            inputStream = context.getResources().openRawResource(R.raw.depth2_info_reply);
        }

        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        StringBuilder stringBuilder = new StringBuilder();
        String strLine;

        try {
            while((strLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(strLine);
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}