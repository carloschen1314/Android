package com.androidproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.androidproject.util.GroupName;
import com.androidproject.util.GroupNameAdapter;
import com.androidproject.util.group;

import java.util.ArrayList;
import java.util.List;

public class GroupChangingActivity extends AppCompatActivity {

    private List<GroupName> groupNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_change);

        Button button = (Button) findViewById(R.id.btn_groupChanging);

        initGroupNames();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcv_groupName);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        GroupNameAdapter adapter = new GroupNameAdapter(groupNameList);
        recyclerView.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设置jason以满足更换更改群组的要求
            }
        });
    }

    private void initGroupNames() {
        //初始化group
//        String groupName = ;
//        Drawable groupProtrait = ;
//        GroupName group = new GroupName(groupName, groupProtrait);
//        groupNameList.add(group);
    }
}
