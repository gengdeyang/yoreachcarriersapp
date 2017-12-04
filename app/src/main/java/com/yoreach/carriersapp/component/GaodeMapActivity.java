package com.yoreach.carriersapp.component;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.navi.model.NaviLatLng;
import com.yoreach.carriersapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GaodeMapActivity extends Activity implements AMapLocationListener {
    public AMapLocationClient mLocationClient;
    public AMapLocationClientOption mLocationOption;

    protected NaviLatLng mEndLatlng = new NaviLatLng(22.652, 113.966);
    protected NaviLatLng mStartLatlng = new NaviLatLng(22.540332, 113.939961);
    protected final List<NaviLatLng> sList = new ArrayList<NaviLatLng>();
    protected final List<NaviLatLng> eList = new ArrayList<NaviLatLng>();
    public TextView locationinfo;
    public Double latinfo;
    public Double loninfo;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.testmain);


        locationinfo = (TextView) findViewById(R.id.location);


    }

    public void dingwei(View v) {
        newLocation();

    }

    //39.9229890000,116.4421600000
    public void daohang(View v) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        String url = "http://uri.amap.com/navigation?from=" + loninfo + "," + latinfo + ",startpoint&to=116.3246,39.966577," + "endpoint&mode=car&policy=1&src=mypage&coordinate=gaode&callnative=1";
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        startActivity(intent);
    }

    private void openBrowserToGuide() {
        String url = "http://uri.amap.com/navigation?to=" + "36.3" + "," + "116.7" + "," + "目的地" + "&mode=car&policy=1&src=mypage&coordinate=gaode&callnative=0";
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void newLocation() {
        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationClient.setLocationListener(this);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setOnceLocation(false);
        mLocationOption.setWifiActiveScan(true);
        mLocationOption.setMockEnable(false);
        mLocationOption.setInterval(5000);
        mLocationClient.setLocationOption(mLocationOption);

        startLocation();
    }

    public void startLocation() {
        mLocationClient.startLocation();
    }

    public void stopLocation() {
        mLocationClient.stopLocation();
        mLocationClient.onDestroy();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                aMapLocation.getLocationType();
                latinfo = aMapLocation.getLatitude();
                loninfo = aMapLocation.getLongitude();
                aMapLocation.getAccuracy();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);
                aMapLocation.getAddress();
                aMapLocation.getCountry();
                aMapLocation.getProvince();
                aMapLocation.getCity();
                aMapLocation.getDistrict();
                aMapLocation.getStreet();
                aMapLocation.getStreetNum();
                aMapLocation.getCityCode();
                aMapLocation.getAdCode();

                StringBuffer buffer = new StringBuffer();
                buffer.append(aMapLocation.getCountry() + "" + aMapLocation.getProvince() + "" + aMapLocation.getCity() + "" + aMapLocation.getDistrict() + "" + aMapLocation.getStreet() + "" + aMapLocation.getStreetNum());
                Log.i("data=", df.format(date) + "");
                Log.i("add=", buffer + "");
                Log.i("xy=", aMapLocation.getLatitude() + " - " + aMapLocation.getLongitude());
                locationinfo.setText(aMapLocation.getLatitude() + " - " + aMapLocation.getLongitude());
            }
        } else {
            Log.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
            Toast.makeText(getApplicationContext(), "AmapError", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void onPause() {
        super.onPause();
    }


}
