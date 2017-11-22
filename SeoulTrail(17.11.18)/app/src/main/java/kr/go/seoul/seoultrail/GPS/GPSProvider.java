package kr.go.seoul.seoultrail.GPS;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by ntsys on 2016-06-22.
 */
public class GPSProvider{
    private Context context;
    private LocationManager mlocManager;
    private LocationListener mlocListener;
    private String provider;
    private Location location;

    private double latitude;
    private double longitude;

    private Criteria criteria;

    public GPSProvider(Context context, LocationManager mlocManager) { // 생성자
        this.mlocManager = mlocManager; // GPS값을 받아오기 위해서는 LocationManager 클래스의 오브젝트가 반드시 필요하다.
        this.context = context;

        criteria = new Criteria(); // Criteria는 위치 정보를 가져올 때 고려되는 옵션 정도로 생각하면 된다.
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
    }

    public void startGetMyLoation() {
        latitude = 0.0;
        longitude = 0.0;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener); // 정보 제공자를 통해 외치 업데이트를 한 다음
        location = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);  // 최종 위치 정보를 파악해내고
    }

    public void setMlocListener(LocationListener mlocListener) {
        this.mlocListener = mlocListener;
    }

    public void removeUpdate() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (mlocManager != null && mlocListener != null) {
            mlocManager.removeUpdates(mlocListener);
        }
    }
}
