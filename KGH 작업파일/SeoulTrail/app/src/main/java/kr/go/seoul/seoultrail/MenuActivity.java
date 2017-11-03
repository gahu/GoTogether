package kr.go.seoul.seoultrail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.astuetz.PagerSlidingTabStrip;

import kr.go.seoul.seoultrail.Common.PublicDefine;
import kr.go.seoul.seoultrail.CourseStamp.CourseStampFragment;

/**
 * Created by ntsys on 2016-08-09.
 */
public class MenuActivity extends BaseActivity {

    private PagerSlidingTabStrip tabsStrip;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.fragment_menu);
        PublicDefine.menuActivty = this;
        // 1. 경로 보기
        Button button = (Button) findViewById(R.id.button);     // 버튼객체를 참조 할 수 있다.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 여기서 Menu Activity를 띄워 줄것인데, 이때 인텐트를 사용해서 띄워 줄것이다.
                // Intent는 MenuActivty를 시스템에서 관리하는데, 시스템에서 관리하니까 띄우고,없애는것도 시스템이 관리
                // 이것을 시스템에 요청을 해야하는데, 이것을 알아먹을수 있는 포맷이 필요한데, 이것을 인텐트라고 한다.
                setContentView(R.layout.fragment_menu);
                Intent intent = new Intent(getApplicationContext(), Course.class);
                startActivityForResult(intent, 101);      // 시스템쪽으로 인텐트를 보낸다. 인텐트 전달하면서, 화면을 띄워줄것을 요청한다.
                // 메뉴 화면에서 다시 이전화면으로 돌아올때 응답을 받아 올때는 startActivityForResult를 사용한다.
                // 이때, startActivityForResult(intent,101); 어떤화면에서 응답을 받았는지 이 코드가 구분자 역할을 한다.
            }
        });


        /* 오류 잡기 */
        // 2. 행사 안내
        Button button2 = (Button) findViewById(R.id.button2);     // 버튼객체를 참조 할 수 있다.
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 여기서 Menu Activity를 띄워 줄것인데, 이때 인텐트를 사용해서 띄워 줄것이다.
                // Intent는 MenuActivty를 시스템에서 관리하는데, 시스템에서 관리하니까 띄우고,없애는것도 시스템이 관리
                // 이것을 시스템에 요청을 해야하는데, 이것을 알아먹을수 있는 포맷이 필요한데, 이것을 인텐트라고 한다.
                Intent intent = new Intent(getApplicationContext(), Event.class);
               // startActivity(intent);
                startActivityForResult(intent, 101);      // 시스템쪽으로 인텐트를 보낸다. 인텐트 전달하면서, 화면을 띄워줄것을 요청한다.
                // 메뉴 화면에서 다시 이전화면으로 돌아올때 응답을 받아 올때는 startActivityForResult를 사용한다.
                // 이때, startActivityForResult(intent,101); 어떤화면에서 응답을 받았는지 이 코드가 구분자 역할을 한다.

            }
        });
        //3. 비디오
        Button button3 = (Button) findViewById(R.id.button3);     // 버튼객체를 참조 할 수 있다.
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 여기서 Menu Activity를 띄워 줄것인데, 이때 인텐트를 사용해서 띄워 줄것이다.
                // Intent는 MenuActivty를 시스템에서 관리하는데, 시스템에서 관리하니까 띄우고,없애는것도 시스템이 관리
                // 이것을 시스템에 요청을 해야하는데, 이것을 알아먹을수 있는 포맷이 필요한데, 이것을 인텐트라고 한다.
                Intent intent = new Intent(getApplicationContext(), Video.class);
                // startActivity(intent);
                startActivityForResult(intent, 101);      // 시스템쪽으로 인텐트를 보낸다. 인텐트 전달하면서, 화면을 띄워줄것을 요청한다.
                // 메뉴 화면에서 다시 이전화면으로 돌아올때 응답을 받아 올때는 startActivityForResult를 사용한다.
                // 이때, startActivityForResult(intent,101); 어떤화면에서 응답을 받았는지 이 코드가 구분자 역할을 한다.

            }
        });
        //4.  코스 스탬프
        Button button4 = (Button) findViewById(R.id.button4);     // 버튼객체를 참조 할 수 있다.
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 여기서 Menu Activity를 띄워 줄것인데, 이때 인텐트를 사용해서 띄워 줄것이다.
                // Intent는 MenuActivty를 시스템에서 관리하는데, 시스템에서 관리하니까 띄우고,없애는것도 시스템이 관리
                // 이것을 시스템에 요청을 해야하는데, 이것을 알아먹을수 있는 포맷이 필요한데, 이것을 인텐트라고 한다.
                Intent intent = new Intent(getApplicationContext(), CourseStampFragment.class);
                // startActivity(intent);
                startActivityForResult(intent, 101);      // 시스템쪽으로 인텐트를 보낸다. 인텐트 전달하면서, 화면을 띄워줄것을 요청한다.
                // 메뉴 화면에서 다시 이전화면으로 돌아올때 응답을 받아 올때는 startActivityForResult를 사용한다.
                // 이때, startActivityForResult(intent,101); 어떤화면에서 응답을 받았는지 이 코드가 구분자 역할을 한다.

            }
        });
    //5. 카페
        Button button5 = (Button) findViewById(R.id.button5);     // 버튼객체를 참조 할 수 있다.
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 여기서 Menu Activity를 띄워 줄것인데, 이때 인텐트를 사용해서 띄워 줄것이다.
                // Intent는 MenuActivty를 시스템에서 관리하는데, 시스템에서 관리하니까 띄우고,없애는것도 시스템이 관리
                // 이것을 시스템에 요청을 해야하는데, 이것을 알아먹을수 있는 포맷이 필요한데, 이것을 인텐트라고 한다.
                Intent intent = new Intent(getApplicationContext(), Cafe.class);
                // startActivity(intent);
                startActivityForResult(intent, 101);      // 시스템쪽으로 인텐트를 보낸다. 인텐트 전달하면서, 화면을 띄워줄것을 요청한다.
                // 메뉴 화면에서 다시 이전화면으로 돌아올때 응답을 받아 올때는 startActivityForResult를 사용한다.
                // 이때, startActivityForResult(intent,101); 어떤화면에서 응답을 받았는지 이 코드가 구분자 역할을 한다.

            }
        });

        //6. 커뮤니티
        Button button6 = (Button) findViewById(R.id.button6);     // 버튼객체를 참조 할 수 있다.
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 여기서 Menu Activity를 띄워 줄것인데, 이때 인텐트를 사용해서 띄워 줄것이다.
                // Intent는 MenuActivty를 시스템에서 관리하는데, 시스템에서 관리하니까 띄우고,없애는것도 시스템이 관리
                // 이것을 시스템에 요청을 해야하는데, 이것을 알아먹을수 있는 포맷이 필요한데, 이것을 인텐트라고 한다.
                Intent intent = new Intent(getApplicationContext(), Community.class);
                // startActivity(intent);
                startActivityForResult(intent, 101);      // 시스템쪽으로 인텐트를 보낸다. 인텐트 전달하면서, 화면을 띄워줄것을 요청한다.
                // 메뉴 화면에서 다시 이전화면으로 돌아올때 응답을 받아 올때는 startActivityForResult를 사용한다.
                // 이때, startActivityForResult(intent,101); 어떤화면에서 응답을 받았는지 이 코드가 구분자 역할을 한다.

            }
        });


        //7. 문의안내
        Button button7 = (Button) findViewById(R.id.button7);     // 버튼객체를 참조 할 수 있다.
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 여기서 Menu Activity를 띄워 줄것인데, 이때 인텐트를 사용해서 띄워 줄것이다.
                // Intent는 MenuActivty를 시스템에서 관리하는데, 시스템에서 관리하니까 띄우고,없애는것도 시스템이 관리
                // 이것을 시스템에 요청을 해야하는데, 이것을 알아먹을수 있는 포맷이 필요한데, 이것을 인텐트라고 한다.
                Intent intent = new Intent(getApplicationContext(), Community.class);
                // startActivity(intent);
                startActivityForResult(intent, 101);      // 시스템쪽으로 인텐트를 보낸다. 인텐트 전달하면서, 화면을 띄워줄것을 요청한다.
                // 메뉴 화면에서 다시 이전화면으로 돌아올때 응답을 받아 올때는 startActivityForResult를 사용한다.
                // 이때, startActivityForResult(intent,101); 어떤화면에서 응답을 받았는지 이 코드가 구분자 역할을 한다.

            }
        });

        //8. 카메라
        Button button8 = (Button) findViewById(R.id.button8);     // 버튼객체를 참조 할 수 있다.
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 여기서 Menu Activity를 띄워 줄것인데, 이때 인텐트를 사용해서 띄워 줄것이다.
                // Intent는 MenuActivty를 시스템에서 관리하는데, 시스템에서 관리하니까 띄우고,없애는것도 시스템이 관리
                // 이것을 시스템에 요청을 해야하는데, 이것을 알아먹을수 있는 포맷이 필요한데, 이것을 인텐트라고 한다.
                Intent intent = new Intent(getApplicationContext(), Camera.class);
                // startActivity(intent);
                startActivityForResult(intent, 101);      // 시스템쪽으로 인텐트를 보낸다. 인텐트 전달하면서, 화면을 띄워줄것을 요청한다.
                // 메뉴 화면에서 다시 이전화면으로 돌아올때 응답을 받아 올때는 startActivityForResult를 사용한다.
                // 이때, startActivityForResult(intent,101); 어떤화면에서 응답을 받았는지 이 코드가 구분자 역할을 한다.

            }
        });
    }




}