package kr.go.seoul.seoultrail.WeatherInfo;

/**
 * Created by bgg89 on 2017-11-23.
 */

public class WeatherInfo {
    private String tmEf;
    private String wf;
    private String tmn;
    private String tmx;
    private String wfKor;
    private String temp;
    private String hour;

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getWfKor() { return wfKor; }

    public void setWfKor(String wfKor) { this.wfKor = wfKor; }

    public String getTemp()  { return temp; }

    public void setTemp(String temp) { this.temp = temp; }

    public String getTmEf() {
        return tmEf;
    }

    public void setTmEf(String tmEf) { this.tmEf = tmEf; }

    public String getWf() {
        return wf;
    }

    public void setWf(String wf) {
        this.wf = wf;
    }

    public String getTmn() {
        return tmn;
    }

    public void setTmn(String tmn) { this.tmn = tmn; }

    public String getTmx() {
        return tmx;
    }

    public void setTmx(String tmx) { this.tmx = tmx; }
}
