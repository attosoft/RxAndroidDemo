package com.attosoft.rxandroiddemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by andy on 2016/2/3.
 */
public class AppAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<AppInfo> mData = new ArrayList<>();

    public void addApps(List<AppInfo> appInfos){
        mData.clear();
        mData.addAll(appInfos);
    }

    public void addApplication(AppInfo info){
        mData.add(info);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(MyApplication.getApplication()).inflate(R.layout.app_item_layout,null);
        return new AppItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        AppItemViewHolder holder = (AppItemViewHolder)viewHolder;
        holder.mAppName.setText(mData.get(i).getName());
        holder.mActivityName.setText(mData.get(i).getIcon());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class AppItemViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.appName)
        public TextView mAppName;
        @Bind(R.id.parentActivityName)
        public TextView mActivityName;

        public AppItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
