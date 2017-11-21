package kr.go.seoul.seoultrail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ntsys on 2016-08-17.
 */
public class BaseActivity extends AppCompatActivity {
    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;

    @Override
    public void onBackPressed() {


        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent1);



        super.onBackPressed();


    }



}
