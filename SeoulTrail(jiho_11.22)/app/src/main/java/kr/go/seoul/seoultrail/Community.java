package kr.go.seoul.seoultrail;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 지호 on 2017-09-29.
 */

public class Community extends BaseActivity {
    private RecyclerView myRecyclerView;
    private Button sendBtn;
    private TextView userName;
    private EditText writeText;
    private ImageView userIcon;
    private ImageButton setPicture;

    private ImageView uploadPicture = null;

    private TestAdapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    SharedPreferences loginPref;

    private boolean validity = true;
    private String nickname;

    private static final int CAMERA_CODE = 1;
    private static final int GALLERY_CODE = 2;

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
        setPicture = (ImageButton) findViewById(R.id.set_picture);


        myRecyclerView.setHasFixedSize(true);

        // 레이아웃 매니저 설정
        myLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(myLayoutManager);

        // 어댑터 설정
        myAdapter = new TestAdapter();
        myRecyclerView.setAdapter(myAdapter);

        // firebase 인스턴스 참조
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        updateBodyText();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeBodyText();
                updateBodyText();
            }
        });

        setPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postPicture();
            }
        });

        loginPref = getSharedPreferences("login", 0);
        String id = loginPref.getString("id", "");
        if(loginPref == null || id.equals("")) {
            setWriteNameDialog();
        } else if(loginPref != null && !id.equals("")){
//            userName.setText(id);
            setWriteNameDialog();
        }

    }

    // 글 입력 메소드
    private void writeBodyText() {
        String username = userName.getText().toString();
        String bodyText = writeText.getText().toString();
        String picture = ".....";



        Post post = new Post(username, bodyText, picture);
        databaseReference.child("posts").push().setValue(post);
        writeText.setText("");
    }

    // 글 적용 메소드
    private void updateBodyText() {
        databaseReference.child("posts").orderByChild("writeTime").limitToLast(100);
        databaseReference.child("posts").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Post post = dataSnapshot.getValue(Post.class);
                myAdapter.add(post);
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

    // 이름, 사진 설정 알림창 설정
    private void setWriteNameDialog() {
        final AlertDialog.Builder nameDialog = new AlertDialog.Builder(Community.this);

        nameDialog.setTitle("닉네임을 설정해주세요");

        final Context context = nameDialog.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View dialogView = inflater.inflate(R.layout.community_dialog, null, false);
        nameDialog.setView(dialogView);

        final EditText setNickname = (EditText) dialogView.findViewById(R.id.set_nickname);
        final Button ok_btn = (Button) dialogView.findViewById(R.id.nickname_ok_btn);
        final Button cancel_btn = (Button) dialogView.findViewById(R.id.nickname_cancel_btn);

        loginPref = getSharedPreferences("login", 0);
        final SharedPreferences.Editor editor = loginPref.edit();

        final AlertDialog dialog = nameDialog.create();

        // 완료 버튼 클릭시 설정
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nickname = setNickname.getText().toString();

                // 닉네임 유효성 검사
                databaseReference.child("posts").orderByKey().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot nameSnapshot : dataSnapshot.getChildren()) {
                            String name = nameSnapshot.child("name").getValue().toString();
                            if(name.equals(nickname)) {
                                Toast.makeText(Community.this, "닉네임을 이미 사용중입니다", Toast.LENGTH_LONG).show();
                                validity = false;
                                break;
                            } else if(validity == false && !name.equals(nickname)) {
                                validity = true;
                            }
                        }

                        if(validity == true) {
                            // 'login'파일에 id, pw 저장
                            editor.putString("id", nickname);
                            editor.commit();
                            userName.setText(nickname);
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void setIconDialog(View v) {
        AlertDialog.Builder iconDialog = new AlertDialog.Builder(Community.this);

        iconDialog.setTitle("사진을 설정해주세요.");

        iconDialog.setPositiveButton("사진찍기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        iconDialog.setNegativeButton("불러오기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        iconDialog.setNeutralButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        iconDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK) return;

        switch(requestCode) {
            case CAMERA_CODE : {
                if(resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    userIcon.setImageURI(uri);
                } else {
                    System.out.println("REULST_OK가 아니란다~~~~~~~~~~~");
                }

//                Bitmap bitmap = null;
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                break;
            }
            case GALLERY_CODE : {
                if(resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    userIcon.setImageBitmap(bitmap);
                }
                break;
            }
        }
    }

    private void postPicture() {
        final AlertDialog.Builder pictureDialog = new AlertDialog.Builder(Community.this);

        pictureDialog.setTitle("사진을 게시하세요");

        final Dialog dialog = pictureDialog.create();

        pictureDialog.setPositiveButton("사진촬영", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, CAMERA_CODE);
            }
        });

        pictureDialog.setNeutralButton("불러오기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (galleryIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(galleryIntent, GALLERY_CODE);
                }else{
                    System.out.println("액티비티가 없댄다~~~~~~~~~~~~~~~~");
                }
            }
        });

        pictureDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });

        pictureDialog.show();
    }



}
