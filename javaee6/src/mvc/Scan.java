package mvc;


import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.beans.*;

public class Scan {
	
	public static void doScan(Map<String,Controllers> m1,Map<String,String> m2) {  
		  
        // ��һ��class��ļ���  
       
        // �Ƿ�ѭ������  
        boolean recursive = true;  
        // ��ȡ�������� �������滻  
        String packageName = "test";  
        String packageDirName = packageName.replace('.', '/');  
        // ����һ��ö�ٵļ��� ������ѭ�����������Ŀ¼�µ�things  
        Enumeration<URL> dirs;  
        try {  
            dirs = Thread.currentThread().getContextClassLoader().getResources(  
                    packageDirName);  
            // ѭ��������ȥ  
            while (dirs.hasMoreElements()) {  
                // ��ȡ��һ��Ԫ��  
                URL url = dirs.nextElement();  
                // �õ�Э�������  
                String protocol = url.getProtocol();  
                // ��������ļ�����ʽ�����ڷ�������  
                if ("file".equals(protocol)) {  
                    System.err.println("扫描数据");  
                    // ��ȡ��������·��  
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");  
                    // ���ļ��ķ�ʽɨ���������µ��ļ� ����ӵ�������  
                    findAndAddClassesInPackageByFile(packageName, filePath,  
                            recursive,m1,m2);  
                } 
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
	
	 public static void findAndAddClassesInPackageByFile(String packageName,  
	            String packagePath, final boolean recursive, Map<String,Controllers> m1,Map<String,String> m2) {  
	        // ��ȡ�˰���Ŀ¼ ����һ��File  
	        File dir = new File(packagePath);  
	        // ��������ڻ��� Ҳ����Ŀ¼��ֱ�ӷ���  
	        if (!dir.exists() || !dir.isDirectory()) {  
	            // log.warn("�û�������� " + packageName + " ��û���κ��ļ�");  
	            return;  
	        }  
	        // ������� �ͻ�ȡ���µ������ļ� ����Ŀ¼  
	        File[] dirfiles = dir.listFiles(new FileFilter() {  
	            // �Զ�����˹��� �������ѭ��(������Ŀ¼) ��������.class��β���ļ�(����õ�java���ļ�)  
	            public boolean accept(File file) {  
	                return (recursive && file.isDirectory())  
	                        || (file.getName().endsWith(".class"));  
	            }  
	        });  
	        // ѭ�������ļ�  
	        for (File file : dirfiles) {  
	            // �����Ŀ¼ �����ɨ��  
	            if (file.isDirectory()) {  
	                findAndAddClassesInPackageByFile(packageName + "."  
	                        + file.getName(), file.getAbsolutePath(), recursive,  
	                        m1,m2);  
	            } else {  
	                // �����java���ļ� ȥ�������.class ֻ��������  
	                String className = file.getName().substring(0,  
	                        file.getName().length() - 6);  
	                try {  
	                    // ��ӵ�������ȥ  
	                    //classes.add(Class.forName(packageName + '.' + className));  
	                                         //�����ظ�ͬѧ�����ѣ�������forName��һЩ���ã��ᴥ��static������û��ʹ��classLoader��load�ɾ�  
	                       Class<?> c=Class.forName(packageName + '.' + className);
	                	   if(c.isAnnotationPresent(Controller.class)){ 
	                		   Map<String ,String> map=new HashMap<String,String>();
	                		   Object o=c.newInstance();
	                	      // m1.put(packageName + '.' + className,c.newInstance());
							   Method[] m=c.getDeclaredMethods();
							   for(Method m3: m){
								   Annotation a=m3.getAnnotation(RequestMapping.class);
								   if(a!=null){
									   
									   RequestMapping r=(RequestMapping)a;
									   String v=r.value();
									   map.put(v, m3.getName());
									   m2.put(v, packageName + '.' + className);
								   }
							   }
							   m1.put(packageName + '.' + className, new Controllers(o,c,map));
	                	   }

	                     
					} 
	                catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}catch (ClassNotFoundException e) {  
	                    // log.error("����û��Զ�����ͼ����� �Ҳ��������.class�ļ�");  
	                    e.printStackTrace();  
	                }  
	            }  
	        }  
	    }  

}
