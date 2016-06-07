package com.szzaq.jointdefence.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DOS on 2016/5/18 0018.
 */
public class TODEAL implements Parcelable {
    public String id, url, title, content, status,created,finish_time;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.url);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.status);
        dest.writeString(this.created);
        dest.writeString(this.finish_time);
    }

    public TODEAL() {
    }

    protected TODEAL(Parcel in) {
        this.id = in.readString();
        this.url = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.status = in.readString();
        this.created = in.readString();
        this.finish_time = in.readString();
    }

    public static final Creator<TODEAL> CREATOR = new Creator<TODEAL>() {
        @Override
        public TODEAL createFromParcel(Parcel source) {
            return new TODEAL(source);
        }

        @Override
        public TODEAL[] newArray(int size) {
            return new TODEAL[size];
        }
    };
}
