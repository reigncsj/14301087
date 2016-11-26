package Aopproxy;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


public class XMLBeanFactory extends AbstractBeanFactory{
	
	private String xmlPath;
	
	public XMLBeanFactory(Resource resource)
	{
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
			Document document = dbBuilder.parse(resource.getInputStream());
            NodeList beanList = document.getElementsByTagName("bean");
            for(int i = 0 ; i < beanList.getLength(); i++)
            {
            	Node bean = beanList.item(i);
            	BeanDefinition beandef = new BeanDefinition();
            	String beanClassName = bean.getAttributes().getNamedItem("class").getNodeValue();
            	String beanName = bean.getAttributes().getNamedItem("id").getNodeValue();
            	
        		beandef.setBeanClassName(beanClassName);
        		
				try {
					Class<?> beanClass = Class.forName(beanClassName);
					beandef.setBeanClass(beanClass);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        		PropertyValues propertyValues = new PropertyValues();
        		
        		NodeList propertyList = bean.getChildNodes();
            	for(int j = 0 ; j < propertyList.getLength(); j++)
            	{
            		Node property = propertyList.item(j);
            		if (property instanceof Element) {
        				Element ele = (Element) property;
        				
        				String name = ele.getAttribute("name");
        				Class<?> type;
						try {
							Object value=null;
							String ref=null;
							type = beandef.getBeanClass().getDeclaredField(name).getType();

	        				if(type!=List.class){
								NodeList propertyList1 = ((Element) property).getElementsByTagName("value");
								NodeList propertyList2 = ((Element) property).getElementsByTagName("ref");
	        					Node property1 = propertyList1.item(0);
	        					if(property1!=null){
	        						Element ele1 = (Element) property1;
	        						value = ele1.getTextContent();
	        					}
	        					Node property2 = propertyList2.item(0);
	        					if(property2!=null){
	        						Element ele2 = (Element) property2;
	        						ref=ele2.getAttribute("local");
	        					}
	        					if(type == Integer.class)
	        						value = Integer.parseInt((String) value);
	        				}else{
	        					NodeList propertyList1 = ((Element) property).getElementsByTagName("list");
	        					Node property1 = propertyList1.item(0);
	        					if(property1!=null){
	        						NodeList propertyList2 = ((Element) property1).getElementsByTagName("value");
	        						List<Object> list=new ArrayList<Object>();
	        						for(int k=0;k<propertyList2.getLength();k++){
	        							Node property2 = propertyList2.item(k);
	        						    Element ele2 = (Element) property2;
	        						    value = ele2.getTextContent();
	        						    list.add(value);
	        						}
	        						value=list;
	        					}
	        				}
	        				
	        				
	        				propertyValues.AddPropertyValue(new PropertyValue(
	        						name,value,ref));
						} catch (NoSuchFieldException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        				
        				
        			}
            	}
            	beandef.setPropertyValues(propertyValues);
            	
            	this.registerBeanDefinition(beanName, beandef);
            }
            
		} catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}

	@Override
	protected BeanDefinition GetCreatedBean(BeanDefinition beanDefinition) {
		
		try {
			// set BeanClass for BeanDefinition
			
			Class<?> beanClass = beanDefinition.getBeanClass();
			// set Bean Instance for BeanDefinition
			Object bean = beanClass.newInstance();	
			
			List<PropertyValue> fieldDefinitionList = beanDefinition.getPropertyValues().GetPropertyValues();
			for(PropertyValue propertyValue: fieldDefinitionList)
			{
				if(propertyValue.getRef()!=null)
					BeanUtil.invokeSetterMethod(bean, propertyValue.getName(),getBean(propertyValue.getRef()));
				else
				    BeanUtil.invokeSetterMethod(bean, propertyValue.getName(), propertyValue.getValue());
			}
			
			beanDefinition.setBean(bean);
			
			return beanDefinition;
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
