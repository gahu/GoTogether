package kr.go.seoul.seoultrail.Common;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ntsys on 2016-10-28.
 */
public class FontUtils {

    private static FontUtils fontUtils;
    private static Typeface mTypeface = null;
    private static Context context;

    public FontUtils(Context context) {
        this.context = context;
    }

    public static FontUtils getInstance(Context context) {
        if (fontUtils == null) {
            fontUtils = new FontUtils(context);
        }
        if (mTypeface == null) {
            mTypeface = Typeface.createFromAsset(context.getAssets(), "arita_bold.ttf"); // 외부폰트 사용
        }
        return fontUtils;
    }


    public SpannableString typeface(CharSequence string) {
        SpannableString s = new SpannableString(string);
        s.setSpan(new TypefaceSpan(mTypeface), 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }

    public void setGlobalFont(View view) {
        if (view != null) {
            if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                int vgCnt = vg.getChildCount();
                for (int i = 0; i < vgCnt; i++) {
                    View v = vg.getChildAt(i);
                    if (v instanceof TextView) {
                        ((TextView) v).setTypeface(mTypeface);
                        ((TextView) v).setIncludeFontPadding(false);
                    }
                    setGlobalFont(v);
                }
            }
        }
    }
}
