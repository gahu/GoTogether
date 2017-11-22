package kr.go.seoul.seoultrail;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by ntsys on 2016-08-09.
 */
public class SeoulTrail extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seoul_trail_activity);
        String ver = "";
        try {
            PackageInfo packageInfo = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0);
            ver = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("NTsys", "페키지 오류 발생");
        }
        ((TextView) findViewById(R.id.ver_info)).setText(ver);
    }
}
