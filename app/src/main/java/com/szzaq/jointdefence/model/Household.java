package com.szzaq.jointdefence.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DOS on 2016/5/16 0016.
 */
public class Household implements Parcelable {
    public String ctl00_MainContent_txtDWPYJC, ctl00_MainContent_txtPLDWN,
            id, url, account, pwd, ctl00_MainContent_txtDWMC,
            ctl00_MainContent_txtSYMC,//使用名称--
            ctl00_MainContent_txtDWDZ,// 单位地址--
            ctl00_MainContent_lblZDDWLB,// 火灾危险性--
            ctl00_MainContent_lblDWLX,// 单位类型--
            ctl00_MainContent_lblDWQTQK,//单位其他情况--
            ctl00_MainContent_txtSJZGDW,//上级单位--
            ctl00_MainContent_txtDWDH,//单位电话--
            ctl00_MainContent_txtDWCZ,//单位传真--
            ctl00_MainContent_txtDWDZYX,// E-Mail--
            ctl00_MainContent_txtYYSZDRS,//营业时最大人数--
            ctl00_MainContent_txtFRDB,// 法人代表--
            ctl00_MainContent_txtAQGLR,//安全管理人--
            ctl00_MainContent_txtAQZRR,//安全责任人--
            ctl00_MainContent_txtZJZXFG, //专兼职消防管理人--
            ctl00_MainContent_txtXFGXDW;//管辖单位
    public String ctl00_MainContent_cblQTQK, ctl00_MainContent_radSF;
    public String ctl00_MainContent_txtPLDWD;
    public String ctl00_MainContent_rblZDXFSS;
    public String ctl00_MainContent_txtDWBH;
    public String ctl00_MainContent_txtPLDWX;
    public String ctl00_MainContent_txtPLDWB;
    public String ctl00_MainContent_txtBZ;
    public String ctl00_MainContent_lblDWZSX;
    public String ctl00_MainContent_lblDWCSX;
    public String ctl00_MainContent_txtDLWZ;
    public String ctl00_MainContent_txtBNCMJ;
    public String ctl00_MainContent_txtBNCS;
    public String ctl00_MainContent_txtXFCDWZ;
    public String ctl00_MainContent_txtXFDTS;
    public String ctl00_MainContent_txtXFCDS;
    public String ctl00_MainContent_txtSSDTS;
    public String ctl00_MainContent_txtAQCKS;
    public String ctl00_MainContent_txtYWXFYS;
    public String ctl00_MainContent_dropRQLX;
    public String ctl00_MainContent_txtJZMJ;
    public String ctl00_MainContent_txtZDMJ;
    public String ctl00_MainContent_txtZGRS;
    public String ctl00_MainContent_txtGDZC;
    public String ctl00_MainContent_lblXZQY;
    public String ctl00_MainContent_txtDWCLSJ;
    public String ctl00_MainContent_dropJJSYZS;
    public String ctl00_MainContent_dropJJSYZ;
    public String ctl00_MainContent_txtYZBM;
    public String ctl00_MainContent_txtZZJGDM;
    public String ctl00_MainContent_txtFRDBSFZ;
    public String ctl00_MainContent_txtAQGLRSFZ;
    public String ctl00_MainContent_txtAQZRRSFZ;
    public String ctl00_MainContent_txtZJZXFGLRDH;
    public String ctl00_MainContent_txtZJZXFGLRSFZ;
    public String ctl00_MainContent_txtBNCWZ;
    public String ctl00_MainContent_txtFRDBDH; //法人代表电话
    public String ctl00_MainContent_txtAQZRRDH; //安全责任人电话
    public String ctl00_MainContent_txtAQGLRDH; //安全责任人电话
    public String ctl00_MainContent_txtZJZXFGLR;//专兼职消防管理人


    /*public Household(String unitname, String unitaddr, String danger, String code) {
        this.ctl00_MainContent_txtDWMC = unitname;
        this.ctl00_MainContent_txtDWDZ = unitaddr;
        this.ctl00_MainContent_lblZDDWLB = danger;
        this.account = code;
    }*/
    public Household(String unitname, String unitaddr, String danger, String ctl00_MainContent_lblDWQTQK) {
        this.ctl00_MainContent_txtDWMC = unitname;
        this.ctl00_MainContent_txtDWDZ = unitaddr;
        this.ctl00_MainContent_lblZDDWLB = danger;
        this.ctl00_MainContent_lblDWQTQK = ctl00_MainContent_lblDWQTQK;
    }

