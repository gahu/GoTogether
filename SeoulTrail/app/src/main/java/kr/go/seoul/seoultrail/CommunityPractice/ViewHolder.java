package kr.go.seoul.seoultrail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 지호 on 2017-10-18.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView mStudentNameText, mScoreText;

    public ViewHolder(View itemView) {
        super(itemView);
        mStudentNameText = (TextView) itemView.findViewById(R.id.textView1);
        mScoreText = (TextView) itemView.findViewById(R.id.textView2);
    }
}
