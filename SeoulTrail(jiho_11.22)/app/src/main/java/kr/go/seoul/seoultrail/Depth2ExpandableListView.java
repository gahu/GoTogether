package kr.go.seoul.seoultrail;

import android.content.Context;
import android.widget.ExpandableListView;

/**
 * Created by 지호 on 2017-10-26.
 */

public class Depth2ExpandableListView extends ExpandableListView {
    public Depth2ExpandableListView(Context context) {
        super(context);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //999999 is a size in pixels. ExpandableListView requires a maximum height in order to do measurement calculations.
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(999999, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
