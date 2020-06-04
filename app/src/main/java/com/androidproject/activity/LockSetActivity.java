package com.androidproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidproject.R;
import com.androidproject.util.APP;
import com.androidproject.util.APPAdapter;
import com.androidproject.util.FormatTools;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

public class LockSetActivity extends AppCompatActivity {

    private List<APP> appList = new ArrayList<>();
    private String id;
    private APPAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_set);
        Button button = (Button) findViewById(R.id.determin);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        //进行数据初始化
        initApps();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.unchose_item);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new APPAdapter(appList);
        recyclerView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //撤销原有数据
                LitePal.deleteAll(APP.class,"user == ?",id);

                SparseBooleanArray selectLists = adapter.getSelectedItem();
                boolean right=false;
                for (int i = 0; i < selectLists.size(); i++) {
                    int key = selectLists.keyAt(i);
                    boolean choose = selectLists.get(key);
                    if(choose){
                        APP app1=appList.get(key);
                        right=app1.save();
                    }
                }
                if(right){
                    Intent intent = new Intent(LockSetActivity.this, MainActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                    Toast.makeText(LockSetActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LockSetActivity.this, "入库失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initApps() {
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        if (packages.size() > 0) {
            for (int i = 0; i < packages.size(); i++) {
                PackageInfo packageInfo = packages.get(i);
//                if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                String packageName = packageInfo.packageName;   //app包名
                String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();

                //图片转换
                Drawable icon = packageInfo.applicationInfo.loadIcon(getPackageManager());
                FormatTools formatTools=FormatTools.getInstance();
                byte[] byte_icon= formatTools.Drawable2Bytes(icon);

                APP apple = new APP(appName, byte_icon, packageName, id);
                appList.add(apple);
//                }
            }
        } else {
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

