package kr.go.seoul.seoultrail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by 지호 on 2017-10-18.
 */

public class TestAdapter extends RecyclerView.Adapter<ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_list_item, parent, false);
        return new ViewHolder(v);
    }

    List<Post> items = new ArrayList<>();
    public void add(Post data) {
        items.add(data);
        Collections.reverse(items);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post item = items.get(position);
        holder.post_name.setText(item.getName());
        holder.post_writeTime.setText(getDiffTimeText(item.getWriteTime()));
        holder.post_body.setText(item.getBodyText());
        // post_picture 받는 부분 코딩해야함.
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // 시간 표시 메소드(방금 전, x분 전, x시간 전 ...)
    String getDiffTimeText(long targetTime) {
        long nowDate = new Date().getTime();
        Date writtenDate = new Date(targetTime);
        long writeDate = new Date(targetTime).getTime();

        long gap = nowDate - writeDate;

        String ret = "";

        gap = (long)(gap/1000);
        long hour = gap/3600;
        gap = gap%3600;
        long min = gap/60;
        long sec = gap%60;

        if(hour > 24) {
            ret = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(writtenDate);
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


