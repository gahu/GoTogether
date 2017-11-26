package kr.go.seoul.seoultrail.TrailInformation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import kr.go.seoul.seoultrail.R;

/**
 * Created by ntsys on 2016-08-09.
 */
public class TourInfoFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private static TourInfoFragment fragment;
    private int mPage;

    private ScrollView mainScrollView;
    private ImageView tourCompletIfon;

    public static TourInfoFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fragment = new TourInfoFragment();
        fragment.setArguments(args);
        //PublicDefine.tourInfoFragment = fragment;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.information_tour_info, container, false);

        mainScrollView = (ScrollView) view.findViewById(R.id.main_scroll_view);
        tourCompletIfon = (ImageView) view.findViewById(R.id.stamp_complete_info);

        return view;
    }

    public void setScroll() {
        int x = tourCompletIfon.getLeft();
        int y = tourCompletIfon.getTop();
        mainScrollView.smoothScrollTo(x, y);
    }
}