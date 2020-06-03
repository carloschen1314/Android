package com.androidproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidproject.R;
import com.androidproject.util.APP;
import com.androidproject.util.APPAdapter;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

public class LockSetActivity extends AppCompatActivity {

    private List<APP> appList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_set);
        Button button=(Button)findViewById(R.id.determin);

        Connector.getDatabase();
        initApps();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.unchose_item);
        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        recyclerView.setLayoutManager(layoutManager);
        APPAdapter adapter = new APPAdapter(appList);
        recyclerView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LockSetActivity.this,MainActivity.class);
                startActivity(intent);
                Toast.makeText(LockSetActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                //入库操作
            }
        });
    }

    private void initApps() {
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        if (packages.size() > 0) {
            for (int i = 0; i < packages.size(); i++) {
                PackageInfo packageInfo = packages.get(i);
//                if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
//                    String packageName = packageInfo.packageName;   //app包名
                    String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                    Drawable icon = packageInfo.applicationInfo.loadIcon(getPackageManager());
                    APP apple = new APP(appName, icon,"123","123");
                    appList.add(apple);
//                }
            }
        }
        else {
            Log.d("LockSetActivity", "没有找到软件包");
        }
    }

//    //得到app信息
//    private List<PackageInfo> getPackages() {
//        // 获取已经安装的所有应用, PackageInfo　系统类，包含应用信息
//        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
//        if (packages.size() > 0) {
//            for (int i = 0; i < packages.size(); i++) {
//                PackageInfo packageInfo = packages.get(i);
//
//                String versionName = packageInfo.versionName;  //app的versionName
//                int versionCode = packageInfo.versionCode;  //app的versionCode
//                String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString(); //app名
//                Drawable icon = packageInfo.applicationInfo.loadIcon(getPackageManager());  //app图标
//
////                if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
////                    //非系统应用
//////                    Log.d("LoginActivity", packageName);
//////                    Log.d("LoginActivity", versionName);
//////                    Log.d("LoginActivity", String.valueOf(versionCode));
//////                    Log.d("LoginActivity", appName);
////                } else {
////                    //系统应用
//////                    Log.d("LoginActivity", appName);
////                }
//            }
//        }
//    }
}

