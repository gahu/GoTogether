package kr.go.seoul.seoultrail.FAQInfo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by 지호 on 2017-10-25.
 */

public class FAQ_ExpandableListDataPump {
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

    public static LinkedHashMap<String, String> getDataAtDepth2() {
        LinkedHashMap<String, String> depth2ExpandableListDetail = new LinkedHashMap<>();

        String certify_reply = "안녕하세요 서울둘레길 안내센터 입니다. 서울둘레길에 소중한 문의주셔서 감사합니다. " +
                "현재 서울둘레길 인증방법은 지도와 스탬프 북을 이용여 스탬프를 직접찍는 방법과 공식어플을 통한 방법이 있습니다. " +
                "스마트폰 이용 시 구글플레이스토어 (또는 아이폰 앱스토어)에서 서울둘레길을 검색하시어 공식어플을 다운받으시고 실행 -> " +
                "GPS위치를 허용 -> 서울둘레길 탐방 하시면 우체통 주변을 통과시 자동으로 스탬프 획득이 가능합니다. " +
                "기타 문의사항이 있으시면 서울둘레길 안내센터 02)779-7902 로 문의주시면 자세히 답변해드리겠습니다.";
        String addInk_reply = "안녕하세요 서울둘레길 안내센터 입니다. 서울둘레길에 소중한 의견주셔서 감사합니다. " +
                "정기적으로 충전조치하고 있습니다. 또한 8개코스 전구간도 현재 스탬프투어에 불편함이 없도록 점검하겠습니다.";
        String info_reply = "안녕하세요 서울둘레길 안내센터 입니다. 서울둘레길에 꼭 필요한 소중한 의견주셔서 감사합니다. " +
                "의견주신 잉크는 5월초 기준으로 전체코스 점검을 통해서 잉크충전을 완료하였고, 주기적인 정검을 통해 이용에 불편이 없도록 조치하고있습니다. " +
                "안내체계 중 리본 또한 좀더 이용에 불편함이 없도록 추가설치 및 교체를 진행한 상태입니다. 오늘도 서울둘레길과 함께 건강한 하루되시기 바랍니다. 감사합니다.";

        depth2ExpandableListDetail.put("둘레길 안내 표식 관련", info_reply);
        depth2ExpandableListDetail.put("스탬프 잉크 보충 요청", addInk_reply);
        depth2ExpandableListDetail.put("인증 안내 문의", certify_reply);


        return depth2ExpandableListDetail;
    }
}
