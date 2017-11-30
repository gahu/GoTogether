package kr.go.seoul.seoultrail;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
<<<<<<< HEAD:SeoulTrail_final/app/src/main/java/kr/go/seoul/seoultrail/Community.java
import com.firebase.ui.storage.images.FirebaseImageLoader;
=======
>>>>>>> ca4c9829395aed517e854978b33530427e00b35b:SeoulTrail/app/src/main/java/kr/go/seoul/seoultrail/Community.java
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import kr.go.seoul.seoultrail.Common.PublicDefine;
import kr.go.seoul.seoultrail.CommunityManager.CommunityAdapter;
import kr.go.seoul.seoultrail.CommunityManager.Post;

import static android.Manifest.permission.CAMERA;

/**
 * Created by 지호 on 2017-09-29.
 */

public class Community extends BaseActivity {
    private RecyclerView myRecyclerView;
    private Button sendBtn;
    private TextView userName;
    private EditText writeText;
    private ImageView userIcon;
    private ImageView setPicture;
    private ImageView postedPicture;
    private ProgressDialog progressDialog;

    private ImageView uploadPicture = null;

    private List<Post> postList = new ArrayList<>();
    private CommunityAdapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    SharedPreferences loginPref;

    private boolean login = false;
    private boolean validity = true;
    private boolean pictureYesOrNo = true;
    private boolean iconYesOrNo = false;

    private static final int SET_ICON_CAMERA = 1;
    private static final int SET_ICON_GALLERY = 2;
    private static final int SET_PICTURE_CAMERA = 3;
    private static final int SET_PICTURE_GALLERY = 4;

    private static final int REQUEST_EXTERNAL_STORAGE = 12;
    private static final int REQUEST_CAMERA = 14;

    private int icon = 1;
    private int picture = 2;

    private Uri mCaptureURI;

    @Override
    public void onBackPressed() {
        PublicDefine.mainActivity.Main_Move();
        // PublicDefine.mainActivity.settingTab(1);
    }
    @Override
    public void onBackPressed() {
        PublicDefine.mainActivity.Main_Move();
        // PublicDefine.mainActivity.settingTab(1);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 레이아웃, 뷰 설정
        setContentView(R.layout.community_activity);
        myRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        sendBtn = (Button) findViewById(R.id.send_btn);
        writeText = (EditText) findViewById(R.id.write_text);
        userName = (TextView) findViewById(R.id.user_name);
        userIcon = (ImageView) findViewById(R.id.user_icon);
        setPicture = (ImageView) findViewById(R.id.set_picture);
        postedPicture = (ImageView) findViewById(R.id.posted_picture);


        myRecyclerView.setHasFixedSize(true);

        // 레이아웃 매니저 설정
        myLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(myLayoutManager);

        // 어댑터 설정
        myAdapter = new CommunityAdapter(postList);
        myRecyclerView.setAdapter(myAdapter);

        // firebase 인스턴스 참조
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://seoultrail-fc963.appspot.com/");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("업로드 중...");

        updateBodyText();

        setPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
<<<<<<< HEAD:SeoulTrail_final/app/src/main/java/kr/go/seoul/seoultrail/Community.java
                // 이름, 본문을 먼저 작성하도록 강제
                AlertDialog.Builder dialog = null;
                dialog = new AlertDialog.Builder(Community.this);
                dialog.setMessage("본문을 먼저 적어주세요.");

                dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                String name = writeText.getText().toString();
                if(login && !name.equals("")) {
                    try {
                        postPicture(picture);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if(login && name.equals("")) {
                    dialog.show();
                } else if(!login && !name.equals("")) {
                    try {
                        postPicture(icon);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
=======
                if(login && !writeText.getText().toString().equals("")) {
                    postPicture(picture);
                } else if(login && writeText.getText().toString().equals("")) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Community.this);
                    dialog.setMessage("본문을 먼저 적어주세요.");

                    dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

>>>>>>> ca4c9829395aed517e854978b33530427e00b35b:SeoulTrail/app/src/main/java/kr/go/seoul/seoultrail/Community.java
                    dialog.show();
                }
            }
        });
        /*setPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(login && !writeText.getText().toString().equals("")) {
                    try {
                        postPicture(picture);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if(login && writeText.getText().toString().equals("")) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Community.this);
                    dialog.setMessage("본문을 먼저 적어주세요.");

                    dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    dialog.show();
                }
            }
        });*/

