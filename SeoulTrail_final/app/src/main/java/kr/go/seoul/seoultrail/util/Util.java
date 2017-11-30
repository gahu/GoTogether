/*
package kr.go.seoul.seoultrail.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import kr.go.seoul.seoultrail.MyApp;

*/
/**
 * Created by smile on 2017. 11. 19..
 *//*


public class Util {

    public static void getDisplay(Activity con) {
        con.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        con.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        con.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        WindowManager wm = (WindowManager) con.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        con.getWindowManager().getDefaultDisplay().getMetrics(dm);
        wm.getDefaultDisplay().getMetrics(dm);
        MyApp.gXResolution = dm.widthPixels;
        MyApp.gYResolution = dm.heightPixels;
        MyApp.Density = (float) (160.0 / dm.densityDpi);



    }
    public static void getDisplay1(Activity con) {
        int x= MyApp.gXResolution;
        int y=MyApp.gYResolution;
        if(x>y){
            MyApp.gXResolution = y;
            MyApp.gYResolution = x;
        }

    }
    public static void fitImagetoImageView(Context context, ImageView imageview, int resid) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inSampleSize = 1;
        options.inPurgeable = true;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), resid, options);
        int width = image.getWidth();
        int height = image.getHeight();
        int iMenuWidth = (int) (width * (MyApp.gXResolution / 600.0));
        int iMenuHeight = (int) (height * (MyApp.gYResolution / 1024.0));
        Bitmap resized = Bitmap.createScaledBitmap(image, iMenuWidth, iMenuHeight, true);
        if (resized != image)
            image.recycle();
        imageview.setImageBitmap(resized);
        imageview.invalidate();
    }
}
*/
