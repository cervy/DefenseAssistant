package com.szzaq.jointdefence.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SmallSiteRecheck implements Parcelable {
    int id;
    String url;
    String[] dangers = new String[]{};
    double lat;
    double lng;
    String location;
    String inspector_names;
    String created;
    String site_img_url;
    String site_name;

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String[] getDangers() {
        return dangers;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getLocation() {
        return location;
    }

    public String getInspector_names() {
        return inspector_names;
    }

    public String getCreated() {
        return created;
    }

    public String getSite_img_url() {
        return site_img_url;
    }

    public String getSite_name() {
        return site_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<SmallSiteRecheck> CREATOR = new Parcelable.Creator<SmallSiteRecheck>() {

        @Override
        public SmallSiteRecheck createFromParcel(Parcel source) {
            int len;
            SmallSiteRecheck recheck = new SmallSiteRecheck();
            recheck.id = source.readInt();
            recheck.url = source.readString();
            len = source.readInt();
            if (len > 0) {
                recheck.dangers = new String[len];
                source.readStringArray(recheck.dangers);
            }
            recheck.lat = source.readDouble();
            recheck.lng = source.readDouble();
            recheck.location = source.readString();
            recheck.created = source.readString();
            recheck.inspector_names = source.readString();
            recheck.site_img_url = source.readString();
            recheck.site_name = source.readString();
            return recheck;
        }

        @Override
        public SmallSiteRecheck[] newArray(int size) {
            return new SmallSiteRecheck[size];
        }

    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(url);
        dest.writeInt(dangers.length);
        if (dangers.length > 0)
            dest.writeStringArray(dangers);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeString(location);
        dest.writeString(created);
        dest.writeString(inspector_names);
        dest.writeString(site_img_url);
        dest.writeString(site_name);
    }


}
