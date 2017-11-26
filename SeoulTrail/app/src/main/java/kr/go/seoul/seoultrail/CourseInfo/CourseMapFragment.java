/*
 * Copyright (C) 2011 Ievgenii Nazaruk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kr.go.seoul.seoultrail.CourseInfo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

import kr.go.seoul.seoultrail.Common.PublicDefine;
import kr.go.seoul.seoultrail.NMap.NMapViewer;

public class CourseMapFragment extends ActivityHostFragment {

    public static final String ARG_PAGE = "ARG_PAGE";
    private CoursePointListFragment nextFrag;
    private ArrayList<String[]> data;

    public static CourseMapFragment newInstance() {
        CourseMapFragment fragment = new CourseMapFragment();
        return fragment;
    }

    @Override
    protected Class<? extends Activity> getActivityClass() {
        SharedPreferences pref = this.getActivity().getSharedPreferences("SYSTEM", 0);
        SharedPreferences.Editor edit = pref.edit();
        edit.putBoolean("ISMAP", true);
        edit.commit();
        return NMapViewer.class;
    }

    public void setCourse(int mPage) {
        NMapViewer.setCourse(mPage + 1);
    }

    public void setSelectionPointPin(int potions) {
        NMapViewer.selectionPointPin(potions);
    }

    public void setShowPointPin() {
        NMapViewer.showPointPin();
    }

    public void showPointList(ArrayList<String[]> cotCoordList) {
        nextFrag = CoursePointListFragment.newInstance();
        startFragment(PublicDefine.courseListFragment.getFragmentManager(), nextFrag.getClass());

        data = new ArrayList<>();
        for (int i = 0; i < cotCoordList.size(); i++) {
            this.data.add(cotCoordList.get(i));
        }
        setCoursePointListHandler.sendEmptyMessageDelayed(0, 300);
    }

    public Handler setCoursePointListHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            nextFrag.setCoursePointList(data);
        }
    };
}
