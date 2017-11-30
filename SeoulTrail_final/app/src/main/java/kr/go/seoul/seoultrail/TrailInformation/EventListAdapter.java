package kr.go.seoul.seoultrail.TrailInformation;

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


public class EventListAdapter extends ArrayAdapter<EventInfo> {

    private ViewHolder viewHolder = null;
    private LayoutInflater inflater = null;
    private ArrayList<EventInfo> infoList = null;
    private Context mContext = null;

    public EventListAdapter(Context c, int ResourceId, ArrayList<EventInfo> arrays) {
        super(c, ResourceId, arrays);
        this.inflater = LayoutInflater.from(c);
        this.mContext = c;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public EventInfo getItem(int position) {
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
            v = inflater.inflate(R.layout.information_event_list_item, null);
            viewHolder.eventImg = (ImageView) v.findViewById(R.id.event_img);
            viewHolder.eventTitle = (TextView) v.findViewById(R.id.event_title);
            viewHolder.eventRegDate = (TextView) v.findViewById(R.id.event_regdate);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        viewHolder.eventTitle.setText(getItem(position).getEventTitle());
        viewHolder.eventRegDate.setText(getItem(position).getEventRegDate());
        Glide.with(mContext).load(getItem(position).getImagURL()).into(viewHolder.eventImg);

        FontUtils.getInstance(this.mContext).setGlobalFont(parent);

        return v;
    }

    class ViewHolder {
        public ImageView eventImg;
        public TextView eventTitle;
        public TextView eventRegDate;
    }

}
