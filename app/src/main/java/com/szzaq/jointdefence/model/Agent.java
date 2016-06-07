package com.szzaq.jointdefence.model;
/**
 * 代理人实体类
 * @author jiajiaUser
 *
 */
public class Agent {
	private int role_id;
	private String role_name;
	private int id;
	private String name;
	private String resource;
	public int getRole_id() {
		return role_id;
	}
	
	public String getRole_name() {
		return role_name;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
