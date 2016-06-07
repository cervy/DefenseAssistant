package com.szzaq.jointdefence.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable{
	 private String url;
     private String task_user;
     private String title;
     private String content;
     private String task_type;
     private String expiration;
     private String sender;
     private String status;
     private String related_device;
     private String created;
  
 	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTask_user() {
		return task_user;
	}
	public void setTask_user(String task_user) {
		this.task_user = task_user;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTask_type() {
		return task_type;
	}
	public void setTask_type(String task_type) {
		this.task_type = task_type;
	}
	public String getExpiration() {
		return expiration;
	}
	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRelated_device() {
		return related_device;
	}
	public void setRelated_device(String related_device) {
		this.related_device = related_device;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public static final Parcelable.Creator<Task> CREATOR = new Creator<Task>() {  
  	  
        @Override  
        public Task createFromParcel(Parcel source) {  
        	Task task = new Task();  
	       	task.url = source.readString();
	        task.task_user = source.readString();
	        task.title = source.readString();
	        task.content = source.readString();
	        task.task_type = source.readString();
	        task.expiration = source.readString();
	        task.sender = source.readString();
	        task.status = source.readString();
	        task.related_device = source.readString();
	        task.created = source.readString();        	

            return task;  
        }  
  
	    @Override  
	    public Task[] newArray(int size) {  
	        return new Task[size];  
	    }  
    };
    
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		 dest.writeString(url);
	     dest.writeString(task_user);
	     dest.writeString(title);
	     dest.writeString(content);
	     dest.writeString(task_type);
	     dest.writeString(expiration);
	     dest.writeString(sender);
	     dest.writeString(status);
	     dest.writeString(related_device);
	     dest.writeString(created);
		
	}
}
