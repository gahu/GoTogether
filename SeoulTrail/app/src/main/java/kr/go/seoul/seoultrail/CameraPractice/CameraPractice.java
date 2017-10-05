package kr.go.seoul.seoultrail.CameraPractice;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

import kr.go.seoul.seoultrail.R;

/**
 * Created by 지호 on 2017-10-04.
 */

public class CameraPractice extends Activity {
    private Button captureBtn;
    private Button galleryBtn;
    private ImageView captureImageView;

    private Uri mImageCaptureUri = null;
    private Bitmap bitmap = null;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_GALLERY = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        captureBtn = (Button) findViewById(R.id.button_capture);
        galleryBtn = (Button) findViewById(R.id.button_gallery);
        captureImageView = (ImageView) findViewById(R.id.iv_captureImage);

        captureBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                catptureImageDirSetup();
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);

            }
        });

        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK);
                gallery.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(gallery, REQUEST_GALLERY);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK) {
            Log.println(Log.DEBUG, "DEBUG", "resultCode is not RESULT_OK. resultCode is " + resultCode);
        } else {
            if(requestCode == REQUEST_IMAGE_CAPTURE) {
                // intent에서 넘어온 Uri에서 직접 이미지를 찾아온다
                try {
                    AssetFileDescriptor afd = getContentResolver().openAssetFileDescriptor(mImageCaptureUri, "r");
                    BitmapFactory.Options opt = new BitmapFactory.Options();
                    opt.inSampleSize = 4;
                    bitmap = BitmapFactory.decodeFileDescriptor(afd.getFileDescriptor(), null, opt);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                captureImageView.setBackgroundDrawable(drawable);

                captureImageAddToGallery();
            } else if(requestCode == REQUEST_GALLERY) {
                // ....
            }
        }
    }

    private void catptureImageDirSetup() {
        // 파일 이름 설정
        String fileName = "img_" + System.currentTimeMillis() + ".png";
        // 저장 경로 설정
        String path = Environment.getExternalStorageDirectory() + "/DCIM/Dulle";
        // 파일 생성하여 Uri로 변환
        File dir = new File(path);
        if(!dir.exists()) { dir.mkdirs(); }
        File file = new File(dir, fileName);
        mImageCaptureUri = Uri.fromFile(file);
    }

    private void captureImageAddToGallery() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(mImageCaptureUri.getPath());
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

}
