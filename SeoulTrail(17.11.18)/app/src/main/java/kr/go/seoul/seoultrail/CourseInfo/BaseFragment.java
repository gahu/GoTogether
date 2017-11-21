package kr.go.seoul.seoultrail.CourseInfo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import kr.go.seoul.seoultrail.R;

/**
 * Created by ntsys on 2016-08-11.
 */
public class BaseFragment extends Fragment {

    protected void startFragment(FragmentManager fm, Class<? extends BaseFragment> fragmentClass) {
        BaseFragment fragment = null;
        try {
            fragment = fragmentClass.newInstance();
        } catch (java.lang.InstantiationException e) {
            Log.e("NTsys", "초기화 오류 발생");
        } catch (IllegalAccessException e) {
            Log.e("NTsys", "초기화 오류 발생");
        }
        if (fragment == null) {
            throw new IllegalStateException("cannot start fragment. " + fragmentClass.getName());
        }
        if (fm == null) {
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.content, fragment).addToBackStack(null).commit();
        } else {
            fm.beginTransaction().add(R.id.content, fragment).addToBackStack(null).commit();
        }
    }

    protected void finishFragment() {
        getFragmentManager().popBackStack();
    }

    public void onPressedBackkey() {
        finishFragment();
    }
}