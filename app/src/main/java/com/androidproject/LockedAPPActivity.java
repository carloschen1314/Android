package com.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class LockedAPPActivity extends AppCompatActivity {

    private List<APP> appList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locked_app);
        Button button=(Button)findViewById(R.id.chooseAPP);

        initApps();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.chose_item);
        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        recyclerView.setLayoutManager(layoutManager);
        APPAdapter adapter = new APPAdapter(appList);
        recyclerView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(LockedAPPActivity.this,LockSetActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initApps() {
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        if (packages.size() > 0) {
            for (int i = 0; i < packages.size(); i++) {
                PackageInfo packageInfo = packages.get(i);
                if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
//                    String packageName = packageInfo.packageName;   //app包名
                    String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                    Drawable icon = packageInfo.applicationInfo.loadIcon(getPackageManager());
                    APP apple = new APP(appName, icon);
                    appList.add(apple);
                }
            }
        } else {
            Log.d("LockSetActivity", "没有找到软件包");
        }
    }
}
