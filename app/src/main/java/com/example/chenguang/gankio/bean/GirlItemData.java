package com.example.chenguang.gankio.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class GirlItemData implements Parcelable {
    private String title;
    private String url;
    private String id;
    private int width;
    private int height;

    public GirlItemData() {
    }

    public GirlItemData(String title, String url, String id, int width, int height) {
        this.title = title;
        this.url = url;
        this.id = id;
        this.width = width;
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String detailUrl) {
        this.id = detailUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeString(this.id);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
    }

    protected GirlItemData(Parcel in) {
        this.title = in.readString();
        this.url = in.readString();
        this.id = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
    }

    public static final Parcelable.Creator<GirlItemData> CREATOR = new Parcelable
            .Creator<GirlItemData>() {
        @Override
        public GirlItemData createFromParcel(Parcel source) {
            return new GirlItemData(source);
        }

        @Override
        public GirlItemData[] newArray(int size) {
            return new GirlItemData[size];
        }
    };

    @Override
    public String toString() {
        return "GirlItemData{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", id='" + id + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
