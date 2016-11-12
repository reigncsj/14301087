package ioc;

import java.beans.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class AnnotationIocContainer {
	public void bind(String packageName,Map<String, Object> map,ArrayList<Bean> b ,ArrayList<String> auto) {

        try {
        	/*ClassDetector cd=new ClassDetector();
        	cd.getClasses("src");
            ArrayList<String> list = cd.getCom();
            ArrayList<String> list2 = cd.getAuto();
            */
        	Set<Class<?>> list=Scan.getClasses("test", 1);
        	Set<Class<?>> list1=Scan.getClasses("test", 2);
            
            for (Class<?> clazz : list) {

                Component beanAnnotation = clazz.getAnnotation(Component.class);

                System.out.println("class="+clazz.getName()+",bean_id="+beanAnnotation.value());

                Object bean = clazz.newInstance();  //如需通过构造函数注入,需要在此处理

                map.put(beanAnnotation.value(), bean);
                b.add(new Bean(beanAnnotation.value(),clazz.getName()));
            }

            for (Class<?> clazz : list1) {
            	Autowired aw=clazz.getAnnotation(Autowired.class);
            	auto.add(clazz.getName());
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
	
	public void processSetterAnnotation(Bean bean,Map<String, Object> beanMap){ 
        	

            Object o=beanMap.get(bean.getId());
            //获取bean的属性描述器
        	try {  
                Field[] fields = o.getClass().getDeclaredFields();
                for(Field field : fields){  
                    if(field!=null && field.isAnnotationPresent(Autowired.class)){  
                    	
                        Object injectBean = null;  
                         
                            for(String key : beanMap.keySet()){  
                                //判断当前属性所属的类型是否在配置文件中存在  
                                if(field.getType().isAssignableFrom(beanMap.get(key).getClass())){  
                                    //获取类型匹配的实例对象  
                                    injectBean = beanMap.get(key);  
                                    break;
                                }
                            }
                        

                        if(injectBean!=null){
                            //允许访问private字段  
                            field.setAccessible(true);  
                            //把引用对象注入属性  
                            field.set(o, injectBean);  
                        }

                    }
                } 
            } catch (Exception e) {  
                e.printStackTrace();
            }  
    }


}
