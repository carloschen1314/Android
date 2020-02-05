package com.androidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class LockActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Button runButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_activity);
        runButton = findViewById(R.id.btn);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        navView.setCheckedItem(R.id.nav_personalInfro);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        this.getPackages();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
//            case R.id.backup:
//                Toast.makeText(this, "You Clicked Bakup", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.delete:
//                Toast.makeText(this, "You Clicked Delete", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.settings:
//                Toast.makeText(this, "You Clicked Settings", Toast.LENGTH_SHORT).show();
//                break;
            default:
                break;
        }
        return true;
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
