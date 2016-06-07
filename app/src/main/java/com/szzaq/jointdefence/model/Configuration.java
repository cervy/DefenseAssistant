package com.szzaq.jointdefence.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Configuration {
	public HashMap<String,String> [] ORG_TYPE_CHOICES ;
	public HashMap<String,String> [] INSPECT_TYPE_COHICES;
	public HashMap<String,String> [] INSPECT_METHOD_CHOICES;
	public HashMap<String,String> [] INSPECT_TIME_TYPE_CHOICES;
	public HashMap<String,String> [] HAS_CERTIFICATION_CHOICES;



	public HashMap<String,String> [] DANGER_STATUS_CHOICES;
	public HashMap<String,String> [] DEVICE_TYPE_CHOICES;
	public HashMap<String,String> [] DEVICE_STATUS_CHOICES;
	public HashMap<String,String> [] DANGER_FIX_TYPE_CHOICES;
	public HashMap<String,String> [] ROLE_CHOICES;
	public HashMap<String,String> [] COWORKERS;
	public HashMap<String,String> [] TASK_TYPE_CHOICES;
	public HashMap<String,String> [] TASK_STATUS_CHOICES;
	public HashMap<String,ArrayList<HashMap<String,Object>>> DANGER_TYPES;
	
	
	public String getTypeName(HashMap<String,String> maps[],String type){
		if (maps==null) return type;
		String ret = "";

		for (HashMap<String,String> map:maps){
			for (String key:map.keySet()){
				if(type.equals(key)){
					return map.get(key);
				}
			}
		}
		return ret;
		
	}
	
	public ArrayList<HashMap<String,Object>> getDangerTypes(String siteType){
		return DANGER_TYPES.get(siteType);
	}
}