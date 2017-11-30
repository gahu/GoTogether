package kr.go.seoul.seoultrail.CommunityManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import kr.go.seoul.seoultrail.BaseActivity;
import kr.go.seoul.seoultrail.Common.TouchImageView;
import kr.go.seoul.seoultrail.R;

/**
 * Created by 지호 on 2017-11-30.
 */

public class ZoomImage extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_popup);

        TouchImageView zoom_Touchimage = (TouchImageView) findViewById(R.id.popup_image_view);

        Intent intent = getIntent();
        Bitmap bitmap = intent.getParcelableExtra("image");

        zoom_Touchimage.setImageBitmap(bitmap);

        ImageView btnClose = (ImageView) findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
