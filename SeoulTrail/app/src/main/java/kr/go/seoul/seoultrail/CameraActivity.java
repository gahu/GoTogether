package kr.go.seoul.seoultrail;

/**
 * Created by bgg89 on 2017-11-26.
 */

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.camera2.CameraDevice;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class CameraActivity extends Activity{

    private String mCurrentPhotoPath; // 현재 사용중인 사진의 경로를 담을 변수
    private Uri mImageCaptureUri = null; // 파일이 갖고 있는 경로 capture, album


    static final int REQUEST_IMAGE_CAPTURE = 2001;
    static final int REQUEST_IMAGE_CROP = 2003;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        captureImageAddToGallery();
    }

    // 경로 저장
    private File catptureImageDirSetup() throws IOException {
        // 파일 이름 설정
        String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(System.currentTimeMillis());
        String imageFileName = "JPEG_" + fileName + ".jpg";
        File imageFile = null;
        // 저장 경로 설정
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Dulle/";
        // 파일 생성하여 Uri로 변환
        File storageDir = new File(path);

        if(!storageDir.exists()) { storageDir.mkdirs(); }

        imageFile = new File(storageDir, imageFileName);
        mCurrentPhotoPath = imageFile.getAbsolutePath();
        Log.i("mCurrentPhotoPath", mCurrentPhotoPath);
        mImageCaptureUri = Uri.fromFile(imageFile);

        return imageFile;
    }

    // 캡쳐사진 저장 dispatchTakePictureIntent
    private void captureImageAddToGallery() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraIntent.resolveActivity(getPackageManager()) != null) {
            try {
                catptureImageDirSetup();
            } catch (IOException ex) {
                Toast.makeText(getApplicationContext(), "사진찍기에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    // 이미지 crop
    private void cropImage() {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        cropIntent.setDataAndType(mImageCaptureUri,"image/*");
        cropIntent.putExtra("scale", true);


        // crop된 이미지를 해당 경로에 저장
        cropIntent.putExtra("output", mImageCaptureUri);

        startActivityForResult(cropIntent, REQUEST_IMAGE_CROP);
    }

    // 갤러리 새로고침, ACTION_MEDIA_MOUNTED는 하나의 폴더, FILE은 하나의 파일을 새로고침 할 때 사용함
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        // 동기화화
        this.sendBroadcast(mediaScanIntent);
        Toast.makeText(this, "사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }

    // ActivityResult = 가져온 사진 뿌리기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK) {
            Toast.makeText(getApplicationContext(), "onActivityResult : RESULT_NOT_OK", Toast.LENGTH_SHORT).show();
        } else {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    cropImage();
                    break;

                case REQUEST_IMAGE_CROP:
                    galleryAddPic();
                    break;
            }
        }
    }

}