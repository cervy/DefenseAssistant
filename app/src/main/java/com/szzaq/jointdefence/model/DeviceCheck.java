package com.szzaq.jointdefence.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DeviceCheck implements Parcelable {
    String status;
    String fix_type;
    String device;
    String advice;
    String expiration;
    String created;
    String parent_record;
    String inspect_type;
    String inspect_method;
    String inspect_time_type;
    String[] inspector = new String[]{};
    String[] superviser_assistant = new String[]{};
    String lat;
    String lng;
    String location;
    String[] img = new String[]{};
    String inspector_name;
    String assistant_name;
    String device_name;
    String enterprise_name;
    String[] sub_recordsString = new String[]{};
    String[] img_urls = new String[]{};
    String[] img_names = new String[]{};
    Task[] related_tasks;
    String inspector_portrait;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFix_type() {
        return fix_type;
    }

    public void setFix_type(String fix_type) {
        this.fix_type = fix_type;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getParent_record() {
        return parent_record;
    }

    public void setParent_record(String parent_record) {
        this.parent_record = parent_record;
    }

    public String getInspect_type() {
        return inspect_type;
    }

    public void setInspect_type(String inspect_type) {
        this.inspect_type = inspect_type;
    }

    public String getInspect_method() {
        return inspect_method;
    }

    public void setInspect_method(String inspect_method) {
        this.inspect_method = inspect_method;
    }

    public String getInspect_time_type() {
        return inspect_time_type;
    }

    public void setInspect_time_type(String inspect_time_type) {
        this.inspect_time_type = inspect_time_type;
    }

    public String[] getInspector() {
        return inspector;
    }

    public void setInspector(String[] inspector) {
        this.inspector = inspector;
    }

    public String[] getSuperviser_assistant() {
        return superviser_assistant;
    }

    public void setSuperviser_assistant(String[] superviser_assistant) {
        this.superviser_assistant = superviser_assistant;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String[] getImg() {
        return img;
    }

    public void setImg(String[] img) {
        this.img = img;
    }

    public String getInspector_name() {
        return inspector_name;
    }

    public void setInspector_name(String inspector_name) {
        this.inspector_name = inspector_name;
    }

    public String getAssistant_name() {
        return assistant_name;
    }

    public void setAssistant_name(String assistant_name) {
        this.assistant_name = assistant_name;
    }

    public String getDevice_name() {
        return device_name;
    }

    public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }

    public String getEnterprise_name() {
        return enterprise_name;
    }

    public void setEnterprise_name(String enterprise_name) {
        this.enterprise_name = enterprise_name;
    }

    public String[] getSub_recordsString() {
        return sub_recordsString;
    }

    public void setSub_recordsString(String[] sub_recordsString) {
        this.sub_recordsString = sub_recordsString;
    }

    public String[] getImg_urls() {
        return img_urls;
    }

    public void setImg_urls(String[] img_urls) {
        this.img_urls = img_urls;
    }


    public Task[] getRelated_tasks() {
        return related_tasks;
    }

    public void setRelated_tasks(Task[] related_tasks) {
        this.related_tasks = related_tasks;
    }


    public String getInspector_portrait() {
        return inspector_portrait;
    }


    public static final Parcelable.Creator<DeviceCheck> CREATOR = new Creator<DeviceCheck>() {

        @Override
        public DeviceCheck createFromParcel(Parcel source) {
            DeviceCheck deviceCheck = new DeviceCheck();
            deviceCheck.status = source.readString();
            deviceCheck.fix_type = source.readString();
            deviceCheck.device = source.readString();
            deviceCheck.advice = source.readString();
            deviceCheck.expiration = source.readString();
            deviceCheck.created = source.readString();
            deviceCheck.parent_record = source.readString();
            deviceCheck.inspect_type = source.readString();
            deviceCheck.inspect_method = source.readString();
            deviceCheck.inspect_time_type = source.readString();
            int len = source.readInt();
            if (len > 0) {
                deviceCheck.inspector = new String[len];
                source.readStringArray(deviceCheck.inspector);
            }
            len = source.readInt();
            if (len > 0) {
                deviceCheck.superviser_assistant = new String[len];
                source.readStringArray(deviceCheck.superviser_assistant);
            }
            deviceCheck.lat = source.readString();
            deviceCheck.lng = source.readString();
            deviceCheck.location = source.readString();
            len = source.readInt();
            if (len > 0) {
                deviceCheck.img = new String[len];
                source.readStringArray(deviceCheck.img);
            }
            deviceCheck.inspector_name = source.readString();
            deviceCheck.assistant_name = source.readString();
            deviceCheck.device_name = source.readString();
            deviceCheck.enterprise_name = source.readString();
            len = source.readInt();
            if (len > 0) {
                deviceCheck.sub_recordsString = new String[len];
                source.readStringArray(deviceCheck.sub_recordsString);
            }
            len = source.readInt();
            if (len > 0) {
                deviceCheck.img_urls = new String[len];
                source.readStringArray(deviceCheck.img_urls);
            }

            len = source.readInt();
            if (len > 0) {
                deviceCheck.img_names = new String[len];
                source.readStringArray(deviceCheck.img_names);
            }

            len = source.readInt();
            if (len > 0) {
                Parcelable[] parcelables = source.readParcelableArray(this.getClass().getClassLoader());
                deviceCheck.related_tasks = new Task[parcelables.length];
                for (int i = 0; i < parcelables.length; i++) {
                    deviceCheck.related_tasks[i] = (Task) parcelables[i];
                }
            }
            deviceCheck.inspector_portrait = source.readString();
            return deviceCheck;
        }

        @Override
        public DeviceCheck[] newArray(int size) {
            return new DeviceCheck[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int arg1) {
        dest.writeString(status);
        dest.writeString(fix_type);
        dest.writeString(device);
        dest.writeString(advice);
        dest.writeString(expiration);
        dest.writeString(created);
        dest.writeString(parent_record);
        dest.writeString(inspect_type);
        dest.writeString(inspect_method);
        dest.writeString(inspect_time_type);
        dest.writeInt(inspector.length);
        if (inspector.length > 0) {
            dest.writeStringArray(inspector);
        }
        dest.writeInt(superviser_assistant.length);
        if (superviser_assistant.length > 0) {
            dest.writeStringArray(superviser_assistant);
        }
        dest.writeString(lat);
        dest.writeString(lng);
        dest.writeString(location);
        dest.writeInt(img.length);
        if (img.length > 0) {
            dest.writeStringArray(img);
        }
        dest.writeString(inspector_name);
        dest.writeString(assistant_name);
        dest.writeString(device_name);
        dest.writeString(enterprise_name);
        dest.writeInt(sub_recordsString.length);
        if (sub_recordsString.length > 0) {
            dest.writeStringArray(sub_recordsString);
        }
        dest.writeInt(img_urls.length);
        if (img_urls.length > 0) {
            dest.writeStringArray(img_urls);
        }
        dest.writeInt(img_names.length);
        if (img_names.length > 0) {
            dest.writeStringArray(img_names);
        }
        dest.writeInt(related_tasks.length);
        if (related_tasks.length > 0) {
            dest.writeParcelableArray(related_tasks, related_tasks.length);
        }
        dest.writeString(inspector_portrait);
    }

    public String[] getImg_names() {
        return img_names;
    }

    public void setImg_names(String[] img_names) {
        this.img_names = img_names;
    }

    public static class Task implements Parcelable {
        String user_name;
        int status;
        String expiration;
        int task_type;
        String[] task_created_check;


        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getExpiration() {
            return expiration;
        }

        public void setExpiration(String expiration) {
            this.expiration = expiration;
        }

        public int getTask_type() {
            return task_type;
        }

        public void setTask_type(int task_type) {
            this.task_type = task_type;
        }

        public String[] getTask_created_check() {
            return task_created_check;
        }

        public void setTask_created_check(String[] task_created_check) {
            this.task_created_check = task_created_check;
        }

        protected Task() {

        }

        @Override
        public int describeContents() {
            return 0;
        }


        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(user_name);
            dest.writeInt(status);
            dest.writeString(expiration);
            dest.writeInt(task_type);
            dest.writeInt(task_created_check.length);
            if (task_created_check.length > 0)
                dest.writeStringArray(task_created_check);
        }

        public static final Parcelable.Creator<Task> CREATOR = new Creator<Task>() {

            @Override
            public Task createFromParcel(Parcel source) {
                Task task = new Task();
                task.user_name = source.readString();
                task.status = source.readInt();
                task.expiration = source.readString();
                task.task_type = source.readInt();
                int len = source.readInt();
                if (len > 0) {
                    task.task_created_check = new String[len];
                    source.readStringArray(task.task_created_check);
                }
                return task;
            }

            @Override
            public Task[] newArray(int size) {
                return new Task[size];
            }

        };
    }
}
