package com.androidproject.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidproject.R;
import com.androidproject.util.group;
import com.androidproject.util.groupAdapter;

import java.util.ArrayList;
import java.util.List;

public class Fragment3 extends Fragment {

    private List<group> groupList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment3,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initgroups();
        RecyclerView recyclerView = (RecyclerView)getActivity().findViewById(R.id.group_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        groupAdapter adapter = new groupAdapter(groupList);
        recyclerView.setAdapter(adapter);
    }

    private void initgroups() {
//        if () {
//            //设置组信息交互
//        }
//        else {
//            Log.d("Fragment3", "没有找到group");
//        }
    }
}
