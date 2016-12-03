package mvc;

import java.util.*;

public class ModelAndView {
	private String name;
	private Map<String,Object> map=new HashMap<String,Object>();
	public void setViewName(String name){
		this.name=name;
	}
	public void setMap(Map<String, Object> s){
		map=s;
	}
	public void addObject(String s,Object o){
		map.put(s, o);
	}
    
	public Object getMap(String name){
		return map.get(name);
	}
	
	public String getName(){
		return name;
	}
	
}
