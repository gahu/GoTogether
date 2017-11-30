package kr.go.seoul.seoultrail;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import kr.go.seoul.seoultrail.Common.TouchImageView;

/**
 * Created by ntsys on 2017-02-16.
 */
public class ImageViewPopup extends Activity {
    private TouchImageView touchImageView;
    private ImageView btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_popup);
        int res_id = getIntent().getIntExtra("RESID", 0);

        touchImageView = (TouchImageView) findViewById(R.id.popup_image_view);

        touchImageView.setImageResource(res_id);

        btnClose = (ImageView) findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
