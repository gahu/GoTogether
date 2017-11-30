package kr.go.seoul.seoultrail.TrailVideo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import kr.go.seoul.seoultrail.Common.FontUtils;
import kr.go.seoul.seoultrail.R;

/**
 * Created by ntsys on 2016-08-16.
 */
public class VideoListAdapter extends ArrayAdapter<VideoInfo> {

    private ViewHolder viewHolder = null;
    private LayoutInflater inflater = null;
    private ArrayList<VideoInfo> infoList = null;
    private Context mContext = null;

    public VideoListAdapter(Context c, int ResourceId,
                            ArrayList<VideoInfo> arrays) {
        super(c, ResourceId, arrays);
        this.inflater = LayoutInflater.from(c);
        this.mContext = c;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public VideoInfo getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {

        View v = convertview;

        if (v == null) {
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.video_list_item, null);
            viewHolder.videoImg = (ImageView) v.findViewById(R.id.video_img);
            viewHolder.videoTitle = (TextView) v.findViewById(R.id.video_title);
            viewHolder.videoRegDate = (TextView) v.findViewById(R.id.video_regdate);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        viewHolder.videoTitle.setText(getItem(position).getVideoTitle());
        viewHolder.videoRegDate.setText(getItem(position).getVideoRegDate());
        Glide.with(mContext).load(getItem(position).getImagURL()).into(viewHolder.videoImg);

        FontUtils.getInstance(this.mContext).setGlobalFont(parent);

        return v;
    }

    class ViewHolder {
        public ImageView videoImg;
        public TextView videoTitle;
        public TextView videoRegDate;
    }

}