        loginPref = getSharedPreferences("login", 0);
        final SharedPreferences.Editor editor = loginPref.edit();
        // 일부러 로그인 정보 삭제
//        editor.clear();
//        editor.commit();
        final String id = loginPref.getString("id", "");

        if(loginPref == null && id.equals("")) {
            login = false;
        } else if(loginPref != null && !id.equals("")){
            login = true;

<<<<<<< HEAD:SeoulTrail_final/app/src/main/java/kr/go/seoul/seoultrail/Community.java
            // 자동 로그인된 화면 설정
            if(iconYesOrNo) {
                StorageReference childRef = storageReference.child("icon/icon_" + id + ".jpg");
                Glide.with(this).using(new FirebaseImageLoader()).load(childRef).into(userIcon);
            } else {
                userIcon.setImageResource(R.drawable.image_profile03);
            }

=======
            // 이름, placeholder, 버튼 설정
>>>>>>> ca4c9829395aed517e854978b33530427e00b35b:SeoulTrail/app/src/main/java/kr/go/seoul/seoultrail/Community.java
            userName.setText(id);
            writeText.setHint(id + "님의 소식을 공유하세요.");
            setPicture.setImageResource(R.drawable.btn01_upload_off);
            sendBtn.setBackgroundResource(R.drawable.btn02_posting_off);
        }

        if(ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getParent(), new String[]{CAMERA}, REQUEST_CAMERA);
        }

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(login == false) {
                    final String newName = writeText.getText().toString();

                    // 닉네임 유효성 검사
                    databaseReference.child("posts").orderByKey().addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(login == true) {
                                return;
                            }
                            for(DataSnapshot nameSnapshot : dataSnapshot.getChildren()) {
                                String oldName = nameSnapshot.child("name").getValue().toString();
                                if(oldName.equals(newName)) {
                                    Toast.makeText(Community.this, "닉네임을 이미 사용중입니다", Toast.LENGTH_LONG).show();
                                    validity = false;
                                    hideKeyboard();
                                    break;
                                } else if (newName.equals("")) {
                                    Toast.makeText(Community.this, "닉네임을 입력해주세요.", Toast.LENGTH_LONG).show();
                                    validity = false;
                                    hideKeyboard();
                                    break;
                                } else if(validity == false && !oldName.equals(newName)) {
                                    validity = true;
                                }
                            }

                            if(validity == true) {
                                // 'login'파일에 id, pw 저장
                                editor.putString("id", newName);
                                editor.putBoolean("iconYesOrNo", iconYesOrNo);
                                editor.commit();
                                userName.setText(newName);
                                hideKeyboard();
                                changeLoginScene();
                                return;
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
<<<<<<< HEAD:SeoulTrail_final/app/src/main/java/kr/go/seoul/seoultrail/Community.java
                    hideKeyboard();
=======


>>>>>>> ca4c9829395aed517e854978b33530427e00b35b:SeoulTrail/app/src/main/java/kr/go/seoul/seoultrail/Community.java
                    writeBodyText();
                }

            }
        });

        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 닉네임을 먼저 적도록 강제함 => db에 icon 저장시 name활용해야하기 때문
                if(login == false && !writeText.getText().toString().equals("")) {
<<<<<<< HEAD:SeoulTrail_final/app/src/main/java/kr/go/seoul/seoultrail/Community.java
                    // 카메라 권한 설정
                    try {
                        postPicture(icon);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

=======
                    System.out.println(writeText.getText().toString() + "이거다~~~~~~~~~~~~~~~~~~");
                    postPicture(icon);
>>>>>>> ca4c9829395aed517e854978b33530427e00b35b:SeoulTrail/app/src/main/java/kr/go/seoul/seoultrail/Community.java
                } else if (login == false && writeText.getText().toString().equals("")) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Community.this);
                    dialog.setMessage("닉네임을 먼저 적어주세요");

                    dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    dialog.show();
<<<<<<< HEAD:SeoulTrail_final/app/src/main/java/kr/go/seoul/seoultrail/Community.java
                }
            }
        });

        writeText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
