package com.androidproject.activity;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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
        Drawable drawable = ContextCompat.getDrawable(getActivity(),R.mipmap.user_photo);
        group group1 = new group("test1",10, drawable);
        group group2 = new group("test2",18, drawable);
        group group3 = new group("test3",30, drawable);
        groupList.add(group1);
        groupList.add(group2);
        groupList.add(group3);
    }
}
