package kr.go.seoul.seoultrail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ntsys on 2017-02-14.
 */
public class GuideActivity extends BaseActivity {

    private ImageView btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        /*
        btnClose = (ImageView) findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        */
    }

    public void callNumber(View view) {
        String number = ((TextView) view).getText().toString();
        if (number.contains("~")) {
            number = number.split("~")[0];
        }
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
        startActivity(intent);
    }
}