=======
>>>>>>> ca4c9829395aed517e854978b33530427e00b35b:SeoulTrail/app/src/main/java/kr/go/seoul/seoultrail/Community.java
                }
            }
        });

    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(writeText.getWindowToken(), 0);
    }

    private void changeLoginScene() {
        // 로그인된 화면으로 전환
        loginPref = getSharedPreferences("login", 0);
        String id = loginPref.getString("id", "");

        // 아이콘 미설정시 기본이미지로 설정
        if(!iconYesOrNo) {
            userIcon.setImageResource(R.drawable.image_profile03);
        }

        userName.setText(id);
        writeText.setText("");
        writeText.setHint(id + "님의 소식을 공유하세요.");
        setPicture.setImageResource(R.drawable.btn01_upload_off);
        sendBtn.setBackgroundResource(R.drawable.btn02_posting_off);
        login = true;

        Toast.makeText(Community.this, "닉네임이 등록되었습니다", Toast.LENGTH_LONG).show();
    }


    // 글 입력 메소드
    private void writeBodyText() {
        long nowTime = new Date().getTime();
        String username = userName.getText().toString();
        String bodyText = writeText.getText().toString();
<<<<<<< HEAD:SeoulTrail_final/app/src/main/java/kr/go/seoul/seoultrail/Community.java
        if (postedPicture.getVisibility() == View.GONE) {
=======
        if(postedPicture.getVisibility() == View.GONE) {
>>>>>>> ca4c9829395aed517e854978b33530427e00b35b:SeoulTrail/app/src/main/java/kr/go/seoul/seoultrail/Community.java
            pictureYesOrNo = false;
        } else {
            pictureYesOrNo = true;
        }
<<<<<<< HEAD:SeoulTrail_final/app/src/main/java/kr/go/seoul/seoultrail/Community.java
        if(bodyText.equals("") && !pictureYesOrNo) {
            Toast.makeText(this, "글을 입력해주세요.", Toast.LENGTH_LONG).show();
            return;
        }
=======

>>>>>>> ca4c9829395aed517e854978b33530427e00b35b:SeoulTrail/app/src/main/java/kr/go/seoul/seoultrail/Community.java
        Post post = new Post(iconYesOrNo, username, nowTime, bodyText, pictureYesOrNo);
        post.setWriteTime(post.getWriteTime());
        databaseReference.child("posts").push().setValue(post);

        // 글쓰기 화면 초기화
        writeText.setText("");
        postedPicture.setVisibility(View.GONE);
<<<<<<< HEAD:SeoulTrail_final/app/src/main/java/kr/go/seoul/seoultrail/Community.java
        Toast.makeText(this, "글이 게시되었습니다.", Toast.LENGTH_LONG).show();
=======
>>>>>>> ca4c9829395aed517e854978b33530427e00b35b:SeoulTrail/app/src/main/java/kr/go/seoul/seoultrail/Community.java
    }


    // 글 적용 메소드
    private void updateBodyText() {
        databaseReference.child("posts").orderByChild("writeTime").limitToFirst(100).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                // 최신순 정렬
                Post post = dataSnapshot.getValue(Post.class);
                postList.add(post);
                Collections.sort(postList, new Comparator<Post>() {
                    @Override
                    public int compare(Post post, Post t1) {
                        if(post.getWriteTime() > t1.getWriteTime()) {
                            return -1;
                        } else if(post.getWriteTime() < t1.getWriteTime()) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK) return;

        switch(requestCode) {
            case SET_ICON_CAMERA: {
                if(resultCode == RESULT_OK) {
<<<<<<< HEAD:SeoulTrail_final/app/src/main/java/kr/go/seoul/seoultrail/Community.java


                    progressDialog.show();

                    // firebase 서버에 업로드
                    String iconFileName = "icon_" + writeText.getText() + ".jpg";
                    StorageReference childRef = storageReference.child("icon/" + iconFileName);
                    UploadTask uploadTask = childRef.putFile(mCaptureURI);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                        }
                    });

                    userIcon.setImageURI(mCaptureURI);

                    iconYesOrNo = true;


=======
                    Uri uri = data.getData();

                    if(uri != null) {
                        progressDialog.show();

                        // firebase 서버에 업로드
                        String iconFileName = "icon_" + writeText.getText() + ".jpg";
                        StorageReference childRef = storageReference.child("icon/" + iconFileName);
                        UploadTask uploadTask = childRef.putFile(uri);

                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                            }
                        });

                        userIcon.setImageURI(uri);

                        iconYesOrNo = true;
                    }

                }
                break;
            }
            case SET_ICON_GALLERY: {
                if(resultCode == RESULT_OK) {
                    Uri uri = data.getData();

                    if(uri != null) {
                        progressDialog.show();

                        // firebase 서버에 업로드
                        String iconFileName = "icon_" + writeText.getText() + ".jpg";
                        StorageReference childRef = storageReference.child("icon/" + iconFileName);
                        UploadTask uploadTask = childRef.putFile(uri);

                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                            }
                        });

                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        userIcon.setImageBitmap(bitmap);
                        iconYesOrNo = true;
                    }
