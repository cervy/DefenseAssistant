package com.szzaq.jointdefence.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SmallSite implements Parcelable{
	int id;
    String url;
    String name;
    String site_type;
    String code;
    String address;
    String scope;
    int certificate;
    int employee_count;
    int area;
    int floors;
    String img_url;
    String owner;
    String owner_phone;
    String responsible;
    String responsible_phone;
    String administrator;
    String administrator_phone;
    String supervisor_org;
    String site_type_name;
    String grid;
    double lat;
    double lng;

	@Override
	public String toString() {
		return "SmallSite{" +
				"id=" + id +
				", url='" + url + '\'' +
				", name='" + name + '\'' +
				", site_type='" + site_type + '\'' +
				", code='" + code + '\'' +
				", address='" + address + '\'' +
				", scope='" + scope + '\'' +
				", certificate=" + certificate +
				", employee_count=" + employee_count +
				", area=" + area +
				", floors=" + floors +
				", img_url='" + img_url + '\'' +
				", owner='" + owner + '\'' +
				", owner_phone='" + owner_phone + '\'' +
				", responsible='" + responsible + '\'' +
				", responsible_phone='" + responsible_phone + '\'' +
				", administrator='" + administrator + '\'' +
				", administrator_phone='" + administrator_phone + '\'' +
				", supervisor_org='" + supervisor_org + '\'' +
				", site_type_name='" + site_type_name + '\'' +
				", grid='" + grid + '\'' +
				", lat=" + lat +
				", lng=" + lng +
				'}';
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSite_type() {
		return site_type;
	}
	public void setSite_type(String site_type) {
		this.site_type = site_type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public int getCertificate() {
		return certificate;
	}
	public void setCertificate(int certificate) {
		this.certificate = certificate;
	}
	public int getEmployee_count() {
		return employee_count;
	}
	public void setEmployee_count(int employee_count) {
		this.employee_count = employee_count;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public String getImgUrl() {
		return img_url;
	}
	public void setImg(String img_url) {
		this.img_url = img_url;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getOwner_phone() {
		return owner_phone;
	}
	public void setOwner_phone(String owner_phone) {
		this.owner_phone = owner_phone;
	}
	public String getResponsible() {
		return responsible;
	}
	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}
	public String getResponsible_phone() {
		return responsible_phone;
	}
	public void setResponsible_phone(String responsible_phone) {
		this.responsible_phone = responsible_phone;
	}
	public String getAdministrator() {
		return administrator;
	}
	public void setAdministrator(String administrator) {
		this.administrator = administrator;
	}
	public String getAdministrator_phone() {
		return administrator_phone;
	}
	public void setAdministrator_phone(String administrator_phone) {
		this.administrator_phone = administrator_phone;
	}
	public String getSupervisor_org() {
		return supervisor_org;
	}
	public void setSupervisor_org(String supervisor_org) {
		this.supervisor_org = supervisor_org;
	}
	public String getGrid() {
		return grid;
	}
	public void setGrid(String grid) {
		this.grid = grid;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	
	public int getId() {
		return id;
	}
	
	public String getSite_type_name() {
		return site_type_name;
	}
	
	public int getFloors() {
		return floors;
	}
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
	    dest.writeString(url);
	    dest.writeString(name);
	    dest.writeString(site_type);
	    dest.writeString(code);
	    dest.writeString(address);
	    dest.writeString(scope);
	    dest.writeInt(certificate);
	    dest.writeInt(employee_count);
	    dest.writeInt(area);
	    dest.writeInt(floors);
	    dest.writeString(img_url);
	    dest.writeString(owner);
	    dest.writeString(owner_phone);
	    dest.writeString(responsible);
	    dest.writeString(responsible_phone);
	    dest.writeString(administrator);
	    dest.writeString(administrator_phone);
	    dest.writeString(supervisor_org);
	    dest.writeString(grid);
	    dest.writeDouble(lat);
	    dest.writeDouble(lng);
		dest.writeString(site_type_name);
	}
    
	public static final  Parcelable.Creator<SmallSite> CREATOR = new Parcelable.Creator<SmallSite>() {

		@Override
		public SmallSite createFromParcel(Parcel source) {
			SmallSite site = new SmallSite();
			site.id = source.readInt();
		    site.url = source.readString();
		    site.name = source.readString();
		    site.site_type = source.readString();
		    site.code = source.readString();
		    site.address = source.readString();
		    site.scope = source.readString();
		    site.certificate = source.readInt();
		    site.employee_count = source.readInt();
		    site.area =source.readInt();
		    site.floors = source.readInt();
		    site.img_url = source.readString();
		    site.owner = source.readString();
		    site.owner_phone = source.readString();
		    site.responsible = source.readString();
		    site.responsible_phone = source.readString();
		    site.administrator = source.readString();
		    site.administrator_phone = source.readString();
		    site.supervisor_org = source.readString();
		    site.grid = source.readString();
		    site.lat = source.readDouble();
		    site.lng = source.readDouble();
		    site.site_type_name = source.readString();
			return site;
		}

		@Override
		public SmallSite[] newArray(int size) {
			return new SmallSite[size];
		}
	};
}
