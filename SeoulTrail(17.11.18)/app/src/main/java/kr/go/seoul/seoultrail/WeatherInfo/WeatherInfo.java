package kr.go.seoul.seoultrail.WeatherInfo;

/**
 * Created by bgg89 on 2017-11-23.
 */

public class WeatherInfo {
    String attrnewswrites, category, now_degree, now_hour, wfKor, sky = null;
    // 발표 시간, 도시, 현재 온도, 첫번째 날씨의 시간, 현재 날씨, 현재 날씨 상태

    String wdKor, ws, reh, pop, namet = null;
    // 현재 바람, 풍속, 습도, 비올확률 %

    public String getAttrnewswrites() {
        return attrnewswrites;
    }

    public void setAttrnewswrites(String attrnewswrites) {
        this.attrnewswrites = attrnewswrites;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNow_degree() {
        return now_degree;
    }

    public void setNow_degree(String now_degree) {
        this.now_degree = now_degree;
    }

    public String getNow_hour() {
        return now_hour;
    }

    public void setNow_hour(String now_hour) {
        this.now_hour = now_hour;
    }

    public String getWfKor() {
        return wfKor;
    }

    public void setWfKor(String wfKor) {
        this.wfKor = wfKor;
    }

    public String getSky() {
        return sky;
    }

    public void setSky(String sky) {
        this.sky = sky;
    }

    public String getWdKor() {
        return wdKor;
    }

    public void setWdKor(String wdKor) {
        this.wdKor = wdKor;
    }

    public String getWs() {
        return ws;
    }

    public void setWs(String ws) {
        this.ws = ws;
    }

    public String getReh() {
        return reh;
    }

    public void setReh(String reh) {
        this.reh = reh;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getNamet() {
        return namet;
    }

    public void setNamet(String namet) {
        this.namet = namet;
    }

    public WeatherInfo(String attrnewswrites, String category, String now_degree, String now_hour, String wfKor, String sky,
                       String wdKor, String ws, String reh, String pop, String namet) {
        this.attrnewswrites = attrnewswrites;
        this.category = category;
        this.now_degree = now_degree;
        this.now_hour = now_hour;
        this.wfKor = wfKor;
        this.sky = sky;

        this.wdKor = wdKor;
        this.ws = ws;
        this.reh = reh;
        this.pop = pop;
        this.namet = namet;

    }
}
