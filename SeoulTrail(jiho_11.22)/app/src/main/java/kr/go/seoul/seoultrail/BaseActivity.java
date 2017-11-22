package kr.go.seoul.seoultrail;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.nethru.android.applogging.NethruAppLoggingException;
import com.nethru.android.applogging.WLAppTrackLogging;

import kr.go.seoul.seoultrail.Common.FontUtils;
import kr.go.seoul.seoultrail.Common.PublicDefine;

/**
 * Created by ntsys on 2016-08-17.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        AlertDialog.Builder alert_confirm = new AlertDialog.Builder(BaseActivity.this);
        alert_confirm.setMessage(FontUtils.getInstance(this).typeface("\"확인\" 버튼 클릭 시\n App 이 종료됩니다.")).setCancelable(false).setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            WLAppTrackLogging.logging(BaseActivity.this, PublicDefine.appLoggingActionKey + "APP_END");
                        } catch (NethruAppLoggingException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.exit(0);
                    }
                }).setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog alert = alert_confirm.create();
        final Typeface mTypeface = Typeface.createFromAsset(this.getAssets(), "NotoSansCJKkr-DemiLight.otf");
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alert.getButton(Dialog.BUTTON_POSITIVE).setTypeface(mTypeface);
                alert.getButton(Dialog.BUTTON_NEGATIVE).setTypeface(mTypeface);
            }
        });
        alert.show();
        return false;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        FontUtils.getInstance(this).setGlobalFont(getWindow().getDecorView());
    }
}
