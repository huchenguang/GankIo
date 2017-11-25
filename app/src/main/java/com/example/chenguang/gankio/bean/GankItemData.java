package com.example.chenguang.gankio.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by chenguang on 2017/7/26.
 */

public class GankItemData implements Parcelable {
    public String _id;
    public String createAt;
    public String desc;
    public List<String> images;
    public String publishedAt;
    public String source;
    public String type;
    public String url;
    public boolean used;
    public String who;

    protected GankItemData(Parcel in) {
        _id = in.readString();
        createAt = in.readString();
        desc = in.readString();
        images = in.createStringArrayList();
        publishedAt = in.readString();
        source = in.readString();
        type = in.readString();
        url = in.readString();
        used = in.readByte() != 0;
        who = in.readString();
    }

    public static final Creator<GankItemData> CREATOR = new Creator<GankItemData>() {
        @Override
        public GankItemData createFromParcel(Parcel in) {
            return new GankItemData(in);
        }

        @Override
        public GankItemData[] newArray(int size) {
            return new GankItemData[size];
        }
    };

    @Override
    public String toString() {
        return "GankItemData{" +
                "_id='" + _id + '\'' +
                ", createAt='" + createAt + '\'' +
                ", desc='" + desc + '\'' +
                ", images=" + images +
                ", publishedAt='" + publishedAt + '\'' +
                ", source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", used=" + used +
                ", who='" + who + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(createAt);
        dest.writeString(desc);
        dest.writeStringList(images);
        dest.writeString(publishedAt);
        dest.writeString(source);
        dest.writeString(type);
        dest.writeString(url);
        dest.writeByte((byte) (used ? 1 : 0));
        dest.writeString(who);
    }
}