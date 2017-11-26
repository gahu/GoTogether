package kr.go.seoul.seoultrail.CommunityManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import kr.go.seoul.seoultrail.R;

/**
 * Created by 지호 on 2017-10-18.
 */

public class CommunityAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<Post> postList;

    public CommunityAdapter(List<Post> postList) {
        this.postList = postList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Post item = postList.get(position);
        if(!item.getIconURL().equals("")) {
            System.out.println("not null " + item.getIconURL());
            Uri uri = Uri.parse(item.getIconURL());
            holder.post_icon.setImageURI(uri);
        } else {
            System.out.println("null " + item.getIconURL());
            holder.post_icon.setImageResource(R.drawable.image_profile03);
        }
        holder.post_name.setText(item.getName());
        holder.post_writeTime.setText(getDiffTimeText(item.getWriteTime()));
        holder.post_body.setText(item.getBodyText());
        if(item.getPictureYesOrNo()) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
            StorageReference childRef = storageReference.child("image.jpg");

            childRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    holder.post_picture.setImageBitmap(bitmap);
                    holder.post_picture.setVisibility(View.VISIBLE);
                }
            });
        } else {
            holder.post_picture.setVisibility(View.GONE);
        }
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


