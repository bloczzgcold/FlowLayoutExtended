package com.mc.flowlayout.flowlayout;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangzhenguo on 2016/11/11.
 * 流式布局child data
 */

public class FlowChild implements Parcelable {
    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.name);
    }

    public FlowChild() {
    }

    protected FlowChild(Parcel in) {
        this.code = in.readString();
        this.name = in.readString();
    }

    public static final Creator<FlowChild> CREATOR = new Creator<FlowChild>() {
        @Override
        public FlowChild createFromParcel(Parcel source) {
            return new FlowChild(source);
        }

        @Override
        public FlowChild[] newArray(int size) {
            return new FlowChild[size];
        }
    };
}
