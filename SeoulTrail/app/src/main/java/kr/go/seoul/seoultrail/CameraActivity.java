package kr.go.seoul.seoultrail;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by 임지호 on 2017-10-10.
 * Modified by JO on 2017-11-21.
 */

public class CameraActivity extends BaseActivity{

    private String mCurrentPhotoPath; // 현재 사용중인 사진의 경로를 담을 변수
    private Uri mImageCaptureUri = null; // 파일이 갖고 있는 경로 capture, album


    static final int REQUEST_IMAGE_CAPTURE = 2001;
    static final int REQUEST_IMAGE_CROP = 2003;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            captureImageAddToGallery();
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    // 경로 저장
    private File catptureImageDirSetup() throws IOException {
        // 파일 이름 설정
        String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(System.currentTimeMillis());
        String imageFileName = fileName + ".jpg";

//        File stroageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Dulle/" + imageFileName);
//        mCurrentPhotoPath = "file:" + stroageDir.getAbsolutePath();
//        System.out.println("mCurrentPhotopath~~~~~~~~~~~~~~~" + mCurrentPhotoPath);
//        Log.i("mCurrentPhotoPath", mCurrentPhotoPath);

        File stroageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Dulle/" + imageFileName);

        mCurrentPhotoPath = stroageDir.getAbsolutePath();

        return stroageDir;
    }

    // 캡쳐사진 저장 dispatchTakePictureIntent
    private void captureImageAddToGallery() throws IOException {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        if(cameraIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = catptureImageDirSetup();
            } catch (IOException ex) {
                Toast.makeText(getApplicationContext(), "사진찍기에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }

            if(photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", catptureImageDirSetup());
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    // 갤러리 새로고침, ACTION_MEDIA_MOUNTED는 하나의 폴더, FILE은 하나의 파일을 새로고침 할 때 사용함
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

        File f = new File(mCurrentPhotoPath);
        Uri contentUri = FileProvider.getUriForFile(this, "kr.go.seoul.seoultrail.provider", f);
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
//                    galleryAddPic();
                    Uri uri = Uri.parse(mCurrentPhotoPath);

                    if(uri != null) {

                        MediaScannerConnection.scanFile(getApplicationContext(), new String[]{uri.getPath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("ExternalStorage", "Scanned " + path + ":");
                                Log.i("ExternalStorage", "-> uri=" + uri);
//                            Toast.makeText(getApplicationContext(), "사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

                    File f = new File(mCurrentPhotoPath);
                    Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", f);
                    mediaScanIntent.setData(contentUri);
                    // 동기화화
                    this.sendBroadcast(mediaScanIntent);
                    Toast.makeText(this, "사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show();

                    break;

                case REQUEST_IMAGE_CROP:
//                    Uri uri = data.getData();
//                    addImageToGallery(uri.toString(), getApplicationContext());
//                    galleryAddPic();
//                    break;
            }
        }
    }

}