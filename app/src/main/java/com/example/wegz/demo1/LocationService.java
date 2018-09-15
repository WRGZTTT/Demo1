package com.example.wegz.demo1;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;

import java.util.Timer;
import java.util.TimerTask;

public class LocationService extends Service{

    //定位点信息
    public LatLng latlng;
    private String strLocationProvince;//定位点的省份
    private String strLocationCity;//定位点的城市
    private String strLocationDistrict;//定位点的区县
    private String strLocationStreet;//定位点的街道信息
    private String strLocationStreetNumber;//定位点的街道号码
    private String strLocationAddrStr;//定位点的详细地址(包括国家和以上省市区等信息)
    private String addrs;
    private String lat1;
    private String lon1;
    private LocationClient mLocationClient =null;//定位客户端
    public MyLocationListener mMyLocationListener = new MyLocationListener();
    private Timer mTimer = null;
    private TimerTask mTimerTask = null;
    private boolean isStop = false;
    private String UserName;



    @Override
    public void onStart(Intent intent, int startId) {
        // this will trigger AbstractBackgroundService.onStart()
        super.onStart(intent, startId);
        UserName = intent.getStringExtra("UserName");
    }



    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.setLocOption(setLocationClientOption());
        mLocationClient.registerLocationListener(mMyLocationListener);
    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 定位客户端参数设定，更多参数设置，查看百度官方文档
     * @return
     */
    private LocationClientOption setLocationClientOption() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(com.baidu.location.LocationClientOption.LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setScanSpan(5000);//每隔5秒发起一次定位
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
        option.setOpenGps(true);//是否打开gps
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到该描述，不设置则在4G情况下会默认定位到“天安门广场”
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要，不设置则拿不到定位点的省市区信息
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        /*可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        该参数若不设置，则在4G状态下，会出现定位失败，将直接定位到天安门广场
         */
        return option;
    }
    /**
     * 定位监听器
     * @author User
     *
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location==null) {
                return;
            }
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            latlng = new LatLng(lat, lng);
            //定位点地址信息做非空判断
            if ("".equals(location.getProvince())) {
                strLocationProvince = "未知省";
            }else {
                strLocationProvince = location.getProvince();
            }
            if ("".equals(location.getCity())) {
                strLocationCity = "未知市";
            }else {
                strLocationCity = location.getCity();
            }
            if ("".equals(location.getDistrict())) {
                strLocationDistrict = "未知区";
            }else {
                strLocationDistrict = location.getDistrict();
            }
            if ("".equals(location.getStreet())) {
                strLocationStreet = "未知街道";
            }else {
                strLocationStreet = location.getStreet();
            }
            if ("".equals(location.getStreetNumber())) {
                strLocationStreetNumber = "";
            }else {
                strLocationStreetNumber =location.getStreetNumber();
            }
            if ("".equals(location.getAddrStr())) {
                strLocationAddrStr = "";
            }else {
                strLocationAddrStr =location.getAddrStr();
            }
            //定位成功后对获取的数据依据需求自定义处理，这里只做log显示
            Log.d("tag", "latlng.lat="+lat);
            Log.d("tag", "latlng.lng="+lng);
            Log.d("tag", "strLocationProvince="+strLocationProvince);
            Log.d("tag", "strLocationCity="+strLocationCity);
            Log.d("tag", "strLocationDistrict="+strLocationDistrict);
            addrs = strLocationProvince+strLocationCity+strLocationDistrict+strLocationStreet;
            Log.d("tag",""+addrs);







            // 到此定位成功，没有必要反复定位
            // 应该停止客户端再发送定位请求
            if (mLocationClient.isStarted()) {
                Log.d("tag", "mLocationClient.isStarted()==>mLocationClient.stop()");
                mLocationClient.stop();

            }

        }

    }

}