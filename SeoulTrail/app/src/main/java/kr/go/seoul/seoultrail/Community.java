package kr.go.seoul.seoultrail;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import kr.go.seoul.seoultrail.CommunityManager.CommunityAdapter;
import kr.go.seoul.seoultrail.CommunityManager.Post;

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
    private List<String> postListKeys = new ArrayList<>();
    private CommunityAdapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    SharedPreferences loginPref;

    private boolean login = false;
    private boolean validity = true;
    private boolean pictureYesOrNo = true;

    private static final int SET_ICON_CAMERA = 1;
    private static final int SET_ICON_GALLERY = 2;
    private static final int SET_PICTURE_CAMERA = 3;
    private static final int SET_PICTURE_GALLERY = 4;
    private int icon = 1;
    private int picture = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 닉네임, 비번 정보 getSharedPreferences 통해 저장


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
        storageReference = storage.getReference();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("업로드 중...");



        updateBodyText();


        setPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postPicture(picture);
            }
        });

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

            // 아이콘 설정
            String iconURL = loginPref.getString("iconURL", "");
            Uri uri = Uri.parse(iconURL);
            userIcon.setImageURI(uri);

            // 이름, placeholder, 버튼 설정
            userName.setText(id);
            writeText.setHint(id + "님의 소식을 공유하세요.");
            sendBtn.setBackgroundResource(R.drawable.btn02_posting_off);
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
                            for(DataSnapshot nameSnapshot : dataSnapshot.getChildren()) {
                                String oldName = nameSnapshot.child("name").getValue().toString();
                                if(oldName.equals(newName)) {
                                    Toast.makeText(Community.this, "닉네임을 이미 사용중입니다", Toast.LENGTH_LONG).show();
                                    validity = false;
                                    break;
                                } else if (newName.equals("")) {
                                    Toast.makeText(Community.this, "닉네임을 다시 입력해주세요.", Toast.LENGTH_LONG).show();
                                    validity = false;
                                    break;
                                } else if(validity == false && !oldName.equals(newName)) {
                                    validity = true;
                                }
                            }

                            if(validity == true) {
                                // 'login'파일에 id, pw 저장
                                editor.putString("id", newName);
                                editor.commit();
                                userName.setText(newName);
                                changeLoginScene();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    writeBodyText();
                    postedPicture.setVisibility(View.GONE);
                }

            }
        });

        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(login == false) {
                    postPicture(icon);
                }
            }
        });
    }

    private void changeLoginScene() {
        // 로그인된 화면으로 전환
        loginPref = getSharedPreferences("login", 0);
        String id = loginPref.getString("id", "");
        userName.setText(id);
        writeText.setText("");
        System.out.println(id + "무엇이냐~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        writeText.setHint(id + "님의 소식을 공유하세요.");
        sendBtn.setBackgroundResource(R.drawable.btn02_posting_off);
        login = true;
    }


    // 글 입력 메소드
    private void writeBodyText() {
        loginPref = getSharedPreferences("login", 0);
        String iconURL = loginPref.getString("iconURL", "");
        System.out.println(iconURL + "iconURL 너는 무엇이냐~~~~~~~~~~~~~~~~~~~~~~~~");
        long nowTime = new Date().getTime();
        String username = userName.getText().toString();
        String bodyText = writeText.getText().toString();

        if(postedPicture.getVisibility() == View.GONE) {
            pictureYesOrNo = false;
        } else {
            pictureYesOrNo = true;
        }

        Post post = new Post(iconURL, username, nowTime, bodyText, pictureYesOrNo);
        post.setWriteTime(post.getWriteTime());
        System.out.println("post.getWriteTime은~~~~~~~~~~~~~~~~~" + post.getWriteTime());
        databaseReference.child("posts").push().setValue(post);

        writeText.setText("");
    }

    // 글 적용 메소드
    private void updateBodyText() {
        databaseReference.child("posts").orderByChild("writeTime").limitToFirst(100).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                Post post = dataSnapshot.getValue(Post.class);
                System.out.println("post는~~~~~~~~~~" + post.getBodyText());
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
//                postList.clear();
//                for(DataSnapshot children : dataSnapshot.get) {
//                    Post post = children.getValue(Post.class);
//                    postList.add(post);
//                }
//                Collections.reverse(postList);
//                myAdapter.notifyDataSetChanged();
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

        loginPref = getSharedPreferences("login", 0);
        final SharedPreferences.Editor editor = loginPref.edit();

        if(resultCode != RESULT_OK) return;

        switch(requestCode) {
            case SET_ICON_CAMERA: {
                if(resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    try {
                        userIcon.setImageURI(uri);
                    } catch(OutOfMemoryError e) {
                        Toast.makeText(Community.this, "용량이 부족합니다", Toast.LENGTH_LONG).show();
                    }

                    editor.putString("iconURL", uri.toString());
                    editor.commit();
                }
                break;
            }
            case SET_ICON_GALLERY: {
                if(resultCode == RESULT_OK) {
                    Uri uri = data.getData();

                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    userIcon.setImageBitmap(bitmap);

                    editor.putString("iconURL", uri.toString());
                    editor.commit();
                }
                break;
            }
            case SET_PICTURE_CAMERA: {
                if(resultCode == RESULT_OK) {
                    Uri uri = data.getData();

                    if(uri != null) {
                        progressDialog.show();

                        // firebase 서버에 업로드
                        //String fileName = userName.getText() + " - " + new SimpleDateFormat("yy.MM.dd HH:mm").format(new Date().toString());
                        StorageReference childRef = storageReference.child("image.jpg");
                        UploadTask uploadTask = childRef.putFile(uri);

                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                            }
                        });

                        // 글쓰기창에 띄우기
                        postedPicture.setImageURI(uri);
                        postedPicture.setVisibility(View.VISIBLE);
                    }

                }
                break;
            }
            case SET_PICTURE_GALLERY: {
                if(resultCode == RESULT_OK) {
                    Uri uri = data.getData();

                    if(uri != null) {
                        progressDialog.show();

                        // firebase 서버에 업로드
                        StorageReference childRef = storageReference.child("image.jpg");
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

    private void postPicture(int iconOrPicture) {
        final AlertDialog.Builder pictureDialog = new AlertDialog.Builder(Community.this);

        pictureDialog.setTitle("사진을 게시하세요");

        final Dialog dialog = pictureDialog.create();

        if(iconOrPicture == icon) {
            pictureDialog.setPositiveButton("사진촬영", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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