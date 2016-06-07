package com.szzaq.jointdefence.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*     {"location":"配电房","maintainer_name":"陈龙海","supervisor_tel":"13502424944","fp_company":"http:\/\/120.24.220.29\/api\/fpcompany\/3\/","type":11,"supervisor_assitant_tel":"13502424944","incharge_name":"张凯","id":5930,"supervisor_assitant_name":"黄隆坚","supervisor_org":"惠东县消防大队","key_enterprise_name":"惠东华美达广场酒店","incharge_tel":"0752-8313888","name":"灭火器","fp_company_name":"广州市沃华消防工程有限公司","expired_date":"2999-08-01","maintainer_tel":"13642326618","coin":0,"code":0,"production_date":"2013-08-01","admin_name":"张凯","maintainer":"http:\/\/120.24.220.29\/api\/maintainer\/3\/","url":"http:\/\/120.24.220.29\/api\/keyenterprise\/device\/5930\/","supervisor_name":"黄隆坚","admin":"http:\/\/120.24.220.29\/api\/staff\/66\/","key_enterprise":"http:\/\/120.24.220.29\/api\/keyenterprise\/17\/","admin_tel":"0752-8313888"}*/
public class Device implements Parcelable {
    String id;
    String url;
    String name;
    String type;
    String code;
    String location;
    String key_enterprise;
    String fp_company;
    String admin;
    String maintainer;
    String admin_name;
    String fp_company_name;
    String key_enterprise_name;
    String incharge_name;
    String supervisor_name;
    String supervisor_assitant_name;
    String maintainer_name;
    String supervisor_org;
    String admin_tel;
    String incharge_tel;
    String supervisor_tel;
    String supervisor_assitant_tel;
    String maintainer_tel;
    String production_date;
    String expired_date;


    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getDeviceId() {
        Pattern p = Pattern.compile(".+\\/device\\/(\\d+)\\/$");
        Matcher m = p.matcher(this.url);
        if (m.find()) {
            return m.group(1);
        }
        return null;

    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public String getLocation() {
        return location;
    }

    public String getKey_enterprise() {
        return key_enterprise;
    }

    public String getFp_company() {
        return fp_company;
    }

    public String getAdmin() {
        return admin;
    }

    public String getMaintainer() {
        return maintainer;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public String getFp_company_name() {
        return fp_company_name;
    }

    public String getKey_enterprise_name() {
        return key_enterprise_name;
    }

    public String getIncharge_name() {
        return incharge_name;
    }

    public String getSupervisor_name() {
        return supervisor_name;
    }

    public String getSupervisor_assitant_name() {
        return supervisor_assitant_name;
    }

    public String getMaintainer_name() {
        return maintainer_name;
    }

    public String getSupervisor_org() {
        return supervisor_org;
    }

    public String getAdmin_tel() {
        return admin_tel;
    }

    public String getIncharge_tel() {
        return incharge_tel;
    }

    public String getSupervisor_tel() {
        return supervisor_tel;
    }

    public String getSupervisor_assitant_tel() {
        return supervisor_assitant_tel;
    }

    public String getMaintainer_tel() {
        return maintainer_tel;
    }

    public String getProduction_date() {
        return production_date;
    }

    public void setProduction_date(String production_date) {
        this.production_date = production_date;
    }

    public String getExpire_date() {
        return expired_date;
    }

    public void setExpire_date(String expire_date) {
        this.expired_date = expire_date;
    }

    public static Parcelable.Creator<Device> getCreator() {
        return CREATOR;
    }

    public static final Parcelable.Creator<Device> CREATOR = new Creator<Device>() {

        @Override
        public Device createFromParcel(Parcel source) {
            Device device = new Device();
            device.id = source.readString();
            device.url = source.readString();
            device.name = source.readString();
            device.type = source.readString();
            device.code = source.readString();
            device.location = source.readString();
            device.key_enterprise = source.readString();
            device.fp_company = source.readString();
            device.admin = source.readString();
            device.maintainer = source.readString();
            device.admin_name = source.readString();
            device.fp_company_name = source.readString();
            device.key_enterprise_name = source.readString();
            device.incharge_name = source.readString();
            device.supervisor_name = source.readString();
            device.supervisor_assitant_name = source.readString();
            device.maintainer_name = source.readString();
            device.supervisor_org = source.readString();
            device.admin_tel = source.readString();
            device.incharge_tel = source.readString();
            device.supervisor_tel = source.readString();
            device.supervisor_assitant_tel = source.readString();
            device.maintainer_tel = source.readString();
            device.production_date = source.readString();
            device.expired_date = source.readString();
            return device;
        }

        @Override
        public Device[] newArray(int size) {
            return new Device[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(url);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(code);
        dest.writeString(location);
        dest.writeString(key_enterprise);
        dest.writeString(fp_company);
        dest.writeString(admin);
        dest.writeString(maintainer);
        dest.writeString(admin_name);
        dest.writeString(fp_company_name);
        dest.writeString(key_enterprise_name);
        dest.writeString(incharge_name);
        dest.writeString(supervisor_name);
        dest.writeString(supervisor_assitant_name);
        dest.writeString(maintainer_name);
        dest.writeString(supervisor_org);
        dest.writeString(admin_tel);
        dest.writeString(incharge_tel);
        dest.writeString(supervisor_tel);
        dest.writeString(supervisor_assitant_tel);
        dest.writeString(maintainer_tel);
        dest.writeString(production_date);
        dest.writeString(expired_date);
    }


}
