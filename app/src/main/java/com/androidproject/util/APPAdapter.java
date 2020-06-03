package com.androidproject.util;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.androidproject.R;

import java.util.List;

public class APPAdapter extends RecyclerView.Adapter<APPAdapter.ViewHolder> {

    private List<APP> mAPPList;
    private SparseBooleanArray selectLists = new SparseBooleanArray();

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView appImage;
        TextView appName;
        ImageView appCheck;
        View appView;

        public ViewHolder (View view)
        {
            super(view);
            appImage = (ImageView) view.findViewById(R.id.app_image);
            appName = (TextView) view.findViewById(R.id.appname);
            appCheck=(ImageView)view.findViewById(R.id.app_item_check);
            appView=view;
        }

    }

    public  APPAdapter (List <APP> fruitList){
        mAPPList = fruitList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.appView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(holder.appCheck.getVisibility()==View.INVISIBLE){
                    holder.appCheck.setVisibility(View.VISIBLE);
                    selectLists.put(holder.getAdapterPosition(), true);
                }else{
                    holder.appCheck.setVisibility(View.INVISIBLE);
                    selectLists.put(holder.getAdapterPosition(), false);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        APP app = mAPPList.get(position);
        holder.appImage.setImageDrawable(app.getImage());
        holder.appName.setText(app.getName());
        holder.appName.setSelected(true);
    }

    @Override
    public int getItemCount(){
        return mAPPList.size();
    }

    public SparseBooleanArray getSelectedItem() {
        return selectLists;
    }
}
