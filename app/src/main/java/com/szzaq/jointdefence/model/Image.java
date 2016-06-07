package com.szzaq.jointdefence.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable{
	String title;
	String url;
	String img;
	
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
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(title);
		dest.writeString(url);
		dest.writeString(img);
		
	}
	
	public static final Parcelable.Creator<Image> CREATOR = new Creator<Image>() {  
	  	  
        @Override  
        public Image createFromParcel(Parcel source) {  
        	Image img = new Image(); 
        	img.title = source.readString();
        	img.url = source.readString();
        	img.img = source.readString();
        	return img;
        }

		@Override
		public Image[] newArray(int size) {
			return new Image[size];
		}
    };
}
