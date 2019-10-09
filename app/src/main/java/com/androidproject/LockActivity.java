package com.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.util.List;

public class LockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_activity);
        this.getPackages();

    }

    // 判断是否具有ROOT权限
//    public static boolean is_root() {
//        boolean res = false;
//        try {
//            if ((!new File("/system/bin/su").exists()) &&
//                    (!new File("/system/xbin/su").exists())) {
//                res = false;
//            } else {
//                res = true;
//            }
//            ;
//        } catch (Exception e) {
//
//        }
//        return res;
//    }


    //得到app信息
    private void getPackages() {
        // 获取已经安装的所有应用, PackageInfo　系统类，包含应用信息
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        if (packages.size() > 0) {
            for (int i = 0; i < packages.size(); i++) {
                PackageInfo packageInfo = packages.get(i);
                String packageName = packageInfo.packageName;   //app包名
                String versionName = packageInfo.versionName;  //app的versionName
                int versionCode = packageInfo.versionCode;  //app的versionCode
                String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString(); //app名
                Drawable icon = packageInfo.applicationInfo.loadIcon(getPackageManager());  //app图标

                if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                    //非系统应用
//                    Log.d("LoginActivity", packageName);
//                    Log.d("LoginActivity", versionName);
//                    Log.d("LoginActivity", String.valueOf(versionCode));
//                    Log.d("LoginActivity", appName);
                } else {
                    //系统应用
//                    Log.d("LoginActivity", appName);
                }
            }
        }
    }

}
