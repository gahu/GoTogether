package kr.go.seoul.seoultrail.CommunityManager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kr.go.seoul.seoultrail.R;

/**
 * Created by 지호 on 2017-10-18.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView post_name, post_body, post_writeTime;
    public ImageView post_icon, post_picture;

    public ViewHolder(View itemView) {
        super(itemView);
        post_icon = (ImageView) itemView.findViewById(R.id.post_icon);
        post_name = (TextView) itemView.findViewById(R.id.post_name);
        post_body = (TextView) itemView.findViewById(R.id.post_body);
        post_writeTime = (TextView) itemView.findViewById(R.id.post_writeTime);
        post_picture = (ImageView) itemView.findViewById(R.id.post_picture);
    }
}
