package com.androidproject.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.androidproject.R;

import java.util.List;

public class APPAdapter extends RecyclerView.Adapter<APPAdapter.ViewHolder> {

    private List<APP> mAPPList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView appImage;
        TextView appName;

        public ViewHolder (View view)
        {
            super(view);
            appImage = (ImageView) view.findViewById(R.id.app_image);
            appName = (TextView) view.findViewById(R.id.appname);
        }

    }

    public  APPAdapter (List <APP> fruitList){
        mAPPList = fruitList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        APP app = mAPPList.get(position);
        holder.appImage.setImageDrawable(app.getImageId());
        holder.appName.setText(app.getName());
        holder.appName.setSelected(true);
    }

    @Override
    public int getItemCount(){
        return mAPPList.size();
    }
}
