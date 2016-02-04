package com.attosoft.rxandroiddemo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by andy on 2016/2/3.
 */
@Data
@Accessors(prefix = "m")
public class AppInfo implements Comparable<Object> {

    long mLastUpdateTime;
    String mName;
    String mIcon;

    public AppInfo(String name,long lastUpdateTime,String icon){
        this.mName = name;
        this.mLastUpdateTime = lastUpdateTime;
        this.mIcon = icon;
    }

    @Override
    public int compareTo(Object another) {
        AppInfo f = (AppInfo)another;
//        return 0;
        return getName().compareTo(f.getName());
    }
}
