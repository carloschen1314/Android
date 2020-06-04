package com.androidproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.androidproject.R;
import com.androidproject.util.APP;
import com.androidproject.util.APPAdapter;
import com.androidproject.util.FormatTools;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class LockedAPPActivity extends AppCompatActivity {

    private List<APP> appList = new ArrayList<>();
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locked_app);
        Button button=(Button)findViewById(R.id.chooseAPP);

        Intent intent=getIntent();
        id=intent.getStringExtra("id");

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
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }

    private void initApps() {
        appList = LitePal.where("user like ?", id).find(APP.class);
        PackageManager pm=getPackageManager();
        for(int i=0;i<appList.size();i++){
            try {
                PackageInfo packageInfo=pm.getPackageInfo(appList.get(i).getAddress(),0);
                Drawable icon = packageInfo.applicationInfo.loadIcon(getPackageManager());
                FormatTools formatTools=FormatTools.getInstance();
                byte[] byte_icon= formatTools.Drawable2Bytes(icon);
                appList.get(i).setImage(byte_icon);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

        }

    }
}