    public Household(String ctl00_MainContent_txtDWMC,
                     String ctl00_MainContent_txtSYMC,
                     String ctl00_MainContent_txtDWDZ,
                     String ctl00_MainContent_lblZDDWLB,
                     String ctl00_MainContent_lblDWLX,
                     String ctl00_MainContent_lblDWQTQK,
                     String ctl00_MainContent_txtSJZGDW,
                     String ctl00_MainContent_txtDWDH,
                     String ctl00_MainContent_txtDWCZ,
                     String ctl00_MainContent_txtDWDZYX,
                     String ctl00_MainContent_txtYYSZDRS,
                     String ctl00_MainContent_txtFRDB,
                     String ctl00_MainContent_txtAQGLR,
                     String ctl00_MainContent_txtAQZRR,
                     String ctl00_MainContent_txtZJZXFG,
                     String account,
                     String pwd,
                     String ctl00_MainContent_txtXFGXDW,

                     String ctl00_MainContent_txtPLDWD,
                     String ctl00_MainContent_rblZDXFSS,
                     String ctl00_MainContent_txtDWBH,
                     String ctl00_MainContent_txtPLDWX,
                     String ctl00_MainContent_txtPLDWB,
                     String ctl00_MainContent_txtBZ,
                     String ctl00_MainContent_lblDWZSX,
                     String ctl00_MainContent_lblDWCSX,
                     String ctl00_MainContent_txtDLWZ,
                     String ctl00_MainContent_txtBNCMJ,
                     String ctl00_MainContent_txtBNCS,
                     String ctl00_MainContent_txtXFCDWZ,
                     String ctl00_MainContent_txtXFDTS,
                     String ctl00_MainContent_txtXFCDS,
                     String ctl00_MainContent_txtSSDTS,
                     String ctl00_MainContent_txtAQCKS,
                     String ctl00_MainContent_txtYWXFYS,
                     String ctl00_MainContent_dropRQLX,
                     String ctl00_MainContent_txtJZMJ,
                     String ctl00_MainContent_txtZDMJ,
                     String ctl00_MainContent_txtZGRS,
                     String ctl00_MainContent_txtGDZC,
                     String ctl00_MainContent_lblXZQY,
                     String ctl00_MainContent_txtDWCLSJ,
                     String ctl00_MainContent_dropJJSYZS,
                     String ctl00_MainContent_dropJJSYZ,
                     String ctl00_MainContent_txtYZBM,
                     String ctl00_MainContent_txtZZJGDM,
                     String ctl00_MainContent_txtFRDBSFZ,
                     String ctl00_MainContent_txtAQGLRSFZ,
                     String ctl00_MainContent_txtAQZRRSFZ,
                     String ctl00_MainContent_txtZJZXFGLRDH,
                     String ctl00_MainContent_txtZJZXFGLRSFZ,
                     String ctl00_MainContent_txtBNCWZ,
                     String ctl00_MainContent_txtFRDBDH,
                     String ctl00_MainContent_txtAQZRRDH,
                     String ctl00_MainContent_txtAQGLRDH) {
        this.account = account;
        this.pwd = pwd;
        this.ctl00_MainContent_txtDWMC = ctl00_MainContent_txtDWMC;
        this.ctl00_MainContent_txtSYMC = ctl00_MainContent_txtSYMC;
        this.ctl00_MainContent_txtDWDZ = ctl00_MainContent_txtDWDZ;
        this.ctl00_MainContent_lblZDDWLB = ctl00_MainContent_lblZDDWLB;
        this.ctl00_MainContent_lblDWLX = ctl00_MainContent_lblDWLX;
        this.ctl00_MainContent_lblDWQTQK = ctl00_MainContent_lblDWQTQK;
        this.ctl00_MainContent_txtSJZGDW = ctl00_MainContent_txtSJZGDW;
        this.ctl00_MainContent_txtDWDH = ctl00_MainContent_txtDWDH;
        this.ctl00_MainContent_txtDWCZ = ctl00_MainContent_txtDWCZ;
        this.ctl00_MainContent_txtDWDZYX = ctl00_MainContent_txtDWDZYX;
        this.ctl00_MainContent_txtYYSZDRS = ctl00_MainContent_txtYYSZDRS;
        this.ctl00_MainContent_txtFRDB = ctl00_MainContent_txtFRDB;
        this.ctl00_MainContent_txtAQGLR = ctl00_MainContent_txtAQGLR;
        this.ctl00_MainContent_txtAQZRR = ctl00_MainContent_txtAQZRR;
        this.ctl00_MainContent_txtZJZXFG = ctl00_MainContent_txtZJZXFG;
        this.ctl00_MainContent_txtXFGXDW = ctl00_MainContent_txtXFGXDW;

        this.ctl00_MainContent_txtPLDWD = ctl00_MainContent_txtPLDWD;
        this.ctl00_MainContent_rblZDXFSS = ctl00_MainContent_rblZDXFSS;
        this.ctl00_MainContent_txtDWBH = ctl00_MainContent_txtDWBH;
        this.ctl00_MainContent_txtPLDWX = ctl00_MainContent_txtPLDWX;
        this.ctl00_MainContent_txtPLDWB = ctl00_MainContent_txtPLDWB;
        this.ctl00_MainContent_txtBZ = ctl00_MainContent_txtBZ;
        this.ctl00_MainContent_lblDWZSX = ctl00_MainContent_lblDWZSX;
        this.ctl00_MainContent_lblDWCSX = ctl00_MainContent_lblDWCSX;
        this.ctl00_MainContent_txtDLWZ = ctl00_MainContent_txtDLWZ;
        this.ctl00_MainContent_txtBNCMJ = ctl00_MainContent_txtBNCMJ;
        this.ctl00_MainContent_txtBNCS = ctl00_MainContent_txtBNCS;
        this.ctl00_MainContent_txtXFCDWZ = ctl00_MainContent_txtXFCDWZ;
        this.ctl00_MainContent_txtXFDTS = ctl00_MainContent_txtXFDTS;
        this.ctl00_MainContent_txtXFCDS = ctl00_MainContent_txtXFCDS;
        this.ctl00_MainContent_txtSSDTS = ctl00_MainContent_txtSSDTS;
        this.ctl00_MainContent_txtAQCKS = ctl00_MainContent_txtAQCKS;
        this.ctl00_MainContent_txtYWXFYS = ctl00_MainContent_txtYWXFYS;
        this.ctl00_MainContent_dropRQLX = ctl00_MainContent_dropRQLX;
        this.ctl00_MainContent_txtJZMJ = ctl00_MainContent_txtJZMJ;
        this.ctl00_MainContent_txtZDMJ = ctl00_MainContent_txtZDMJ;
        this.ctl00_MainContent_txtZGRS = ctl00_MainContent_txtZGRS;
        this.ctl00_MainContent_txtGDZC = ctl00_MainContent_txtGDZC;
        this.ctl00_MainContent_lblXZQY = ctl00_MainContent_lblXZQY;
        this.ctl00_MainContent_txtDWCLSJ = ctl00_MainContent_txtDWCLSJ;
        this.ctl00_MainContent_dropJJSYZS = ctl00_MainContent_dropJJSYZS;
        this.ctl00_MainContent_dropJJSYZ = ctl00_MainContent_dropJJSYZ;
        this.ctl00_MainContent_txtYZBM = ctl00_MainContent_txtYZBM;
        this.ctl00_MainContent_txtZZJGDM = ctl00_MainContent_txtZZJGDM;
        this.ctl00_MainContent_txtFRDBSFZ = ctl00_MainContent_txtFRDBSFZ;
        this.ctl00_MainContent_txtAQGLRSFZ = ctl00_MainContent_txtAQGLRSFZ;
        this.ctl00_MainContent_txtAQZRRSFZ = ctl00_MainContent_txtAQZRRSFZ;
        this.ctl00_MainContent_txtZJZXFGLRDH = ctl00_MainContent_txtZJZXFGLRDH;
        this.ctl00_MainContent_txtZJZXFGLRSFZ = ctl00_MainContent_txtZJZXFGLRSFZ;
        this.ctl00_MainContent_txtBNCWZ = ctl00_MainContent_txtBNCWZ;
        this.ctl00_MainContent_txtFRDBDH = ctl00_MainContent_txtFRDBDH;
        this.ctl00_MainContent_txtAQZRRDH = ctl00_MainContent_txtAQZRRDH;
        this.ctl00_MainContent_txtAQGLRDH = ctl00_MainContent_txtAQGLRDH;
    }


