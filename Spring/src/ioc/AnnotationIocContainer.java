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

                Object bean = clazz.newInstance();  //����ͨ�����캯��ע��,��Ҫ�ڴ˴���

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
            //��ȡbean������������
        	try {  
                Field[] fields = o.getClass().getDeclaredFields();
                for(Field field : fields){  
                    if(field!=null && field.isAnnotationPresent(Autowired.class)){  
                    	
                        Object injectBean = null;  
                         
                            for(String key : beanMap.keySet()){  
                                //�жϵ�ǰ���������������Ƿ��������ļ��д���  
                                if(field.getType().isAssignableFrom(beanMap.get(key).getClass())){  
                                    //��ȡ����ƥ���ʵ������  
                                    injectBean = beanMap.get(key);  
                                    break;
                                }
                            }
                        

                        if(injectBean!=null){
                            //�������private�ֶ�  
                            field.setAccessible(true);  
                            //�����ö���ע������  
                            field.set(o, injectBean);  
                        }

                    }
                } 
            } catch (Exception e) {  
                e.printStackTrace();
            }  
    }


}
