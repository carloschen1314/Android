package com.androidproject.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.androidproject.R;

import java.util.List;

public class groupAdapter extends RecyclerView.Adapter<groupAdapter.ViewHolder> {

    private List<group> mGroupList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView headProtrait;
        TextView name;
        TextView time;

        public ViewHolder (View view)
        {
            super(view);
            headProtrait = (ImageView) view.findViewById(R.id.head_protrait);
            name = (TextView) view.findViewById(R.id.username);
            time=(TextView) view.findViewById(R.id.userTime);
        }

    }

    public  groupAdapter (List<group> GroupList){
        mGroupList = GroupList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        group group = mGroupList.get(position);
        holder.headProtrait.setImageDrawable(group.getheadProtrait());
        holder.name.setText(group.getName());
        holder.time.setText((int) group.getTime());
    }

    @Override
    public int getItemCount(){
        return mGroupList.size();
    }
}
