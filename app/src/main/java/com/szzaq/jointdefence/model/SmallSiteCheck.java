package com.szzaq.jointdefence.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SmallSiteCheck implements Parcelable{

    int id;
    String url;
    Danger[] dangers = new Danger[]{};
    String expiration;
    double lat;
    double lng;
    String location;
    String created;
    String site;
    String[] inspectors = new String[]{};
    String inspector_names;
    String site_name;
    String site_img_url;
    String inspector_portrait;
    
    public int getId() {
		return id;
	}



	public String getUrl() {
		return url;
	}



	public Danger[] getDangers() {
		return dangers;
	}



	public String getExpiration() {
		return expiration;
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



	public String getCreated() {
		return created;
	}



	public String getSite() {
		return site;
	}



	public String[] getInspectors() {
		return inspectors;
	}



	public String getInspector_names() {
		return inspector_names;
	}



	public String getSite_name() {
		return site_name;
	}

	

	public String getSite_img_url() {
		return site_img_url;
	}

    

	public String getInspector_portrait() {
		return inspector_portrait;
	}



	/**
	 * @author Betty
	 *
	 */
	public static class Danger implements Parcelable	
            {
                int id;
                int status;
                String url;
                String photo_before_url;
                String photo_after_url;
                String photo_after;
                String desc;
                String danger_type;
                
                
				public int getId() {
					return id;
				}
				public int getStatus() {
					return status;
				}
				
				public void setStatus(int status) {
					this.status = status;
				}
				public String getPhoto_before_url() {
					return photo_before_url;
				}
				
				public void setPhoto_after_url(String photo_after_url) {
					this.photo_after_url = photo_after_url;
				}
				public String getPhoto_after_url() {
					return photo_after_url;
				}
				public String getDesc() {
					return desc;
				}
				
				
				public String getPhoto_after() {
					return photo_after;
				}
				public void setPhoto_after(String photo_after) {
					this.photo_after = photo_after;
				}

				public String getUrl() {
					return url;
				}

				

				public String getDanger_type() {
					return danger_type;
				}



				public static final Parcelable.Creator<Danger> CREATOR = new Parcelable.Creator<Danger>(){

					@Override
					public Danger createFromParcel(Parcel source) {
						Danger danger = new Danger();
						danger.id = source.readInt();
						danger.status = source.readInt();
						danger.url = source.readString();
						danger.photo_before_url = source.readString();
						danger.photo_after_url = source.readString();
						danger.photo_after = source.readString();
						danger.desc = source.readString();
						danger.danger_type = source.readString();
						return danger;
					}

					@Override
					public Danger[] newArray(int size) {
						
						return new Danger[size];
					}
					
				};
				
				@Override
				public int describeContents() {
					return 0;
				}
				@Override
				public void writeToParcel(Parcel dest, int flags) {
	                dest.writeInt(id);
	                dest.writeInt(status);
	                dest.writeString(url);
	                dest.writeString(photo_before_url);
	                dest.writeString(photo_after_url);
	                dest.writeString(photo_after);
	                dest.writeString(desc);
	                dest.writeString(danger_type);
				}
                
                
            }
	
	public static final Parcelable.Creator<SmallSiteCheck> CREATOR = new Parcelable.Creator<SmallSiteCheck>(){

		@Override
		public SmallSiteCheck createFromParcel(Parcel source) {
			SmallSiteCheck check = new SmallSiteCheck();
			check.id = source.readInt();
			check.url = source.readString();
			if (source.readInt()>0){
				Parcelable[] p = source.readParcelableArray(this.getClass().getClassLoader());
				check.dangers = new Danger[p.length];
				for (int i=0;i<p.length;i++)
					check.dangers[i] = (Danger) p[i];
			}
		    check.expiration = source.readString();
		    check.lat = source.readDouble();
		    check.lng = source.readDouble();
		    check.location = source.readString();
		    check.created = source.readString();
		    check.site = source.readString();
		    int len = source.readInt();
		    if (len>0){
		    	check.inspectors = new String[len];
		    	source.readStringArray(check.inspectors);
		    }
		    check.inspector_names = source.readString();
		    check.site_name = source.readString();
		    check.site_img_url = source.readString();
		    check.inspector_portrait = source.readString();
			return check;
		}

		@Override
		public SmallSiteCheck[] newArray(int size) {
			return new SmallSiteCheck[size];
		}

	};
	
	@Override
	public int describeContents() {
		return 0;
	}



	@Override
	public void writeToParcel(Parcel dest, int flags) {
	    dest.writeInt(id);
	    dest.writeString(url);
	    dest.writeInt(dangers.length);
	    if (dangers.length>0)
	    	dest.writeParcelableArray(dangers, dangers.length);
	    dest.writeString(expiration);
	    dest.writeDouble(lat);
	    dest.writeDouble(lng);
	    dest.writeString(location);
	    dest.writeString(created);
	    dest.writeString(site);
	    dest.writeInt(inspectors.length);
	    if (inspectors.length>0)
	    	dest.writeStringArray(inspectors);
	    dest.writeString(inspector_names);
	    dest.writeString(site_name);
		dest.writeString(site_img_url);
		dest.writeString(inspector_portrait);
	}
}