    public Household() {

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ctl00_MainContent_txtDWPYJC);
        dest.writeString(this.ctl00_MainContent_txtPLDWN);
        dest.writeString(this.id);
        dest.writeString(this.url);
        dest.writeString(this.account);
        dest.writeString(this.pwd);
        dest.writeString(this.ctl00_MainContent_txtDWMC);
        dest.writeString(this.ctl00_MainContent_txtSYMC);
        dest.writeString(this.ctl00_MainContent_txtDWDZ);
        dest.writeString(this.ctl00_MainContent_lblZDDWLB);
        dest.writeString(this.ctl00_MainContent_lblDWLX);
        dest.writeString(this.ctl00_MainContent_lblDWQTQK);
        dest.writeString(this.ctl00_MainContent_txtSJZGDW);
        dest.writeString(this.ctl00_MainContent_txtDWDH);
        dest.writeString(this.ctl00_MainContent_txtDWCZ);
        dest.writeString(this.ctl00_MainContent_txtDWDZYX);
        dest.writeString(this.ctl00_MainContent_txtYYSZDRS);
        dest.writeString(this.ctl00_MainContent_txtFRDB);
        dest.writeString(this.ctl00_MainContent_txtAQGLR);
        dest.writeString(this.ctl00_MainContent_txtAQZRR);
        dest.writeString(this.ctl00_MainContent_txtZJZXFG);
        dest.writeString(this.ctl00_MainContent_txtXFGXDW);
        dest.writeString(this.ctl00_MainContent_cblQTQK);
        dest.writeString(this.ctl00_MainContent_radSF);
        dest.writeString(this.ctl00_MainContent_txtPLDWD);
        dest.writeString(this.ctl00_MainContent_rblZDXFSS);
        dest.writeString(this.ctl00_MainContent_txtDWBH);
        dest.writeString(this.ctl00_MainContent_txtPLDWX);
        dest.writeString(this.ctl00_MainContent_txtPLDWB);
        dest.writeString(this.ctl00_MainContent_txtBZ);
        dest.writeString(this.ctl00_MainContent_lblDWZSX);
        dest.writeString(this.ctl00_MainContent_lblDWCSX);
        dest.writeString(this.ctl00_MainContent_txtDLWZ);
        dest.writeString(this.ctl00_MainContent_txtBNCMJ);
        dest.writeString(this.ctl00_MainContent_txtBNCS);
        dest.writeString(this.ctl00_MainContent_txtXFCDWZ);
        dest.writeString(this.ctl00_MainContent_txtXFDTS);
        dest.writeString(this.ctl00_MainContent_txtXFCDS);
        dest.writeString(this.ctl00_MainContent_txtSSDTS);
        dest.writeString(this.ctl00_MainContent_txtAQCKS);
        dest.writeString(this.ctl00_MainContent_txtYWXFYS);
        dest.writeString(this.ctl00_MainContent_dropRQLX);
        dest.writeString(this.ctl00_MainContent_txtJZMJ);
        dest.writeString(this.ctl00_MainContent_txtZDMJ);
        dest.writeString(this.ctl00_MainContent_txtZGRS);
        dest.writeString(this.ctl00_MainContent_txtGDZC);
        dest.writeString(this.ctl00_MainContent_lblXZQY);
        dest.writeString(this.ctl00_MainContent_txtDWCLSJ);
        dest.writeString(this.ctl00_MainContent_dropJJSYZS);
        dest.writeString(this.ctl00_MainContent_dropJJSYZ);
        dest.writeString(this.ctl00_MainContent_txtYZBM);
        dest.writeString(this.ctl00_MainContent_txtZZJGDM);
        dest.writeString(this.ctl00_MainContent_txtFRDBSFZ);
        dest.writeString(this.ctl00_MainContent_txtAQGLRSFZ);
        dest.writeString(this.ctl00_MainContent_txtAQZRRSFZ);
        dest.writeString(this.ctl00_MainContent_txtZJZXFGLRDH);
        dest.writeString(this.ctl00_MainContent_txtZJZXFGLRSFZ);
        dest.writeString(this.ctl00_MainContent_txtBNCWZ);
        dest.writeString(this.ctl00_MainContent_txtFRDBDH);
        dest.writeString(this.ctl00_MainContent_txtAQZRRDH);
        dest.writeString(this.ctl00_MainContent_txtAQGLRDH);
        dest.writeString(this.ctl00_MainContent_txtZJZXFGLR);
    }

    protected Household(Parcel in) {
        this.ctl00_MainContent_txtDWPYJC = in.readString();
        this.ctl00_MainContent_txtPLDWN = in.readString();
        this.id = in.readString();
        this.url = in.readString();
        this.account = in.readString();
        this.pwd = in.readString();
        this.ctl00_MainContent_txtDWMC = in.readString();
        this.ctl00_MainContent_txtSYMC = in.readString();
        this.ctl00_MainContent_txtDWDZ = in.readString();
        this.ctl00_MainContent_lblZDDWLB = in.readString();
        this.ctl00_MainContent_lblDWLX = in.readString();
        this.ctl00_MainContent_lblDWQTQK = in.readString();
        this.ctl00_MainContent_txtSJZGDW = in.readString();
        this.ctl00_MainContent_txtDWDH = in.readString();
        this.ctl00_MainContent_txtDWCZ = in.readString();
        this.ctl00_MainContent_txtDWDZYX = in.readString();
        this.ctl00_MainContent_txtYYSZDRS = in.readString();
        this.ctl00_MainContent_txtFRDB = in.readString();
        this.ctl00_MainContent_txtAQGLR = in.readString();
        this.ctl00_MainContent_txtAQZRR = in.readString();
        this.ctl00_MainContent_txtZJZXFG = in.readString();
        this.ctl00_MainContent_txtXFGXDW = in.readString();
        this.ctl00_MainContent_cblQTQK = in.readString();
        this.ctl00_MainContent_radSF = in.readString();
        this.ctl00_MainContent_txtPLDWD = in.readString();
        this.ctl00_MainContent_rblZDXFSS = in.readString();
        this.ctl00_MainContent_txtDWBH = in.readString();
        this.ctl00_MainContent_txtPLDWX = in.readString();
        this.ctl00_MainContent_txtPLDWB = in.readString();
        this.ctl00_MainContent_txtBZ = in.readString();
        this.ctl00_MainContent_lblDWZSX = in.readString();
        this.ctl00_MainContent_lblDWCSX = in.readString();
        this.ctl00_MainContent_txtDLWZ = in.readString();
        this.ctl00_MainContent_txtBNCMJ = in.readString();
        this.ctl00_MainContent_txtBNCS = in.readString();
        this.ctl00_MainContent_txtXFCDWZ = in.readString();
        this.ctl00_MainContent_txtXFDTS = in.readString();
        this.ctl00_MainContent_txtXFCDS = in.readString();
        this.ctl00_MainContent_txtSSDTS = in.readString();
        this.ctl00_MainContent_txtAQCKS = in.readString();
        this.ctl00_MainContent_txtYWXFYS = in.readString();
        this.ctl00_MainContent_dropRQLX = in.readString();
        this.ctl00_MainContent_txtJZMJ = in.readString();
        this.ctl00_MainContent_txtZDMJ = in.readString();
        this.ctl00_MainContent_txtZGRS = in.readString();
        this.ctl00_MainContent_txtGDZC = in.readString();
        this.ctl00_MainContent_lblXZQY = in.readString();
        this.ctl00_MainContent_txtDWCLSJ = in.readString();
        this.ctl00_MainContent_dropJJSYZS = in.readString();
        this.ctl00_MainContent_dropJJSYZ = in.readString();
        this.ctl00_MainContent_txtYZBM = in.readString();
        this.ctl00_MainContent_txtZZJGDM = in.readString();
        this.ctl00_MainContent_txtFRDBSFZ = in.readString();
        this.ctl00_MainContent_txtAQGLRSFZ = in.readString();
        this.ctl00_MainContent_txtAQZRRSFZ = in.readString();
        this.ctl00_MainContent_txtZJZXFGLRDH = in.readString();
        this.ctl00_MainContent_txtZJZXFGLRSFZ = in.readString();
        this.ctl00_MainContent_txtBNCWZ = in.readString();
        this.ctl00_MainContent_txtFRDBDH = in.readString();
        this.ctl00_MainContent_txtAQZRRDH = in.readString();
        this.ctl00_MainContent_txtAQGLRDH = in.readString();
        this.ctl00_MainContent_txtZJZXFGLR = in.readString();
    }

    public static final Parcelable.Creator<Household> CREATOR = new Parcelable.Creator<Household>() {
        @Override
        public Household createFromParcel(Parcel source) {
            return new Household(source);
        }

        @Override
        public Household[] newArray(int size) {
            return new Household[size];
        }
    };
}
