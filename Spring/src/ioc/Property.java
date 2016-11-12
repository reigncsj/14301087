package ioc;

public class Property {
	private String name="";
	private String value="";
	private String ref="";
	public Property(){
	}
	
	public Property(String name,String value,String ref){
		this.name=name;
		this.value=value;
		this.ref=ref;
	}
	public void setName(String name){
		this.name=name;
	}

	public void setValue(String value){
		this.value=value;
	}
	
	public void setRef(String ref){
		this.ref=ref;
	}
	
	public String getName(){
		return name;
	}
	
	public String getValue(){
		return value;
	}
	
	public String getRef(){
		return ref;
	}
}
