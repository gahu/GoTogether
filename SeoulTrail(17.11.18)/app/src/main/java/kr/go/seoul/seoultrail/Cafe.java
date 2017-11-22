package kr.go.seoul.seoultrail;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import kr.go.seoul.seoultrail.Common.PublicDefine;

/**
 * Created by JO on 2017-10-20.
 */

public class Cafe extends BaseActivity {
    public static final String DAUMCAFEAPP_PACKAGE_NAME = "net.daum.android.cafe";
    public static final String DAUMCAFEAPP_DOWNLOAD_PAGE = "market://details?id=net.daum.android.cafe";
    public Intent cafe;
    public Context mContext;

    public Cafe(MainActivity mainActivity) {
        this.mContext = mainActivity;
    }

    /**
     * myp scheme을 처리할 수 있는 어플리케이션이 존재하는지 검사
     *
     * @return 사용가능할 경우 true
     */

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(), "다음 공식카페로 이동합니다.", Toast.LENGTH_SHORT).show();
        PublicDefine.cafe = this;
        init();
    }

    private void init() {
        Cafe csurl = new Cafe(new MainActivity());
        if (csurl.canOpenCafeAppURL()) {
            startActivity(csurl.getIntent());
        } else {
            csurl.openCafeAppDownloadPage(new MainActivity());
        }
    }

    public boolean canOpenCafeAppURL() {
            PackageManager pm = mContext.getPackageManager();
            List infos = pm.queryIntentActivities(getIntent(), PackageManager.MATCH_DEFAULT_ONLY);

            return infos != null && infos.size() > 0;
    }

    public boolean existCafeApp() {
        PackageManager pm = mContext.getPackageManager();

        try {
            return (pm.getPackageInfo(DAUMCAFEAPP_PACKAGE_NAME, PackageManager.GET_SIGNATURES) != null);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void openCafeAppDownloadPage(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri iurl = Uri.parse(DAUMCAFEAPP_DOWNLOAD_PAGE);
        intent.setData(iurl);
        context.startActivity(intent);
    }

    public Intent getIntent() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("daumcafe://cafehome?grpcode=seoultrail157"));
        return intent;
    }
}