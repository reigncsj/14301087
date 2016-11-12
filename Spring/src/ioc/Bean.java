package ioc;
import java.util.*;


public class Bean {
	private String id;
	private String classname;
	private ArrayList<Property> pro;
	public Bean(String i,String c){
		id=i;
		classname=c;
		pro=new ArrayList<Property>();
	}
	
	public void setId(String id){
		this.id=id;
	}

	public void setClassName(String classname){
		this.classname=classname;
	}
	
	public String getId(){
		return id;
	}
	
	public String getClassName(){
		return classname;
	}
	
	public void addProperty(Property p){
		pro.add(p);
	}
	
	public ArrayList<Property> getProperty(){
		return pro;
	}
}
