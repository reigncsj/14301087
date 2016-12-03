package mvc;

import java.util.Map;

public class Controllers {
	private Object object;
	private Class<?> c;
	private Map<String,String> method;
	
	public Controllers(Object o,Class<?> c,Map<String,String> m){
		object=o;
		this.c=c;
		method=m;
	}
	
	public void setObject(Object o){
		this.object=o;
	}
	public void setClass(Class<?> c){
		this.c=c;
	}
	public void setMethod(Map<String,String> m){
		method=m;
	}
	
	public Object getObject(){
		return object;
	}
	
    public Class<?> getClass1(){
    	return c;
    }
    
    public Map<String,String> getMethod(){
    	return method;
    }
}
