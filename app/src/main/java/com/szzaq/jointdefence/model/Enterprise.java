package com.szzaq.jointdefence.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Enterprise implements Parcelable {
    int id;
    String url;
    String name;
    String address;
    String code;
    String supervisor_org;
    String supervisor;
    String supervisor_assistant;
    String incharge;
    String supervisor_org_name;
    String supervisor_name;
    String supervisor_assistant_name;
    String incharge_name;
    String incharge_mobile;
    String logo_url;
    String logo_img;
/*{"id":1,        "url":"http:\/\/120.24.220.29\/api\/keyenterprise\/1\/",       "name":"金华悦国际酒店（惠州）",       "address":"广东惠州惠城区下埔区下埔大道28号",      "code":0,        "supervisor_org":"http:\/\/120.24.220.29\/api\/org\/3\/",      "supervisor":"http:\/\/120.24.220.29\/api\/fireman\/2\/",
"supervisor_assistant":"http:\/\/120.24.220.29\/api\/fireman\/3\/",       "incharge":"http:\/\/120.24.220.29\/api\/staff\/14\/",       "supervisor_org_name":"惠城区消防大队",         "supervisor_name":"参谋A",        "supervisor_assistant_name":"参谋B",       "incharge_name":"王深义",
"incharge_mobile":"13928309122",    "logo_url":"\/static\/media\/img\/5118795_204120459110_2.png",        "logo_img":"http:\/\/120.24.22029\/api\/imagestore\/334\/"}*/
    @Override
    public String toString() {
        return "Enterprise{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", code='" + code + '\'' +
                ", supervisor_org='" + supervisor_org + '\'' +
                ", supervisor='" + supervisor + '\'' +
                ", supervisor_assistant='" + supervisor_assistant + '\'' +
                ", incharge='" + incharge + '\'' +
                ", supervisor_org_name='" + supervisor_org_name + '\'' +
                ", supervisor_name='" + supervisor_name + '\'' +
                ", supervisor_assistant_name='" + supervisor_assistant_name + '\'' +
                ", incharge_name='" + incharge_name + '\'' +
                ", incharge_mobile='" + incharge_mobile + '\'' +
                ", logo_url='" + logo_url + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCode() {
        return code;
    }

    public String getSupervisor_org() {
        return supervisor_org;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public String getSupervisor_assistant() {
        return supervisor_assistant;
    }

    public String getIncharge() {
        return incharge;
    }

    public String getSupervisor_org_name() {
        return supervisor_org_name;
    }

    public String getSupervisor_name() {
        return supervisor_name;
    }

    public String getSupervisor_assistant_name() {
        return supervisor_assistant_name;
    }

    public String getIncharge_name() {
        return incharge_name;
    }

    public String getIncharge_mobile() {
        return incharge_mobile;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public static Parcelable.Creator<Enterprise> getCreator() {
        return CREATOR;
    }

    public static final Parcelable.Creator<Enterprise> CREATOR = new Creator<Enterprise>() {

        @Override
        public Enterprise createFromParcel(Parcel source) {
            Enterprise enterprise = new Enterprise();
            enterprise.id = source.readInt();
            enterprise.url = source.readString();
            enterprise.name = source.readString();
            enterprise.address = source.readString();
            enterprise.code = source.readString();
            enterprise.supervisor_org = source.readString();
            enterprise.supervisor = source.readString();
            enterprise.supervisor_assistant = source.readString();
            enterprise.incharge = source.readString();
            enterprise.supervisor_org_name = source.readString();
            enterprise.supervisor_name = source.readString();
            enterprise.supervisor_assistant_name = source.readString();
            enterprise.incharge_name = source.readString();
            enterprise.incharge_mobile = source.readString();
            enterprise.logo_url = source.readString();
            return enterprise;
        }

        @Override
        public Enterprise[] newArray(int size) {
            return new Enterprise[size];
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
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(code);
        dest.writeString(supervisor_org);
        dest.writeString(supervisor);
        dest.writeString(supervisor_assistant);
        dest.writeString(incharge);
        dest.writeString(supervisor_org_name);
        dest.writeString(supervisor_name);
        dest.writeString(supervisor_assistant_name);
        dest.writeString(incharge_name);
        dest.writeString(incharge_mobile);
        dest.writeString(logo_url);
    }


}
