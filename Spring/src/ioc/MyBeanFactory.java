package ioc;
import java.io.File;
import java.lang.reflect.Method;
import java.beans.*;
import java.util.*;

import org.dom4j.*;
import org.dom4j.io.*;

public class MyBeanFactory{
	private static final String SRC_PATH= System.getProperty("user.dir")
			 + File.separator + "src" + File.separator ;
	private String path;
	private AnnotationIocContainer aic;
	private ArrayList<Bean> bean;
	private ArrayList<String> auto;
	protected Map<String, Object> beanMap ;
	public MyBeanFactory(){
		bean=new ArrayList<Bean>();
		auto =new ArrayList<String>();
		beanMap = new HashMap<String, Object>();
		aic=new AnnotationIocContainer();
		path=SRC_PATH+"bean.xml";
		parseXml();
		aic.bind("src", beanMap,bean,auto);
		instanceBean();
		
		
		injectBeans();
	}
	
	private void instanceBean() {
		for(int i=0;i<bean.size();i++){
			Bean b=bean.get(i);
			try {
				    Object o=Class.forName(b.getClassName()).newInstance();
				    beanMap.put(b.getId(), o);
				
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				
				e.printStackTrace();
			}
		}
	}
	private void injectBeans() {
		for(int i=0;i<bean.size();i++){
			Bean b=bean.get(i);
			if(b.getProperty().size()!=0&&auto.indexOf(b.getClassName())==-1){
				injectBeanProperties(beanMap.get(b.getId()),b);
			}
			else if(b.getProperty().size()!=0&&auto.indexOf(b.getClassName())!=-1){
				aic.processSetterAnnotation(b, beanMap);
			}
		}
	}
	
	private void injectBeanProperties(Object o,Bean b){
		try{
			ArrayList<Property> pro=b.getProperty();
			PropertyDescriptor[] ps = Introspector.getBeanInfo(o.getClass()).getPropertyDescriptors();
			for(int i=0;i<pro.size();i++){
				for(PropertyDescriptor propertyDescriptor:ps){
					if(propertyDescriptor.getName().equals(pro.get(i).getName())){ 
				        Method setter = propertyDescriptor.getWriteMethod(); 
                        setter.setAccessible(true);
                        if(pro.get(i).getRef()!=null)
                            setter.invoke(o, getBean(pro.get(i).getRef()));  
                        else
                        	setter.invoke(o, pro.get(i).getValue());  
					}
                }
			}
		}catch(Exception e){
			 e.printStackTrace();
		}
		
	    	
	}
	
	public Object getBean(String name){
		Object o=beanMap.get(name);
		return o;
	}
	
	private void parseXml(){

		SAXReader reader = new SAXReader();  
		Bean b1=null;
		String id="";
		String class1="";
	     try {
			Document  document = reader.read(new File(path));
			Element root=document.getRootElement();
			List root1= root.elements("bean");
			for (Iterator it = root1.iterator(); it.hasNext();) {    
				Element elm = (Element) it.next();  
				id=elm.attributeValue("id");
				class1=elm.attributeValue("class");
				b1=new Bean(id,class1);
				List root2=elm.elements("property");
				for (Iterator it1 = root2.iterator(); it1.hasNext();) {   
					Element elm2 = (Element) it1.next();  
					String name=elm2.attributeValue("name");
					String value=elm2.attributeValue("value");
					String ref=elm2.attributeValue("ref");
					b1.addProperty(new Property(name,value,ref));
				}
				bean.add(b1);
			}
			

		} catch (DocumentException e) {		
		}     
	}

}