>>>>>>> ca4c9829395aed517e854978b33530427e00b35b:SeoulTrail/app/src/main/java/kr/go/seoul/seoultrail/Community.java
                }
                break;
            }
            case SET_ICON_GALLERY: {
                if(resultCode == RESULT_OK) {
                    Uri uri = data.getData();

                    if(uri != null) {
                        progressDialog.show();

                        // firebase 서버에 업로드
<<<<<<< HEAD:SeoulTrail_final/app/src/main/java/kr/go/seoul/seoultrail/Community.java
                        String iconFileName = "icon_" + writeText.getText() + ".jpg";
                        StorageReference childRef = storageReference.child("icon/" + iconFileName);
=======
                        System.out.println("writeTExt는 ~~~~~~~~~~~~~~~~~~" +writeText.getText().toString());
                        String fileName = userName.getText().toString() + "_" + writeText.getText().toString() + ".jpg";
                        StorageReference childRef = storageReference.child("image/image_" + fileName);
>>>>>>> ca4c9829395aed517e854978b33530427e00b35b:SeoulTrail/app/src/main/java/kr/go/seoul/seoultrail/Community.java
                        UploadTask uploadTask = childRef.putFile(uri);

                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                            }
                        });

                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        userIcon.setImageBitmap(bitmap);
                        iconYesOrNo = true;
                    }
                }
                break;
            }
            case SET_PICTURE_CAMERA: {
                if(resultCode == RESULT_OK) {

                    progressDialog.show();

                    // firebase 서버에 업로드
                    String fileName = userName.getText().toString() + "_" + writeText.getText().toString() + ".jpg";
                    StorageReference childRef = storageReference.child("image/image_" + fileName);
                    UploadTask uploadTask = childRef.putFile(mCaptureURI);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                        }
                    });

                    // 글쓰기창에 띄우기
                    postedPicture.setImageURI(mCaptureURI);
                    postedPicture.setVisibility(View.VISIBLE);
                }

                break;
            }
            case SET_PICTURE_GALLERY: {
                if(resultCode == RESULT_OK) {
                    Uri uri = data.getData();

                    if(uri != null) {
                        progressDialog.show();

                        // firebase 서버에 업로드
                        String fileName = userName.getText().toString() + "_" + writeText.getText().toString() + ".jpg";
                        StorageReference childRef = storageReference.child("image/image_" + fileName);
                        UploadTask uploadTask = childRef.putFile(uri);

                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                            }
                        });

                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        postedPicture.setImageBitmap(bitmap);
                        postedPicture.setVisibility(View.VISIBLE);
                    }
                }
                break;
            }
        }
    }

    private void postPicture(int iconOrPicture) throws IOException{
        final AlertDialog.Builder pictureDialog = new AlertDialog.Builder(Community.this);

        pictureDialog.setTitle("사진을 게시하세요");

        final Dialog dialog = pictureDialog.create();

        if(iconOrPicture == icon) {
            pictureDialog.setPositiveButton("사진촬영", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(System.currentTimeMillis());
                    String imageFileName = fileName + ".jpg";
                    File stroageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Dulle/" + imageFileName);
                    mCaptureURI = FileProvider.getUriForFile(getApplicationContext(), "kr.go.seoul.seoultrail.provider", stroageDir);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCaptureURI);
                    startActivityForResult(takePictureIntent, SET_ICON_CAMERA);
                }
            });

            pictureDialog.setNeutralButton("불러오기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryIntent.setType("image/*");
                    if (galleryIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(galleryIntent, SET_ICON_GALLERY);
                    }else{
                        System.out.println("액티비티가 없댄다~~~~~~~~~~~~~~~~");
                    }
                }
            });
        } else if (iconOrPicture == picture) {
            pictureDialog.setPositiveButton("사진촬영", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(System.currentTimeMillis());
                    String imageFileName = fileName + ".jpg";
                    File stroageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Dulle/" + imageFileName);
                    mCaptureURI = FileProvider.getUriForFile(getApplicationContext(), "kr.go.seoul.seoultrail.provider", stroageDir);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCaptureURI);
                    startActivityForResult(takePictureIntent, SET_PICTURE_CAMERA);
                }
            });

            pictureDialog.setNeutralButton("불러오기", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    if (galleryIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(galleryIntent, SET_PICTURE_GALLERY);
                    }else{
                        System.out.println("액티비티가 없댄다~~~~~~~~~~~~~~~~");
                    }
                }
            });
        }

        pictureDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });

        pictureDialog.show();
    }



}