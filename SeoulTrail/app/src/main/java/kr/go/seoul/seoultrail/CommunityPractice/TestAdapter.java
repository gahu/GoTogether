package kr.go.seoul.seoultrail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 지호 on 2017-10-18.
 */

public class TestAdapter extends RecyclerView.Adapter<ViewHolder> {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);
        return new ViewHolder(v);
    }

    List<Student> items = new ArrayList<>();
    public void add(Student data) {
        items.add(data);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Student item = items.get(position);
        holder.mStudentNameText.setText(item.getName());
        holder.mScoreText.setText(item.getScore());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}


