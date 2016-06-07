package com.szzaq.jointdefence.model;

public  class User {

	private int id; // 用户ID
	private String username; // 用户名
	private String nickname; // 昵称/真实姓名
	private int fco_id;// (外键) 所属单位id
	private String fco_name; // string 所属单位
	private int role_id; // (外键) integer 角色id
	private String role_name; // (外键) integer 角色名称
	private String phone;// string 手机号码
	private int status;// integer 状态(工作/请假/...)
	private int agent;// (外键) integer 代理人
	private String  agent_name;//integer 代理人
	private String type;// string 用户类型【'消防机构用户、重点单位用户、三小场所用户，对应的字符串分别为：
						// FireController','MaintainUnit','KeyUnit'】
	private String  role_city;//       所属单位所在市
	private String    role_district;    //   所属单位所在区
	private String   role_province ;   //   所属单位所在省
	

	public int getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getNickname() {
		return nickname;
	}
	
	public int getFco_id() {
		return fco_id;
	}

	public String getFco_name() {
		return fco_name;
	}
	
	public int getRole_id() {
		return role_id;
	}
	
	public String getRole_name() {
		return role_name;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public int getStatus() {
		return status;
	}

	public int getAgent() {
		return agent;
	}
	
	public String getType() {
		return type;
	}

	public String getAgent_name() {
		return agent_name;
	}

	public String getRole_city() {
		return role_city;
	}



	public String getRole_district() {
		return role_district;
	}



	public String getRole_province() {
		return role_province;
	}

	

	

	
	
	
}
