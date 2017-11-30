package kr.go.seoul.seoultrail.CommunityManager;

import android.content.Context;
<<<<<<< HEAD:SeoulTrail_final/app/src/main/java/kr/go/seoul/seoultrail/CommunityManager/CommunityAdapter.java
import android.content.Intent;
=======
>>>>>>> ca4c9829395aed517e854978b33530427e00b35b:SeoulTrail/app/src/main/java/kr/go/seoul/seoultrail/CommunityManager/CommunityAdapter.java
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
<<<<<<< HEAD:SeoulTrail_final/app/src/main/java/kr/go/seoul/seoultrail/CommunityManager/CommunityAdapter.java
=======
import com.google.android.gms.tasks.OnSuccessListener;
>>>>>>> ca4c9829395aed517e854978b33530427e00b35b:SeoulTrail/app/src/main/java/kr/go/seoul/seoultrail/CommunityManager/CommunityAdapter.java
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import kr.go.seoul.seoultrail.Community;
import kr.go.seoul.seoultrail.R;

/**
 * Created by 지호 on 2017-10-18.
 */

public class CommunityAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<Post> postList;
    private Context context;

    public CommunityAdapter(List<Post> postList) {
        this.postList = postList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Post item = postList.get(position);
        if(item.getIconYesOrNo()) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
            StorageReference childRef = storageReference.child("icon/icon_" + item.getName() + ".jpg");

            Glide.with(context).using(new FirebaseImageLoader()).load(childRef).into(holder.post_icon);
        } else {
            holder.post_icon.setImageResource(R.drawable.image_profile03);
        }
        holder.post_name.setText(item.getName());
        holder.post_writeTime.setText(getDiffTimeText(item.getWriteTime()));
        holder.post_body.setText(item.getBodyText());
        if(item.getPictureYesOrNo()) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
<<<<<<< HEAD:SeoulTrail_final/app/src/main/java/kr/go/seoul/seoultrail/CommunityManager/CommunityAdapter.java
            StorageReference storageReference = storage.getReferenceFromUrl("gs://seoultrail-fc963.appspot.com/");
=======
            StorageReference storageReference = storage.getReference();
>>>>>>> ca4c9829395aed517e854978b33530427e00b35b:SeoulTrail/app/src/main/java/kr/go/seoul/seoultrail/CommunityManager/CommunityAdapter.java

            String fileName = item.getName() + "_" + item.getBodyText() + ".jpg";
            StorageReference childRef = storageReference.child("image/image_" + fileName);

            Glide.with(context).using(new FirebaseImageLoader()).load(childRef).into(holder.post_picture);
            holder.post_picture.setVisibility(View.VISIBLE);
        } else {
            holder.post_picture.setVisibility(View.GONE);
        }

        holder.post_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = null;
                if((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)) {
                    Drawable drawable = holder.post_picture.getDrawable();
                    if(drawable instanceof BitmapDrawable) {
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                        if(bitmapDrawable.getBitmap() != null) {
                            bitmap = bitmapDrawable.getBitmap();
                        }
                    }
                    bitmap = BitmapFactory.decodeResource(context.getResources(), holder.post_picture.getImageAlpha());

                    /*Intent intent = new Intent(context, ImageViewPopup.class);
                    intent.putExtra("RESID", drawable.getClass().getEnumConstants());
                    context.startActivity(intent);*/
                    Intent intent = new Intent(context, ZoomImage.class);
                    intent.putExtra("image", bitmap);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "롤리팝 이전 버전은 이미지 확대가 불가능합니다.", Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    // 시간 표시 메소드(방금 전, x분 전, x시간 전 ...)
    String getDiffTimeText(long targetTime) {
        System.out.println("targetTime은 뭐다~~~~~~~~~~~~~" + targetTime);
        long nowDate = new Date().getTime();
        Date writtenDate = new Date(targetTime);
        System.out.println("post.getWritetime의 반대는 ~~~~~~~~~~~~~~~~~" + writtenDate);
        long writeDate = new Date(targetTime).getTime();

        long gap = nowDate - writeDate;

        String ret = "";

        gap = (long)(gap/1000);
        long hour = gap/3600;
        gap = gap%3600;
        long min = gap/60;
        long sec = gap%60;

        if(hour > 24) {
            ret = new SimpleDateFormat("yyyy.MM.dd HH:mm").format(writtenDate);
        } else if(hour > 0) {
            ret = hour + "시간 전";
        } else if(min > 0) {
            ret = min+ "분 전";
        } else {
            ret = "방금 전";
        }
        return ret;
    }
}


