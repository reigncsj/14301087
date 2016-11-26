package Aopproxy;

public class PropertyValue {
	public PropertyValue(String name, Object value,String ref) {
		this.name = name;
		this.value = value;
		this.ref=ref;
	}
	private String name;
	
	private Object value;
	
	private String ref;
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
}
