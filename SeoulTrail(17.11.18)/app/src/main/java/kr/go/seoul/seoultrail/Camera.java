package kr.go.seoul.seoultrail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import kr.go.seoul.seoultrail.CameraCapture.UploadFile;

/**
 * Created by 임지호 on 2017-10-10.
 * Modified by JO on 2017-11-21.
 */

public class Camera extends BaseActivity{
    private Button captureBtn;
    private Button galleryBtn;

    ImageView captureImageView;

    private String mCurrentPhotoPath; // 현재 사용중인 사진의 경로를 담을 변수
    private Uri mImageCaptureUri, albumUri = null; // 파일이 갖고 있는 경로 capture, album

    private Bitmap bitmap = null;
    private Boolean album = false; // 앨범에서 가져온 사진인지, 직접 바로 찍은지 구분하기 위한 플래그

    static final int REQUEST_IMAGE_CAPTURE = 2001;
    static final int REQUEST_GALLERY = 2002;
    static final int REQUEST_IMAGE_CROP = 2003;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 캡쳐btn과 앨범btn, 확인용 ImageView
        captureBtn = (Button) findViewById(R.id.button_capture);
        galleryBtn = (Button) findViewById(R.id.button_gallery);
        captureImageView = (ImageView) findViewById(R.id.iv_captureImage);

        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImageAddToGallery();
            }
        });

        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doTakeAlbumAction();
            }
        });

        checkPermission();
    }

    // 경로 저장
    private File catptureImageDirSetup() throws IOException {
        // 파일 이름 설정
        //String fileName = "img_" + String.valueOf(System.currentTimeMillis())+ ".jpg";
        String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(System.currentTimeMillis());
        String imageFileName = "JPEG_" + fileName + ".jpg";
        File imageFile = null;
        // 저장 경로 설정
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Dulle" + imageFileName;
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
        /*Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(mImageCaptureUri.getPath());
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);*/
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

    // 앨범 호출
    private void doTakeAlbumAction() {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        gallery.setType("image/*");
        gallery.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(gallery, REQUEST_GALLERY);
    }

    // 이미지 crop
    private void cropImage() {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        cropIntent.setDataAndType(mImageCaptureUri,"image/*");
        //cropIntent.putExtra("outputX", 200); // crop한 이미지의 x축 크기
        //cropIntent.putExtra("outputY", 200); // crop한 이미지의 Y축 크기
        //cropIntent.putExtra("aspectX", 1); // crop 박스의 x축 비율
        //cropIntent.putExtra("aspectY", 1); // crop 박스의 y축 비율
        cropIntent.putExtra("scale", true);

        if(album == false) {
            // crop된 이미지를 해당 경로에 저장
            cropIntent.putExtra("output", mImageCaptureUri);
        } else if(album == true) {
            // crop된 이미지를 해당 경로에 저장장
           cropIntent.putExtra("output", albumUri);
        }

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
                case REQUEST_GALLERY: // 앨범 이미지 가져오기
                    album = true;
                    File albumFile = null;
                    try {
                        albumFile = catptureImageDirSetup();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if(albumFile != null) {
                        albumUri = Uri.fromFile(albumFile); // 앨범 이미지 Crop한 결과는 새로운 위치에 저장
                    }
                    mImageCaptureUri = data.getData(); // 앨범 이미지의 경로

                case REQUEST_IMAGE_CAPTURE:
                    galleryAddPic();
                    break;

                case REQUEST_IMAGE_CROP:
                    cropImage();
                    uploadFile(mCurrentPhotoPath);
                    break;
           }
        }
    }

    /*@Override
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
    }*/

    public void uploadFile(String filePath) {
        String url = "위의 내용이 기록된 php의 위치";
        try {
            UploadFile uploadFile = new UploadFile(new MenuActivity());
            uploadFile.setPath(filePath);
            uploadFile.execute(url);
        } catch (Exception e) {
            //sendLogMsgPHP(e.toString());
        }
    }

}
