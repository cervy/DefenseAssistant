package com.szzaq.jointdefence.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DOS on 2016/5/13 0013.
 */
public class ZoneDefense implements Parcelable {
    public String id, url, task_type, person_incharge, person_incharge_name,//责任人姓名
            expired_date,//任务完成期限
            assigner, created,//创建时间
            assigntime, finishstate, task_desc, finish_time, report;//任务反馈
    public  String[]attachments;
    public  String [] attachment_urls;
/*



    public ZoneDefense(String tasktype, String zerenren, String deadline, String assigner, String assigntime, String finishstate, String taskdescription, String finishtime, String taskfeedback) {
        this.task_desc = taskdescription;
        this.assigner = assigner;
        this.task_type = tasktype;
        this.person_incharge_name = zerenren;
        this.expired_date = deadline;
        this.assigntime = assigntime;
        this.finishstate = finishstate;
        this.finish_time = finishtime;
        this.report = taskfeedback;
    }


    public ZoneDefense(String tasktype, String zerenren, String finishtime) {
        this.task_type = tasktype;
        this.person_incharge_name = zerenren;
        this.finish_time = finishtime;
    }
*/


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.url);
        dest.writeString(this.task_type);
        dest.writeString(this.person_incharge);
        dest.writeString(this.person_incharge_name);
        dest.writeString(this.expired_date);
        dest.writeString(this.assigner);
        dest.writeString(this.created);
        dest.writeString(this.assigntime);
        dest.writeString(this.finishstate);
        dest.writeString(this.task_desc);
        dest.writeString(this.finish_time);
        dest.writeString(this.report);
        dest.writeStringArray(this.attachments);
        dest.writeStringArray(this.attachment_urls);
    }

    protected ZoneDefense(Parcel in) {
        this.id = in.readString();
        this.url = in.readString();
        this.task_type = in.readString();
        this.person_incharge = in.readString();
        this.person_incharge_name = in.readString();
        this.expired_date = in.readString();
        this.assigner = in.readString();
        this.created = in.readString();
        this.assigntime = in.readString();
        this.finishstate = in.readString();
        this.task_desc = in.readString();
        this.finish_time = in.readString();
        this.report = in.readString();
        this.attachments = in.createStringArray ();
        this.attachment_urls = in.createStringArray();
    }

    public static final Parcelable.Creator<ZoneDefense> CREATOR = new Parcelable.Creator<ZoneDefense>() {
        @Override
        public ZoneDefense createFromParcel(Parcel source) {
            return new ZoneDefense(source);
        }

        @Override
        public ZoneDefense[] newArray(int size) {
            return new ZoneDefense[size];
        }
    };
}
