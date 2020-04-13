package com.androidproject.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.androidproject.R;

import java.util.List;

public class GroupNameAdapter extends RecyclerView.Adapter<GroupNameAdapter.ViewHolder> {

    private List<GroupName> mGroupNameList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView headProtrait;
        TextView name;

        public ViewHolder (View view)
        {
            super(view);
            headProtrait = (ImageView) view.findViewById(R.id.group_protrait);
            name = (TextView) view.findViewById(R.id.group_name);
        }

    }

    public  GroupNameAdapter (List<GroupName> GroupNameList){
        mGroupNameList = GroupNameList;
    }

    @Override
    public GroupNameAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_name,parent,false);
        GroupNameAdapter.ViewHolder holder = new GroupNameAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(GroupNameAdapter.ViewHolder holder, int position){

        GroupName groupName = mGroupNameList.get(position);
        holder.headProtrait.setImageDrawable(groupName.getheadProtrait());
        holder.name.setText(groupName.getName());
    }

    @Override
    public int getItemCount(){
        return mGroupNameList.size();
    }
}
