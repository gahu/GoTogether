package kr.go.seoul.seoultrail;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by 임지호 on 2017-10-10.
 * Modified by JO on 2017-11-21.
 */

public class CameraActivity extends BaseActivity{

    private String mCurrentPhotoPath; // 현재 사용중인 사진의 경로를 담을 변수

    private static final int REQUEST_EXTERNAL_STORAGE = 12;
    private static final int REQUEST_CAMERA = 14;

    private static final int REQUEST_IMAGE_CAPTURE = 2001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 저장공간 permission 체크
        int permissionWriteStorage = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permissionCamera = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        if(permissionWriteStorage != PackageManager.PERMISSION_GRANTED || permissionCamera != PackageManager.PERMISSION_GRANTED) {
            if(permissionWriteStorage != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getParent(), new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, REQUEST_EXTERNAL_STORAGE);
            }
        } else {
            try {
                captureImageAddToGallery();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // 저장공간 permission 체크
        int permissionWriteStorage = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permissionCamera = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        if(permissionWriteStorage != PackageManager.PERMISSION_GRANTED || permissionCamera != PackageManager.PERMISSION_GRANTED) {
            if(permissionWriteStorage != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getParent(), new String[]{WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
            }

            if(permissionCamera != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getParent(), new String[]{CAMERA}, REQUEST_EXTERNAL_STORAGE);
            }

        } else {
            try {
                captureImageAddToGallery();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // 권한 허용 성공시!
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한 허가
                    try {
                        captureImageAddToGallery();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // 권한 거부
                    Toast.makeText(getApplicationContext(), "권한이 없어 카메라를 실행하지 못합니다.", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    // 경로 저장
    private File catptureImageDirSetup() throws IOException {
        // 파일 이름 설정
        String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(System.currentTimeMillis());
        String imageFileName = fileName + ".jpg";

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

    // ActivityResult = 가져온 사진 뿌리기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK) {
            Toast.makeText(getApplicationContext(), "카메라가 취소되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE: {
                    Uri uri = Uri.parse(mCurrentPhotoPath);

                    if (uri != null) {

                        MediaScannerConnection.scanFile(getApplicationContext(), new String[]{uri.getPath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("ExternalStorage", "Scanned " + path + ":");
                                Log.i("ExternalStorage", "-> uri=" + uri);
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
                }
            }
        }
    }
}